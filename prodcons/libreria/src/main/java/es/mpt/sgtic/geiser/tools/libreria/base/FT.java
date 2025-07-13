package es.mpt.sgtic.geiser.tools.libreria.base;

import java.util.HashMap;

public class FT {
    public Long   idAsiento;
    public String FTName;
    public String ruta;
    public HashMap<String, Long> mapa = new HashMap<String, Long>();
    public FT() {}
    public FT(Long id) {
        this.idAsiento = id;
    }
}
