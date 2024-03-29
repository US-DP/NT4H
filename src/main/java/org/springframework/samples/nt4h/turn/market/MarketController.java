package org.springframework.samples.nt4h.turn.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.card.product.exceptions.NotInSaleException;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.turn.exceptions.CapacitiesRequiredException;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayerException;
import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;
import org.springframework.samples.nt4h.turn.exceptions.WithOutProductException;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/market")
public class MarketController {
    private final static String VIEW_MARKET = "turns/marketPhase";
    private final static String PAGE_MARKET = "redirect:/market";
    private final static String NEXT_TURN = "redirect:/turns";

    private final UserService userService;
    private final ProductService productService;
    private final TurnService turnService;
    private final GameService gameService;
    private final Advise advise;

    @Autowired
    public MarketController(UserService userService, ProductService productService, TurnService turnService, GameService gameService, Advise advise) {
        this.userService = userService;
        this.productService = productService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.advise = advise;
    }

    @ModelAttribute("productsOnSale")
    public List<ProductInGame> getProductsInSell() {
        return productService.getMarket();
    }

    @GetMapping
    public String market(HttpSession session, ModelMap modelMap, HttpServletRequest request) {
        advise.getMessage(session, modelMap);
        advise.keepUrl(session, request);
        return VIEW_MARKET;
    }

    @PostMapping
    public String buyProduct(Turn turn) throws NoCurrentPlayerException, NoMoneyException, NotInSaleException, WithOutProductException, CapacitiesRequiredException {
        Player player = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        ProductInGame productInGame = turn.getCurrentProduct();
        if (loggedPlayer != player)
            throw new NoCurrentPlayerException();
        if (productInGame == null)
            throw new WithOutProductException();
        productService.buyProduct(player, productInGame);
        Turn oldTurn = turnService.getTurnsByPhaseAndPlayerId(Phase.MARKET, player.getId());
        oldTurn.addProduct(productInGame);
        turnService.saveTurn(oldTurn);
        advise.buyProduct(productInGame);
        return PAGE_MARKET;
    }

    @GetMapping("/next")
    public String next() {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        Game game = getGame();
        if (loggedPlayer == currentPlayer) {
            game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.REESTABLISHMENT, currentPlayer.getId()));
            gameService.saveGame(game);
            advise.passPhase(game);
        }
        return NEXT_TURN;
    }
}
