package com.sdp.poc.threading.base.templates;

/**
 * Plantilla para crear la clase Singleton threadsafe de una aplicacion
 * Extiende de CABase
 *
 * - Cambiar CtxTemplate por el prefijo apropiado
 * Esta clase contendra los atributos/parametros ESPECIFICOS de cada aplicacion
 * A los que se accede mediante getters/setters
 *
 * Se maneja igual que cualquier Singleton: CtxNombre.getInstance()
 *
 */

import com.sdp.poc.threading.base.CtxBase;

public class CtxTemplate extends CtxBase {

    private CtxTemplate() {}
    private static class CtxTemplateInner { private static final CtxTemplate INSTANCE = new CtxTemplate(); }
    public  static CtxTemplate getInstance() { return CtxTemplateInner.INSTANCE; }
    public  static CtxTemplate getInstance(String app) {
        CtxTemplateInner.INSTANCE.setAppName(app);
        return CtxTemplateInner.INSTANCE;
    }
}

