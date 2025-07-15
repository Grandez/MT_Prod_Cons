package com.sdp.poc.threading.motor.base;

/**
 * Codigos de retorno
 * Se siguen los criterios clasicos
 */

public enum RC {
     OK          (0)
    ,NODATA      (4)
    ,INTERRUPTED (12)
    ,ERROR       (16)
    ,SEVERE      (32)
    ;

    private int code;

    RC (int code)        { this.code = code;   }
    public int getCode() { return code; }
}
