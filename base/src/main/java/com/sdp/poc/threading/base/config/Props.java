package com.sdp.poc.threading.base.config;

import com.sdp.poc.threading.base.logging.CLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Props extends Properties {
    public static Props load(String fileName, String preffix) {
        try {
            Props props = new Props();
            InputStream is = Props.class.getClassLoader().getResourceAsStream(fileName);
            if (is == null) throw new IOException("Fichero no encontrado");
            props.load(is);
            return props;
        } catch (IOException e) {
            CLogger.warning("Props: No se ha podido cargar el fichero de propiedades: " + fileName);
            CLogger.warning("Props: " + e.getLocalizedMessage());
        }
        return new Props();
    }
    public Integer getInteger(String key) { return getInteger(key, null); }
    public Integer getInteger(String key, Integer def) {
        String obj = (String) get(key);
        return (obj == null ? def : Integer.parseInt(obj));
    }
}
