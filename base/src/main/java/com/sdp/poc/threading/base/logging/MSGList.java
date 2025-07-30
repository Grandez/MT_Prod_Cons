package com.sdp.poc.threading.base.logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MSGList {
    public void load() {
        List<MSG> list = new ArrayList<MSG>();

        // Instantiating list using Collections.addAll()
        Collections.addAll(list
                ,new MSG("SMR00000", "%ld;%ld;%d;%d;%d", "Resumen app sin hilos")
                ,new MSG("SMR01000", "%ld;%ld;%d;%d;%d;%d;%d;%d;%d", "Resumen app con hilos")
        );

    }
}
