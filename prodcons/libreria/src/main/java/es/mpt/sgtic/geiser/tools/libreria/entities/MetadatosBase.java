package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class MetadatosBase {
    @Column(name="CD_ANEXO")
    protected Long cdAnexo;
    @Column(name="TP_METADATO")
    protected Character tipo;
    @Column(name="CAMPO")
    protected String campo;
    @Column(name="VALOR")
    protected String valor;

    public Long getCdAnexo() {
        return cdAnexo;
    }

    public void setCdAnexo(Long cdAnexo) {
        this.cdAnexo = cdAnexo;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
