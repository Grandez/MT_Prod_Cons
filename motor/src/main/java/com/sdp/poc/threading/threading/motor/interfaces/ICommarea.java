package com.sdp.poc.threading.threading.motor.interfaces;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public interface ICommarea {
    public  static final long   ENDT = Long.MAX_VALUE; // Acabar lo pendiente
    public  static final long   ENDI = -1l;            // Acabar lo actual
    public  static final String ENDS = String.valueOf(Long.MAX_VALUE);

    public int getNumThreads();
    public int getTimeout   ();

}
