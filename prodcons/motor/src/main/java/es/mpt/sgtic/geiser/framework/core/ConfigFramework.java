package es.mpt.sgtic.geiser.framework.core;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.*;

public class ConfigFramework<T> {
    public  static final long   ENDT = Long.MAX_VALUE; // Acabar lo pendiente
    public  static final long   ENDI = -1l;            // Acabar lo actual
    public  static final String ENDS = String.valueOf(Long.MAX_VALUE);

    public  static final String SEP  = ":";  // Separador de valores en CLP
    public  static final String TOK  = ";";  // Separador de campos en mensajes

    public String preffix = "JGG";
    // Loader degenera a partir de 750
    public int chunk     =  500;  // Transaccion Chunk (5 -> 3x -> 10 -> 1.5x -> 15)
    public int threads   =    5;  // Hilos (si cero dinamico)
    public int mode      =    2;  // Agresividad para la CPU
    public int timer     =    0;  // Maximo tiempo ejecucion en minutos

    public int check = 0; // Mostrar configuracion y volumen

    public String host   = "localhost";
    public int    port   = 3306;
    public String schema = "GEISER";
    public int    driver = 1;

    public String user   = "user";
    public String pwd    = "pwd";

    public int level = 0x3F;
    private Long   begin;

    private static ConfigFramework<?> instance;

    // public ArrayBlockingQueue<Long>    qdat = new ArrayBlockingQueue<>(16392);
    public PriorityBlockingQueue<T> qdat = new PriorityBlockingQueue<T>();
    public LinkedBlockingQueue<String> qlog = new LinkedBlockingQueue<>();
//    public EntityManagerFactory emf;
//    public EntityManager em;
    public HikariDataSource DS;
    public SessionFactory sessionFactory;
    private Properties props = new Properties();

    // Contadores

    private int read  = 0;
    private int items = 0;
    private int write = 0;

    protected ConfigFramework(T obj) {
        begin = System.currentTimeMillis();
    }


    public static synchronized  <T> ConfigFramework getInstance(T obj) {
        if (instance == null) instance = new ConfigFramework<>(obj);
        return (ConfigFramework<T>) instance;
    }
    public Session newSession() {
        return sessionFactory.openSession();
    }
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void setParm(String name, String value) {
        props.put(name.toUpperCase(), value);
    }
    public void setParms(Properties parms) {
        for (Object key : parms.keySet()) {
            props.put(((String) key).toUpperCase(), parms.get(key));
        }
    }
    public String getElapsed() {
        long amount = System.currentTimeMillis() - begin;
        int ms = (int) amount % 1000;
        amount /= 1000;
        int ss = (int) amount % 60;
        amount -= ss;
        amount = (int) amount / 60;
        int min = (int) amount % 60;
        int hour = (int) ((amount - min) / 60);
        return String.format("%1d:%02d:%02d.%03d", hour, min, ss, ms);
    }
    public Connection getConnection() throws SQLException {
        Connection connx = DS.getConnection();
        connx.setAutoCommit(false);
        return connx;
    }
    protected void printConfig() {
        pfsn("# General");
        ppnn("check", -1);
        ppsnc("preffix", preffix, "Prefijo de variables de entorno sin subrayado");

        pfsn("# Motor");
        ppnn("threads", threads);
        ppnn("chunk",   chunk);
        ppn ("mode", mode);
        switch (mode) {
            case 1: pfsn(" # Conservador"); break;
            case 2: pfsn(" # Normal");      break;
            case 3: pfsn(" # Agresivo");    break;
            default: ppn("");
        }
        pfsn("# Base de datos");
        ppsn("host",  host);
        ppnn("port", port);
        ppsn("schema", schema);
        ppsn("user", user + ":" + pwd);
    }
    protected void pfsn(String fmt, Object... args) {
        System.out.println(String.format(fmt, args));
    }
    protected void ppnn(String name, long value) {
        System.out.println(String.format("%s\t= %6d",name, value));
    }
    protected void ppnc(String name, long value, String cmt) {
        System.out.println(String.format("%s\t= %6d\t #%s",name, value, cmt));
    }
    protected void pps(String name, String value) {
        System.out.print(String.format("%s\t= %s",name, value));
    }
    protected void ppsn(String name, String value) {
        System.out.println(String.format("%s\t= %s",name, value));
    }
    protected void ppsnc(String name, String value, String cmt) {
        System.out.println(String.format("%s\t= %s\t #%s",name, value, cmt));
    }
    protected void ppsc(String name, String value, String cmt) {
        System.out.println(String.format("%s\t= %s\t#%s",name, value, cmt));
    }
    protected void ppn(String name, long value) {
        System.out.print(String.format("%s\t= %6d",name, value));
    }

    protected void ppn(Object... args) {
        System.out.println(String.format("%s\t= %s",args));
    }

    // Para el contador usamos uno unico
    public synchronized void read()  { read++;  }
    public synchronized void write() { write++; }
    public synchronized void item()  { items++; }

    public synchronized int getRead()   { return read;   }
    public synchronized int getWrite()  { return write;  }
    public synchronized int getItems()  { return items;  }

    public void printSummary() {
        Logger.summary("Leidos",     getRead());
        Logger.summary("Procesados", getItems());
        Logger.summary("Escritos",   getWrite());
        Logger.elapsed(getElapsed());
    }
    public void printElapsed() {
        Logger.elapsed(getElapsed());
    }
    public void manageConnections() {

        try {
            Properties props = new Properties();
            ClassLoader loader = this.getClass().getClassLoader();
            InputStream input = loader.getResourceAsStream("hibernate.properties");
            props.load(input);

                // Crear la configuración de Hibernate usando las propiedades cargadas
            Configuration conf = new Configuration();
            conf.setProperties(props);
//            addEntities(conf, props);

            // Crear el ServiceRegistry
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(conf.getProperties())
                    .build();

            // Crear el SessionFactory
            sessionFactory = conf.buildSessionFactory(serviceRegistry);

        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
/*
        Configuration configuration = new Configuration();

        // Usamos el archivo properties
        configuration.configure("hibernate.properties");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

 */
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
        // Crear el SessionFactory
//        SessionFactory sessionFactory = configuration.buildSessionFactory();

//        cfg.emf = Persistence.createEntityManagerFactory("prod_con");
//        cfg.em = cfg.emf.createEntityManager();

//        DataSource ds = cfg.emf.unwrap(DataSource.class);
//        System.out.println("Usado DataSource: " + ds.getClass().getName());

 */
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
/*
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
*/
/*
    public static void main(String[] args) {
        // Especifica el paquete que deseas explorar
        Reflections reflections = new Reflections("com.example"); // Cambia "com.example" por el paquete que deseas explorar

        // Obtén todas las clases del paquete
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

        // Muestra las clases encontradas
        for (Class<?> clazz : classes) {
            System.out.println(clazz.getName());
        }
    }
 */
}
