package com.sdp.poc.threading.base.logging;

public enum MSGTYPE {
     MSG(0x04)
    ,ERR(0x08)
    ,NFO(0x10)
    ,WAR(0x20)
    ;
    private final int value;

    MSGTYPE(int value) { this.value = value; }

    public int getValue() { return value; }

}
