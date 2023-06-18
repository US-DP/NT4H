package org.springframework.samples.nt4h.user

import org.hibernate.validator.constraints.URL
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.samples.nt4h.achievement.Achievement
import org.springframework.samples.nt4h.message.Message
import org.springframework.samples.nt4h.model.BaseEntity
import org.springframework.samples.nt4h.player.Player
import org.springframework.samples.nt4h.player.Tier
import org.springframework.samples.nt4h.statistics.Statistic
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
data class User(
    // Attributes -------------------------------------------------------------
    @Column(unique = true)
    @Size(min = 1, max = 20)
    var username: String = "",

    var password: String = "",

    var enable: String = "TRUE",

    var isConnected: Boolean = true,

    @URL
    var avatar: String = "",

    @Enumerated
    var tier: Tier = Tier.BRONZE,

    @Size(max = 100)
    var description: String = "",

    var authority: String = "USER",

    // Relationships ----------------------------------------------------------
    @OneToMany(cascade = [CascadeType.ALL])
    var achievements: List<Achievement>? = null,

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    var birthDate: LocalDate? = null,

    @OneToMany(cascade = [CascadeType.ALL])
    var friends: List<User>? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    var statistic: Statistic? = null,

    @OneToMany(cascade = [CascadeType.ALL])
    var sentMessages: List<Message> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL])
    var receivedMessages: List<Message> = mutableListOf(),

    @OneToOne(cascade = [CascadeType.ALL])
    var player: Player? = null
) : BaseEntity()
