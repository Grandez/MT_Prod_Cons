package com.sdp.poc.threading.config;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class Commarea {
    private CAMotor ca;

    protected Commarea() {
        ca = CAMotor.getInstance();
    }
    public int getNumThreads()                    { return ca.getNumThreads(); }
    public int getTimeout()                       { return ca.getTimeout();    }
    public PriorityBlockingQueue<Long> getQueue() { return ca.getQueue();  }
    public LinkedBlockingQueue<String> getQLog()  { return ca.getQLog();   }
    public CAMotor getCAMotor()                   { return ca;            }

    public void setNumThreads(int numThreads)     { ca.setNumThreads(numThreads); }
    public void setTimeout   (int timeout)        { ca.setTimeout(timeout); }
    public void setCustomConfiguration(Properties props) {
      ca.setCustomProps(props);
    }
}
