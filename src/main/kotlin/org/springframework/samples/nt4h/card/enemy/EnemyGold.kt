package org.springframework.samples.nt4h.card.enemy

import org.hibernate.validator.constraints.Range
import org.springframework.samples.nt4h.model.BaseEntity
import javax.persistence.Entity

@Entity
data class EnemyGold(
    @Range(min = 0, max = 4)
    var hidden: Int = 0,
    @Range(min = 0, max = 4)
    var notHidden: Int = 0
): BaseEntity()
