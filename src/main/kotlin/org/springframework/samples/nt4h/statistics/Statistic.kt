package org.springframework.samples.nt4h.statistics

import org.hibernate.validator.constraints.Range
import org.springframework.samples.nt4h.model.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.validation.constraints.Min
import javax.validation.constraints.PositiveOrZero

@Entity
data class Statistic(
    // Attributes -------------------------------------------------------------
    var gold: Int = 0,
    var glory: Int = 0,
    var numOrcsKilled: Int = 0,
    var numWarLordKilled: Int = 0,
    var numWonGames: Int = 0,
    var numPlayedGames: Int = 0,
    var timePlayed: Int = 0,
    var numPlayers: Int = 0,
    var damageDealt: Int = 0,
    var highestScore: Int = 0,
    var experiencePoints: Int = 0,

    // Relationships ----------------------------------------------------------
    @OneToMany(cascade = [CascadeType.ALL])
    var gameEvents: List<GameEvent> = mutableListOf(),
    ) : BaseEntity()
