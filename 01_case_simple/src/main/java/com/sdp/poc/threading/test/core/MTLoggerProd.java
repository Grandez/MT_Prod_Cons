/**
 * Simplemente pone los mensajes en la cola
 * si procede
 */
package com.sdp.poc.threading.test.core;

import com.sdp.poc.threading.test.base.MSG;
import com.sdp.poc.threading.test.base.MSGTYPE;

import java.util.concurrent.LinkedBlockingQueue;

public class MTLoggerProd  {
    private final LinkedBlockingQueue<String> cola;
    private MTEnv env;
    public MTLoggerProd(MTEnv env) {
        this.cola = env.getQLog();
        this.env  = env;
    }
    // Se escribe siempre
    public void msg(MSG msg, Object ... parms) {
          write(MSGTYPE.MSG, msg, parms);
    }
    private void write(MSGTYPE type, MSG msg, Object ... parms) {
        StringBuffer buff = new StringBuffer();
        buff.append(String.format("%d;%d;%s;%s"
                                  ,type.getValue()
                                  ,System.currentTimeMillis()
                                  ,env.getAppName()
                                  ,msg.getCode()));
        for (int i = 0; i < parms.length; i++) {
            buff.append(';');
            buff.append(parms[i]);
        }
        try {
            cola.put(buff.toString());
        } catch (InterruptedException e) {
            // Do nothing
        }
    }
}
