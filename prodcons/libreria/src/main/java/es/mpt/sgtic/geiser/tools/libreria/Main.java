package es.mpt.sgtic.geiser.tools.libreria;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import es.mpt.sgtic.geiser.framework.core.Motor;
import es.mpt.sgtic.geiser.framework.exceptions.ProdConException;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;
import es.mpt.sgtic.geiser.tools.libreria.processes.loader.LoaderCons;
import es.mpt.sgtic.geiser.tools.libreria.processes.loader.LoaderProd;
import es.mpt.sgtic.geiser.tools.libreria.tools.PARMS;
import es.mpt.sgtic.geiser.tools.libreria.tools.Parameters;
import es.mpt.sgtic.geiser.tools.libreria.entities.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.logging.Level;

public class Main {

    private Config cfg;

    public static void main( String[] args )     {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Main app = new Main();
        app.run(args);
        System.exit(0);
    }
    private void run(String[] args) {
        // Este caso concreto de programa incorpora 3 programas diferentes
        // Indexados por cfg.acction
//        Class[] prods = {null, Productor.class, LoaderProd.class, XMLProd.class};
//        Class[] cons  = {null, Consumidor.class, LoaderProd.class, XMLProd.class};
        Class[] prods = {null, null, LoaderProd.class, null};
        Class[] cons  = {null, null, LoaderCons.class, null};

        Motor motor = new Motor();
        try {
            prepareEnvironment(args);
            motor.run(prods[cfg.action], cons[cfg.action]);
        } catch (ProdConException e) {
            throw new RuntimeException(e);
        }
//        finally {
//            cfg.em.close();
//            cfg.emf.close();
//        }
        cfg.printSummary();

        System.exit(0);
    }

    private void prepareEnvironment(String[] args) throws ProdConException {
        cfg = Config.getInstance();
        Parameters parms = new Parameters(PARMS.values());
        parms.processArgs(args);
        cfg.manageConnections();
    }
    // Comenzar la transacción
    private void connect2DB() {
        Configuration configuration = new Configuration();

        // Usamos el archivo properties
        configuration.configure("hibernate.properties");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        cfg.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
 /*
        BBDDPool();
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.show_sql", "true");

        // Configurar el DataSource para Hibernate
        configuration.setProperty("hibernate.c3p0.dataSource", cfg.DS.toString());
        configuration.setProperty("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        addEntities(configuration);
        cfg.session = configuration.buildSessionFactory();;
*/
        // Crear el SessionFactory
//        SessionFactory sessionFactory = configuration.buildSessionFactory();

//        cfg.emf = Persistence.createEntityManagerFactory("prod_con");
//        cfg.em = cfg.emf.createEntityManager();

//        DataSource ds = cfg.emf.unwrap(DataSource.class);
//        System.out.println("Usado DataSource: " + ds.getClass().getName());
/*
        Properties props = cfg.getConnectionInfo();
        Configuration configuration = new Configuration();
        configuration.setProperties(props);

        addEntities(configuration);

        cfg.emf = configuration.buildSessionFactory();
        // emf = Persistence.createEntityManagerFactory("xml2bbdd", props);
        cfg.em = cfg.emf.createEntityManager();

 */
    }

    private void addEntities(Configuration configuration) {
        configuration.addAnnotatedClass(AsientoNew.class);
        configuration.addAnnotatedClass(AsientoOld.class);
        configuration.addAnnotatedClass(AnexoNew.class);
        configuration.addAnnotatedClass(AnexoOld.class);
        configuration.addAnnotatedClass(InteresadoNew.class);
        configuration.addAnnotatedClass(InteresadoOld.class);
        configuration.addAnnotatedClass(EstadoAsientoNew.class);
        configuration.addAnnotatedClass(EstadoAsientoOld.class);
    }

/*
    private void printSummary() {
        int hours = 0;
        long elapsed = (System.currentTimeMillis() - begin) / 1000;
        long seconds = elapsed % 60;
        long minutes = elapsed / 60;
        if (minutes > 60) {
            hours = (int) minutes / 60;
            minutes = minutes % 60;
        }

        System.out.printf("Ficheros procesados: %8d", count);
        System.out.printf("Ficheros erroneos:   %8d", errs);
        System.out.printf("Tiempo:              %02d:%02d:%02d", hours, minutes, seconds);
    }
*/
    private void BBDDPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mariadb://localhost:4406/GEISER");
        config.setUsername("root");
        config.setPassword("jgg");
        config.setMaximumPoolSize(25);  // Número máximo de conexiones simultáneas
        cfg.DS = new HikariDataSource(config);
    }
}
/*
    Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                properties.put("hibernate.hbm2ddl.auto", "update");
                properties.put("hibernate.show_sql", "true");
                properties.put("hibernate.format_sql", "true");

                // Configuración de la conexión a la base de datos
                properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mi_base_de_datos");
                properties.put("javax.persistence.jdbc.user", "root");
                properties.put("javax.persistence.jdbc.password", "mi_contraseña");
                properties.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
*/