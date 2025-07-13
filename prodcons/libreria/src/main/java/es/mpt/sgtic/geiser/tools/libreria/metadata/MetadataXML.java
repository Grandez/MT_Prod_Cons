package es.mpt.sgtic.geiser.tools.libreria.metadata;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MetadataXML {
    int segmento = -1;
    long idNode = 0;
    int bloque = -1;
    String clave = null;
    String valor = null;
    MetadataContainer work = new MetadataContainer();
    HashMap<Long, String> hash = new HashMap<>();

    public MetadataContainer procesarXML(Path path, long idAsiento) {
        try {
            String xml = obtenerFicheroTecnico(path);
            Document doc = parseXML(xml);
            doc.getDocumentElement().normalize();
            processXMLDoc(doc);
            return processContainer();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return null;
    }
    /*
    public MetadataContainer procesarXML(TbggeiAsiento asiento) {
        try {
            String xml = obtenerFicheroTecnico(asiento);
            Document doc = parseXML(xml);
            doc.getDocumentElement().normalize();
            processXMLDoc(doc);
            return processContainer(asiento);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return null;
    }
    */
    private Document parseXML (String xml)  throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
    private void processXMLDoc ( Document doc) {
        Node root = getRoot(doc);
        if (root == null) return;
        processNodes(root);
    }
    private void processNodes(Node root) {
        boolean terminal = false;
        Node node;
        NodeList childNodes = root.getChildNodes();
        for(int i = 0; i < childNodes.getLength(); i++) {
            terminal = false;
            node = childNodes.item(i);
            if (node.getNodeName().startsWith("#")) continue;

            if (node.getNodeName().toLowerCase().contains("metadatos")) {
                resetMetadato(0);
                if (segmento == MetadataCtes.ANEXO) bloque = MetadataCtes.ENI;
            }
            if (node.getNodeName().toLowerCase().contains("metadatosregistro")) {
                segmento = resetMetadato(MetadataCtes.ASIENTO);
                idNode = MetadataCtes.ASIENTO;
            }
            if (node.getNodeName().toLowerCase().contains("metadatosanexo"))  {
                segmento = resetMetadato(MetadataCtes.ANEXO);
                idNode++;
            }
            if (node.getNodeName().toLowerCase().contains("metadatosgenerales"))   {
                bloque = resetMetadato(MetadataCtes.GENERAL);
            }
            if (node.getNodeName().toLowerCase().contains("metadatosadicionales")) {
                bloque = resetMetadato(MetadataCtes.ADICIONAL);
            }
            if (node.getNodeName().toLowerCase().contains("atributo")) {
                putMetadato(0);
            }
            if (node.getNodeName().toLowerCase().contains("campo")) {
                clave = node.getFirstChild().getNodeValue();
                terminal = true;
            }
            if (node.getNodeName().toLowerCase().contains("valor")) {
                valor = node.getFirstChild().getNodeValue();
                if (clave.compareToIgnoreCase(MetadataNames.HASH_CODE.label) == 0) {
                    hash.put(idNode, valor);
                }
                terminal = true;
            }
            if (segmento == MetadataCtes.ANEXO && bloque == MetadataCtes.ENI) {
                procesoENI(node);
            }
            if (!terminal && childNodes.item(i).hasChildNodes()) processNodes(childNodes.item(i));
        }
        putMetadato(0);
    }

    /*
     * Se cogen todos los metadatos del "saco" y se ponen en su sitio
     * La clave esta formada por 3 campos: nnnnn s b
     * nnnnn es un contador. Donde 1 es Asiento y los demas son anexos (por ahora)
     * s     es el segmento: general, adicional
     */
    private MetadataContainer processContainer() {
        MetadataContainer container = new MetadataContainer();
        long[] keyOld;
        long keyNew;
//        HashMap<String, Long> hashID = obtieneIDAnexos(asiento.getTbggeiAnexos());
        for (Map.Entry<Long, MetadataSet> set : work.getBag().entrySet()) {
            keyOld = work.splitKey(set.getKey());
            switch ((int) keyOld[1]) {
                case MetadataCtes.ASIENTO:
                    // keyOld[0] = idAsiento;
                    break;
                default:
                    String h = hash.get(keyOld[1]);
//                    Long idAnexo = hashID.get(h);
//                    Long idAnexo = getIdAnexo(asiento.getId(), keyOld);
                    // keyOld[0] = getIdAnexo(idAsiento, keyOld);
                    break;
            }
            keyNew = work.joinKey(keyOld);
            container.setMetadataSet(keyNew, work.getMetadataSet(set.getKey()));
        }
        container.organize();
        return container;
    }
    /*
    private HashMap<String, Long> obtieneIDAnexos(Set<TbggeiAnexo> anexos) {
        HashMap<String, Long> hash = new HashMap<>();
        for (TbggeiAnexo anexo : anexos) {
            TbggeiAnexoTemporal temp = anexo.getAnexoTemporal();
            if (temp != null) {
                String value = temp.getCodigoHashSeguro();
                if (value != null) hash.put(value, anexo.getId());
            }
        }
        return hash;
    }

     */
    private Node getRoot(Document doc) {
        Node node;
        NodeList hijos = doc.getChildNodes();
        for(int i = 0; i < hijos.getLength(); i++) {
            node = hijos.item(i);
            if (node.getNodeName().toLowerCase().contains("ficherotecnico")) {
                return node;
            }
        }
        return null;
    }
    private int resetMetadato(int id) {
        if (clave != null && valor != null)
            putMetadato(id);
        clave = null;
        valor = null;
        return id;
    }
    private int putMetadato(int id) {
        if (clave == null || valor == null) return id;

        MetadataSet ms = work.getMetadataSet(work.joinKey(idNode, segmento, bloque));
        ms.put(new Metadato(clave, valor));
        clave = null;
        valor = null;
        return id;
    }
    private void procesoENI(Node node) {
        String name = node.getNodeName();
        if (name.toLowerCase().contains("metadatos")) return;
        String[] toks = name.split(":");
        clave = toks[toks.length - 1];
        Node parent = node.getFirstChild();
        valor = (parent == null) ? null : parent.getNodeValue();
    }

    public String obtenerFicheroTecnico(Path path) {

        try {
            return new String( Files.readAllBytes(path));
        } catch (IOException e) {
            System.err.println("ERROR EN OBTENER FICHERO TECNICO: " + path.toString());
            e.printStackTrace();
        }
        return null;
    }
    /*
     * para las pruebas masivas
     * En la vida real los busca a partir de datos del anexo
     * Simulamos que cada x anexos se produce un error de identificacion
     * Ese anexo se identificara en negativo como (asiento * 100) + numero de anexo
     * Asi se pueden identificar claramente los errores
     */
    private Long getIdAnexo(Long idAsiento, long[] keyOld) {
        if (keyOld[0] % 3 == 0)
            return ((idAsiento * 100) + keyOld[0]) * -1;
        // Aqui el id del Anexo es simplemente el orden en que lo ha encontrado
        return keyOld[0];
    }
}

