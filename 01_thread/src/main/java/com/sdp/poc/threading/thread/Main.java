package com.sdp.poc.threading.thread;

import com.sdp.poc.threading.base.mask.RC;
import com.sdp.poc.threading.base.parameters.CLP;
import com.sdp.poc.threading.base.parameters.CLP_Parameter;
import com.sdp.poc.threading.base.parameters.CLP_TYPE;
import com.sdp.poc.threading.base.parameters.Props;
import com.sdp.poc.threading.base.MainBase;
import com.sdp.poc.threading.base.logging.QLoggerProd;
import com.sdp.poc.threading.mtlatch.core.Motor;
import com.sdp.poc.threading.thread.core.CtxThread;
import com.sdp.poc.threading.thread.prodcons.Consumer;
import com.sdp.poc.threading.thread.prodcons.Productor;

import java.util.*;

import static java.lang.System.out;

public class Main extends MainBase {
    private CtxThread ctx = CtxThread.getInstance();
    private QLoggerProd logger;

    public static void main(String[] args) {
        Main main = new Main();
        main.run(args);
    }
    private void run(String[] args) {
        try {
            appInit("thread", ctx, args);
            Motor motor = new Motor(ctx);
            motor.run(Productor.class, Consumer.class);
        } catch (SecurityException se) {
            ctx.rc |= RC.INTERRUPTED;
           System.err.println("Control-c pulsado");
        } catch (Exception se) {
            ctx.rc |= RC.CRITICAL;
            System.err.println(se.getLocalizedMessage());
        } finally {
            appEnd();
        }

    }
    ///////////////////////////////////////////////////////////////////////////
    // MainBase
    ///////////////////////////////////////////////////////////////////////////

     protected Props parseParms(String[] args) {
        Map<String, CLP_Parameter> options = new HashMap<>();

        options.put("n", new CLP_Parameter("n", "items", CLP_TYPE.PINT));
        options.put("t", new CLP_Parameter("t", "threads", CLP_TYPE.PINT));
        options.put("e", new CLP_Parameter("e", "timeout", CLP_TYPE.PINT));

        Props props = CLP.parseParms(args, options);
        if (props.get("help") != null) showHelp();
        return props;
    }
    protected void loadConfig() {
        Props props = ctx.getCommandLine();
        ctx.setItems(props.getInteger("items", ctx.getItems()));
    }
    protected void showHelp() {
        out.println("POC para analisis de procesos multihilo");
        out.println("Crea hilos bajo demanda");
        out.println();
        out.println("Uso: java -jar 01_case_simple.jar [-t n][-e n][-n n]");
        out.println("\t   -t n - Numero de hilos");
        out.println("\t   -e n - Maximo tiempo elapsed en minutos");
        out.println("\t   -n n - Numero de mensajes");
        System.exit(RC.OK);
    }
}
