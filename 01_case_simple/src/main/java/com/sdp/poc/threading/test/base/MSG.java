package com.sdp.poc.threading.test.base;

public enum MSG {
    SUMMARY("SMR02000", "%ld;%ld;%d;%d;%d")
    ;

    private final String code;
    private final String fmt;

    MSG(String code, String fmt) {
        this.code = code;
        this.fmt = fmt;
    }

    public String getCode() { return code; }
    public String getFormat() { return fmt; }
}
