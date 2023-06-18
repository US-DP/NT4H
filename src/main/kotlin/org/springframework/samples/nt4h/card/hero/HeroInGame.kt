package org.springframework.samples.nt4h.card.hero

import org.springframework.samples.nt4h.model.BaseEntity
import org.springframework.samples.nt4h.player.Player
import javax.persistence.*
import javax.validation.constraints.Max

@Entity
data class HeroInGame(
    // Attributes -------------------------------------------------------------
    @Max(3)
    var health: Int = 0,
    var isAlive: Boolean = true,
    var isDefending: Boolean = false,
    var isStunned: Boolean = false,
    var score: Int = 0,

    // Relationships ----------------------------------------------------------
    @ManyToOne(cascade = [CascadeType.ALL])
    var hero: Hero? = null,
    @ManyToOne(cascade = [CascadeType.ALL])
    var player: Player? = null
) : BaseEntity()
