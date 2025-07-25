package com.sdp.poc.threading.test.base;

public enum MSGTYPE {
     MSG(0x00)
    ,ERR(0x01)
    ,NFO(0x02)
    ,WAR(0x04)
    ;
    private final int value;

    MSGTYPE(int value) { this.value = value; }

    public int getValue() { return value; }

}
