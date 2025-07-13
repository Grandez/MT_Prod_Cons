package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.*;

@Entity
@Table(name = "LI_MENSAJE_CONTROL")
public class MensajeControlNew extends MensajeControlBase {
    @Id
    @Column(name="CD_MENSAJE_CONTROL")
    @GeneratedValue(generator="LI_MENSAJE_CONTROL_SEQ")
    @SequenceGenerator(name="LI_MENSAJE_CONTROL_SEQ",sequenceName="LI_MENSAJE_CONTROL_SEQ")
    public Long id;

    public Long getId()        { return id;    }
    public void setId(Long id) { this.id = id; }
}
