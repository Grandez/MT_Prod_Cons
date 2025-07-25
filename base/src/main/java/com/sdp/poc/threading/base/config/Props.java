package com.sdp.poc.threading.base.config;

import com.sdp.poc.threading.base.msg.Logger2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Props {
    public static Properties load(String basename) {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + basename;

        Properties props = new Properties();
        try {
            props.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            Logger2.warning("Props: No se ha podido cargar el fichero de propiedades: " + basename);
            Logger2.warning("Props: " + e.getLocalizedMessage());
        }
        return props;
    }
}
