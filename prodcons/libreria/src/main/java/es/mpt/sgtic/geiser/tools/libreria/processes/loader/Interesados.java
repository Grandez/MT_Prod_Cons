package es.mpt.sgtic.geiser.tools.libreria.processes.loader;

import es.mpt.sgtic.geiser.tools.libreria.base.TBL;
import es.mpt.sgtic.geiser.tools.libreria.entities.DireccionOld;
import es.mpt.sgtic.geiser.tools.libreria.entities.InteresadoOld;

public class Interesados extends LoaderBase {
    public long load(long idAsiento) {
        long idInteresado = 0;
        int items = rnd.getInt(5);
        for (int i = 0; i < items; i++) {
            InteresadoOld interesado = new InteresadoOld();
            cfg.write();
            idInteresado = cfg.inc(TBL.INTERESADO.ordinal());
            interesado.setId(idInteresado);
            interesado.setCdAsiento(idAsiento);
            interesado.setCdDireccion(loadDireccion());
            interesado.setDsObservaciones("Observaciones " + rnd.getNumber(5));
            int tipo = rnd.getInt(2);
            interesado.setDocIdentificacion(tipo % 2 == 0 ? "18816699P" : "O00011161");
            interesado.setTpDocIdentificacion(tipo % 2 == 0 ? 'N' : 'O');
            interesado.setTpPersona(tipo % 2 == 0 ? '0' : '1');
            interesado.setDsNombre("Nombre " + rnd.getNumber(7));
            interesado.setDsPrimerApellido("Apellido " + rnd.getNumber(7));
            interesado.setDsSegundoApellido("Apellido " + rnd.getNumber(7));
            interesado.setDsRazonSocial("Razon " + rnd.getNumber(7));
            interesado.setItMail(rnd.getInt(2) == 1 ? '1' : '0');
            interesado.setItSMS(rnd.getInt(2) == 1 ? '1' : '0');
            interesado.setItReceptorNotificaciones(rnd.getInt(2) == 1 ? '1' : '0');
            cfg.em.persist(interesado);
        }
        return idInteresado;
    }
    // Direccion comun para todos
    private long loadDireccion() {
        DireccionOld dir = new DireccionOld();
        cfg.write();
        long idDireccion = cfg.inc(TBL.INTERESADO.ordinal());
        dir.setId(idDireccion);
        dir.setDireccion("Direccion postal");
        dir.setCanal("01");
        dir.setDirElectronica("electronico@mail.com");
        dir.setCorreo("correo@mail.com");
        dir.setCdMunicipio("0133");
        dir.setCdPais("724");
        dir.setCdPostal("28300");
        dir.setCdProvincia("28");
        dir.setTelefono("911234567");
        cfg.em.persist(dir);
        return idDireccion;
    }

}
