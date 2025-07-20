package com.sdp.poc.threading.base.msg;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Mensajes a consola
 */
public class Msg {
    public static void error(int rc, String msg) {
        System.err.println(Color.RED_BOLD + msg + Color.RESET);
        System.exit(rc);
    }
    public static void warning(String msg) {
        print(Color.BLUE_BOLD, msg);
    }
    public static void info(String msg) {
        print(Color.WHITE_BOLD, msg);
    }

    private static void print(Color color, String msg) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss - ");
        String strdate = dateFormat.format(new Date()).toString();
        System.out.println(strdate + color + msg + Color.RESET);
    }
}
