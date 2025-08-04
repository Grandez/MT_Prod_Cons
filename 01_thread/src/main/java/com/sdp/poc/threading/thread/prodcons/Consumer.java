package com.sdp.poc.threading.thread.prodcons;

import com.sdp.poc.threading.base.CtxBase;
import com.sdp.poc.threading.base.otros.Operacion;
import com.sdp.poc.threading.base.system.Rand;
import com.sdp.poc.threading.mtlatch.base.ThreadBase;

public class Consumer extends ThreadBase implements Runnable {
    CtxBase ctx;

    public Consumer(CtxBase ctx) {
        super(ctx.getLatch());
        this.ctx = ctx;
    }

    @Override
    public void run() {
        Operacion op = new Operacion();
        Rand r = new Rand(1,100);
        op.sumar(r.next(), r.next());
        op.multiplicar(r.next(), r.next());
        ctx.write();
        ctx.getLatch().countDown();
    }
}
