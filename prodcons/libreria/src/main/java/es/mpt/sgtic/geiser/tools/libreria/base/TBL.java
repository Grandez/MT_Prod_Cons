package es.mpt.sgtic.geiser.tools.libreria.base;

public enum TBL {
     ASIENTO("AsientoOld")
    ,INTERESADO("InteresadoOld")
    ,ANEXO("AnexoOld")
    ,ESTADO("EstadoAsientoOld")
    ,MD_ASIENTO("MetadatosAsiento")
    ,MD_ANEXO("MetadatosAnexo")
    ,DIRECCION("DireccionOld")
    ,CONTROL("MensajeControlNew")
    ;
    public final String table;

    TBL(String table) {
       this.table = table;
    }
}
