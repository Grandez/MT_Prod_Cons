package es.mpt.sgtic.geiser.prodcons.logging;
/**
 * Generador de Logs
 * Lee de la cola y los escribe donde se decida
 * El proceso acabara cuando se reciba un mensaje tipo -1
 */

import es.mpt.sgtic.geiser.prodcons.beans.Color;
import es.mpt.sgtic.geiser.prodcons.config.ProdConsConfig;
import es.mpt.sgtic.geiser.prodcons.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import static es.mpt.sgtic.geiser.prodcons.beans.Color.*;

public class QLoggerCons implements IProdConsLogger {

    private final LinkedBlockingQueue<Object> cola;
    private String header = "";
    private int   level = 0x3F;
    private Color color = Color.RESET;
    private IProdConsConfig cfg;
//    private Color color = Color.RESET;

    private PrintStream out = System.out;
    private Logger log = LoggerFactory.getLogger(getClass());

    public QLoggerCons(String label) {
        this.cfg    = ProdConsConfig.getConfig(label);
        this.cola   = cfg.getQLog();
        this.header = "[" + label + "] ";
    }

    @Override
    public void run() {
        long mark = 0L;
        try {
            while (mark >= 0 && mark < Long.MAX_VALUE) {
                Object obj = cola.take();
                if (obj instanceof  String)    processMessage   ((String) obj);
                if (obj instanceof  Exception) processException ((Exception) obj);
                if (obj instanceof  Long)      mark = (Long) obj;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
//        log.info(cfg.getSummary());
    }
    private void processMessage (String msg) {
        String[] toks = msg.split(ProdConsConfig.TOK);
        int type = 0;
        try {
            type = Integer.parseInt(toks[0]);
        } catch (NumberFormatException ex) {
            return;
        }
        String[] vars = Arrays.copyOfRange(toks, 2, toks.length);
        print(type, toks[1], vars);
    }
    private void processException (Exception ex) {
        String NULLPTR = "Posible nullPointerException";
        String msg = ex.getLocalizedMessage();
        Throwable ex2 = ex.getCause();
        int causas = 0;
        print(1, (msg == null) ? NULLPTR : msg);
        while (ex2 != null) {
            msg = ex2.getLocalizedMessage();
            if (msg == null) msg = NULLPTR;
            print(1, String.format("Causa %2d: %s", ++causas, msg));
            ex2 = ex2.getCause();
        }
        int deep = 0;
        for (StackTraceElement trace : ex.getStackTrace()) {
            print(1, String.format("%4d - %s", ++deep, trace.toString()));
        }
    }
    private void print(int type, String fmt, Object... args) {
        String msg = header + " " + String.format(fmt, args);
        switch (type) {
            case 0x01: log.error (msg); break;
            case 0x02: log.warn  (msg); break;
            case 0x04: log.info  (msg); break;
            case 0x40: log.error (msg); break;
            default:   log.debug (msg);
        }
        // System.out.println(msg);
        // out.println(color.getColor() + getPrfx() + msg + RESET.getColor());
    }
    private static String getPrfx() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date()) + " - ";
    }

    public void runColor() {
        long mark = 0L;

            while (mark >= 0 || mark < Long.MAX_VALUE) {
//                String msg = cola.take();
                String msg = "nada";
                String[] toks = msg.split(";");
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
                printColor(toks[1], vars);
            }
    }
    private void printColor(String fmt, Object... args) {
        String msg = String.format(fmt, args);
        out.println(color.getColor() + getPrfx() + msg + RESET.getColor());
    }
    private static String getPrfxColor() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date()) + " - ";
    }

}
