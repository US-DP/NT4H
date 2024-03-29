package org.springframework.samples.nt4h.card.ability;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.card.product.StateProduct;
import org.springframework.samples.nt4h.game.Accessibility;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AbilityController.class})
@ExtendWith(SpringExtension.class)
class AbilityControllerTest {
    @Autowired
    private AbilityController abilityController;

    @MockBean
    private AbilityService abilityService;

    @MockBean
    private GameService gameService;

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserService userService;
    @MockBean
    private TurnService turnService;

    private Player player;
    private Game game;
    private User user;
    private Turn turn;
    private AbilityInGame abilityInGame;
    private ProductInGame productInGame;

    @BeforeEach
    void setUp() {
        Deck deck = new Deck();
        deck.setId(1);
        deck.setInDeck(new ArrayList<>());
        deck.setInDiscard(new ArrayList<>());
        deck.setInHand(new ArrayList<>());
        player = new Player();
        player.setAlive(true);
        player.setBirthDate(null);
        player.setDamageProtect(1);
        player.setDeck(new Deck());
        player.setGame(new Game());
        player.setHasEvasion(true);
        player.setHeroes(new ArrayList<>());
        player.setHost(true);
        player.setId(1);
        player.setName("Name");
        player.setNextPhase(Phase.START);
        player.setReady(true);
        player.setSequence(1);
        player.setStatistic(new Statistic());
        player.setTurns(new ArrayList<>());
        player.setWounds(1);
        turn = new Turn();
        turn.setCurrentEnemy(new EnemyInGame());
        turn.setCurrentProduct(new ProductInGame());
        turn.setGame(new Game());
        turn.setId(1);
        turn.setPhase(Phase.START);
        turn.setPlayer(new Player());
        turn.setUsedAbilities(new ArrayList<>());
        turn.setUsedEnemies(new ArrayList<>());
        turn.setUsedProducts(new ArrayList<>());

        Statistic statistic = new Statistic();
        statistic.setDamageDealt(1);
        statistic.setGlory(1);
        statistic.setGold(1);
        statistic.setId(1);
        statistic.setNumOrcsKilled(10);
        statistic.setNumPlayedGames(10);
        statistic.setNumPlayers(10);
        statistic.setNumWarLordKilled(10);
        statistic.setNumWonGames(10);
        statistic.setTimePlayed(1);
        game = new Game();
        game.setAccessibility(Accessibility.PUBLIC);
        game.setActualOrcs(new ArrayList<>());
        game.setAlivePlayersInTurnOrder(new ArrayList<>());
        game.setAllOrcsInGame(new ArrayList<>());
        game.setCurrentPlayer(player);
        game.setCurrentTurn(turn);
        game.setFinishDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game.setId(1);
        game.setMaxPlayers(3);
        game.setMode(Mode.UNI_CLASS);
        game.setName("Name");
        game.setPassiveOrcs(new ArrayList<>());
        game.setPassword("iloveyou");
        game.setPlayers(new ArrayList<>());
        game.setSpectators(new ArrayList<>());
        game.setStartDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game.setStatistic(statistic);

        Ability ability = new Ability();
        ability.setAttack(1);
        ability.setBackImage("Back Image");
        ability.setFrontImage("Front Image");
        ability.setId(1);
        ability.setMaxUses(3);
        ability.setName("Name");
        ability.setPathName("Path Name");
        ability.setQuantity(1);
        ability.setRole(Role.WIZARD);


        productInGame = new ProductInGame();
        productInGame.setGame(new Game());
        productInGame.setId(1);
        productInGame.setName("Name");
        productInGame.setPlayer(new Player());
        productInGame.setProduct(new Product());
        productInGame.setStateProduct(StateProduct.IN_SALE);
        productInGame.setTimesUsed(1);

        abilityInGame = new AbilityInGame();
        abilityInGame.setAbility(ability);
        abilityInGame.setAbilityCardType(AbilityCardType.WARRIOR);
        abilityInGame.setAttack(1);
        abilityInGame.setId(1);
        abilityInGame.setPlayer(player);
        abilityInGame.setProduct(true);
        abilityInGame.setProductInGame(productInGame);
        abilityInGame.setTimesUsed(1);


        Enemy enemy = new Enemy();
        enemy.setBackImage("Back Image");
        enemy.setFrontImage("Front Image");
        enemy.setHasCure(true);
        enemy.setHealth(1);
        enemy.setHiddenGlory(1);
        enemy.setHiddenGold(1);
        enemy.setId(1);
        enemy.setIsNightLord(true);
        enemy.setLessDamageWizard(true);
        enemy.setMaxUses(3);
        enemy.setName("Name");
        enemy.setNotHiddenGlory(1);
        enemy.setNotHiddenGold(1);

        EnemyInGame enemyInGame = new EnemyInGame();
        enemyInGame.setActualHealth(1);
        enemyInGame.setEnemy(enemy);
        enemyInGame.setId(1);
        enemyInGame.setNightLord(true);

        Product product = new Product();
        product.setAttack(1);
        product.setBackImage("Back Image");
        product.setCapacity(new ArrayList<>());
        product.setFrontImage("Front Image");
        product.setId(1);
        product.setMaxUses(3);
        product.setName("Name");
        product.setPathName("Path Name");
        product.setPrice(1);
        product.setQuantity(1);

        deck.setInDiscard(List.of(abilityInGame));
        deck.setInHand(List.of(abilityInGame, abilityInGame));
        deck.setInDeck(List.of(abilityInGame));
        player.setDeck(deck);
        game.setCurrentPlayer(player);
        turn.setCurrentAbility(abilityInGame);
        game.setCurrentTurn(turn);

        user = new User();
        user.setAuthority("JaneDoe");
        user.setAvatar("Avatar");
        user.setBirthDate(LocalDate.ofEpochDay(1L));
        user.setDescription("The characteristics of someone or something");
        user.setEnable("Enable");
        user.setFriends(new ArrayList<>());
        user.setGame(game);
        user.setId(1);
        user.setIsConnected(true);
        user.setPassword("iloveyou");
        user.setPlayer(player);
        user.setReceivedMessages(new ArrayList<>());
        user.setSentMessages(new ArrayList<>());
        user.setStatistic(statistic);
        user.setTier(Tier.IRON);
        user.setUsername("janedoe");


    }


