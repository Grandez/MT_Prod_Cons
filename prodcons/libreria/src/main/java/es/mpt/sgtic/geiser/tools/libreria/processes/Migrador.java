package es.mpt.sgtic.geiser.tools.libreria.processes;

/**
 * IProceso responsable de realizar la migracion
 * Por cada registro seleccionado segun el criterio, genera:
 *   - 1 nuevo asiento
 *   - 1 nuevo estado asiento
 *   - n anexos asociados menos el fichero tecnico
 *   - n interesados
 *   - Los metadatos correspondientes
 *   Actualiza la tabla ASIENTO_OLD
 *   - 0 - No se ha realizado el proceso
 *   - 1 - Se han migrado las tablas
 *   - 2 - Se ha migrado el fichero tecnico
 *   - 3 - Se han migrado tablas y ft
 */


import es.mpt.sgtic.geiser.tools.libreria.base.Clonador;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;
import es.mpt.sgtic.geiser.tools.libreria.base.FT;
import es.mpt.sgtic.geiser.tools.libreria.entities.AsientoNew;
import es.mpt.sgtic.geiser.tools.libreria.entities.AsientoOld;
import es.mpt.sgtic.geiser.tools.libreria.migrators.Anexos;
import es.mpt.sgtic.geiser.tools.libreria.migrators.Estados;
import es.mpt.sgtic.geiser.tools.libreria.migrators.Metadatos;
import es.mpt.sgtic.geiser.tools.libreria.migrators.Personas;

import javax.persistence.EntityManager;

public class Migrador {
    Config cfg     = null;
    EntityManager em      = null;
    Long          id      = null;

    private Personas personas  = new Personas();
    private Estados estados   = new Estados();
    private Anexos anexos    = new Anexos();
    private Metadatos metadatos = new Metadatos();

    public void migra (EntityManager emf, long id) throws Exception {
        long beg = System.currentTimeMillis();
        this.em = emf;
        this.id = id;
        cfg = Config.getInstance();

        //AsientoOld old = em.getReference(AsientoOld.class, id);
        AsientoOld old = em.find(AsientoOld.class, id);

        // Registro convertido
        if (old == null) return;
        if (old.getItDestRefUnica() != null && old.getItDestRefUnica() == '0' ) return;

        AsientoNew nuevo = Clonador.clone(old, AsientoNew.class);

        nuevo.setCdModoRegistro("01");
        nuevo.setFeRegPresentacion(nuevo.getFeEntrada());
        nuevo.setItDestRefUnica('0');

        em.persist(nuevo);

                personas.procesaInteresados  (em, id);
                estados.procesaEstados       (em, id, nuevo);
        FT ft = anexos.procesaAnexos         (em, id);
                metadatos.procesaMetadatos   (em, id, ft);
    }
}



