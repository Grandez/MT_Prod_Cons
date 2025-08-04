package com.sdp.poc.threading.base.logging;
/**
 * Generador de Logs
 * Lee de la cola y los escribe donde se decida
 * El proceso acabara cuando se reciba un mensaje tipo -1
 */

import com.sdp.poc.threading.base.files.MFiles;
import com.sdp.poc.threading.base.mask.Bit;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QLoggerCons extends QLoggerBase implements Runnable {

    private int   level = 0x3F;
    private Color color;
    private PrintStream pout = System.out;

    boolean std = false;
    boolean out = false;
    boolean err = false;
    String  app = "NOAPP";
    BufferedWriter buff;
    FileWriter log;

    public QLoggerCons()           {  }
    public QLoggerCons(String app) { this.app   = app; }
    @Override
    public void run() {
        Thread.currentThread().setName("logger");
        openLogFile();
        long mark = 1L;

        try {
            while (true) {
                String msg = qlog.take();
                String[] toks = msg.split(":");
                mark = Long.parseLong(toks[1]);
                if (mark < 0 || mark == Long.MAX_VALUE) break;

//                mark = checkConsole(mark);
                write2log(msg);

                // MSGTYPE type = new MSGTYPE((int) mark);
//                int type = (int) mark;
//
//                String[] vars = Arrays.copyOfRange(toks, 2, toks.length);
//                switch (type &  level) {
//                    case 0x01: pout = System.err;
//                               color = Color.RED_BOLD;
//                               break;
//                    case 0x02: color = Color.YELLOW_BOLD; break;
//                    case 0x04: color = Color.BLUE_BOLD;   break;
//                    case 0x08: color = Color.GREEN_BOLD;  break;
//                    case 0x10: color = Color.BLACK_BOLD;  break;
//                    case 0x20: color = Color.RESET;       break;
//                    default: continue;
//                }
//                print(toks[1], vars);
            }
        } catch (InterruptedException e) {
            // Por ahora simplemente cerramos el flujo
            System.err.println("Interrupcion del logger");
            closeLogFile();
            Thread.currentThread().interrupt();
        }
        closeLogFile();
    }
    private void print(String fmt, Object... args) {
        String msg = String.format(fmt, args);
        pout.println(color.getColor() + getPrfx() + msg + Color.RESET.getColor());
    }
    private void write2log(String msg) {
        try {
            log.write(msg + "\n");
        } catch (Exception ex) {
            // Do nothing
        }
    }
    private static String getPrfx() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date()) + " - ";
    }
    private void openLogFile() {
        try {
            File dir = MFiles.createTempSubDir("mt");
            String name = app == null ? "mt" : app;
            log = MFiles.createAppendFile(dir, name + ".log", "rw-rw-rw-");

//            buff = Files.newBufferedWriter(file);
//            log = new PrintWriter(buff);
        } catch (Exception e) {
            System.err.println("Error creando log file");
            System.err.println(e.getLocalizedMessage());
        };
    }
    private void closeLogFile() {
        try {
            if (log != null)  log.close();
//            if (buff != null) buff.close();
        } catch (Exception ex) {
            // Do nothing
        } finally {
            log = null;
//            buff = null;
        }
    }
    ///  Chequea si el mensaje se debe sacar por consola
    private long checkConsole(long mark) {
        std = false;
        if (Bit.set(mark, 1)) { // Usar consola
            std = true;
            out = Bit.set(mark, 2) ? false : true;
            err = Bit.set(mark, 2) ? true : false;
        }
        return Bit.unset(mark,3);
    }
}
