package org.springframework.samples.nt4h.phase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.ability.inGame.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.deck.Deck;
import org.springframework.samples.nt4h.card.ability.deck.DeckService;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.card.enemy.inGame.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.phase.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.phase.exceptions.WithOutAbilityException;
import org.springframework.samples.nt4h.phase.exceptions.WithOutEnemyException;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.User;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/heroAttack")
public class HeroAttackController {

    private final static String NEXT_TURN = "redirect:/turns";
    private final static String PAGE_HERO_ATTACK = "redirect:/heroAttack";
    private final static String VIEW_HERO_ATTACK = "turns/attackPhase";
    private final static String PAGE_ABILITY = "redirect:/abilities";

    private final UserService userService;
    private final TurnService turnService;
    private final GameService gameService;
    private final PlayerService playerService;
    private final Advise advise;
    private final CacheManager cacheManager;
    private final DeckService deckService;

    @Autowired
    public HeroAttackController(UserService userService, TurnService turnService, GameService gameService, PlayerService playerService, Advise advise, CacheManager cacheManager, DeckService deckService) {
        this.userService = userService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.playerService = playerService;
        this.advise = advise;
        this.cacheManager = cacheManager;
        this.deckService = deckService;
    }

    @ModelAttribute("loggedUser")
    private User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("currentPlayer")
    private Player getPlayer() {
        return getLoggedUser().getGame().getCurrentPlayer();
    }

    @ModelAttribute("game")
    private Game getGame() {
        return getPlayer().getGame();
    }

    @ModelAttribute("newTurn")
    private Turn getNewTurn() {
        return new Turn();
    }

    @ModelAttribute("loggedPlayer")
    private Player getLoggedPlayer() {
        User loggedUser = getLoggedUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(new Statistic()).build();
    }

    @ModelAttribute("chat")
    private Message getChat() {
        return new Message();
    }

    @GetMapping
    public String showHeroAttackBoard(HttpSession session, ModelMap modelMap, HttpServletRequest request) {
        advise.getMessage(session, modelMap);
        advise.keepUrl(session, request);
        cacheManager.deleteEndAttackHero(session);
        return VIEW_HERO_ATTACK;
    }

    @PostMapping
    public String modifyCardAttributes(Turn turn, HttpSession session) throws NoCurrentPlayer, WithOutAbilityException, WithOutEnemyException {
        Player player = getPlayer();
        Game game = getGame();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        AbilityInGame usedAbility = turn.getCurrentAbility();
        EnemyInGame attackedEnemy = turn.getCurrentEnemy();
        if (usedAbility == null)
            throw new WithOutAbilityException();
        if (usedAbility.getAbility().isEnemyIsNeeded() && attackedEnemy == null)
            throw new WithOutEnemyException();
        Turn oldTurn = turnService.getTurnsByPhaseAndPlayerId(Phase.HERO_ATTACK, player.getId());
        playerService.savePlayer(player);
        oldTurn.getUsedEnemies().add(attackedEnemy);
        oldTurn.setCurrentEnemy(attackedEnemy);
        oldTurn.getUsedAbilities().add(usedAbility);
        oldTurn.setCurrentAbility(usedAbility);
        turnService.saveTurn(oldTurn);
        if (attackedEnemy != null) {
            advise.heroAttack(usedAbility, attackedEnemy);
            cacheManager.setAttackedEnemy(session, attackedEnemy.getId());
        }
        Optional<Enemy> nighLord = game.getActualOrcs().stream().map(EnemyInGame::getEnemy).filter(Enemy::getIsNightLord).findFirst();
        return nighLord.map(enemy -> PAGE_ABILITY + "/" + enemy.getName().toLowerCase() + "/" + usedAbility.getId()).orElse(PAGE_ABILITY);
    }

    @GetMapping("/makeDamage")
    public String attackEnemy(HttpSession session) throws NoCurrentPlayer, WithOutAbilityException, WithOutEnemyException {
        Player player = getPlayer();
        Game game = getGame();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        Turn turn = turnService.getTurnsByPhaseAndPlayerId(Phase.HERO_ATTACK, player.getId());
        Deck deck = loggedPlayer.getDeck();
        AbilityInGame usedAbility = turn.getCurrentAbility();
        EnemyInGame attackedEnemy = turn.getCurrentEnemy();
        if (usedAbility == null)
            throw new WithOutAbilityException();
        List<EnemyInGame> enemies = cacheManager.getEnemiesAlsoAttacked(session);
        if (attackedEnemy != null && !enemies.contains(attackedEnemy))
            enemies.add(attackedEnemy);
        Integer effectDamage = cacheManager.getSharpeningStone(session) + cacheManager.getAttack(session);
        List<Integer> enemiesMoreDamage = enemies.stream().map(enemy -> cacheManager.getEnemiesThatReceiveMoreDamageForEnemy(session, enemy)).collect(Collectors.toList());
        gameService.attackEnemies(usedAbility, effectDamage, enemies, enemiesMoreDamage, player, game, getLoggedUser().getId());
        if (cacheManager.hasToBeDeletedAbility(session))
            deckService.deleteAbilityInHand(player.getDeck(), usedAbility);
        else
            deckService.specificCardFromHandToDiscard(deck, usedAbility);
        Optional<String> nextUrl = cacheManager.getNextUrl(session);
        return nextUrl.orElse(PAGE_HERO_ATTACK);
    }

    @GetMapping("/next")
    public String next() {
        Player player = getPlayer();
        Game game = getGame();
        if (player == getGame().getCurrentPlayer()) {
            game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(((game.getActualOrcs().isEmpty()) && (game.getAllOrcsInGame().isEmpty())) ? Phase.END: Phase.ENEMY_ATTACK, player.getId()));
            gameService.saveGame(game);
            advise.passPhase(game);
        }
        return NEXT_TURN;
    }

}
