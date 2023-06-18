package org.springframework.samples.nt4h.card.hero

import org.springframework.samples.nt4h.capacity.Capacity
import org.springframework.samples.nt4h.card.Card
import org.springframework.samples.nt4h.card.ability.Ability
import javax.persistence.*

@Entity
data class Hero(
    // Attributes -------------------------------------------------------------
    var health: Int = 0,
    @Enumerated(EnumType.STRING)
    var role: Role = Role.UNDEFINED,

    // Relationships ----------------------------------------------------------
    @ManyToMany(cascade = [CascadeType.ALL])
    var abilities: MutableList<Ability> = mutableListOf(),
    @ManyToMany(cascade = [CascadeType.ALL])
    var capacities: MutableList<Capacity> = mutableListOf()
) : Card()
