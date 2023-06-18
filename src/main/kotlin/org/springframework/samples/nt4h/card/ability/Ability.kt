package org.springframework.samples.nt4h.card.ability


import org.hibernate.validator.constraints.Range
import org.springframework.samples.nt4h.card.Item
import org.springframework.samples.nt4h.card.TimesUsed
import org.springframework.samples.nt4h.card.hero.Role
import javax.persistence.Entity
import javax.validation.constraints.Min

@Entity
data class Ability(
    // Attributes -------------------------------------------------------------
    var role: Role = Role.UNDEFINED,
    @Range(min = 0, max = 4)
    var attack: Int = 0,
    @Min(1)
    var quantity: Int = 1,
    var pathName: String = "",
    var limit: TimesUsed = TimesUsed.UNDEFINED,
) : Item()
