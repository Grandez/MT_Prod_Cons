package com.sdp.poc.threading.base.config;

import com.sdp.poc.threading.base.logging.CLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Props extends Properties {
    public static Properties load(String basename) {
//        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//        TestGameTable.class.getClassLoader().getResource("dice.jpg");
//        String appConfigPath = rootPath + basename;

        // Props props = new Properties();
        return new Props();
//        try {
//            props.load(new FileInputStream(appConfigPath));
//        } catch (IOException e) {
//            CLogger.warning("Props: No se ha podido cargar el fichero de propiedades: " + basename);
//            CLogger.warning("Props: " + e.getLocalizedMessage());
//        }
//        return props;
    }
}
