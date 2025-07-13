package es.mpt.sgtic.geiser.prodcons.logging;
/**
 * Logger via cola
 * Se ha preferido usar solo QLogger para evitar confusiones
 * Realmente es un productor de mensajes en formato CSV
 */

import java.util.concurrent.LinkedBlockingQueue;

public class QLogger {
    private static LinkedBlockingQueue<Object> cola = null;

    public static void setQueue (LinkedBlockingQueue<Object> queue) { cola = queue; }
    public static void err     (String fmt, Object... args) { put(   1, String.format(sfmt(fmt), args));   }
    public static void warn    (String fmt, Object... args) { put(   2, String.format(sfmt(fmt), args));   }
    public static void info    (String fmt, Object... args) { put(   4, String.format(sfmt(fmt), args));   }
    public static void log     (String fmt, Object... args) { put(   8, String.format(sfmt(fmt), args));   }
    public static void detail  (String fmt, Object... args) { put(0x10, String.format(sfmt(fmt), args));   }
    public static void debug   (String fmt, Object... args) { put(0x20, String.format(sfmt(fmt), args));   }
    public static void resumen (String fmt, Object... args) { put(0x40, String.format(fmt, args));         }

    public static void exception (Exception ex)           {
        try {
            cola.put(ex);
        } catch (Exception e ) {
            // InterruptedEception
        }
    }
    private static String sfmt(String fmt) { return Thread.currentThread().getName() + " - " + fmt; }
    public static void summary(String label, int value) { info("%-10s: %11d", label, value);            }
    public static void elapsed(String value)            { info("%-10s: %-11s", "Elapsed", value); }
    private static void put (int type, String fmt, Object... args) {
        StringBuilder str = new StringBuilder();
        str.append(type).append(';').append(fmt);
        for (int i = 0; i < args.length; i++) str.append(';').append(args[i]);
        try {
            cola.put(str.toString());
        } catch (Exception e) {
            // Errores de escritura en log son ignorados
        }
    }
}
