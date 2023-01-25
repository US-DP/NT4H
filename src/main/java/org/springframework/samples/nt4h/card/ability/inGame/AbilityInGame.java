package org.springframework.samples.nt4h.card.ability.inGame;

import lombok.*;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.card.product.StateProduct;
import org.springframework.samples.nt4h.card.product.inGame.ProductInGame;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "abilities_in_game")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AbilityInGame extends BaseEntity {

    public AbilityInGame(Ability ability, Player player) {
        this.attack = ability.getAttack();
        this.isProduct = false;
        this.ability = ability;
        this.player = player;
    }

    public AbilityInGame(ProductInGame productInGame, Player player) {
        productInGame.setStateProduct(StateProduct.PLAYER);
        productInGame.setPlayer(player);
        this.attack = productInGame.getProduct().getAttack();
        this.isProduct = true;
        this.productInGame = productInGame;
        this.player = player;
    }

    @Min(0)
    private Integer attack;

    @NotNull
    private boolean isProduct;

    @ManyToOne
    private Ability ability;

    @ManyToOne
    private Player player;

    @ManyToOne
    private ProductInGame productInGame;

    @Enumerated(EnumType.STRING)
    private AbilityCardType abilityCardType;

    public void onDeleteSetNull() {
        ability = null;
        player = null;
        if (productInGame != null) {
            productInGame.onDeleteSetNull();
        }
    }
}
