package es.mpt.sgtic.geiser.framework.core;
/**
 * Generador de Logs
 * Lee de la cola y los escribe donde se decida
 * El proceso acabara cuando se reciba un mensaje tipo -1
 */

import es.mpt.sgtic.geiser.framework.interfaces.ILogger;
import es.mpt.sgtic.geiser.framework.base.Color;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import static es.mpt.sgtic.geiser.framework.base.Color.*;

public class LoggerCons implements ILogger {

    private final LinkedBlockingQueue<String> cola;
    private CountDownLatch latch;
    private int   level = 0x3F;
    private Color color = Color.RESET;

    private PrintStream out = System.out;

    public LoggerCons() {
        this.cola = ConfigFramework.getInstance().qlog;
        this.latch = latch;
        level = ConfigFramework.getInstance().level;
    }
    @Override
    public void run() {
        long mark = 0L;
        try {
            while (mark >= 0 || mark < Long.MAX_VALUE) {
                String msg = cola.take();
                String[] toks = msg.split(ConfigFramework.TOK);
                mark = Long.parseLong(toks[0]);
                if (mark < 0 || mark == Long.MAX_VALUE) break;
                int type = (int) mark;

                String[] vars = Arrays.copyOfRange(toks, 2, toks.length);
                switch (type &  level) {
                    case 0x01: out = System.err;
                               color = RED_BOLD;
                               break;
                    case 0x02: color = YELLOW_BOLD; break;
                    case 0x04: color = BLUE_BOLD;   break;
                    case 0x08: color = GREEN_BOLD;  break;
                    case 0x10: color = BLACK_BOLD;  break;
                    case 0x20: color = RESET;       break;
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
        out.println(color.getColor() + getPrfx() + msg + RESET.getColor());
    }
    private static String getPrfx() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date()) + " - ";
    }
}
