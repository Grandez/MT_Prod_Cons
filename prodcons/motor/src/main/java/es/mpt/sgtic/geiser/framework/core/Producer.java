package es.mpt.sgtic.geiser.framework.core;

import es.mpt.sgtic.geiser.framework.interfaces.IProducer;

public abstract class Producer<T> extends ProdCons<T>  implements Runnable {
    protected Producer(T type) {
        super(type);
    }
}
