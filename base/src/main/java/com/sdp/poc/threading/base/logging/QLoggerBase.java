package com.sdp.poc.threading.base.logging;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class QLoggerBase {
    protected final String SEP_TOK = ":";
    protected static LinkedBlockingQueue<String> qlog = new LinkedBlockingQueue<>();
}
