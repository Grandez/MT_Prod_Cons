package es.mpt.sgtic.geiser.tools.libreria.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LI_INTERESADO")
public class InteresadoNew extends InteresadoBase {
    @Id
    @Column(name="CD_INTERESADO")
    Long id;
}
