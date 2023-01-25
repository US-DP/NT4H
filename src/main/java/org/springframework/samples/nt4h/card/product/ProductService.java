package org.springframework.samples.nt4h.card.product;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.capacity.StateCapacity;
import org.springframework.samples.nt4h.card.ability.inGame.AbilityInGame;
import org.springframework.samples.nt4h.card.product.exceptions.NotInSaleException;
import org.springframework.samples.nt4h.card.product.inGame.ProductInGame;
import org.springframework.samples.nt4h.card.product.inGame.ProductInGameRepository;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.phase.exceptions.CapacitiesRequiredException;
import org.springframework.samples.nt4h.phase.exceptions.NoMoneyException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductInGameRepository productInGameRepository;
    private final PlayerService playerService;
    private static final Integer MAX_PRODUCTS_ON_SALE = 5;

    @Transactional(rollbackFor = {NoMoneyException.class, NotInSaleException.class})
    public void buyProduct(Player player, ProductInGame productInGame) throws NoMoneyException, NotInSaleException, CapacitiesRequiredException {
        ProductInGame selectedProduct = getProductInGameById(productInGame.getId());
        List<StateCapacity> stateCapacities = player.getHeroes().stream()
            .flatMap(hero -> hero.getCapacities().stream().map(Capacity::getStateCapacity))
            .collect(Collectors.toList());
        List<StateCapacity> capacitiesNeeded = productInGame.getProduct().getCapacity().stream().map(Capacity::getStateCapacity).collect(Collectors.toList());
        stateCapacities.retainAll(capacitiesNeeded);
        if (!capacitiesNeeded.isEmpty() && stateCapacities.size() == 0)
            throw new CapacitiesRequiredException();
        if (Objects.requireNonNull(selectedProduct.getStateProduct()) == StateProduct.IN_SALE) {
            if (player.getStatistic().getGold() < selectedProduct.getProduct().getPrice())
                throw new NoMoneyException();
            AbilityInGame abilityInGame = new AbilityInGame(productInGame, player);
            player.getDeck().getInDeck().add(abilityInGame);
            saveProductInGame(productInGame);
            player.getStatistic().setGold(player.getStatistic().getGold() - selectedProduct.getProduct().getPrice());
            playerService.savePlayer(player);
        } else {
            throw new NotInSaleException();
        }
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ProductInGame
    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
    public ProductInGame getProductInGameById(int id) {
        return productInGameRepository.findById(id).orElseThrow(() -> new NotFoundException("ProductInGame not found"));
    }

    @Transactional
    public void saveProductInGame(ProductInGame productInGame) {
        productInGameRepository.save(productInGame);
    }

    @Transactional
    public void deleteProductInGame(ProductInGame productInGame) {
        productInGame.onDeleteSetNull();
        productInGameRepository.save(productInGame);
        productInGameRepository.delete(productInGame);
    }

    @Transactional(readOnly = true)
    public List<ProductInGame> getMarket() {
        return productInGameRepository.findAll().stream().filter(product -> product.getStateProduct().equals(StateProduct.IN_SALE)).limit(MAX_PRODUCTS_ON_SALE)
            .collect(Collectors.toList());
    }

    @Transactional
    public void addProduct(Game game) {
        List<Product> shuffledProducts = getAllProducts();
        Collections.shuffle(shuffledProducts);
        shuffledProducts.forEach(
            product -> IntStream.range(0, product.getQuantity())
                    .forEach(i -> saveProductInGame(new ProductInGame(product, game))));
    }
}
