package es.mpt.sgtic.geiser.tools.libreria.processes.loader;

import es.mpt.sgtic.geiser.tools.libreria.base.TBL;
import es.mpt.sgtic.geiser.tools.libreria.entities.AsientoOld;

import java.sql.Date;

public class Asiento extends LoaderBase {
    public long load(long id) {
        AsientoOld asiento = new AsientoOld();
        long idAsiento = cfg.inc(TBL.ASIENTO.ordinal()) ;
        asiento.setId(idAsiento);
        char c = rnd.getInt(2) == 0 ? 'e' : 's';
        asiento.setNuRegEntrada(String.format("REGAGE%s%c000%s", rnd.getNumber(2,5, 20), c, rnd.getNumber(8)));
        asiento.setFeEntrada(new Date(System.currentTimeMillis()));
        asiento.setCdUnTrOrigen(String.format("E%s", rnd.getNumber(8)));
        asiento.setDsUnTrOrigen("Delegacion del gobierno " + asiento.getCdUnTrOrigen());
        asiento.setDsResumen("Resumen " + rnd.getNumber());
        asiento.setCdAsunto("Codigo de asunto");
        asiento.setRfExterna("Ref externa");
        asiento.setNuExpediente("Numero expediente");
        asiento.setCdTpTransporte(String.format("%s", rnd.getNumber(1,2)));
        asiento.setNuTransporte(rnd.getNumber());
        asiento.setCdIntercambio(String.format("%s_%s_%s", rnd.getNumber(8), rnd.getNumber(2,5, 20),rnd.getNumber(8)));
        asiento.setCdTpRegistro(rnd.getNumber(1,2));
        asiento.setCdDocFisica(rnd.getNumber(1,4));
        asiento.setDsObservaciones("Observaciones ");
        asiento.setCdInPrueba("0");
        asiento.setCdEnRgInicio(rnd.getNumber(8));
        asiento.setDsEnRgInicio("Oficina registral " + asiento.getCdEnRgInicio());
        // asiento.setTsEntrada();
        asiento.setCdModoRegistro(rnd.getNumber(1,2));
        asiento.setFeRegPresentacion(new Date(System.currentTimeMillis()));
        // asiento.setTsRegPresentacion();
        asiento.setCdSIA(rnd.getNumber(6));
        asiento.setCdIntercambioPrevio(String.format("%s_%s_%s", rnd.getNumber(8)
                , rnd.getNumber(2,5, 20)
                ,rnd.getNumber(8)));
        asiento.setCdUnTrInicio(String.format("E%s", rnd.getNumber(8)));
        asiento.setDsUnTrInicio("Delegacion del gobierno " + asiento.getCdUnTrInicio());
        asiento.setItDestRefUnica(rnd.getNumber(1,2).compareTo("0") == 0 ? '0' : '1');
        asiento.setDsExpone("Expone algo");
        asiento.setDsSolicita("Solicita algo");

        cfg.em.persist(asiento);
        return idAsiento;
    }

}
