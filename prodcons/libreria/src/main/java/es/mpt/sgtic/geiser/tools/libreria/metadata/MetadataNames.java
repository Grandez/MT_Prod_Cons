package es.mpt.sgtic.geiser.tools.libreria.metadata;
/*
 * Conjunto de metadatos soportados
 * Tenemos 3 atributos:
 *    - ID
 *    - Etiqueta: Nombre del metadato oficial
 *    - Ubicacion: Esta en Metadatos o es un dato almacenado en las tablas "oficiales"
 *      - true -> esta en tabla de metadatos
 *
 * Si el id es LIBRE (0) entonces no se realiza chequeo alguno
 * Si la norma SICRES cambia la etiqueta, se cambia aqui
 *
 */
public enum MetadataNames {
     LIBRE("Libre", true) // Este vale 0
    ,CREADOR("OrigenCiudadanoAdministracion")
    ,CSV( "CSV" )
    ,ELABORACION("EstadoElaboracion")
    ,FECHA("FechaCaptura")
    ,FIRMA("TipoFirma")
    ,FORMAT( "NombreFormato")
    ,HASH_CODE( "CodigoHash")
    ,HASH_ALG( "AlgoritmoHash")
    ,ID_ENI( "Identificador")
    ,IDIOMA("Idioma", true)
    ,NTI_VERSION( "versionNTI")
    ,ORGANO  ("Organo")
    ,ORGANO_JUSTIFICANTE  ("Organo")
    ,RESOLUCION("Resolucion", true)
    ,SIZE("Tamanio")
    ,TIPO_DOCUMENTAL("TipoDocumental", true)
    ;

    public final String label;
    public final boolean tabla; // Debe ir en la Tabla Metadatos?

    private MetadataNames(String label) {
        this.label = label;
        this.tabla = false;
    }
    private MetadataNames(String label, boolean tabla) {
        this.label = label;
        this.tabla = tabla;
    }

    public static MetadataNames getEnum(String value) {
        for(MetadataNames v : values()) {
            if(v.label.equalsIgnoreCase(value)) return v;
        }
        return MetadataNames.LIBRE;
    }
}
