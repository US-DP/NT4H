package org.springframework.samples.nt4h.statistics

import org.springframework.samples.nt4h.model.BaseEntity
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Enumerated

@Entity
data class GameEvent(
    // Attributes -------------------------------------------------------------
    @Enumerated
    var eventType: EventType? = EventType.UNDEFINED,
    var eventDate: LocalDateTime? = LocalDateTime.now(),
    var eventDescription: String? = ""
) : BaseEntity()
