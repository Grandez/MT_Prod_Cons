package com.sdp.poc.threading.base;

import com.sdp.poc.threading.base.parameters.Props;
import com.sdp.poc.threading.base.datetime.Time;
import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.base.logging.QLogger;
import com.sdp.poc.threading.base.logging.QLoggerProd;
import com.sdp.poc.threading.base.system.Shutdown;

public abstract class MainBase {
    protected QLoggerProd logger;
    protected CtxBase ctx;

    // Analiza la linea de comandos segun la aplicacion
    protected abstract Props parseParms(String[] args);
    // Sobreescribe la configuracion de la app con los parametros de linea de comandos
    protected abstract void  loadConfig();
    // Muestra la ayuda de la aplicacion
    protected abstract void  showHelp();

    protected void appInit(String name, CtxBase ctx, String[] args) {
        Shutdown.setHook();
        QLogger.start(name);

        this.ctx = ctx;
        ctx.setAppName(name);
        ctx.setCommandLine(parseParms(args));

        logger = QLogger.getLogger(ctx);
        loadConfig();
    }

    protected void appEnd() {
        // Separamos las ejecuciones con timeout que las que no
        // Desvituarian los analisis
        String type = ctx.getTimeout() > 0 ? "SMR01000" : "SMR01001";
        logger.msg(type, System.currentTimeMillis() - ctx.getBeg()
                , ctx.getRC()
                , ctx.getInput(), ctx.getOutput(), ctx.getErrors()
                , ctx.getNumThreads(), ctx.getChunk(), ctx.getTimeout()
        );
        QLogger.stop();
        CLogger.info(String.format("RC: %2d - Elapsed: %s", ctx.getRC(),
                Time.elapsed(System.currentTimeMillis() - ctx.getBeg(), true)));
        System.exit(ctx.getRC());
    }

}
