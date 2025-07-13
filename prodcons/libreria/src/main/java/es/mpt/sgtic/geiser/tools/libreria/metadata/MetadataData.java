package es.mpt.sgtic.geiser.tools.libreria.metadata;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static es.mpt.sgtic.geiser.tools.libreria.metadata.MetadataNames.*;

/*
 * Contiene los conjuntos de metadatos asociados a "algo"
 * conjunto: general, adicional, eni, etc
 * algo: asiento, anexo, etc.
 */
public class MetadataData implements  IMetadata {
    MetadataSet[] sets = new MetadataSet[MetadataCtes.TYPES + 1];

    public MetadataData() {
        for (int i = 0; i < sets.length; i++) sets[i] = new MetadataSet(i);
    }

    public MetadataSet[] getMetadataSets()         { return sets;        }
    public MetadataSet   getMetadataSet(int block) {
        return sets[block];
    }
    public MetadataSet   getMetadatosGenerales()   { return getMetadataSet(MetadataCtes.GENERAL);   }
    public MetadataSet   getMetadatosAdicionales() { return getMetadataSet(MetadataCtes.ADICIONAL); }
    private MetadataSet  getMetadataENI()          { return getMetadataSet(MetadataCtes.ENI);       }

    public MetadataData add(int block, MetadataSet bag) {
        sets[block] = bag;
        return this;
    }

    public MetadataENI getMetadatosENI() {
        MetadataENI eni = new MetadataENI();
        MetadataSet md = getMetadataSet(MetadataCtes.ENI);
        Metadato metadato = md.getMetadato(ID_ENI);
//        if (metadato != null) eni.setId(metadato.getValor());
        metadato = md.getMetadato(NTI_VERSION);
        eni.setVersionNTI(metadato.getValor());
        metadato = md.getMetadato(ELABORACION);
//        if (metadato != null) eni.setEstadoElaboracion(metadato.getValor());
        metadato = md.getMetadato(CREADOR);
        boolean creador = false;
        if (metadato != null) creador = metadato.getValor().compareTo("0") == 0 ? false : true;
        eni.setOrigenCiudadanoAdministracion(creador);
        metadato = md.getMetadato(ORGANO);
//        if (metadato != null) eni.setOrgano(md.getMetadato(ORGANO).getList());
        metadato = md.getMetadato(TIPO_DOCUMENTAL);
//        if (metadato != null) eni.setTipoDocumental(getTipoDocumental(metadato));
        metadato = md.getMetadato(TIPO_DOCUMENTAL);
        eni.setFechaCaptura(new Date());

        return eni;
    }
    public Metadato      get(MetadataNames name) {
        for (int i = 0; i < sets.length; i++) {
            if (sets[i] == null) continue;
            MetadataSet ms = getMetadataSet(i);
            Metadato m = ms.getMetadato(name);
            if (m != null) return m;
        }
        return null;
    }
    public void    add(Metadato md)            { add(MetadataCtes.WEB, md); }
    public void    add(int block, Metadato md) {
      MetadataSet ms = getMetadataSet(block);
      ms.put(md);
    }

    private XMLGregorianCalendar fecha2gregorianCalendar(Metadato metadato) {
        if (metadato == null) return null;
        String fecha = metadato.getValor();
        // formato yyyy-mm-ddThh:mm:ssZ
        // En java 8 esto seria mas facil
        if (fecha == null) return null;
        try {
            int yy = Integer.parseInt(fecha.substring(0,4));
            int MM = Integer.parseInt(fecha.substring(5,7));
            int dd = Integer.parseInt(fecha.substring(8,10));
            int hh = Integer.parseInt(fecha.substring(11,13));
            int mm = Integer.parseInt(fecha.substring(14,16));
            int ss = Integer.parseInt(fecha.substring(17,19));
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(yy,MM,dd,hh,mm,ss);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (Exception ex) {
            return null;
        }
    }
    /*
    private TipoDocumental getTipoDocumental(Metadato metadato) {
        if (metadato == null) return TipoDocumental.fromValue(TipoDocumentalEnum.OTROS.getCodigo());
        TipoDocumentalEnum td = TipoDocumentalEnum.findByCodigo(metadato.getValor());
        return TipoDocumental.fromValue(td.getCodigo());
    }

     */
}
