package es.mpt.sgtic.geiser.tools.libreria.migrators;

import es.mpt.sgtic.geiser.tools.libreria.base.Config;
import es.mpt.sgtic.geiser.tools.libreria.base.FT;
import es.mpt.sgtic.geiser.tools.libreria.entities.MetadatosAnexo;
import es.mpt.sgtic.geiser.tools.libreria.entities.MetadatosAsiento;
import es.mpt.sgtic.geiser.tools.libreria.entities.MetadatosBase;
import es.mpt.sgtic.geiser.tools.libreria.metadata.MetadataContainer;
import es.mpt.sgtic.geiser.tools.libreria.metadata.MetadataData;
import es.mpt.sgtic.geiser.tools.libreria.metadata.MetadataSet;
import es.mpt.sgtic.geiser.tools.libreria.metadata.Metadato;
import es.mpt.sgtic.geiser.tools.libreria.processes.XML.Parser;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

public class Metadatos {
    Config cfg = null;
    EntityManager em  = null;
    Long          id  = null;

    public Metadatos () {
        this.cfg = Config.getInstance();
    }
    public void      procesaMetadatos   (EntityManager emf, long id, FT ft) throws Exception {
        this.em = emf;
        Parser parser = new Parser();
        MetadataContainer container = parser.procesarXML(ft);
        /*
         * Ahora tenemos:
         * 1 - todos los metadatos en bruto con sus hash
         * 2 - todos los anexos con su par id-hash de las tablas (FT)
         *
         * 3 - Obtenemos el par id-hash del container
         * 4 - Mapeamos el id del container al id del anexo
         */
        HashMap<String, Long> mapaContainer = getMapOfContainer(container);
        HashMap<Long, Long>   mapa          = getMapOfAnexos(mapaContainer, ft.mapa);

        procesaMetadatosAsiento (container);
        procesaMetadatosAnexos  (container, mapa);
    }
    private void procesaMetadatosAsiento (MetadataContainer container) {
        HashMap<Long, MetadataData> mda = container.getMetadataAsientos();
        // Realmente solo hay 1
        for (Map.Entry<Long, MetadataData> mdAsiento : mda.entrySet()) {
            MetadataData mdd = mdAsiento.getValue();
            for (Metadato md : mdd.getMetadatosGenerales().getSet())   grabaMetadato(md, id,'G', new MetadatosAsiento());
            for (Metadato md : mdd.getMetadatosAdicionales().getSet()) grabaMetadato(md, id,'P', new MetadatosAsiento());
        }
    }
    private void procesaMetadatosAnexos (MetadataContainer container, Map<Long, Long> mapa) {
        HashMap<Long, MetadataData> mda = container.getMetadataAnexos();
        for (Map.Entry<Long, MetadataData> mdAnexo : mda.entrySet()) {
            Long id = mapa.get(mdAnexo.getKey());
            MetadataData mdd = mdAnexo.getValue();
            for (Metadato md : mdd.getMetadatosGenerales().getSet())   grabaMetadato(md, id,'G', new MetadatosAnexo());
            for (Metadato md : mdd.getMetadatosAdicionales().getSet()) grabaMetadato(md, id,'P', new MetadatosAnexo());
        }
    }
    private void grabaMetadato(Metadato md, Long id, char tipo, Object rec) {
        MetadatosBase base = (MetadatosBase) rec;
        base.setCampo(md.getClave());
        base.setValor(md.getValor());
        base.setTipo(tipo);
        base.setCdAnexo(id);
        if (rec instanceof MetadatosAnexo) {
            ((MetadatosAnexo) rec).setId(++cfg.ids[8]);
            em.persist((MetadatosAnexo) rec);
        } else {
            ((MetadatosAsiento) rec).setId(++cfg.ids[7]);
            em.persist((MetadatosAsiento) rec);
        }
    }
    /**
     * Obtiene un mapa de Hash -> id anexo asociado
     * Si no hay hash no se mete en el mapa
     * @param container El contenedor
     * @return map hash -> id anexo
     */

    private HashMap<String, Long> getMapOfContainer(MetadataContainer container) {
        HashMap<String, Long> mapa = new HashMap<String, Long>();
        Map<Long, MetadataData> mdAnexos = container.getMetadataAnexos();
        for (Map.Entry<Long, MetadataData> mdAnexo : mdAnexos.entrySet()) {
            MetadataSet mdSet = mdAnexo.getValue().getMetadataSet(1);
            Metadato md = mdSet.getMetadato("CodigoHash");
            if (md != null) mapa.put(md.getValor(), mdAnexo.getKey());
        }
        return mapa;
    }

    /**
     * Asocia el id del Anexo real con el id ficticio del contenedor
     * @param mapaContainer mapa hash/id en container
     * @param mapaAnexos    mapa Hash/id del anexo
     * @return map id de anexo -> id container
     */
    private HashMap<Long, Long> getMapOfAnexos(HashMap<String, Long> mapaContainer, Map<String, Long>  mapaAnexos) {
        HashMap<Long, Long> mapa = new HashMap<Long, Long>();
        for (Map.Entry<String, Long> entry : mapaAnexos.entrySet()) {
            mapa.put(entry.getValue(), mapaContainer.get(entry.getKey()));
        }
        return mapa;
    }
}
