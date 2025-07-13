package es.mpt.sgtic.geiser.tools.libreria.processes.loader;

import es.mpt.sgtic.geiser.tools.libreria.base.TBL;
import es.mpt.sgtic.geiser.tools.libreria.entities.AnexoOld;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class Anexo extends LoaderBase  {
    String[] hsh = {
            "4019ef2d9dd0617e6f7306c5cd3b9859bdb50dfbb33c98f52aefa0256f2d74eb298d167b6af8ec4ef87866a646ac85a1ed6abeb2261e2de4f5eb02067221632c"
            ,"5019ef2d9dd0617e6f7306c5cd3b9859bdb50dfbb33c98f52aefa0256f2d74eb298d167b6af8ec4ef87866a646ac85a1ed6abeb2261e2de4f5eb02067221632c"
            ,"9f91934569cbf7aaff1876cb94a91646f54f85fc51db465394e42676bf6d241680f4dfa22951cf1d77975f10c0a7c904a8e5f6b80ad5db1e61d04636c0238ad1"
            ,"0f91934569cbf7aaff1876cb94a91646f54f85fc51db465394e42676bf6d241680f4dfa22951cf1d77975f10c0a7c904a8e5f6b80ad5db1e61d04636c0238ad1"
            ,"ac702d3055de20d78d4e3a5d9aa5a47fc9ed0d86f1cd23f76c20e73e2181794b5370516cb8a85839cea69006c476ccf90b12091a0fd4f438c6aae94ac79482d9"
            ,"bc702d3055de20d78d4e3a5d9aa5a47fc9ed0d86f1cd23f76c20e73e2181794b5370516cb8a85839cea69006c476ccf90b12091a0fd4f438c6aae94ac79482d9"
            ,"6ceeb471fc1fb35546f5ff770b526cb22c3c9e4e1dd021db04b6c9319f053d3a5ee425f0efb483ff03c4d36697886140d0aafaa7a5b067472045a14840259ab7"
            ,"7ceeb471fc1fb35546f5ff770b526cb22c3c9e4e1dd021db04b6c9319f053d3a5ee425f0efb483ff03c4d36697886140d0aafaa7a5b067472045a14840259ab7"
    };

    public long load(long idAsiento) {
        long idAnexo = 0;
        int items = rnd.getInt(5);
        for (int i = 0; i <= items; i++) {
            AnexoOld anexo = new AnexoOld();
            cfg.write();
            idAnexo = cfg.inc(TBL.ANEXO.ordinal());
            anexo.setId(idAnexo);
            anexo.setCdAsiento(idAsiento);
            anexo.setTsAnexo(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()));
            anexo.setCdCSV(UUID.randomUUID().toString());
            String var = (i == items) ? "ficheroTecnico_metadatado.xml" : String.format("anexo_%s.txt", rnd.getNumber(8));
            anexo.setDsNombreOriginal(var);

            String s = String.format("O%s_%s_%s_%s_%s", rnd.getNumber(8)
                    , rnd.getNumber(2,5, 20)
                    , rnd.getNumber(8)
                    , rnd.getNumber(2)
                    , rnd.getNumber(4));
            var = String.format("O%s_%s_%s_%s_%s.txt", rnd.getNumber(8)
                    , rnd.getNumber(2,5, 20)
                    , rnd.getNumber(8)
                    , rnd.getNumber(2)
                    , rnd.getNumber(4));
            if (i == items) var = "FicheroTecnico_001.xml";
            anexo.setIdFichero(var);
            anexo.setCdValidez(rnd.getNumber(2));
            anexo.setCdTipoDoc(rnd.getNumber(2));
            anexo.setDeTipoMime("application/txt");
            anexo.setDsHash(hsh[rnd.getInt(8)]);

            var = (i == items) ? "c:/nas" : "/ruta/" + anexo.getIdFichero();
            anexo.setDsRutaArchivo(var);

            anexo.setDsObservaciones("Observaciones");
            anexo.setNuReintentos(0);
            anexo.setTamanoKB((long) rnd.getInt(6));
            cfg.em.persist(anexo);
        }
        return idAnexo;
    }

}
