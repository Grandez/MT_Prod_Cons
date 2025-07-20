package com.sdp.poc.threading.core;
/**
 * Clase base de los consumidores
 * Se encarga de gestionar la transaccionalidad segun los chunks definidos
 */

public class ConsumerBase {
    protected void setThreadName () { setThreadName(this.getClass().getSimpleName()); }
    protected void setThreadName (String proceso) {
        String tname = Thread.currentThread().getName();
        String[] toks = tname.split("-");
        Thread.currentThread().setName(proceso + "-" + String.format("%03d", Integer.parseInt(toks[toks.length - 1])));
    }
    protected String getName() { return Thread.currentThread().getName(); }
}
