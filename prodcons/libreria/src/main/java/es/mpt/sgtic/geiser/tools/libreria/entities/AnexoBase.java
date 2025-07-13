package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AnexoBase {
    @Column(name="CD_ASIENTO")
    protected Long cdAsiento;
    @Column(name="DS_NOMBRE_ORIGINAL")
    protected String dsNombreOriginal;
    @Column(name="ID_FICHERO")
    protected String idFichero;
    @Column(name="CD_VALIDEZ")
    protected String cdValidez;
    @Column(name="CD_TP_DOC")
    protected String cdTipoDoc;
    @Column(name="DS_HASH")
    protected String dsHash;
    @Column(name="DS_TP_MIME")
    protected String deTipoMime;
    @Column(name="DS_RUTA_ARCHIVO")
    protected String dsRutaArchivo;
    @Column(name="CD_CSV")
    protected String cdCSV;
    @Column(name="ID_DOC_FIRMADO")
    protected String idDocFirmado;
    @Column(name="DS_OBSERVACIONES")
    protected String dsObservaciones;
    @Column(name="DS_CERTIFICADO")
    protected String dsCertificado;
    @Column(name="DS_FIRMA")
    protected String dsFirma;
    @Column(name="TS_ANEXO")
    protected String tsAnexo;
    @Column(name="DS_VAL_CERTIFICADO")
    protected String dsValCertificado;
    @Column(name="IT_JUSTIFICANTE")
    protected Character itJustificante;
    @Column(name="NU_REINTENTOS")
    protected Integer nuReintentos;
    @Column(name="RF_UNICA")
    protected String rfUnica;
    @Column(name="IT_CONTENIDO")
    protected Character itContenido;
    @Column(name="TAMANO_KB")
    protected Long tamanoKB;
    @Column(name="TIPO_FIRMA")
    protected String tipoFirma;
    @Column(name="DS_RESUMEN")
    protected String dsResumen;
    @Column(name="CD_FORMULARIO")
    protected String cdFormulario;

    public Long getCdAsiento() {
        return cdAsiento;
    }

    public void setCdAsiento(Long cdAsiento) {
        this.cdAsiento = cdAsiento;
    }

    public String getDsNombreOriginal() {
        return dsNombreOriginal;
    }

    public void setDsNombreOriginal(String dsNombreOriginal) {
        this.dsNombreOriginal = dsNombreOriginal;
    }

    public String getIdFichero() {
        return idFichero;
    }

    public void setIdFichero(String idFichero) {
        this.idFichero = idFichero;
    }

    public String getCdValidez() {
        return cdValidez;
    }

    public void setCdValidez(String cdValidez) {
        this.cdValidez = cdValidez;
    }

    public String getCdTipoDoc() {
        return cdTipoDoc;
    }

    public void setCdTipoDoc(String cdTipoDoc) {
        this.cdTipoDoc = cdTipoDoc;
    }

    public String getDsHash() {
        return dsHash;
    }

    public void setDsHash(String dsHash) {
        this.dsHash = dsHash;
    }

    public String getDeTipoMime() {
        return deTipoMime;
    }

    public void setDeTipoMime(String deTipoMime) {
        this.deTipoMime = deTipoMime;
    }

    public String getDsRutaArchivo() {
        return dsRutaArchivo;
    }

    public void setDsRutaArchivo(String dsRutaArchivo) {
        this.dsRutaArchivo = dsRutaArchivo;
    }

    public String getCdCSV() {
        return cdCSV;
    }

    public void setCdCSV(String cdCSV) {
        this.cdCSV = cdCSV;
    }

    public String getIdDocFirmado() {
        return idDocFirmado;
    }

    public void setIdDocFirmado(String idDocFirmado) {
        this.idDocFirmado = idDocFirmado;
    }

    public String getDsObservaciones() {
        return dsObservaciones;
    }

    public void setDsObservaciones(String dsObservaciones) {
        this.dsObservaciones = dsObservaciones;
    }

    public String getDsCertificado() {
        return dsCertificado;
    }

    public void setDsCertificado(String dsCertificado) {
        this.dsCertificado = dsCertificado;
    }

    public String getDsFirma() {
        return dsFirma;
    }

    public void setDsFirma(String dsFirma) {
        this.dsFirma = dsFirma;
    }

    public String getTsAnexo() {
        return tsAnexo;
    }

    public void setTsAnexo(String tsAnexo) {
        this.tsAnexo = tsAnexo;
    }

    public String getDsValCertificado() {
        return dsValCertificado;
    }

    public void setDsValCertificado(String dsValCertificado) {
        this.dsValCertificado = dsValCertificado;
    }

    public Character getItJustificante() {
        return itJustificante;
    }

    public void setItJustificante(Character itJustificante) {
        this.itJustificante = itJustificante;
    }

    public Integer getNuReintentos() {
        return nuReintentos;
    }

    public void setNuReintentos(Integer nuReintentos) {
        this.nuReintentos = nuReintentos;
    }

    public String getRfUnica() {
        return rfUnica;
    }

    public void setRfUnica(String rfUnica) {
        this.rfUnica = rfUnica;
    }

    public Character getItContenido() {
        return itContenido;
    }

    public void setItContenido(Character itContenido) {
        this.itContenido = itContenido;
    }

    public Long getTamanoKB() {
        return tamanoKB;
    }

    public void setTamanoKB(Long tamanoKB) {
        this.tamanoKB = tamanoKB;
    }

    public String getTipoFirma() {
        return tipoFirma;
    }

    public void setTipoFirma(String tipoFirma) {
        this.tipoFirma = tipoFirma;
    }

    public String getDsResumen() {
        return dsResumen;
    }

    public void setDsResumen(String dsResumen) {
        this.dsResumen = dsResumen;
    }

    public String getCdFormulario() {
        return cdFormulario;
    }

    public void setCdFormulario(String cdFormulario) {
        this.cdFormulario = cdFormulario;
    }
}