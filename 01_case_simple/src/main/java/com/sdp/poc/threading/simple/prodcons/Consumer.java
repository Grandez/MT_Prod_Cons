package com.sdp.poc.threading.simple.prodcons;

import com.sdp.poc.threading.base.msg.QLogger;
import com.sdp.poc.threading.simple.base.CA;
import com.sdp.poc.threading.interfaces.IConsumer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Consumer implements IConsumer {
    @Override
    public void run() {
        SimpleDateFormat sdp = new SimpleDateFormat("HH:mm:ss");
        CA ca =  CA.getInstance();
//        QLogger qlog = ca.getQLog();

        try {
            while (true) {
                Long msg = ca.getQueue().take();
                if (msg < 0 || msg == Long.MAX_VALUE) break;
                System.out.println(sdp.format(new Date()) + " - " + Thread.currentThread().getName() + " - mensaje " + msg);
            }
        } catch (InterruptedException e) {
            QLogger.info(" Interrumpido");
        } catch (Exception e) {
            System.err.println("Excepcion no controlada");
            System.err.println(e.getLocalizedMessage());
        }

    }
}
