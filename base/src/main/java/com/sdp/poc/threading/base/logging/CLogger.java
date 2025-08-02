package com.sdp.poc.threading.base.logging;

import com.sdp.poc.threading.base.CtxBase;
import com.sdp.poc.threading.base.mask.RC;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CLogger {
    private static CtxBase ca = null;
    public static void error(String msg, Object ... args) {
        if (ca == null) ca = CtxBase.getInstance();
        ca.rc |= RC.ERROR;
        System.err.println(Color.RED_BOLD.getColor() + String.format(msg, args) + Color.RESET.getColor());
    }
    public static void debug(String msg) {
        print(msg);
    }
    public static void warning(String msg, Object ...args) {
        if (ca == null) ca = CtxBase.getInstance();
        ca.rc |= RC.WARNING;
        print(Color.BLUE_BOLD, String.format(msg, args));
    }
    public static void info(String msg) {
        print(Color.BOLD, msg);
    }

    private static void print(Color color, String msg) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss - ");
        String strdate = dateFormat.format(new Date()).toString();
        System.out.println(strdate + color.getColor() + msg + Color.RESET.getColor());
    }
    private static void print(String msg) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss - ");
        String strdate = dateFormat.format(new Date()).toString();
        System.out.println(strdate + msg);
    }

}
