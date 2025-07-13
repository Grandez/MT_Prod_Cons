package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Date;

@MappedSuperclass
public class EstadoAsientoBase {
    @Column(name="CD_ASIENTO")
    protected Long cdAsiento;
    @Column(name="CD_ESTADO")
    protected String estado;
    @Column(name="FE_MODIFICACION")
    protected Date fechaModificacion;
    @Column(name="IT_PROCESADO")
    protected Character itProcesado;
    @Column(name="IT_ENVIO")
    protected Character itEnvio;
    @Column(name="NU_REINTENTOS")
    protected Integer reintentos;
    @Column(name="CD_TP_ANOTACION")
    protected String cdTpAnotacion;
    @Column(name="DS_TP_ANOTACION")
    protected String dsTpAnotacion;
    @Column(name="CD_EN_RG_PROCESA")
    protected String cdEnRgProcesa;
    @Column(name="CD_EN_RG_ORIGEN")
    protected String cdEnRgOrigen;
    @Column(name="DS_EN_RG_ORIGEN")
    protected String dsEnRgOrigen;
    @Column(name="CD_EN_RG_DESTINO")
    protected String cdEnRgDestino;
    @Column(name="DS_EN_RG_DESTINO")
    protected String dsEnRgDestino;
    @Column(name="CD_UN_TR_DESTINO")
    protected String cdUnTrDestino;
    @Column(name="DS_UN_TR_DESTINO")
    protected String dsUnTrDestino;
    @Column(name="NO_USUARIO")
    protected String usuario;
    @Column(name="CT_USUARIO")
    protected String correo;
    @Column(name="AP_VERSION")
    protected String aplicacion;
    @Column(name="FE_ENTRADA_DESTINO")
    protected Date fechaEntrada;
    @Column(name="NU_RG_ENTRADA_DESTINO")
    protected String nuRgEntradaDestino;
    @Column(name="FE_ENVIO")
    protected Date fechaEnvio;
    @Column(name="ERROR_TECNICO")
    protected Character errorTecnico;
    @Column(name="DS_ERROR_TECNICO")
    protected String errorTecnicoDesc;

    public Long getCdAsiento() {
        return cdAsiento;
    }

    public void setCdAsiento(Long cdAsiento) {
        this.cdAsiento = cdAsiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Character getItProcesado() {
        return itProcesado;
    }

    public void setItProcesado(Character procesado) {
        this.itProcesado = procesado;
    }

    public Character getItEnvio() {
        return itEnvio;
    }

    public void setItEnvio(Character envio) {
        this.itEnvio = envio;
    }

    public Integer getReintentos() {
        return reintentos;
    }

    public void setReintentos(Integer reintentos) {
        this.reintentos = reintentos;
    }

    public String getCdTpAnotacion() {
        return cdTpAnotacion;
    }

    public void setCdTpAnotacion(String cdTpAnotacion) {
        this.cdTpAnotacion = cdTpAnotacion;
    }

    public String getDsTpAnotacion() {
        return dsTpAnotacion;
    }

    public void setDsTpAnotacion(String dsTpAnotacion) {
        this.dsTpAnotacion = dsTpAnotacion;
    }

    public String getCdEnRgDestino() {
        return cdEnRgDestino;
    }

    public void setCdEnRgDestino(String cdEnRgDestino) {
        this.cdEnRgDestino = cdEnRgDestino;
    }

    public String getDsEnRgDestino() {
        return dsEnRgDestino;
    }

    public void setDsEnRgDestino(String dsEnRgDestino) {
        this.dsEnRgDestino = dsEnRgDestino;
    }

    public String getCdUnTrDestino() {
        return cdUnTrDestino;
    }

    public void setCdUnTrDestino(String cdUnTrDestino) {
        this.cdUnTrDestino = cdUnTrDestino;
    }

    public String getDsUnTrDestino() {
        return dsUnTrDestino;
    }

    public void setDsUnTrDestino(String dsUnTrDestino) {
        this.dsUnTrDestino = dsUnTrDestino;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Character getErrorTecnico() {
        return errorTecnico;
    }

    public void setErrorTecnico(Character errorTecnico) {
        this.errorTecnico = errorTecnico;
    }

    public String getErrorTecnicoDesc() {
        return errorTecnicoDesc;
    }

    public void setErrorTecnicoDesc(String errorTecnicoDesc) {
        this.errorTecnicoDesc = errorTecnicoDesc;
    }

    public String getCdEnRgProcesa() {
        return cdEnRgProcesa;
    }

    public void setCdEnRgProcesa(String cdEnRgProcesa) {
        this.cdEnRgProcesa = cdEnRgProcesa;
    }

    public String getCdEnRgOrigen() {
        return cdEnRgOrigen;
    }

    public void setCdEnRgOrigen(String cdEnRgOrigen) {
        this.cdEnRgOrigen = cdEnRgOrigen;
    }

    public String getDsEnRgOrigen() {
        return dsEnRgOrigen;
    }

    public void setDsEnRgOrigen(String dsEnRgOrigen) {
        this.dsEnRgOrigen = dsEnRgOrigen;
    }

    public String getNuRgEntradaDestino() {
        return nuRgEntradaDestino;
    }

    public void setNuRgEntradaDestino(String nuRgEntradaDestino) {
        this.nuRgEntradaDestino = nuRgEntradaDestino;
    }
}
