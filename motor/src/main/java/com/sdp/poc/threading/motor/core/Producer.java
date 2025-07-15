package com.sdp.poc.threading.motor.core;

public abstract class Producer<T> extends ProdCons<T>  implements Runnable {
    protected Producer(T type) {
        super(type);
    }
}
