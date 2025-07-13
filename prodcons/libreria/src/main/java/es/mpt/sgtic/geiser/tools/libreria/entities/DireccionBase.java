package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class DireccionBase {
    @Column(name="CD_PAIS")
    String cdPais;
    @Column(name="CD_PROVINCIA")
    String cdProvincia;
    @Column(name="CD_MUNICIPIO")
    String cdMunicipio;
    @Column(name="DS_DIRECCION")
    String direccion;
    @Column(name="CD_POSTAL")
    String cdPostal;
    @Column(name="DS_CORREO_ELEC")
    String correo;
    @Column(name="DS_TELEFONO")
    String telefono;
    @Column(name="DS_DIR_ELEC")
    String dirElectronica;
    @Column(name="CD_CANAL_NOTIFICA")
    String canal;

    public String getCdPais() {
        return cdPais;
    }

    public void setCdPais(String cdPais) {
        this.cdPais = cdPais;
    }

    public String getCdProvincia() {
        return cdProvincia;
    }

    public void setCdProvincia(String cdProvincia) {
        this.cdProvincia = cdProvincia;
    }

    public String getCdMunicipio() {
        return cdMunicipio;
    }

    public void setCdMunicipio(String cdMunicipio) {
        this.cdMunicipio = cdMunicipio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCdPostal() {
        return cdPostal;
    }

    public void setCdPostal(String cdPostal) {
        this.cdPostal = cdPostal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDirElectronica() {
        return dirElectronica;
    }

    public void setDirElectronica(String dirElectronica) {
        this.dirElectronica = dirElectronica;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }
}
