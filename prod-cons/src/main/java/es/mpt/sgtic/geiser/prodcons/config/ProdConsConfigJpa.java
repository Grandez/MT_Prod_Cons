package es.mpt.sgtic.geiser.prod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class ProdConsConfigJpa {

    /*
     * Estos son los paquetes que se han tenido que incorporar para que el sistema simplemente se ejecute
     * reconociendo las entidades
     * Seria interesante por rendimiento separar:
     *  - Las entidades comunes
     *  - Las entidades cron
     */
    private String[] paquetes = {
             "es.mpt.sgtic.geiser.core.entity"
            ,"es.mpt.sgtic.geiser.core.entity.registro"
            ,"es.mpt.sgtic.geiser.core.entity.registro.raw"
            ,"es.mpt.sgtic.geiser.core.entity.rgeco"
            ,"es.mpt.sgtic.geiser.corecomun.ambito.entity"
            ,"es.mpt.sgtic.geiser.coreeell.entity"
            ,"es.mpt.sgtic.geiser.corecomun.gestionusuarios.entity"
            ,"es.mpt.sgtic.geiser.corecomun.notificacion.entity"
            ,"es.mpt.sgtic.geiser.corecomun.bloqueos.entity"
            ,"es.mpt.sgtic.geiser.corecomun.entorno.entity"
            ,"es.mpt.sgtic.geiser.corecomun.sia.entity"
    };
    // Inyección de JNDI (puedes ajustar el nombre del recurso JNDI)
    @Bean
    public DataSource dataSource() throws NamingException {
        Context context = new InitialContext();
        return (DataSource) context.lookup("java:comp/env/jdbc/MptGeiser");
    }

    // Bean para configurar el EntityManagerFactory
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);  // Usar el DataSource obtenido via JNDI
        factory.setPackagesToScan(paquetes);

        // Configuración de Hibernate y JPA
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factory.setJpaVendorAdapter(vendorAdapter);
        Properties jpaProperties = setProperties();
        factory.setJpaProperties(jpaProperties);

        return factory;
    }

    // Bean para la gestión de transacciones
    @Bean(name = "txManagerThreads")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Properties setProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        jpaProperties.put("hibernate.connection.autoReconnect", "true");
        jpaProperties.put("hibernate.connection.autoReconnectForPools", "true");
        jpaProperties.put("hibernate.connection.autocommit", "true");
        jpaProperties.put("hibernate.jdbc.fetch_size", "200");
        jpaProperties.put("hibernate.jdbc.timeout", "300000");
        jpaProperties.put("hibernate.cache.use_second_level_cache", "false");
        /*
			<property name="hibernate.connection.useUnicode" value="true" />
			<property name="hibernate.connection.characterEncoding" value="UTF-8" />
			<!-- Relacionado con trazas -->
			<property name="hibernate.show_sql" value="false" />
			<property name="hibernate.format_sql" value="true" />
			<property name="hibernate.use_sql_comments" value="true" />
			<property name="hibernate.generate_statistics" value="false" />
			<property name="hibernate.type" value="trace" />

			<!-- Configuración de la caché de planes de query -->
			<property name="hibernate.query.plan_cache_max_size" value="256" />
			<property name="hibernate.query.plan_parameter_metadata_max_size" value="128" />
        */
        return jpaProperties;
    }
}
