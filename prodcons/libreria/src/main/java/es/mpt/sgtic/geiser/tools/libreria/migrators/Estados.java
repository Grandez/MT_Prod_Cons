package es.mpt.sgtic.geiser.tools.libreria.migrators;


import es.mpt.sgtic.geiser.tools.libreria.base.Clonador;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;
import es.mpt.sgtic.geiser.tools.libreria.entities.AsientoNew;
import es.mpt.sgtic.geiser.tools.libreria.entities.EstadoAsientoNew;
import es.mpt.sgtic.geiser.tools.libreria.entities.EstadoAsientoOld;
import es.mpt.sgtic.geiser.tools.libreria.entities.MensajeControlNew;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Estados {
    Config cfg        = null;
    EntityManager em  = null;
    Long          id  = null;

    public Estados() {
        this.cfg = Config.getInstance();
    }
    public void procesaEstados (EntityManager emf, long id, AsientoNew asiento) throws Exception {
        this.em = emf;
        String var = null;
        String[] estados  = {"ERCH", "RRCH", "RCHERR"};
        String[] mensajes = {"PERCH", "PRAERCH"};

        TypedQuery<EstadoAsientoOld> qry = em.createQuery("SELECT i FROM EstadoAsientoOld i WHERE i.cdAsiento = :cdAsiento", EstadoAsientoOld.class);
        qry.setParameter("cdAsiento", id);

        for (EstadoAsientoOld estado : (List<EstadoAsientoOld>) qry.getResultList()) {
            EstadoAsientoNew nuevo = Clonador.clone(estado, EstadoAsientoNew.class);

            nuevo.setErrorTecnico('0');
            var = nuevo.getEstado();
            if (var != null) {
                boolean change = false;
                // Nuevos mensajes de control
                for (int i = 0 ; i < mensajes.length; i++) {
                    if (var.compareToIgnoreCase(estados[i]) == 0) {
                        change = procesaMensajeControl(nuevo, asiento);
                        break;
                    }
                }
                // Tipo de anotacion si no es un nuevo mensaje
                if (change == false) {
                    for (int i = 0 ; i < estados.length; i++) {
                        if (var.compareToIgnoreCase(estados[i]) == 0) {
                            change = true;
                            break;
                        }
                    }
                }
                if (change) nuevo.setCdTpAnotacion("02");
            }
            em.persist(nuevo);
        }
    }
    /*
     * Estados que pasan a mensajes de control
     */
    private boolean procesaMensajeControl(EstadoAsientoNew estado, AsientoNew asiento) {
        MensajeControlNew control = new MensajeControlNew();
        control.setId(System.currentTimeMillis());
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            // Do nothing
        }
        control.setCdIntercambio(asiento.getCdIntercambio());
        control.setCdEnRgOrigen(estado.getCdEnRgOrigen());
        control.setCdEnRgDestino(estado.getCdEnRgDestino());
        control.setNuRgEntrada(asiento.getNuRegEntrada());
        control.setFeEntrada(asiento.getFeEntrada());
        control.setCdInPrueba(asiento.getCdInPrueba());
        control.setDsMensaje(estado.getDsTpAnotacion());
        control.setNuReintentos(estado.getItEnvio() == 0 ? 999 : 0);

        control.setCdTpMensaje(estado.getEstado().compareToIgnoreCase("PERCH") == 0 ? "05" : "06");
        if (estado.getItEnvio() == 0) control.setFeEnvio(estado.getFechaEnvio());

        em.persist(control);
        return true;
    }
}
