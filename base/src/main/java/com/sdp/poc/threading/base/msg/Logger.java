package com.sdp.poc.threading.base.msg;
/**
 * Logger via cola
 * Se ha preferido usar solo Logger para evitar confusiones, realmente es un productor
 */

import java.util.concurrent.LinkedBlockingQueue;

public class Logger {

//    private static LinkedBlockingQueue<String> cola = null;
//
//    // public Logger() { cola = ConfigFramework.getInstance().qlog; }
//    public Logger() {  }
//    public static void setQueue (LinkedBlockingQueue queue) { cola = queue; }
//    public static void err   (String fmt, Object... args) { put(   1, String.format(sfmt(fmt), args));   }
//    public static void warn  (String fmt, Object... args) { put(   2, String.format(sfmt(fmt), args));   }
//    public static void info  (String fmt, Object... args) { put(   4, String.format(sfmt(fmt), args));   }
//    public static void log   (String fmt, Object... args) { put(   8, String.format(sfmt(fmt), args));   }
//    public static void detail(String fmt, Object... args) { put(0x10, String.format(sfmt(fmt), args));   }
//    public static void debug (String fmt, Object... args) { put(0x20, String.format(sfmt(fmt), args));   }
//
//    private static String sfmt(String fmt) { return Thread.currentThread().getName() + " - " + fmt; }
//    public static void summary(String label, int value) {
//        System.out.println(String.format("%-15s: %15d", label, value));
//    }
//    public static void elapsed(String value) {
//        String elapsed = "Elapsed";
//        System.out.println(String.format("%-15s: %-20s", elapsed, value));
//    }
//    private static void put (int type, String fmt, Object... args) {
//        StringBuilder str = new StringBuilder();
//        str.append(type).append(';').append(fmt);
//        for (int i = 0; i < args.length; i++) str.append(';').append(args[i]);
//        try {
//            cola.put(str.toString());
//        } catch (Exception e) {
//            // Errores de escritura en log son ignorados
//        }
//    }
}
