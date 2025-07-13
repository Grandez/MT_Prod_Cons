package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LI_METADATOS_ANEXO")
public class MetadatosAnexo extends MetadatosBase {
    @Id
    @Column(name="CD_METADATO_ANEXO")
    Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
