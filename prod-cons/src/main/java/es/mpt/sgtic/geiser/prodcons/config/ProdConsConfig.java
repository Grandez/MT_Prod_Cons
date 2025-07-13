package es.mpt.sgtic.geiser.prodcons.config;

/**
 * Mantiene la informacion de configuracion y datos de ejecucion
 * de un proceso Productor/Consumidor
 *
 * Puesto que puede haber varios procesos diferentes ejecutandose
 * concurrentemente, y para no andar pasando la configuracion
 *
 * Esta clase tiene un mapa estatico de cada una de las configuraciones
 * en ejecucion identificadas por CommonCronConstantes
 *
 * Asi cada hilo coge su configuracion correcta
 * Notese que la configuracion es de solo lectura una vez cargada
 */

import es.mpt.sgtic.geiser.prodcons.interfaces.IProdConsConfig;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class ProdConsConfig extends Properties {
    private static HashMap<String, IProdConsConfig> configs = new HashMap<>();

    public  static final long   END_NORMAL = Long.MAX_VALUE; // Acabar lo pendiente
    public  static final long   END_INT    = -1l;            // Acabar lo actual
    public  static final String ENDS       = String.valueOf(Long.MAX_VALUE);

    public  static final String SEP  = ":";  // Separador de valores en CLP
    public  static final String TOK  = ";";  // Separador de campos en mensajes

    // Valores de la tabla de configuracion
    public static String LOTE  = "lote";
    public static String CHUNK = "chunk";
    public static String HILOS = "hilos";

    // Loader degenera a partir de 750
    public int lote      =  500;  // Transaccion Chunk (5 -> 3x -> 10 -> 1.5x -> 15)
    public int chunk     =   50;  // Transaccion Chunk (5 -> 3x -> 10 -> 1.5x -> 15)
    public int threads   =   10;  // Hilos (si cero dinamico)
    public int mode      =    2;  // Agresividad para la CPU
    public int timer     =   -1;  // Maximo tiempo ejecucion en minutos
    public String label  = null;
    public String cron   = null;

    public String logFile = "cron";
    private Long  begin;

    private HashMap<Long, Object> beans = new HashMap<>();
    // Para runnables
    // public CountDownLatch latch;

    public static IProdConsConfig setConfig(String process, IProdConsConfig config) {
        configs.put(process, config);
        return config;
    }
    public static IProdConsConfig getConfig(String process) {
        return configs.get(process);
    }
    public static IProdConsConfig delConfig(String process) {
        return configs.remove(process);
    }

    public PriorityBlockingQueue<Long> qdat = new PriorityBlockingQueue<>();
    public LinkedBlockingQueue<Object> qlog = new LinkedBlockingQueue<>();
    protected ProdConsConfig() { begin = System.currentTimeMillis();
    }
    protected ProdConsConfig(IProdConsConfig props, boolean reset) {
        begin = System.currentTimeMillis();
        loadProps(props);
    }

    public void   putTask(Long id)              { this.getQueue().put(id); }
    public void   putBean(Long id, Object bean) { beans.put(id, bean);     }
    public Object getBean(Long id)              { return beans.remove(id); }

    public void    setLogFile(String name) { logFile = name; }
    public String  getLogFile()            { return logFile; }
    public String  getIdTabla()            { return label; }

    public PriorityBlockingQueue<Long> getQueue() { return qdat; }
    public LinkedBlockingQueue<Object> getQLog()  { return qlog; }

    public void setLote (String valor) {
        try {
            this.lote = Integer.parseInt(valor);
        } catch (NumberFormatException ex) {}
    }
    public void setChunk (String valor) {
        try {
            this.chunk = Integer.parseInt(valor);
        } catch (NumberFormatException ex) {}
    }
    public void setHilos (String valor) {
        try {
            this.threads = Integer.parseInt(valor);
        } catch (NumberFormatException ex) {}
    }

/*
    public String getSummary() {
        StringBuilder str = new StringBuilder();
        str.append("Leidos ").append(getRead());
        str.append(TOK).append("Procesados: ").append(getWrite());
        str.append(TOK).append("Elapsed: ").append(getElapsed());
        return str.toString();
    }
    public void printElapsed() {
        QLogger.elapsed(getElapsed());
    }
*/
  protected void loadProps(IProdConsConfig props) {
//      this.props = props;
      begin      = System.currentTimeMillis();
      threads    = props.getThreads();
      timer      = props.getTimer();
      chunk      = props.getChunk();
      label      = props.getIdTabla();
      cron       = props.getCronExpresion();

      ajustaTimer(timer);
  }
  /*
   * Por seguridad
   * Si la tarea se ejecuta cada x minutos
   * Garantizar que finaliza antes
   */
  private void ajustaTimer (int valor) {
      if (valor >= 0) return;
      if (cron == null) return;
      String[] toks = cron.split(" ");
      for (int i = 2; i < 6; i++) {
          if (toks[i].compareTo("*") == 0) return;
          if (toks[i].compareTo("?") == 0) return;
      }
      toks = toks[1].split("/");
      try {
          int minutos = Integer.parseInt(toks[1]);
          if (minutos > 0) timer = (minutos * 60) - 10; // 10 segundos de margen
      } catch (NumberFormatException ex) {
          // Nada
      }

  }
}
