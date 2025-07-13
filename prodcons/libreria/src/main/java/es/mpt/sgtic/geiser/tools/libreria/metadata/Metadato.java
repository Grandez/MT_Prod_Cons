package es.mpt.sgtic.geiser.tools.libreria.metadata;

import java.util.ArrayList;
import java.util.List;

/*
 * Metadato: Pareja Clave/Valor
 * Metadata: conjunto de metadtos
 *
 * Esta clase podria ser un par clave/valor
 * Pero al incluirle un id, lo podremos usar para obtener el valor on demand
 * Por ejemplo:
 *  id = 0 => en un par libre y se procesa tal cual
 *  id = 23 (Por ejemplo, donde 23 = HASH, podria usarse para recalcular el Hash u obtenerlo de otro sitio)
 */
public class Metadato {
    private String clave = null;
    private String valor = null;
    private List<String> valores = null;
    private int    id = 0;

    public Metadato() {

    }
    public Metadato(MetadataNames item, String value) {
        // FIXME cuidado con el ordinal como id, si altero la lista de los names puedo cambiar datos existentes de id!!
        this.id = item.ordinal();
        setMetadato(item.label, value);
    }
    public Metadato(String clave, String valor) {
        this.id = MetadataNames.getEnum(clave).ordinal();
        setMetadato(clave, valor);
    }
    public Metadato(int id, String clave, String valor) {
        this.id = id;
        setMetadato(clave, valor);
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }
    public List<String> getList() {         return valores == null ? new ArrayList<String>() : valores ;     }
    public List<String> getAsList() {
        if (valores != null) return valores;
        List<String> list = new ArrayList<>();
        if (valor != null) list.add(valor);
        return list;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*
     * Se incorpora control de listas
     * Por defecto se guarda el valor en valor
     * Si existe valor entonces es un multivalor, se guarda en la lista
     */
    private void  setMetadato(String clave, String value) {
        if (value == null) return;
        if (valor == null) {
            this.clave = clave;
            this.valor = value;
        } else {
            if (valores == null) {
                valores = new ArrayList<>();
                valores.add(valor);
            }
            valores.add(value);
        }
    }

}
