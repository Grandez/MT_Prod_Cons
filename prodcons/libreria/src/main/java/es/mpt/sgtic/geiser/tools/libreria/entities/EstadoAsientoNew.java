package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LI_ESTADO_ASIENTO")
public class EstadoAsientoNew extends EstadoAsientoBase {
    @Id
    @Column(name="CD_ESTADO_ASIENTO")
    protected Long id;

    public Long getId()        { return id;    }
    public void setId(Long id) {
        this.id = id;
    }
}
