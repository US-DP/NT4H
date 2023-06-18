package org.springframework.samples.nt4h.game

import org.springframework.samples.nt4h.card.enemy.EnemyInGame
import org.springframework.samples.nt4h.player.Player
import org.springframework.samples.nt4h.user.User
import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
data class GameParticipants(
    // Relationships ----------------------------------------------------------
    @OneToMany(cascade = [CascadeType.ALL])
    var alivePlayersInTurnOrder: List<Player>? = null,
    @OneToMany(cascade = [CascadeType.ALL])
    var actualOrcs: List<EnemyInGame>? = null,
    @OneToMany(cascade = [CascadeType.ALL])
    var allOrcsInGame: List<EnemyInGame>? = null,
    @OneToMany(cascade = [CascadeType.ALL])
    var passiveOrcs: List<EnemyInGame>? = null,
    @OneToMany(cascade = [CascadeType.ALL])
    var spectators: List<User>? = null
)
