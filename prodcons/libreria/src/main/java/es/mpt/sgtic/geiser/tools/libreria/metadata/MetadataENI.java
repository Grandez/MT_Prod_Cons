package es.mpt.sgtic.geiser.tools.libreria.metadata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MetadataENI {
    protected String versionNTI = "version NTI";
    protected String identificador;
    protected List<String> organo;
    protected Date fechaCaptura;
    protected boolean origenCiudadanoAdministracion;
//    protected TipoEstadoElaboracion estadoElaboracion;
//    protected TipoDocumental tipoDocumental;
    protected String id;

    public MetadataENI() {
    }

    public String getVersionNTI() {
        return this.versionNTI;
    }

    public void setVersionNTI(String value) {
        this.versionNTI = value;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(String value) {
        this.identificador = value;
    }

    public List<String> getOrgano() {
        if (this.organo == null) {
            this.organo = new ArrayList();
        }

        return this.organo;
    }

    public Date getFechaCaptura() {
        return this.fechaCaptura;
    }

    public void setFechaCaptura(Date value) {
        this.fechaCaptura = value;
    }

    public boolean isOrigenCiudadanoAdministracion() {
        return this.origenCiudadanoAdministracion;
    }

    public void setOrigenCiudadanoAdministracion(boolean value) {
        this.origenCiudadanoAdministracion = value;
    }
/*
    public TipoEstadoElaboracion getEstadoElaboracion() {
        return this.estadoElaboracion;
    }

    public void setEstadoElaboracion(TipoEstadoElaboracion value) {
        this.estadoElaboracion = value;
    }
    public TipoDocumental getTipoDocumental() {
        return this.tipoDocumental;
    }

    public void setTipoDocumental(TipoDocumental value) {
        this.tipoDocumental = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }
    void setEstadoElaboracion (String estado) {
        EnumeracionEstadoElaboracion ela = EnumeracionEstadoElaboracion.fromValue(estado);
        TipoEstadoElaboracion tipo = new TipoEstadoElaboracion();
//        tipo.setValorEstadoElaboracion(ela);
//        super.setEstadoElaboracion(tipo);
    }
    void setOrgano (List<String> organos) {
        this.organo = organos;
    }
*/
}
