package com.sdp.poc.threading.base.interfaces;

public interface ICABase {
    void read();
    void write();
    void err();
    void setRead (long v);
    void setWrite(long v);
    void setErr  (long v);
    void addRead (long v);
    void addWrite(long v);
    void addErr  (long v);
    long getBeg  ();
    long getRead ();
    long getWrite();
    long getErr  ();
    int getRC();
    long getInput();
    long getOutput();
    long getErrors();

    void setNumThreads(int threads);
    void setTimeout   (int timeout);
    void setChunk     (int chunk);

    int getNumThreads();
    int getTimeout   ();
    int getChunk     ();

}
