package org.springframework.samples.nt4h.card.ability

import org.springframework.samples.nt4h.model.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
data class Deck(
    // Relationships ----------------------------------------------------------
    @OneToMany(cascade = [CascadeType.ALL])
    var inHand: MutableList<AbilityInGame> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    var inDeck: MutableList<AbilityInGame> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    var inDiscard: MutableList<AbilityInGame> = mutableListOf()
) : BaseEntity()
