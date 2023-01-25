package org.springframework.samples.nt4h.card.enemy.inGame;

import lombok.*;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class EnemyInGame extends BaseEntity {

    public EnemyInGame(Enemy enemy) {
        this.actualHealth = enemy.getHealth();
        List<String> nighLordsNames = List.of("Gurdrug", "Roghkiller", "Shriekknifer");
        this.isNightLord = nighLordsNames.contains(enemy.getName());
        this.enemy = enemy;
    }
    @NotNull
    @Max(value = 10)
    private Integer actualHealth;

    @NotNull
    private boolean isNightLord;

    @ManyToOne
    private Enemy enemy;

    public void onDeleteSetNull() {
        enemy = null;
    }

}
