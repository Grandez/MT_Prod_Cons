package com.sdp.poc.threading.base.config;

import com.sdp.poc.threading.base.interfaces.ICABase;
import com.sdp.poc.threading.base.system.PID;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;

public class CtxBase implements ICABase {
    private String appName = "NONAME";
    public  int    rc  = 0;

    private Long    pid;
    private Long    beg;

    private Long    in  = 0L;
    private Long    out = 0L;
    private Long    err = 0L;

    private int threads = 1;
    private int timeout = 0;
    private int chunk = 0;

    protected Props props;
    protected Props customProps = new Props();

    private static class CABaseInner    { private static final CtxBase INSTANCE = new CtxBase(); }
    public  static CtxBase getInstance() { return CtxBase.CABaseInner.INSTANCE; }

    private static PriorityBlockingQueue<Long> qdat = new PriorityBlockingQueue<Long>();
    private static CountDownLatch latch;

    public String getAppName()  { return appName; }
    public int    getRC()       { return rc;      }
    public long   getPid()      { return pid;     }
    public long   getBeg()      { return beg;     }
    public long   getInput()    { return in;      }
    public long   getOutput()   { return out;     }
    public long   getErrors()   { return err;     }
    public void   setRC(int rc) { this.rc = rc;   }

    public void setNumThreads(int threads) { this.threads = threads; }
    public void setTimeout   (int timeout) { this.timeout = timeout; }
    public void setChunk     (int chunk)   { this.chunk   = chunk; }

    public int getNumThreads() { return threads; }
    public int getTimeout   () { return timeout; }
    public int getChunk     () { return chunk;   }

    public void setAppName (String appName) { this.appName = appName; }

    public CtxBase() {
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

    public void setProperties (Props props) { this.props = props; }
    public void setCustomProps(Props props) { this.customProps = props; }

    public Props getCustomProps()            { return customProps; }
    public PriorityBlockingQueue<Long> getQueue() { return qdat; }
    public CountDownLatch              getLatch() { return latch; }
    public void                        setLatch(CountDownLatch latch) { this.latch = latch; }
}
