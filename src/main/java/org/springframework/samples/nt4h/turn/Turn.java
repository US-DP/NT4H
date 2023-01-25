package org.springframework.samples.nt4h.turn;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.samples.nt4h.card.ability.inGame.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.inGame.EnemyInGame;
import org.springframework.samples.nt4h.card.product.inGame.ProductInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.phase.Phase;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.*;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Entity
@Getter
@Setter
// TODO: Replantear esta calse. No tiene sentido que tenga una lista de abilities, enemies y products
public class Turn extends BaseEntity implements Jsonable {

    public Turn(Player player, Phase phase) {
        this.player = player;
        this.game = player.getGame();
        this.phase = phase;
        usedAbilities = Lists.newArrayList();
        usedEnemies = Lists.newArrayList();
        usedProducts = Lists.newArrayList();
        player.getTurns().add(this);
    }

    public Turn() {
        usedAbilities = Lists.newArrayList();
        usedEnemies = Lists.newArrayList();
        usedProducts = Lists.newArrayList();
    }

    @Enumerated
    private Phase phase;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<AbilityInGame> usedAbilities;

    @ManyToOne(cascade = CascadeType.ALL)
    private AbilityInGame currentAbility;

    @ManyToOne(cascade = CascadeType.ALL)
    private EnemyInGame currentEnemy;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<EnemyInGame> usedEnemies;

    @ManyToOne(cascade = CascadeType.ALL)
    private ProductInGame currentProduct;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductInGame> usedProducts;

    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player player;

    @Override
    public String toJson() {
        JsonObject json = new JsonObject();
        json.put("phase", getPhase().toString());
        return json.toJson();
    }

    @Override
    public void toJson(Writer writer) {
        try {
            writer.write(toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

