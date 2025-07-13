package es.mpt.sgtic.geiser.tools.libreria.base.exceptions;

import es.mpt.sgtic.geiser.framework.tools.Messages;

public class LibException extends Exception {
    int code;
    public LibException(Messages msg, Throwable cause) {
        super(msg.getMessage(), cause);
        this.code = msg.getCode();
    }
    public LibException(Messages msg) {
        super(msg.getMessage());
        this.code = msg.getCode();
    }
    public LibException(Messages msg, Object ... toks) {
        super(msg.getMessage());
        this.code = msg.getCode();
    }
    public LibException(String msg) {
        super(msg);
        this.code = 9999;
    }

    public int getRC() {
        return code / 1000;
    }

}