    @Test
    void testChooseEnemy() {
        MockHttpSession session=new MockHttpSession();
        session.setAttribute("name","Name");
        session.setAttribute("nextUrl","redirect:/heroAttack/makeDamage");
        session.setAttribute("Name",List.of(2,3,4));
        assertEquals("redirect:/heroAttack/makeDamage",abilityController.chooseEnemy(turn, null,session));
    }

    @Test
    @Disabled
    void testFindInDiscard() {
        MockHttpSession session=new MockHttpSession();
        when(userService.getLoggedUser()).thenReturn(user);
        assertEquals("redirect:/heroAttack/makeDamage",abilityController.findInDiscard(turn));
    }

    @Test
    void testChooseAbilityFromDeck() {
        MockHttpSession session=new MockHttpSession();
        session.setAttribute("nextUrl","redirect:/heroAttack/makeDamage");
        assertEquals("redirect:/heroAttack/makeDamage",abilityController.chooseAbilityFromDeck(turn, session));
    }

    @Test
    void testChooseProductFromMarket() {
        MockHttpSession session=new MockHttpSession();
        session.setAttribute("nextUrl","redirect:/heroAttack/makeDamage");
        assertEquals("redirect:/heroAttack/makeDamage",abilityController.chooseProductFromMarket(turn, session));
    }


    @Test
    void testExchangeProducts() {
        MockHttpSession session=new MockHttpSession();
        session.setAttribute("inMarket",5);
        when(productService.getProductInGameById(anyInt())).thenReturn(productInGame);
        assertEquals("redirect:/heroAttack/makeDamage",abilityController.exchangeProducts(turn,session));
    }


    @Test
    @Disabled
    void testExchangeCards() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        when(abilityService.getAbilityInGameById(1)).thenReturn(abilityInGame);
        when(turnService.getTurnsByPhaseAndPlayerId(any(), anyInt())).thenReturn(turn);
        assertEquals("redirect:/heroAttack/makeDamage",abilityController.exchangeCards(turn,new MockHttpSession()));
    }

    @Test
    void testFindEffect() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        when(abilityService.getAbilityInGameById(1)).thenReturn(abilityInGame);
        when(turnService.getTurnsByPhaseAndPlayerId(any(), anyInt())).thenReturn(turn);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abilities");
        MockMvcBuilders.standaloneSetup(abilityController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(4))
            .andExpect(
                MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/abilities/Path Name"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/abilities/Path Name"));
    }

    @Test
    @Disabled
    void testLoseCard() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/abilities/loseCard");
        MockMvcBuilders.standaloneSetup(abilityController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(5))
            .andExpect(MockMvcResultMatchers.model()
                .attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser", "turn"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/heroAttack/makeDamage"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/heroAttack/makeDamage"));
    }
}
