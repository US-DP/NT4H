package org.springframework.samples.nt4h.card.decorator

import org.springframework.samples.nt4h.model.BaseEntity
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Decorator(
    var name: String = "",
    var description: String = "",
    var levelRequirement: Int = 1,
    var isActive: Boolean = false,
    var duration: Int = 0,
    var effect: String = ""
): BaseEntity() {

    abstract fun activate()
    abstract fun deactivate()
    abstract fun update()
}
