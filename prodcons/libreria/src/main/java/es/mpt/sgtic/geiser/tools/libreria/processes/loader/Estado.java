package es.mpt.sgtic.geiser.tools.libreria.processes.loader;

import es.mpt.sgtic.geiser.framework.core.Logger;
import es.mpt.sgtic.geiser.tools.libreria.base.TBL;
import es.mpt.sgtic.geiser.tools.libreria.entities.EstadoAsientoOld;

import java.sql.Date;

public class Estado extends LoaderBase {
    String[] estados = {"PE",  "PRAE",    "PRC",   "EC", "ERCH", "EERR",   "R",   "PERCH",
                        "PRE", "PRAERCH", "PRARE", "RC", "RRCH", "RCHERR", "RRE", "REERR","RG"};
    int      rng = 17; // Numero de estados
    String[] cdg =  {"01", "02", "03", "04"};
    String[] dsc =  {"Pendiente (sin Identificador de Intercambio)","Envío", "Reenvío", "Rechazo"};

    public long load(long idAsiento) {
        EstadoAsientoOld estado = new EstadoAsientoOld();
        long idEstado = cfg.inc(TBL.ESTADO.ordinal());
        cfg.write();
        estado.setId(idEstado);
        estado.setCdAsiento(idAsiento);
        estado.setAplicacion("GSER");
        estado.setEstado(estados[rnd.getInt(rng)]);
        estado.setCorreo("uncorreo@mail.com");
        estado.setErrorTecnico(rnd.getInt(2) % 2 == 0 ? '0' : '1');
        estado.setErrorTecnicoDesc("Descripcion de error");
        estado.setReintentos(rnd.getInt(10));
        estado.setCdEnRgProcesa("O" + rnd.getNumber(8));
        estado.setCdEnRgOrigen("O" + rnd.getNumber(8));
        estado.setDsEnRgOrigen("Oficina " + estado.getCdEnRgOrigen());
        estado.setCdEnRgDestino("O" + rnd.getNumber(8));
        estado.setDsEnRgDestino("Oficina " + estado.getCdEnRgDestino());
        estado.setCdUnTrDestino("E" + rnd.getNumber(8));
        estado.setDsUnTrDestino("Delegacion " + estado.getCdUnTrDestino());
        estado.setFechaEntrada(new Date(System.currentTimeMillis()));
        estado.setUsuario("user" + rnd.getNumber(3));
        estado.setFechaModificacion(new Date(System.currentTimeMillis()));

        int i = rnd.getInt(4);
        // Código indica el motivo de la anotación: [‘01’ = Pendiente (sin Identificador de Intercambio), ‘02’ = Envío, ‘03’ = Reenvío, ‘04’ = Rechazo].
        estado.setCdTpAnotacion(cdg[i]);
        estado.setDsTpAnotacion(dsc[i]);

        // 'Flag que indica si la información ha sido procesada por la oficina.';
        estado.setItEnvio(rnd.getInt(2) % 2 == 0 ? '0' : '1');
        estado.setItProcesado(rnd.getInt(2) % 2 == 0 ? '0' : '1');

        cfg.em.persist(estado);
        return idEstado;
    }
}
