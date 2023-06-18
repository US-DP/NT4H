package org.springframework.samples.nt4h.card.product.inGame;

import lombok.*;
import org.springframework.samples.nt4h.card.product.Product;
import org.springframework.samples.nt4h.card.product.StateProduct;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.model.NamedEntity;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "products_in_game")
public class ProductInGame extends NamedEntity {

    public ProductInGame(Product product,Game game) {
        this.product = product;
        this.stateProduct = StateProduct.IN_SALE;
        this.game = game;
        setName(product.getName());
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    private StateProduct stateProduct;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player player;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;

    public void onDeleteSetNull() {
        product = null;
        player = null;
        game = null;
    }
}
