package org.springframework.samples.nt4h.card.enemy

import org.hibernate.validator.constraints.Range
import org.springframework.samples.nt4h.card.Card
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
open class Enemy(
    // Attributes -------------------------------------------------------------
    var hasCure: Boolean = false,
    var lessDamageWizard: Boolean = false,
    @Range(min = 2, max = 10)
    var health: Int = 0,

    // Relationships ----------------------------------------------------------
    @OneToOne(cascade = [CascadeType.ALL])
    var glory: EnemyGlory = EnemyGlory(),
    @OneToOne(cascade = [CascadeType.ALL])
    var gold: EnemyGold = EnemyGold()
) : Card()

