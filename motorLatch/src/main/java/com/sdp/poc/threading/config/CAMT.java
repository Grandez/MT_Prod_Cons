package com.sdp.poc.threading.config;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class CAMT {
    String name = "motor";
    int threads = 1;
    int timeout = 0;
    int chunk = 0;

    public  static final long   ENDT = Long.MAX_VALUE; // Acabar lo pendiente
    public  static final long   ENDI = -1l;            // Acabar lo actual
    public  static final String ENDS = String.valueOf(Long.MAX_VALUE);

    public  static final String SEP  = ":";  // Separador de valores en CLP
    public  static final String TOK  = ";";  // Separador de campos en mensajes

    public static PriorityBlockingQueue<Long> qdat = new PriorityBlockingQueue<Long>();
    public static LinkedBlockingQueue<String> qlog = new LinkedBlockingQueue<String>();

    private Properties customProps = new Properties();

    private CAMT() {}
    private static class ConfigInner      {
        private static final CAMT INSTANCE = new CAMT();
    }
    public  static CAMT getInstance()       { return CAMT.ConfigInner.INSTANCE; }
    public  static CAMT getCustomInstance() {
        return CAMT.ConfigInner.INSTANCE;
    }

    public void setNumThreads(int threads) { this.threads = threads; }
    public void setTimeout   (int timeout) { this.timeout = timeout; }
    public void setChunk     (int chunk)   { this.chunk   = chunk; }

    public int getNumThreads() { return threads; }
    public int getTimeout   () { return timeout; }
    public int getChunk     () { return chunk;   }

    public Properties getCustomProps() {return customProps; }
    public PriorityBlockingQueue<Long> getQueue() { return qdat; }
    public void setCustomProps(Properties props) { this.customProps = props; }
}
