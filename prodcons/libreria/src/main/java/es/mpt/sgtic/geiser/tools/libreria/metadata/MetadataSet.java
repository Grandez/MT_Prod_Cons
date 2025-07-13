package es.mpt.sgtic.geiser.tools.libreria.metadata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
/*
 * Contiene todos los metadatos de un bloque
 * O de manera general un conjunto de metadatos
 */
public class MetadataSet {
    HashMap<String, Metadato> metadata = new HashMap<>();
    int idBlock = 0;
    public MetadataSet() {}
    public MetadataSet(int block) {
        this.idBlock = 0;
    }
    public int getId() { return idBlock; }
    public MetadataSet put (Metadato item) {
        if (item == null) return this;
        metadata.put(item.getClave(), item);
        return this;
    }
    public Metadato getMetadato(String clave)       { return metadata.get(clave);      }
    public Metadato getMetadato(MetadataNames name) {
        return metadata.get(name.label);
    }
    public Set<Metadato> getSet() {
        Set<Metadato> set = new HashSet<Metadato>();
        set.addAll(metadata.values());
        return set;
    }

}
