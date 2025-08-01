package com.sdp.poc.threading.base.core;

import com.sdp.poc.threading.base.config.CtxBase;
import com.sdp.poc.threading.base.config.Props;
import com.sdp.poc.threading.base.datetime.Time;
import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.base.logging.QLogger;
import com.sdp.poc.threading.base.logging.QLoggerProd;
import com.sdp.poc.threading.base.system.Shutdown;

import java.util.Properties;

public abstract class MainBase {
    protected QLoggerProd logger;
    protected CtxBase ctx;

    protected abstract Props parseParms(String[] args);
    protected abstract void  loadConfig();
    protected abstract void  showHelp();

    protected void appInit(String name, CtxBase ca, String[] args) {
        Shutdown.setHook();
        QLogger.start(name);

        ca.setAppName(name);
        ca.setProperties(parseParms(args));

        logger = QLogger.getLogger(ca);
    }

    protected void appEnd() {
        logger.msg("SMR01000", System.currentTimeMillis() - ctx.getBeg()
                , ctx.getRC()
                , ctx.getInput(), ctx.getOutput(), ctx.getErrors()
                , ctx.getNumThreads(), ctx.getChunk(), ctx.getTimeout()
        );
        QLogger.stop();
        CLogger.info(String.format("RC: %2d - Elapsed: %s", ctx.getRC(),
                Time.elapsed(System.currentTimeMillis() - ctx.getBeg())));
        System.exit(ctx.getRC());
    }

}
