package es.mpt.sgtic.geiser.tools.libreria.base;


import es.mpt.sgtic.geiser.framework.core.ConfigFramework;

public class Config extends ConfigFramework {
    private static Config instance;
    public String path = "c:/nas"; // Para pruebas
    public long[] ids = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private long id = 0;

    public int action    =    2;  // 1 - Migrar 2 - Cargar 3 - XML
    public int registros =  10; // 500;

    /*

        private static Config instance;

        private Long   begin;
        private int    verbose = 0;

        // private String driver = "com.mysql.cj.jdbc.Driver";
        // private String driver = "oracle.jdbc.OracleDriver";
    */
    private Config() {
        super();
    }

    public static Config getInstance() {
        if (instance == null) { instance = new Config(); }
        return instance;
    }
    public void manageConnections() {
         super.manageConnections();
         System.out.println("Conexiones");
    }
    // Para el contador usamos uno unico
    public synchronized long inc(int idx) {
        //ids[idx]++;
        //return ids[idx];
        return ++id;
    }
    public synchronized void set(long id) { this.id = id; }
    public synchronized long get()        { return id;    }

    /*
     * Genera en stdout la informacion de configuracion del proceso
     * Usable para generar un fichero de configuracion
     */
    public void printConfig() {
        super.printConfig();
        ppsc("path",      path,"Ruta de archivos XML");
        ppnc("registros", registros,"Registros a cargar en miles");
    }

    public void printSummary() {
        super.printSummary();
        super.printElapsed();
    }

}
/*
    Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                properties.put("hibernate.hbm2ddl.auto", "update");

                // Configuración de la conexión a la base de datos
                properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mi_base_de_datos");
                properties.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");

                // Crear el EntityManagerFactory sin utilizar persistence.xml
                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myJpaUnit", properties);

                // Crear el EntityManager
                EntityManager entityManager = entityManagerFactory.createEntityManager();

                // Comenzar la transacción
                entityManager.getTransaction().begin();

                // Operaciones con la base de datos
                MyEntity entity = new MyEntity();
                entity.setId(1L);
                entity.setName("Ejemplo sin persistence.xml");
                entityManager.persist(entity);

                // Confirmar la transacción
                entityManager.getTransaction().commit();

                // Cerrar el EntityManager
                entityManager.close();

                // Cerrar la EntityManagerFactory
                entityManagerFactory.close();
                }

 */