package org.springframework.samples.nt4h.player

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.samples.nt4h.card.ability.Deck
import org.springframework.samples.nt4h.card.hero.HeroInGame
import org.springframework.samples.nt4h.game.Game
import org.springframework.samples.nt4h.model.NamedEntity
import org.springframework.samples.nt4h.statistics.Statistic
import org.springframework.samples.nt4h.turn.Phase
import java.time.LocalDate
import javax.persistence.*


@Entity
data class Player(
    // Attributes -------------------------------------------------------------
    var hasEvasion: Boolean? = true,

    var sequence: Int? = -1,

    var nextPhase: Phase? = Phase.START,

    var ready: Boolean? = false,

    var host: Boolean? = false,

    var wounds: Int? = 0,

    var damageProtect: Int? = 0,

    var alive: Boolean = true,

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    var birthDate: LocalDate? = null,

    // Relationships ----------------------------------------------------------
    @OneToOne(cascade = [CascadeType.ALL])
    var statistic: Statistic? = null,
    @OneToMany(cascade = [CascadeType.ALL])
    var heroes: List<HeroInGame> = mutableListOf(),

    @ManyToOne(cascade = [CascadeType.ALL])
    var game: Game? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    var deck: Deck? = null
) : NamedEntity()
