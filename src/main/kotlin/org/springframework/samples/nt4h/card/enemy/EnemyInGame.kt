package org.springframework.samples.nt4h.card.enemy

import org.springframework.samples.nt4h.card.decorator.Decorator
import org.springframework.samples.nt4h.model.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.validation.constraints.Max

@Entity
data class EnemyInGame(
    // Attributes -------------------------------------------------------------
    @Max(value = 10)
    var actualHealth: Int = 0,
    var isNightLord: Boolean = false,
    var isDefeated: Boolean = false,
    var turnCount: Int = 0,

    // Relationships ----------------------------------------------------------
    @ManyToOne(cascade = [CascadeType.ALL])
    var enemy: Enemy? = null,
    @OneToMany(cascade = [CascadeType.ALL])
    var decorators: MutableList<Decorator> = mutableListOf()
) : BaseEntity()
