package org.springframework.samples.nt4h.card.product

import org.hibernate.validator.constraints.Range
import org.springframework.samples.nt4h.capacity.Capacity
import org.springframework.samples.nt4h.card.Item
import org.springframework.samples.nt4h.card.TimesUsed
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.ManyToMany
import javax.validation.constraints.Min

@Entity
data class Product(
    // Attributes -------------------------------------------------------------
    @Range(min = 3, max = 8)
    var price: Int = 0,
    @Range(min = 0, max = 4)
    var attack: Int = 0,
    @Min(1)
    var quantity: Int = 0,
    var pathName: String = "",
    @Enumerated
    var limit: TimesUsed = TimesUsed.UNDEFINED,

    // Relationships ----------------------------------------------------------
    @ManyToMany(cascade = [CascadeType.ALL])
    var capacity: MutableList<Capacity> = mutableListOf()
) : Item()
