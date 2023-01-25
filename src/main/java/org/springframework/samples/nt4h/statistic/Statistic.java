package org.springframework.samples.nt4h.statistic;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Getter
@Setter
@Entity
public class Statistic extends BaseEntity {

    public Statistic() {
        this.gold = 0;
        this.glory = 0;
        this.numWarLordKilled = 0;
        this.numOrcsKilled = 0;
        this.damageDealt = 0;
        this.numPlayedGames = 0;
        this.numWonGames = 0;
        this.timePlayed = 0;
    }

    @Min(0)
    private Integer gold;

    @Min(0)
    private Integer glory;

    @Min(0)
    private Integer numOrcsKilled;

    @Min(0)
    private Integer numWarLordKilled;

    @Min(0)
    private Integer numWonGames;

    @Min(0)
    private Integer numPlayedGames;

    @Min(0)
    private Integer timePlayed;

    @Range(max = 4, min = 2)
    private Integer numPlayers;

    @Min(0)
    private Integer damageDealt;
}
