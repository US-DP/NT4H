package org.springframework.samples.nt4h.card

import org.hibernate.validator.constraints.URL
import org.springframework.samples.nt4h.model.NamedEntity
import javax.persistence.MappedSuperclass
import javax.validation.constraints.Min

@MappedSuperclass
open class Card(
    @URL
    var frontImage: String = "",

    @URL
    var backImage: String = "",
) : NamedEntity()
