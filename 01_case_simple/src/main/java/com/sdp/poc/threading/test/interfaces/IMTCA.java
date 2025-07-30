package com.sdp.poc.threading.test.interfaces;

import com.sdp.poc.threading.base.interfaces.ICABase;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;

public interface IMTCA extends ICABase {
    Properties getCustomProps();
    Integer    getNumThreads();
    Integer    getTimeout();
    Integer    getChunk();

    void setNumThreads(int threads);
    void setTimeout   (int timeout);
    void setChunk     (int chunk);

    CountDownLatch              getLatch();
    PriorityBlockingQueue<Long> getQueue();
}
