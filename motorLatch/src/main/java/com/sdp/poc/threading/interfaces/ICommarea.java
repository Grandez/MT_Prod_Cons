package com.sdp.poc.threading.interfaces;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public interface ICommarea {
    public int getNumThreads();
    public int getTimeout   ();

    public PriorityBlockingQueue<Long> getQueue();

    public void setNumThreads(int threads);
    public void setTimeout   (int timeout);
}
