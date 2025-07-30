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
    long getRead ();
    long getWrite();
    long getErr  ();

}
