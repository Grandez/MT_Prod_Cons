package com.sdp.poc.threading.motor.core;

/**
 * Factoria
 *
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import javax.persistence.Entity;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import static com.sdp.poc.threading.motor.tools.Messages.NO_PROPS;

public class Factory implements AutoCloseable {
    private static Factory instance;

    private SessionFactory prodFactory;
    private SessionFactory consFactory;

    private Properties props = new Properties();

    public static Factory getInstance() {
        if (instance == null) instance = new Factory();
        return instance;
    }

    private Factory() {
        prodFactory = createProdConnections();
        consFactory = createConsConnections();
    }

    private SessionFactory createProdConnections() { return getSessionFactory(PROPS_PROD);  }
    private SessionFactory createConsConnections() { return getSessionFactory(PROPS_CONS);  }

    private SessionFactory getSessionFactory(String config) {
        Properties props = new Properties();
        ClassLoader loader = this.getClass().getClassLoader();
        InputStream input = loader.getResourceAsStream(config);
        if (input == null) {
            Logger.warn(config, NO_PROPS);
            input = loader.getResourceAsStream(PROPS_GRAL);
        }
        try {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Crear la configuración de Hibernate usando las propiedades cargadas
        Configuration conf = new Configuration();
        conf.setProperties(props);
        addEntities(conf, props);

        StandardServiceRegistryBuilder tmp = new StandardServiceRegistryBuilder();
        tmp = tmp.applySettings(conf.getProperties());
        ServiceRegistry reg = tmp.build();

        ServiceRegistry srv = new StandardServiceRegistryBuilder()
                .applySettings(conf.getProperties())
                .build();

        return conf.buildSessionFactory(srv);

    }
    public Session getProdSession() { return prodFactory.getCurrentSession(); }
    public Session getConsSession() { return consFactory.getCurrentSession(); }
    public Session newProdSession() { return prodFactory.openSession();       }
    public Session newConsSession() { return consFactory.openSession();       }

    public SessionFactory getConnection() {

        try {
            Properties props = new Properties();
            ClassLoader loader = this.getClass().getClassLoader();
            InputStream input = loader.getResourceAsStream("hibernate.properties");
            props.load(input);

            // Crear la configuración de Hibernate usando las propiedades cargadas
            Configuration conf = new Configuration();
            conf.setProperties(props);
            addEntities(conf, props);

            // Crear el ServiceRegistry
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(conf.getProperties())
                    .build();

            // Crear el SessionFactory
            return conf.buildSessionFactory(serviceRegistry);

        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            ex.printStackTrace();
            return null;
        }
    }
    private void addEntities(Configuration conf, Properties props) {
        int idx = 0;
        String str = props.getProperty("entities");
        if (str == null) return;
        if (str.trim().length() == 0) return;
        String[] toks = str.split(",");
        try {
            for (idx = 0; idx < toks.length; idx++) {
                Reflections reflections = new Reflections(toks[idx], new TypeAnnotationsScanner(), new SubTypesScanner(false));
                Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Entity.class);
                for (Class<?> clazz : classes) conf.addAnnotatedClass(clazz);
            }
        } catch (Exception ex) {
            System.err.println("El paquete " + toks[idx] + " parece que no le gusta");
        }
    }

    @Override
    public void close() {
        if (prodFactory != null) prodFactory.close();
        if (consFactory != null) consFactory.close();
    }
}
