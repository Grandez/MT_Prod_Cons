package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class MensajeControlBase {

@Column(name="CD_INTERCAMBIO")
protected String cdIntercambio;
@Column(name="CD_EN_RG_ORIGEN")
protected String cdEnRgOrigen;
@Column(name="CD_EN_RG_DESTINO")
protected String cdEnRgDestino;
@Column(name="CD_TP_MENSAJE")
protected String cdTpMensaje;
@Column(name="NU_RG_ENTRADA")
protected String nuRgEntrada;
@Column(name="FE_ENTRADA")
protected Date feEntrada;
@Column(name="CD_IN_PRUEBA")
protected String cdInPrueba;
@Column(name="CD_ID_FICHERO")
protected String cdIdFichero;
@Column(name="CD_ERROR")
protected String cdError;
@Column(name="DS_MENSAJE")
protected String dsMensaje;
@Column(name="NU_REINTENTOS")
protected Integer nuReintentos;
@Column(name="FE_ENVIO")
protected Date feEnvio;

    public String getCdIntercambio() {
        return cdIntercambio;
    }

    public void setCdIntercambio(String cdIntercambio) {
        this.cdIntercambio = cdIntercambio;
    }

    public String getCdEnRgOrigen() {
        return cdEnRgOrigen;
    }

    public void setCdEnRgOrigen(String cdEnRgOrigen) {
        this.cdEnRgOrigen = cdEnRgOrigen;
    }

    public String getCdEnRgDestino() {
        return cdEnRgDestino;
    }

    public void setCdEnRgDestino(String cdEnRgDestino) {
        this.cdEnRgDestino = cdEnRgDestino;
    }

    public String getCdTpMensaje() {
        return cdTpMensaje;
    }

    public void setCdTpMensaje(String cdTpMensaje) {
        this.cdTpMensaje = cdTpMensaje;
    }

    public String getNuRgEntrada() {
        return nuRgEntrada;
    }

    public void setNuRgEntrada(String nuRgEntrada) {
        this.nuRgEntrada = nuRgEntrada;
    }

    public Date getFeEntrada() {
        return feEntrada;
    }

    public void setFeEntrada(Date feEntrada) {
        this.feEntrada = feEntrada;
    }

    public String getCdInPrueba() {
        return cdInPrueba;
    }

    public void setCdInPrueba(String cdInPrueba) {
        this.cdInPrueba = cdInPrueba;
    }

    public String getCdIdFichero() {
        return cdIdFichero;
    }

    public void setCdIdFichero(String cdIdFichero) {
        this.cdIdFichero = cdIdFichero;
    }

    public String getCdError() {
        return cdError;
    }

    public void setCdError(String cdError) {
        this.cdError = cdError;
    }

    public String getDsMensaje() {
        return dsMensaje;
    }

    public void setDsMensaje(String dsMensaje) {
        this.dsMensaje = dsMensaje;
    }

    public Integer getNuReintentos() {
        return nuReintentos;
    }

    public void setNuReintentos(Integer nuReintentos) {
        this.nuReintentos = nuReintentos;
    }

    public Date getFeEnvio() {
        return feEnvio;
    }

    public void setFeEnvio(Date feEnvio) {
        this.feEnvio = feEnvio;
    }
}
