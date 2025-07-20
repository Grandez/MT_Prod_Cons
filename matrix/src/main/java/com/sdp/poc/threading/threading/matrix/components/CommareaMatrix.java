/**
 * Clase de comunicacion entre hilos y procesos
 * Extiende de la clase base Commarea del Motor MT
 *
 * Se gestiona como un Singleton Threadsafe
 * En esta clase se da acceso a todos los datos que puedan necesitar
 * el productor y el consumidor especificos de cada aplicacion
 */
package com.sdp.poc.threading.threading.matrix.components;

import com.sdp.poc.threading.config.Commarea;
import com.sdp.poc.threading.interfaces.ICommarea;

public class CommareaMatrix extends Commarea implements ICommarea {
    Matriz matriz;

    private CommareaMatrix() {}

    private static class ConfigInner      { private static final CommareaMatrix INSTANCE = new CommareaMatrix(); }
    public  static CommareaMatrix getInstance() { return ConfigInner.INSTANCE; }

    public CommareaMatrix setMatrix    (Matriz matriz)         { this.matriz = matriz;       return this; }

    public Matriz getMatrix    () { return matriz;       }
}
