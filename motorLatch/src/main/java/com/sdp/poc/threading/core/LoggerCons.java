package com.sdp.poc.threading.core;
/**
 * Generador de Logs
 * Lee de la cola y los escribe donde se decida
 * El proceso acabara cuando se reciba un mensaje tipo -1
 */

import com.sdp.poc.threading.base.logging.Color;
import com.sdp.poc.threading.config.CAMotor;
import com.sdp.poc.threading.interfaces.ILogger;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class LoggerCons implements ILogger {

    private final LinkedBlockingQueue<String> cola;
    private CountDownLatch latch;
    private int   level = 0x3F;
    private Color color;
    private PrintStream out = System.out;

    public LoggerCons() {
        this.cola = CAMotor.getInstance().getQLog();
        this.latch = latch;
        //level = ConfigFramework.getInstance().level;
    }
    @Override
    public void run() {
        long mark = 0L;

        try {
            while (mark >= 0 || mark < Long.MAX_VALUE) {
                String msg = cola.take();
                String[] toks = msg.split(CAMotor.TOK);
                mark = Long.parseLong(toks[0]);
                if (mark < 0 || mark == Long.MAX_VALUE) break;
                int type = (int) mark;

                String[] vars = Arrays.copyOfRange(toks, 2, toks.length);
                switch (type &  level) {
                    case 0x01: out = System.err;
                               color = Color.RED_BOLD;
                               break;
                    case 0x02: color = Color.YELLOW_BOLD; break;
                    case 0x04: color = Color.BLUE_BOLD;   break;
                    case 0x08: color = Color.GREEN_BOLD;  break;
                    case 0x10: color = Color.BLACK_BOLD;  break;
                    case 0x20: color = Color.RESET;       break;
                    default: continue;
                }
                print(toks[1], vars);
            }
        } catch (InterruptedException e) {
            System.out.println("Excepcion del logger");
            Thread.currentThread().interrupt();
        }
    }
    private void print(String fmt, Object... args) {
        String msg = String.format(fmt, args);
        out.println(color.getColor() + getPrfx() + msg + Color.RESET.getColor());
    }
    private static String getPrfx() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date()) + " - ";
    }
}
