/**
 * Simplemente pone los mensajes en la cola
 * si procede en funcion del tipo de mensaje y de su nivel
 */
package com.sdp.poc.threading.base.logging;

import com.sdp.poc.threading.base.config.CtxBase;
import com.sdp.poc.threading.base.mask.RC;

public class QLoggerProd extends QLoggerBase{
    CtxBase ca;
    public QLoggerProd(CtxBase ca) {
        this.ca = ca;
    }
    // Se escribe siempre
    public void msg(String code, Object ... parms) {
        writeMessage(code, parms);
    }
    public void info(int level, String code, Object ... parms) {
        if (checkLevel(MSGTYPE.NFO, level)) writeMessage(code, parms);
    }
    public void warn(int level, String code, Object ... parms) {
        ca.rc |= RC.WARNING;
        if (checkLevel(MSGTYPE.NFO, level)) writeMessage(code, parms);
    }

    private boolean checkLevel(MSGTYPE type, int level) {
        return true;
    }
    private void writeMessage(String code, Object ... parms) {
        StringBuffer head = mountHeader();
        StringBuffer buff = new StringBuffer();
        buff.append(System.currentTimeMillis()).append(';');
        buff.append(head).append(';').append(code);
        for (int i = 0; i < parms.length; i++) buff.append(';').append(parms[i]);
        try {
            qlog.put(buff.toString());
        } catch (Exception e) {
            // Do nothing
            System.err.println("Dev: check");
        }

    }

    /**
     * Monta la parte de cabecera del mensaje
     * @return el buffer con la cabecera
     */
    private StringBuffer mountHeader() {
        StringBuffer buff = new StringBuffer(512);
        buff.append(ca.getAppName());
        buff.append(";").append(ca.getPid());
        buff.append(';').append(Thread.currentThread().getName());

        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StackTraceElement item = stack[4];
        buff.append(";").append(item.getClassName());
        buff.append(";").append(item.getMethodName());
        buff.append(";").append(item.getLineNumber());
        return buff;
    }
}
