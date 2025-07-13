package es.mpt.sgtic.geiser.tools.libreria.migrators;

import es.mpt.sgtic.geiser.tools.libreria.base.Clonador;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;
import es.mpt.sgtic.geiser.tools.libreria.base.FT;
import es.mpt.sgtic.geiser.tools.libreria.entities.AnexoNew;
import es.mpt.sgtic.geiser.tools.libreria.entities.AnexoOld;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Anexos {
    Pattern pattern = Pattern.compile(".*ficheroTecnico.*\\.xml$");

    Config cfg        = null;
    EntityManager em  = null;
    Long          id  = null;

    public Anexos() {
        this.cfg = Config.getInstance();
    }
    public FT procesaAnexos        (EntityManager emf, long id) throws Exception {
        this.em = emf;
        FT anexos = new FT(id);

        Query qry = em.createQuery("SELECT i FROM AnexoOld i WHERE i.cdAsiento = :idAsiento");
        qry.setParameter("idAsiento", id);
        for (AnexoOld anexo : (List<AnexoOld>) qry.getResultList()) {
            Matcher matcher = pattern.matcher(anexo.getDsNombreOriginal());
            if (matcher.find()) {  // Es fichero Tecnico?
                anexos.mapa.put(anexo.getDsHash(), anexo.getId());
                anexos.ruta   = anexo.getDsRutaArchivo();
                anexos.FTName = anexo.getIdFichero();
                continue;
            }

            AnexoNew nuevo = Clonador.clone(anexo, AnexoNew.class);
            anexos.mapa.put(nuevo.getDsHash(), nuevo.getId()); // Guardar el Hash para match
            switch (Integer.parseInt(nuevo.getCdTipoDoc())) {
                case 1: nuevo.setCdTipoDoc("02"); break;
                case 2: if (!nuevo.getDsNombreOriginal().toUpperCase().startsWith("JUSTIFICANTE")) nuevo.setCdTipoDoc("03");
                    break;
                default: nuevo.setCdTipoDoc("03");
            }
            em.persist(anexo);
        }
        return anexos;
    }

}
