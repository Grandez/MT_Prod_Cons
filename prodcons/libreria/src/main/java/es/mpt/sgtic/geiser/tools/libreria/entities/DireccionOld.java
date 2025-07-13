package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LI_DIRECCION_OLD")
public class DireccionOld extends DireccionBase {
    @Id
    @Column(name="CD_DIRECCION")
    Long id;

    public Long getId()        { return id;    }
    public void setId(Long id) {
        this.id = id;
    }

}
