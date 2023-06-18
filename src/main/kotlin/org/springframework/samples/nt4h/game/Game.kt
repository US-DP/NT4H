package org.springframework.samples.nt4h.game

import org.springframework.samples.nt4h.model.NamedEntity
import org.springframework.samples.nt4h.player.Player
import org.springframework.samples.nt4h.statistics.Statistic
import org.springframework.samples.nt4h.turn.Phase
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Game(
    // Attributes -------------------------------------------------------------
    var startDate: LocalDateTime = LocalDateTime.now(),
    var finishDate: LocalDateTime? = null,
    var maxPlayers: Int = 4,
    var password: String? = null,
    var isActive: Boolean = true,
    @Enumerated
    var mode: Mode = Mode.UNDEFINED,
    @Enumerated
    var accessibility: Accessibility? = Accessibility.PUBLIC,
    @Enumerated
    var currentPhase: Phase? = Phase.START,

    // Relationships ----------------------------------------------------------
    @OneToOne(cascade = [CascadeType.ALL])
    var currentPlayer: Player? = null,
    @OneToOne(cascade = [CascadeType.ALL])
    var statistic: Statistic? = null,
    @Embedded
    var gameParticipants: GameParticipants? = null
) : NamedEntity() {

    val isUniClass: Int
        get() = if (mode == Mode.UNI_CLASS) 1 else 2
}
