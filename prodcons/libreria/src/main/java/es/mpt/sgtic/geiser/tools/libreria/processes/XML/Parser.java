package es.mpt.sgtic.geiser.tools.libreria.processes.XML;

import es.mpt.sgtic.geiser.tools.libreria.base.Config;
import es.mpt.sgtic.geiser.tools.libreria.base.FT;
import es.mpt.sgtic.geiser.tools.libreria.metadata.*;
import es.mpt.sgtic.geiser.tools.libreria.processes.IProceso;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import es.mpt.sgtic.geiser.tools.libreria.base.exceptions.LibException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser implements IProceso {

    private Config cfg;
    long idNode  = 1;
    int segmento = -1;
    int bloque   = -1;
    String clave = null;
    String valor = null;
    int idAsiento = 1;

    Pattern pattern = Pattern.compile(".*ficheroTecnico.*\\.xml$");

    MetadataContainer work = new MetadataContainer();
    HashMap<Long, String> hash = new HashMap<>();

    public int run() throws LibException {
        cfg = Config.getInstance();

        try {
            recorrerDirectorios(Paths.get(cfg.path));
        } catch (IOException e) {
            throw new LibException("Error al acceder a los directorios: " + e.getMessage());
        }
        return 0;
    }
    public void recorrerDirectorios(Path path) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) recorrerDirectorios(entry);
                if (Files.isRegularFile(entry)) {
                    Matcher matcher = pattern.matcher(entry.toString());
                    if(!matcher.find()) continue;
                    procesarFichero(entry);
                }
            }
        } catch (Exception ex) {
            System.err.println("Fallo");
        }
    }
    private void procesarFichero (Path path) {
        MetadataXML metadataXML = new MetadataXML();
        MetadataContainer container = metadataXML.procesarXML(path, ++idAsiento);
        HashMap<String, Long> map = getMapOfContainer(container);

/*
        count++;
        asiento.newId();


        // Nos guardamos el nombre del fichero en el asiento
        HashMap<Long, MetadataData> asientos = container.getMetadataAsientos();
        MetadataData md = asientos.get(asiento.getId());
        md.getMetadatosGenerales().put(new Metadato("nombre_fichero", path.toString()));

        procesarContainer(path, container);
*/
    }

    public MetadataContainer procesarXML(FT ft) {
        try {
            String xml = obtenerFicheroTecnico(ft);
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
    public String obtenerFicheroTecnico(FT ft) {
        Path path = Paths.get("C:/nas/ficheroTecnico_comun.xml");
        // Path path = Paths.get((ft.ruta + "/" + ft.FTName).replaceAll("//", "/"));
        try {
            return new String( Files.readAllBytes(path));
        } catch (IOException e) {
            System.err.println("ERROR EN OBTENER FICHERO TECNICO: " + path.toString());
            e.printStackTrace();
        // Out of memory ( > 2 Gb) or Security (Not allowed)
        } catch (Exception ex) {
            System.err.println("ERROR de seguridad EN OBTENER FICHERO TECNICO: " + path.toString());
            ex.printStackTrace();
        }
        return null;
    }
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
    private Node   getRoot(Document doc) {
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
    private void   processNodes(Node root) {
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
    private int    resetMetadato(int id) {
        if (clave != null && valor != null) putMetadato(id);
        clave = null;
        valor = null;
        return id;
    }
    private int    putMetadato(int id) {
        if (clave == null || valor == null) return id;

        MetadataSet ms = work.getMetadataSet(work.joinKey(idNode, segmento, bloque));
        ms.put(new Metadato(clave, valor));
        clave = null;
        valor = null;
        return id;
    }
    private void   procesoENI(Node node) {
        String name = node.getNodeName();
        if (name.toLowerCase().contains("metadatos")) return;
        String[] toks = name.split(":");
        clave = toks[toks.length - 1];
        Node parent = node.getFirstChild();
        valor = (parent == null) ? null : parent.getNodeValue();
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
                    // keyOld[0] = asiento.getId();
                    break;
                default:
                    String h = hash.get(keyOld[1]);
//                    Long idAnexo = hashID.get(h);
//                    Long idAnexo = getIdAnexo(asiento.getId(), keyOld);
                    // keyOld[0] = getIdAnexo(asiento.getId(), keyOld);
                    break;
            }
            keyNew = work.joinKey(keyOld);
            container.setMetadataSet(keyNew, work.getMetadataSet(set.getKey()));
        }
        container.organize();
        return container;
    }
/*
    private TbggeiAnexo getFTAsAnexo(Set<TbggeiAnexo> anexos) {
        for (TbggeiAnexo anexo : anexos) {
            if (anexo.getDeNombrefichero()    == null) continue;
            if (anexo.getTblgeiTipoDocAnexo() == null) continue;
            String nombre = anexo.getDeNombrefichero();
            if (anexo.getDeNombrefichero().startsWith(conf.getNombreFicheroTecnico())) {
                if (anexo.getTblgeiTipoDocAnexo().getTipoDocAnexoEnum() == TipoDocAnexoEnum.FICHERO_TECNICO_INTERNO) {
                    return anexo;
                }
            }
        }
        return null;
    }

 */
    /*
     * para las pruebas masivas
     * En la vida real los busca a partir de datos del anexo
     * Simulamos que cada x anexos se produce un error de identificacion
     * Ese anexo se identificara en negativo como (asiento * 100) + numero de anexo
     * Asi se pueden identificar claramente los errores
     */
/*
    private Long getIdAnexo(Long idAsiento, long[] keyOld) {
        if (keyOld[0] % 3 == 0)
            return ((idAsiento * 100) + keyOld[0]) * -1;
        // Aqui el id del Anexo es simplemente el orden en que lo ha encontrado
        return keyOld[0];
    }

 */
    private HashMap<String, Long> getMapOfContainer(MetadataContainer container) {
        HashMap<String, Long> mapa = new HashMap<String, Long>();
        Map<Long, MetadataSet> saco = container.getBag();
        Set<Long> claves = saco.keySet();
        return mapa;
    }

}

