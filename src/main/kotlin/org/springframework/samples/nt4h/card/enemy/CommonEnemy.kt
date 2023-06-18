package org.springframework.samples.nt4h.card.enemy

import org.springframework.samples.nt4h.card.decorator.Decorator
import javax.persistence.Entity
import javax.persistence.OneToMany


@Entity
data class CommonEnemy(
    @OneToMany
    var decorator: List<Decorator> = mutableListOf()
) : Enemy()
