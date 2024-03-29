package org.springframework.samples.nt4h.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.card.product.StateProduct;
import org.springframework.samples.nt4h.game.exceptions.*;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {GameController.class})
@ExtendWith(SpringExtension.class)
class GameControllerTest {
    @MockBean
    private Advise advise;

    @Autowired
    private GameController gameController;

    @MockBean
    private GameService gameService;

    @MockBean
    private HeroService heroService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private UserService userService;

    private Player player;
    private Game game;
    private User user;
    private Turn turn;
    private AbilityInGame abilityInGame;
    private HeroInGame heroInGame;
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
        game.setId(null);
        game.setMaxPlayers(3);
        game.setMode(Mode.UNI_CLASS);
        game.setName("Juego Test");
        game.setPassiveOrcs(new ArrayList<>());
        game.setPassword("malo");
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
        ability.setName("Abilidad");
        ability.setPathName("Path Name");
        ability.setQuantity(1);
        ability.setRole(Role.WIZARD);


        ProductInGame productInGame = new ProductInGame();
        productInGame.setGame(new Game());
        productInGame.setId(1);
        productInGame.setName("Daga");
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
        enemy.setName("Orco");
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
        deck.setInHand(List.of(abilityInGame));
        deck.setInDeck(List.of(abilityInGame));
        player.setDeck(deck);
        game.setCurrentPlayer(player);
        turn.setCurrentAbility(abilityInGame);
        game.setCurrentTurn(turn);

        user = new User();
        user.setAuthority("DOKTOL");
        user.setAvatar("Avatar");
        user.setBirthDate(LocalDate.ofEpochDay(1L));
        user.setDescription("Wink");
        user.setEnable("Enable");
        user.setFriends(new ArrayList<>());
        user.setGame(game);
        user.setId(1);
        user.setIsConnected(true);
        user.setPassword("rocker");
        user.setPlayer(player);
        user.setReceivedMessages(new ArrayList<>());
        user.setSentMessages(new ArrayList<>());
        user.setStatistic(statistic);
        user.setTier(Tier.IRON);
        user.setUsername("The Rock");

        Hero hero= new Hero();
        hero.setCapacities(List.of(Capacity.builder().build()));
        hero.setName("The Rock");
        hero.setAbilities(List.of(ability));
        hero.setMaxUses(2);
        hero.setHealth(3);
        hero.setRole(Role.WIZARD);
        hero.setFrontImage("");
        hero.setBackImage("");

        heroInGame= new HeroInGame();
        heroInGame.setHero(hero);
        heroInGame.setPlayer(player);
        heroInGame.setHealth(3);
    }

    @Test
    void testShowCurrentGame() {
        when(userService.getLoggedUser()).thenReturn(user);
        assertEquals("games/gameLobby", gameController.showCurrentGame(new MockHttpSession(), new MockHttpServletRequest()));

    }

    @Test
    void testShowGame() throws UserInAGameException {
        when(gameService.getGameById(anyInt())).thenReturn(game);
        doNothing().when(gameService).addSpectatorToGame(any(),any());
        assertEquals("games/gameLobby",gameController.showGame(123, new ModelMap()));

    }

    @Test
    void testJoinGame()
        throws FullGameException, IncorrectPasswordException, UserHasAlreadyAPlayerException, UserInAGameException {
        when(gameService.getGameById(anyInt())).thenReturn(game);
        when(userService.getLoggedUser()).thenReturn(user);
        ModelMap model = new ModelMap();
        MockHttpSession session = new MockHttpSession();
        assertEquals("redirect:/games/current",gameController.joinGame(123, "iloveyou", model, session, new MockHttpServletRequest()));
    }

    @Test
    void testInitHeroSelectForm() {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpSession session = new MockHttpSession();
        ModelMap model = new ModelMap();
        assertEquals("games/heroSelect",gameController.initHeroSelectForm(session, model, new MockHttpServletRequest()));

    }

    @Test
    void testProcessHeroSelectForm()
        throws HeroAlreadyChosenException, PlayerIsReadyException, RoleAlreadyChosenException {
        when(userService.getLoggedUser()).thenReturn(user);
        assertEquals("redirect:/games/current",gameController.processHeroSelectForm(heroInGame));
    }

    @Test
    void testInitCreationForm() throws UserInAGameException {
        when(userService.getLoggedUser()).thenReturn(user);
        assertEquals("games/createGame", gameController.initCreationForm());
    }

    @Test
    void testProcessCreationForm() throws FullGameException {
        when(userService.getLoggedUser()).thenReturn(user);
        assertEquals("redirect:/games/current",gameController.processCreationForm(game, new BindException("Target", "Object Name")));
    }

    @Test
    void testOrderPlayers() {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpSession session = new MockHttpSession();
        assertEquals("games/preSelectOrder",gameController.orderPlayers(session, new MockHttpServletRequest()));
    }

    @Test
    void testProcessOrderPlayers() {
        when(userService.getLoggedUser()).thenReturn(user);
        when(playerService.getPlayerById(anyInt())).thenReturn(player);
        doNothing().when(playerService).deletePlayerById(anyInt());
        MockHttpSession session = new MockHttpSession();
        assertEquals("games/selectOrder", gameController.processOrderPlayers(session, new MockHttpServletRequest()));
    }


    @Test
    void testDeletePlayer() {
        when(userService.getLoggedUser()).thenReturn(user);
        when(playerService.getPlayerById(anyInt())).thenReturn(player);
        doNothing().when(playerService).deletePlayerById(anyInt());
        assertEquals("redirect:/games", gameController.deletePlayer(1));
    }
}

