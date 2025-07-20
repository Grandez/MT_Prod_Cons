package com.sdp.poc.threading.simple.base;
/**
 * Clase contenedora de la informacion necesaria para los productores y consumidores
 * Hereda de Commarea que contiene la informacion necesaria para el motor de threading
 */

import com.sdp.poc.threading.config.Commarea;
import com.sdp.poc.threading.interfaces.ICommarea;

public class CA extends Commarea implements ICommarea {

        int items = 100000;

        private CA() {}

        private static class ConfigInner    { private static final CA INSTANCE = new CA(); }
        public  static CA    getInstance()  { return CA.ConfigInner.INSTANCE; }

        public CA setItems (int items)    {
            this.items = items;
            return this;
        }
        public CA setItems (String items) {
            if (items == null) return this;
            this.items = Integer.parseInt(items);
            return this;
        }
        public int getItems() { return items; }
}
