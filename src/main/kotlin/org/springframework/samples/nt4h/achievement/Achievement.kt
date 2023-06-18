package org.springframework.samples.nt4h.achievement

import org.springframework.samples.nt4h.model.NamedEntity
import javax.persistence.Entity
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

@Entity
data class Achievement(
        @Size(min = 3, max = 50)
        var name: String="",
        @Positive
        var threshold: Int=0,
        @Size(min = 3, max = 255)
        var description: String=""): NamedEntity()
