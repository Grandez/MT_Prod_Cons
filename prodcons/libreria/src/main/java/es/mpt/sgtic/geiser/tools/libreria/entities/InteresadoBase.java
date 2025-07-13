package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class InteresadoBase {
    @Column(name="CD_ASIENTO")
    Long cdAsiento;
    @Column(name="CD_DIRECCION")
    Long  cdDireccion;
    @Column(name="CD_REPRESENTANTE")
    Long cdRepresentante;
    @Column(name="CD_TP_DOC_INDENTIF")
    Character tpDocIdentificacion;
    @Column(name="DOC_INDENTIF")
    String docIdentificacion;
    @Column(name="DS_RAZON_SOCIAL")
    String dsRazonSocial;
    @Column(name="DS_NOMBRE")
    String dsNombre;
    @Column(name="DS_PR_APELLIDO")
    String dsPrimerApellido;
    @Column(name="DS_SG_APELLIDO")
    String dsSegundoApellido;
    @Column(name="DS_OBSERVACIONES")
    String dsObservaciones;
    @Column(name="IT_RECEPT_NOTIFIC")
    Character itReceptorNotificaciones;
    @Column(name="TP_PERSONA")
    Character tpPersona;
    @Column(name="CD_DIRECT_UNIF")
    String cdDirectorioUnificado;
    @Column(name="IT_SOLICITA_SMS")
    Character itSMS;
    @Column(name="IT_SOLICITA_MAIL")
    Character itMail;

    public Long getCdAsiento() {
        return cdAsiento;
    }

    public void setCdAsiento(Long cdAsiento) {
        this.cdAsiento = cdAsiento;
    }

    public Long getCdDireccion() {
        return cdDireccion;
    }

    public void setCdDireccion(Long cdDireccion) {
        this.cdDireccion = cdDireccion;
    }

    public Long getCdRepresentante() {
        return cdRepresentante;
    }

    public void setCdRepresentante(Long cdRepresentante) {
        this.cdRepresentante = cdRepresentante;
    }

    public Character getTpDocIdentificacion() {
        return tpDocIdentificacion;
    }

    public void setTpDocIdentificacion(Character tpDocIdentificacion) {
        this.tpDocIdentificacion = tpDocIdentificacion;
    }

    public String getDocIdentificacion() {
        return docIdentificacion;
    }

    public void setDocIdentificacion(String docIdentificacion) {
        this.docIdentificacion = docIdentificacion;
    }

    public String getDsRazonSocial() {
        return dsRazonSocial;
    }

    public void setDsRazonSocial(String dsRAzonSocial) {
        this.dsRazonSocial = dsRAzonSocial;
    }

    public String getDsNombre() {
        return dsNombre;
    }

    public void setDsNombre(String dsNombre) {
        this.dsNombre = dsNombre;
    }

    public String getDsPrimerApellido() {
        return dsPrimerApellido;
    }

    public void setDsPrimerApellido(String dsPrimerApellido) {
        this.dsPrimerApellido = dsPrimerApellido;
    }

    public String getDsSegundoApellido() {
        return dsSegundoApellido;
    }

    public void setDsSegundoApellido(String dsSegundoApellido) {
        this.dsSegundoApellido = dsSegundoApellido;
    }

    public String getDsObservaciones() {
        return dsObservaciones;
    }

    public void setDsObservaciones(String dsObservaciones) {
        this.dsObservaciones = dsObservaciones;
    }

    public Character getItReceptorNotificaciones() {
        return itReceptorNotificaciones;
    }

    public void setItReceptorNotificaciones(Character itReceptorNotificaciones) {
        this.itReceptorNotificaciones = itReceptorNotificaciones;
    }

    public Character getTpPersona() {
        return tpPersona;
    }

    public void setTpPersona(Character tpPersona) {
        this.tpPersona = tpPersona;
    }

    public String getCdDirectorioUnificado() {
        return cdDirectorioUnificado;
    }

    public void setCdDirectorioUnificado(String cdDirectorioUnificado) {
        this.cdDirectorioUnificado = cdDirectorioUnificado;
    }

    public Character getItSMS() {
        return itSMS;
    }

    public void setItSMS(Character itSMS) {
        this.itSMS = itSMS;
    }

    public Character getItMail() {
        return itMail;
    }

    public void setItMail(Character itMail) {
        this.itMail = itMail;
    }
}
