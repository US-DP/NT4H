//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package org.springframework.samples.nt4h.card.product;

import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
@TestInstance(Lifecycle.PER_CLASS)
public class ProductServiceTest {
    @Autowired
    protected ProductService productService;
    @Autowired
    protected GameService gameService;
    @Autowired
    private UserService userService;

    @Autowired
    protected PlayerService playerService;
    protected ProductInGame productInGame;
    private Integer idProductInGame;
    private Integer idGame;
    private Integer idPlayer;


    @Test
    void shouldFindAllProduct() {
        List<Product> list = this.productService.getAllProducts();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(9, list.size());
        Assertions.assertFalse(list.isEmpty());
    }



    @Test
    void shouldUpdateProductInGame() {
        ProductInGame updatedProductInGame = this.productService.getProductInGameById(idProductInGame);
        StateProduct newStateProduct = StateProduct.PLAYER;
        updatedProductInGame.setStateProduct(newStateProduct);
        productService.saveProductInGame(updatedProductInGame);
        updatedProductInGame = this.productService.getProductInGameById(idProductInGame);
        Assertions.assertEquals(updatedProductInGame.getStateProduct(), newStateProduct);
    }
}

