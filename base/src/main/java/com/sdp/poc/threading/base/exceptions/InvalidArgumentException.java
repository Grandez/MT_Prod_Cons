package com.sdp.poc.threading.base.exceptions;

import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.base.mask.RC;

public class InvalidArgumentException extends Exception {
    public InvalidArgumentException(String fmt, Object... args) {
        super(String.format(fmt,args));
    }
}
