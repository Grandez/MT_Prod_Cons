package es.mpt.sgtic.geiser.tools.libreria.migrators;


import es.mpt.sgtic.geiser.tools.libreria.base.Clonador;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;
import es.mpt.sgtic.geiser.tools.libreria.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class Personas {
    Config cfg = null;
    EntityManager em  = null;
    Long          id  = null;

    public Personas() {
        this.cfg = Config.getInstance();
    }
    public void      procesaInteresados   (EntityManager emf, long id) throws Exception {
        String sql = "SELECT i FROM InteresadoOld i WHERE i.cdAsiento = :idAsiento";
        this.em = emf;

        Query qry = em.createQuery(sql);
        qry.setParameter("idAsiento", id);
        for (InteresadoOld interesado : (List<InteresadoOld>) qry.getResultList()) {
            InteresadoNew nuevo = Clonador.clone(interesado, InteresadoNew.class);
            nuevo.setTpPersona(nuevo.getDsRazonSocial() == null ? '2' : '1');
            procesaDireccion(interesado.getCdDireccion());
            em.persist(nuevo);
        }
    }
    private void      procesaDireccion     (Long idDir) throws Exception {
        String sql = "SELECT i FROM DireccionOld i WHERE i.id = :idDireccion";
        String var = null;
        if (id == null) return;
        if (id == 1) return; // Para pruebas

        TypedQuery<DireccionOld> qry = em.createQuery(sql, DireccionOld.class);
        qry.setParameter("idDireccion", idDir);

        DireccionOld dir = qry.getSingleResult();
        if (dir == null) return;

        DireccionNew nuevo = Clonador.clone(dir, DireccionNew.class);
        var = nuevo.getCanal();
        if (var != null) nuevo.setCanal(var.compareTo("01") == 0 ? "1" : "2");
        em.persist(nuevo);
    }
}
