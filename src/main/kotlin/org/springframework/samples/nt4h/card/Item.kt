package org.springframework.samples.nt4h.card

import org.springframework.samples.nt4h.game.Game
import org.springframework.samples.nt4h.player.Player
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.ManyToOne

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Item(
    // Relationships ----------------------------------------------------------
    @ManyToOne
    var player: Player? = null,
    @ManyToOne
    var game: Game? = null
) : Card()
