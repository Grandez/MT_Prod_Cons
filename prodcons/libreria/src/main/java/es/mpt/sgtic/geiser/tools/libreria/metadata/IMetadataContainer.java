package es.mpt.sgtic.geiser.tools.libreria.metadata;

import java.util.HashMap;

public interface IMetadataContainer {
    public HashMap<Long, MetadataData> getMetadataAsientos();
    public MetadataData                getMetadataAsiento();
    public HashMap<Long, MetadataData> getMetadataAnexos();
    public HashMap<Long, MetadataData> getMetadataJustificantes();

}
