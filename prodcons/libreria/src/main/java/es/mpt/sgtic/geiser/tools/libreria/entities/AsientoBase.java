package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Date;

@MappedSuperclass
public class AsientoBase {
    @Column(name="NU_RG_ENTRADA")
    protected String nuRegEntrada;
    @Column(name="FE_ENTRADA")
    protected Date feEntrada;
    @Column(name="CD_UN_TR_ORIGEN")
    protected String cdUnTrOrigen;
    @Column(name="DS_UN_TR_ORIGEN")
    protected String dsUnTrOrigen;
    @Column(name="DS_RESUMEN")
    protected String dsResumen;
    @Column(name="CD_ASUNTO")
    protected String cdAsunto;
    @Column(name="RF_EXTERNA")
    protected String rfExterna;
    @Column(name="NU_EXPEDIENTE")
    protected String nuExpediente;
    @Column(name="CD_TP_TRANSPORTE")
    protected String cdTpTransporte;
    @Column(name="NU_TRANSPORTE")
    protected String nuTransporte;
    @Column(name="CD_INTERCAMBIO")
    protected String cdIntercambio;
    @Column(name="CD_TP_REGISTRO")
    protected String cdTpRegistro;
    @Column(name="CD_DOC_FISICA")
    protected String cdDocFisica;
    @Column(name="DS_OBSERVACIONES")
    protected String dsObservaciones;
    @Column(name="CD_IN_PRUEBA")
    protected String cdInPrueba;
    @Column(name="CD_EN_RG_INICIO")
    protected String cdEnRgInicio;
    @Column(name="DS_EN_RG_INICIO")
    protected String dsEnRgInicio;
    @Column(name="TS_ENTRADA")
    protected String tsEntrada;
    @Column(name="CD_MODO_REGISTRO")
    protected String cdModoRegistro;
    @Column(name="FE_REG_PRESENTACION")
    protected Date feRegPresentacion;
    @Column(name="TS_REG_PRESENTACION")
    protected String tsRegPresentacion;
    @Column(name="CD_SIA")
    protected String cdSIA;
    @Column(name="CD_INTERCAMBIO_PREVIO")
    protected String cdIntercambioPrevio;
    @Column(name="CD_UN_TR_INICIO")
    protected String cdUnTrInicio;
    @Column(name="DS_UN_TR_INICIO")
    protected String dsUnTrInicio;
    @Column(name="IT_DEST_REF_UNICA")
    protected Character itDestRefUnica;
    @Column(name="DS_EXPONE")
    protected String dsExpone;
    @Column(name="DS_SOLICITA")
    protected String dsSolicita;

    public String getNuRegEntrada() {
        return nuRegEntrada;
    }

    public void setNuRegEntrada(String nuRegEntrada) {
        this.nuRegEntrada = nuRegEntrada;
    }

    public Date getFeEntrada() {
        return feEntrada;
    }

    public void setFeEntrada(Date feEntrada) {
        this.feEntrada = feEntrada;
    }

    public String getCdUnTrOrigen() {
        return cdUnTrOrigen;
    }

    public void setCdUnTrOrigen(String cdUnTrOrigen) {
        this.cdUnTrOrigen = cdUnTrOrigen;
    }

    public String getDsUnTrOrigen() {
        return dsUnTrOrigen;
    }

    public void setDsUnTrOrigen(String dsUnTrOrigen) {
        this.dsUnTrOrigen = dsUnTrOrigen;
    }

    public String getDsResumen() {
        return dsResumen;
    }

    public void setDsResumen(String dsResumen) {
        this.dsResumen = dsResumen;
    }

    public String getCdAsunto() {
        return cdAsunto;
    }

    public void setCdAsunto(String cdAsunto) {
        this.cdAsunto = cdAsunto;
    }

    public String getRfExterna() {
        return rfExterna;
    }

    public void setRfExterna(String rfExterna) {
        this.rfExterna = rfExterna;
    }

    public String getNuExpediente() {
        return nuExpediente;
    }

