package com.sdp.poc.threading.test.interfaces;

public interface IMTLogger {
    void info(String msg, Object ...args);
    void warn(String msg, Object ...args);
    void debug(String msg, Object ...args);
    void error(String msg, Object ...args);
    void log  (String msg, Object ...args);
}
