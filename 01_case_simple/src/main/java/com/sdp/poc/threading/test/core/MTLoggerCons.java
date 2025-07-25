package com.sdp.poc.threading.test.core;
/**
 * Generador de Logs
 * Lee de la cola y los escribe donde se decida
 * El proceso acabara cuando se reciba un mensaje tipo -1
 */

import com.sdp.poc.threading.base.files.MFiles;
import com.sdp.poc.threading.base.msg.Color;
import com.sdp.poc.threading.interfaces.ILogger;
import com.sdp.poc.threading.test.interfaces.IMTLogger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class MTLoggerCons implements ILogger {

    private final LinkedBlockingQueue<String> cola;
    private CountDownLatch latch;
    private int   level = 0x3F;
    private Color color;
    private PrintStream out = System.out;
    private IMTLogger logger;
    private MTEnv env;

    BufferedWriter buff;
    FileWriter log;

    public MTLoggerCons(MTEnv env, IMTLogger logger) {
        this.cola   = env.getQLog();
        this.logger = logger;
        this.env    = env;
    }
    @Override
    public void run() {
        Thread.currentThread().setName("logger");
        openLogFile();
        long mark = 1L;

        try {
            while (true) {
                String msg = cola.take();
                String[] toks = msg.split(env.TOK);
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
            closeLogger();
            Thread.currentThread().interrupt();
        }
        closeLogger();
    }
    private void print(String fmt, Object... args) {
        String msg = String.format(fmt, args);
        out.println(color.getColor() + getPrfx() + msg + Color.RESET.getColor());
    }
    private static String getPrfx() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date()) + " - ";
    }
    private void openLogFile() {
        try {
            File dir = MFiles.createTempSubDir("mt");
            String name = env.getAppName() == null ? "mt" : env.getAppName();
            log = MFiles.createAppendFile(dir, name + ".log", "rw-rw-rw-");

//            buff = Files.newBufferedWriter(file);
//            log = new PrintWriter(buff);
        } catch (Exception e) {
            System.err.println("Error creando log file");
            System.err.println(e.getLocalizedMessage());
        };
    }
    private void closeLogger() {
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
}
