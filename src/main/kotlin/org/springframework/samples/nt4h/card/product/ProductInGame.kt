package org.springframework.samples.nt4h.card.product

import org.springframework.samples.nt4h.card.TimesUsed
import org.springframework.samples.nt4h.game.Game
import org.springframework.samples.nt4h.model.NamedEntity
import org.springframework.samples.nt4h.player.Player
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.validation.constraints.Positive

@Entity
data class ProductInGame(
    // Attributes -------------------------------------------------------------
    @Positive
    var timesUsed: Int = 0,
    @Enumerated
    var stateProduct: StateProduct = StateProduct.UNDEFINED,
    // var quantityRemaining: Int = 0, // TODO: Cambiar por un enumerado
    var isActive: Boolean = true,

    // Relationships ----------------------------------------------------------
    @ManyToOne(cascade = [CascadeType.ALL])
    var product: Product? = null,
    @ManyToOne(cascade = [CascadeType.ALL])
    var player: Player? = null
) : NamedEntity() {

    fun isAvailable(): Boolean {
        return product?.limit?.haveMoreUses(timesUsed) ?: false
    }
}
