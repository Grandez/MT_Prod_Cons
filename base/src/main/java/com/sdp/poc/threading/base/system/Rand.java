package com.sdp.poc.threading.base.system;

import java.util.Random;

public class Rand {
    private Random  r;
    private Integer min;
    private Integer max;

    public Rand()         { r = new Random();     }
    public Rand(int seed) { r = new Random(seed); }
    public Rand(int min, int max) {
        this();
        setRange(min,max);
    }
    public Rand(int seed, int min, int max) {
        this(seed);
        setRange(min,max);
    }
    public int next() {
        if (min == null) return r.nextInt();
        return r.nextInt(max-min) + min;
    }
    private void setRange(int min, int max) {
        this.min = min;
        this.max = max;

    }
}
