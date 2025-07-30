package com.sdp.poc.threading.simple.base;
/**
 * Area de comunicacion o contexto de aplicacion
 * Extiende de:
 * - CAMT si es multihilo
 * - CABase si no lo es
 */

import com.sdp.poc.threading.test.base.CAMT;
import com.sdp.poc.threading.test.interfaces.IMTCA;

import java.util.Properties;

public class CASimple extends CAMT<Long> implements IMTCA {
    private Integer items = 1000;
    Properties props;
    private CASimple() {}
    private static class CASimpleInner    { private static final CASimple INSTANCE = new CASimple(); }
    public  static CASimple getInstance() { return CASimple.CASimpleInner.INSTANCE; }
    public  static CASimple getInstance(String app) {
        CASimple.CASimpleInner.INSTANCE.setAppName(app);
        return CASimple.CASimpleInner.INSTANCE;
    }
    public void setProperties(Properties props) { this.props = props; }
    public Properties getCustomProps()          { return props; }

    public int getItems() {
        Integer max = (Integer) props.get("items");
        if (max != null) items = max;
        return items;
    }
}
