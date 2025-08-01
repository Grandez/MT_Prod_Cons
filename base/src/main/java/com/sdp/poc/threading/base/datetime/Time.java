package com.sdp.poc.threading.base.datetime;

import java.util.concurrent.TimeUnit;

public class Time {
    public static String elapsed(long elapsed) {
        long HH = TimeUnit.MILLISECONDS.toHours(elapsed);
        long MM = TimeUnit.MILLISECONDS.toMinutes(elapsed) % 60;
        long SS = TimeUnit.MILLISECONDS.toSeconds(elapsed) % 60;
        return String.format("%02d:%02d:%02d", HH, MM, SS);
    }
}
