package com.sdp.poc.threading.base.mask;

/**
 * Operaciones a nivel bit
 */
public class Bit {
    public static boolean set (int mask, int target)  { return (mask & target) != 0; }
    public static boolean set (long mask, int target) { return (mask & target) != 0; }

    public static int  unset (int mask,  int target)  { return (mask & ~target); }
    public static long unset (long mask, long target) { return (mask & ~target); }
}
