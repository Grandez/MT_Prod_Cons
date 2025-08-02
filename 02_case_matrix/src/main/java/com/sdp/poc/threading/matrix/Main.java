package com.sdp.poc.threading.matrix;

import com.sdp.poc.threading.base.config.CLP;
import com.sdp.poc.threading.base.config.CLP_Parameter;
import com.sdp.poc.threading.base.config.CLP_TYPE;
import com.sdp.poc.threading.base.config.Props;
import com.sdp.poc.threading.base.core.MainBase;
import com.sdp.poc.threading.base.logging.QLoggerProd;
import com.sdp.poc.threading.base.mask.RC;
import com.sdp.poc.threading.matrix.core.CtxMatrix;
import com.sdp.poc.threading.matrix.core.Matrix;
import com.sdp.poc.threading.matrix.core.TYPES;
import com.sdp.poc.threading.matrix.prodcons.Consumer;
import com.sdp.poc.threading.matrix.prodcons.Productor;
import com.sdp.poc.threading.matrix.tools.MatrixBuilder;
import com.sdp.poc.threading.mtlatch.core.Motor;

import java.util.*;

import static java.lang.System.out;

public class Main extends MainBase {
    private CtxMatrix ctx = CtxMatrix.getInstance();;
    private QLoggerProd logger;

    public static void main(String[] args) {
        Main main = new Main();
        main.run(args);
    }
    private void run(String[] args) {
        List<Matrix> matrices;
        try {
            appInit("matrix", ctx, args);
            Motor motor = new Motor(ctx);
            for (Matrix m : creaMatrices()) {
                m.split(); // Fuerza a crear los arrays rows y cols
                ctx.setMatrix(m);
                motor.run(Productor.class, Consumer.class);
                ctx.getResult().print("Result:");
            }
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

    private List<Matrix> creaMatrices() {
        MatrixBuilder builder = new MatrixBuilder();
        List<Matrix> lista = new ArrayList<>();
        for (TYPES type : TYPES.values()) {
            lista.add(builder.build(ctx.getRows(), ctx.getRows(), type));
        }
        return lista;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MainBase
    ///////////////////////////////////////////////////////////////////////////

    protected Props parseParms(String[] args) {
        Map<String, CLP_Parameter> options = new HashMap<>();

        options.put("t", new CLP_Parameter("t", "threads", CLP_TYPE.PINT));
        options.put("e", new CLP_Parameter("e", "timeout", CLP_TYPE.PINT));
        options.put("r", new CLP_Parameter("r", "rows", CLP_TYPE.PINT));

        Props props = CLP.parseParms(args, options);
        if (props.get("help") != null) showHelp();
        return props;
    }

    protected void loadConfig() {
        // No hay fichero de propiedades. solo linea de comandos
        Props props = ctx.getCommandLine();
        ctx.setRows(props.getInteger("rows", ctx.getRows()));
    }
    protected void showHelp() {
        out.println("POC para analisis de procesos multihilo");
        out.println("Matrices: Eleva una matriz al cuadrado");
        out.println();
        out.println("Uso: java -jar 02_case_matrix.jar [-t n][-n n]");
        out.println("\t   -t n - Numero de hilos");
        out.println("\t   -e n - Maximo tiempo elapsed en minutos");
        out.println("\t   -r n - Numero de filas/columnas de la matriz");
        System.exit(0);
    }

}