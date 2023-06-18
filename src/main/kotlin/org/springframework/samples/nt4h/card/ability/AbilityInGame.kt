package org.springframework.samples.nt4h.card.ability

import org.springframework.samples.nt4h.card.product.ProductInGame
import org.springframework.samples.nt4h.model.BaseEntity
import org.springframework.samples.nt4h.player.Player
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.validation.constraints.Positive


@Entity
data class AbilityInGame(
    // Attributes -------------------------------------------------------------
    @Positive
    var timesUsed: Int = 0,
    @Positive
    var attack: Int = 0,
    @Enumerated
    var abilityCardType: AbilityCardType = AbilityCardType.UNDEFINED,

    // Relationships ----------------------------------------------------------
    @ManyToOne
    var ability: Ability? = null,
    @ManyToOne
    var player: Player? = null,
) : BaseEntity() {

    fun isAvailable(): Boolean {
        return ability?.limit?.haveMoreUses(timesUsed) ?: false
    }
}
