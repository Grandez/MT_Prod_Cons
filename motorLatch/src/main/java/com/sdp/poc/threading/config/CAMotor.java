package com.sdp.poc.threading.config;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class CAMotor {
    String name = "motor";
    int threads;
    int timeout = 0;
    int chunk = 0;

    public  static final long   ENDT = Long.MAX_VALUE; // Acabar lo pendiente
    public  static final long   ENDI = -1l;            // Acabar lo actual
    public  static final String ENDS = String.valueOf(Long.MAX_VALUE);

    public  static final String SEP  = ":";  // Separador de valores en CLP
    public  static final String TOK  = ";";  // Separador de campos en mensajes

    public PriorityBlockingQueue<Long> qdat = new PriorityBlockingQueue<Long>();
//    public LinkedBlockingQueue<String> qlog = new LinkedBlockingQueue<>();

    private Properties customProps = new Properties();

    private CAMotor() {}
    private static class ConfigInner      { private static final CAMotor INSTANCE = new CAMotor(); }
    public  static CAMotor getInstance() { return CAMotor.ConfigInner.INSTANCE; }

    public void setNumThreads(int threads) { this.threads = threads; }
    public void setTimeout   (int timeout) { this.timeout = timeout; }
    public void setCustomProps(Properties props) { this.customProps = props; }

    public int getNumThreads() { return threads; }
    public int getTimeout   () { return timeout; }
    public Properties getCustomProps() {return customProps; }
    public PriorityBlockingQueue<Long> getQueue() { return qdat; }
//    public LinkedBlockingQueue<String> getQLog() { return qlog; }

}
