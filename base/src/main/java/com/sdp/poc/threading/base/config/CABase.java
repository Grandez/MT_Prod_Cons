package com.sdp.poc.threading.base.config;

import com.sdp.poc.threading.base.interfaces.ICABase;
import com.sdp.poc.threading.base.system.PID;

public class CABase implements ICABase {
    private String appName = "NONAME";
    private Long    pid;
    private Long    beg;
    private Integer rc  = 0;
    private Long    in  = 0L;
    private Long    out = 0L;
    private Long    err = 0L;
    private static class CABaseInner    { private static final CABase INSTANCE = new CABase(); }
    public  static CABase getInstance() { return CABase.CABaseInner.INSTANCE; }

    public String getAppName()  { return appName; }
    public int    getRC()       { return rc;      }
    public Long   getPid()      { return pid;     }
    public Long   getBeg()      { return beg;     }
    public Long   getInput()    { return in;      }
    public Long   getOutput()   { return out;     }
    public Long   getErrors()   { return err;     }
    public void   setRC(int rc) { this.rc = rc;   }

    public void setAppName (String appName) { this.appName = appName; }

    public CABase() {
        this.pid = PID.getpid();
        this.beg = System.currentTimeMillis();
    }

    // Metodos para los contadores
    public void read()           { in++;       }
    public void write()          { out++;      }
    public void err()            { err++;      }
    public void setRead (long v) { in   = v;   }
    public void setWrite(long v) { out  = v;   }
    public void setErr  (long v) { err  = v;   }
    public void addRead (long v) { in  += v;   }
    public void addWrite(long v) { out += v;   }
    public void addErr  (long v) { err += v;   }
    public long getRead ()       { return in;  }
    public long getWrite()       { return out; }
    public long getErr  ()       { return err; }

}
