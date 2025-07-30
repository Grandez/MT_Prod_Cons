package com.sdp.poc.threading.base.logging;

public class MSG {
    private final String code;
    private final String fmt;
    private final String desc;

    MSG(String code, String fmt, String desc) {
        this.code = code;
        this.fmt = fmt;
        this.desc = desc;
    }

    public String getCode() { return code; }
    public String getFormat() { return fmt; }
}
