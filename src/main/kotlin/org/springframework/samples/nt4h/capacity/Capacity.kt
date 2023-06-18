package org.springframework.samples.nt4h.capacity

import org.springframework.samples.nt4h.model.BaseEntity
import javax.persistence.Entity
import javax.persistence.Enumerated

@Entity
data class Capacity(
    @Enumerated
    var stateCapacity: StateCapacity = StateCapacity.PRUEBA,
    var lessDamage: Boolean = false
) : BaseEntity()
