package es.mpt.sgtic.geiser.tools.libreria.metadata;

import java.util.*;

/*
 * Los metadatos pertenecen a un asiento
 * Hay un conjunto de metadatos por segmento
 * Hay un conjunto de elementos por segmento
 */
public class MetadataContainer implements IMetadataContainer {
    /*
     * Vector/Array de segmentos
     * Ver: MetadataCtes.SEGMENTOS
     * Cada elemento esta en su indice
     * Ejemplo:
     *              +-------+---------+-------+------------+--------------+-------+
     *  Segmentos   | Vacio | ASIENTO | ANEXO | INTERESADO | JUSTIFICANTE | etc...|
     *              +-------+---------+-------+------------+--------------+-------+
     *                                    |
     *                     +--------+---------+--------+------+
     *   Elementos         | Anexo1 | Anexo2  | Anexo3 | .... |
     *                     +--------+---------+--------+------+
     *                                  |
     *                +--------+-----------+-----+--------+------+
     *     Bloques    | General| Adicional | ENI | libres | .... |
     *                +--------+-----------+-----+--------+------+
     *                             |
     *                +--------+-----------+-----+--------+------+
     *                |              Metadatos                   |
     *                +--------+-----------+-----+--------+------+
     *
     */
    private Vector<HashMap<Long, MetadataData>> segments = new Vector<>(MetadataCtes.SEGMENTOS + 1);
    /*
     * Contiene todos los conjuntos de metadatos (MetdataSet) por bloques
     * Es decir: generales, adicionales, ENI, etc.
     * La clave tiene tres campos que la hace unica: iiiiisb -
     *    iiii = id del objeto
     *    s    = segmento
     *    b    = bloque
     * Ejemplo: 12345623 es:
     *   id = 123456
     *   segmento: 1 = ASIENT0, 2 = ANEXO, 3 = Interesado, etc
     *   bloque:   1 = general, 2 = adicional, 3 = ENI, etc
     */
    private HashMap<Long, MetadataSet> saco = new HashMap<>(); // Todos los set de metadatos con id unico

    // el asiento al que pertenece el contenedor de metadatos
    private Long idAsiento;

    public MetadataContainer() {
        // El array son los segmentos mas 2 para controlar el 0
        for (int i = 0; i < MetadataCtes.SEGMENTOS + 2; i++) segments.add(new HashMap<Long, MetadataData>());
    }

    public MetadataContainer(Long idAsiento) {
        this.idAsiento = idAsiento;
        // El array son los segmentos mas 2 para controlar el 0
        for (int i = 0; i < MetadataCtes.SEGMENTOS + 2; i++) segments.add(new HashMap<Long, MetadataData>());
        segments.get(MetadataCtes.ASIENTO).put(idAsiento, new MetadataData());
    }

    public HashMap<Long, MetadataData> getMetadataAsientos()      { return getSegment(MetadataCtes.ASIENTO);      }

    @Override
    public MetadataData getMetadataAsiento() {
        return null;
    }

    public HashMap<Long, MetadataData> getMetadataAnexos()        { return getSegment(MetadataCtes.ANEXO);        }
    public HashMap<Long, MetadataData> getMetadataJustificantes() { return getSegment(MetadataCtes.JUSTIFICANTE); }


    /**
     * Devuelve los metadatos del segmento de asiento. Como son exclusivos de este segmento, se nombran con la palabra
     * "Registro", por contraposición al asiento, que podría confundirse con todos los metadatos del mismo.
     * @return
     */

    public MetadataData getMetadataRegistro(){
        return getSegment(MetadataCtes.ASIENTO).get(idAsiento);
    }

    public Vector<HashMap<Long, MetadataData>> getSegments() { return segments; }

    public MetadataData getMetadataOfItem(int type, long id) {
        HashMap<Long, MetadataData> map = getSegment(type);
        return (map == null) ? new MetadataData() : map.get(id);
    }

    public List<MetadataData>  getMetadata(int type) {
        List<MetadataData> lista = new ArrayList<MetadataData>();
        HashMap<Long, MetadataData> map = getSegment(type);
        if (map == null) return lista;
        for ( MetadataData item : map.values()) lista.add(item);
        return lista;
    }
    public MetadataSet getMetadataSet(Long id) {
        if (!saco.containsKey(id)) saco.put(id, new MetadataSet());
        return saco.get(id);
    }
    public void setMetadataSet(Long id, MetadataSet set) {
        saco.put(id, set);
    }

    public Map<Long, MetadataSet> getBag() {
        return saco;
    }
    /*
     * Pasa el saco de conjuntos de metadatos a su sitio
     */
    public void organize() {
        HashMap<Long, MetadataData> map;
        for ( long key : saco.keySet() ) {
            long id;
            int seg;
            int block;

            if (key < 0) { // Caso anexos no identificados
                seg = MetadataCtes.ANEXO;
                int temp = (int) ((key * -1) % 100);
                block = temp % 10;
                id = (key / 100) * -1; // Estos son segmento y bloque
                id = id /100;         // Este es el control del anexo
            } else {
                id = key / 100;
                int temp  = (int) key % 100;
                seg   = temp / 10;            // Segmento
                block = temp % 10;            // Bloque
            }

            map = getSegment(seg);
            // Crea el bloque si no existe
            if (!map.containsKey(id)) map.put(id, new MetadataData());
            // Pone los metadatos en su sitio
            MetadataData data = map.get(id).add(block, saco.get(key));
        }

    }
    /*
     * Manejo de la clave unica del saco
     * Es decir: generales, adicionales, ENI, etc.
     * La clave tiene tres campos que la hace unica: iiiiisb -
     *    iiii = id del objeto
     *    s    = segmento
     *    b    = bloque
     * Ejemplo: 12345623 es:
     *   id = 123456
     *   segmento: 1 = ASIENT0, 2 = ANEXO, 3 = Interesado, etc HASTA 9 SEGMENTOS
     *   bloque:   1 = general, 2 = adicional, 3 = ENI, etc    HASTA 9 BLOQUES
     *   En caso de que haga falta mas la clave pasa a iiiissbb
     */
    public long[] splitKey(long key) {
        long[] ids = new long[3];
        ids[0] = key / 100;
        long rem = key % 100;
        ids[1] = rem / 10;
        ids[2] = rem % 10;
        return ids;
    }
    public long joinKey(long[] keys) {
        if (keys[0] > -1) return (keys[0] * 100) + (keys[1] * 10) + keys[2];
        keys[0] *= -1;
        long temp = (keys[0] * 100) + (keys[1] * 10) + keys[2];
        return temp * -1;
    }
    public long joinKey(long id, int segment, int block) {
        if (id < 0) {
            System.out.println("para");
        }

        return (id * 100) + (segment * 10) + block;
    }
    public HashMap<Long, MetadataData> getSegment(int type) {
        // Control de no sobrepasar el tamano del array
        if (segments.capacity() < type) segments.insertElementAt(new HashMap<Long, MetadataData>(), type);
        HashMap<Long, MetadataData> data = segments.get(type);
        return data;
    }

    public boolean contieneMetadatosDeAnexos(){
        return !getSegment(MetadataCtes.ANEXO).isEmpty();
    }
}