    public void setNuExpediente(String nuExpediente) {
        this.nuExpediente = nuExpediente;
    }

    public String getCdTpTransporte() {
        return cdTpTransporte;
    }

    public void setCdTpTransporte(String cdTpTransporte) {
        this.cdTpTransporte = cdTpTransporte;
    }

    public String getNuTransporte() {
        return nuTransporte;
    }

    public void setNuTransporte(String nuTransporte) {
        this.nuTransporte = nuTransporte;
    }

    public String getCdIntercambio() {
        return cdIntercambio;
    }

    public void setCdIntercambio(String cdIntercambio) {
        this.cdIntercambio = cdIntercambio;
    }

    public String getCdTpRegistro() {
        return cdTpRegistro;
    }

    public void setCdTpRegistro(String cdTpRegistro) {
        this.cdTpRegistro = cdTpRegistro;
    }

    public String getCdDocFisica() {
        return cdDocFisica;
    }

    public void setCdDocFisica(String cdDocFisica) {
        this.cdDocFisica = cdDocFisica;
    }

    public String getDsObservaciones() {
        return dsObservaciones;
    }

    public void setDsObservaciones(String dsObservaciones) {
        this.dsObservaciones = dsObservaciones;
    }

    public String getCdInPrueba() {
        return cdInPrueba;
    }

    public void setCdInPrueba(String cdInPrueba) {
        this.cdInPrueba = cdInPrueba;
    }

    public String getCdEnRgInicio() {
        return cdEnRgInicio;
    }

    public void setCdEnRgInicio(String cdEnRgInicio) {
        this.cdEnRgInicio = cdEnRgInicio;
    }

    public String getDsEnRgInicio() {
        return dsEnRgInicio;
    }

    public void setDsEnRgInicio(String dsEnRgInicio) {
        this.dsEnRgInicio = dsEnRgInicio;
    }

    public String getTsEntrada() {
        return tsEntrada;
    }

    public void setTsEntrada(String tsEntrada) {
        this.tsEntrada = tsEntrada;
    }

    public String getCdModoRegistro() {
        return cdModoRegistro;
    }

    public void setCdModoRegistro(String cdModoRegistro) {
        this.cdModoRegistro = cdModoRegistro;
    }

    public Date getFeRegPresentacion() {
        return feRegPresentacion;
    }

    public void setFeRegPresentacion(Date feRegPresentacion) {
        this.feRegPresentacion = feRegPresentacion;
    }

    public String getTsRegPresentacion() {
        return tsRegPresentacion;
    }

    public void setTsRegPresentacion(String tsRegPresentacion) {
        this.tsRegPresentacion = tsRegPresentacion;
    }

    public String getCdSIA() {
        return cdSIA;
    }

    public void setCdSIA(String cdSIA) {
        this.cdSIA = cdSIA;
    }

    public String getCdIntercambioPrevio() {
        return cdIntercambioPrevio;
    }

    public void setCdIntercambioPrevio(String cdIntercambioPrevio) {
        this.cdIntercambioPrevio = cdIntercambioPrevio;
    }

    public String getCdUnTrInicio() {
        return cdUnTrInicio;
    }

    public void setCdUnTrInicio(String cdUnTrInicio) {
        this.cdUnTrInicio = cdUnTrInicio;
    }

    public String getDsUnTrInicio() {
        return dsUnTrInicio;
    }

    public void setDsUnTrInicio(String dsUnTrInicio) {
        this.dsUnTrInicio = dsUnTrInicio;
    }

    public Character getItDestRefUnica() {
        return itDestRefUnica;
    }

    public void setItDestRefUnica(Character itDestRefUnica) {
        this.itDestRefUnica = itDestRefUnica;
    }

    public String getDsExpone() {
        return dsExpone;
    }

    public void setDsExpone(String dsExpone) {
        this.dsExpone = dsExpone;
    }

    public String getDsSolicita() {
        return dsSolicita;
    }

    public void setDsSolicita(String dsSolicita) {
        this.dsSolicita = dsSolicita;
    }
}
