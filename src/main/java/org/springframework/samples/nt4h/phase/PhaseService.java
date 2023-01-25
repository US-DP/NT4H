package org.springframework.samples.nt4h.phase;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.deck.Deck;
import org.springframework.samples.nt4h.card.ability.deck.DeckService;
import org.springframework.samples.nt4h.card.ability.inGame.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.card.enemy.inGame.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.phase.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.phase.exceptions.WhenEvasionDiscardAtLeast2Exception;
import org.springframework.samples.nt4h.phase.exceptions.WithOutAbilityException;
import org.springframework.samples.nt4h.phase.exceptions.WithOutEnemyException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.statistic.StatisticService;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PhaseService {

    private final UserService userService;
    private final TurnService turnService;
    private final CacheManager cacheManager;
    private final StatisticService statisticService;
    private final GameService gameService;
    private final EnemyService enemyService;
    private final DeckService deckService;
    private final PlayerService playerService;


    public Game getGame() {
        return userService.getLoggedUser().getGame();
    }


    public Player getCurrentPlayer() {
        return getGame().getCurrentPlayer();
    }


    public User getLoggedUser() {
        return userService.getLoggedUser();
    }


    public Player getLoggedPlayer() {
        User loggedUser = getLoggedUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(new Statistic()).build();
    }

    public void isCurrentPlayer() throws NoCurrentPlayer {
        if (getGame().getCurrentPlayer() != getLoggedPlayer()) {
            throw new NoCurrentPlayer();
        }
    }

    // --------------------
    // Fase de ataque héroe.
    // --------------------
    public void preAttackHero(Turn turn) throws WithOutAbilityException, WithOutEnemyException {
        AbilityInGame usedAbility = turn.getCurrentAbility();
        EnemyInGame attackedEnemy = turn.getCurrentEnemy();
        if (usedAbility == null)
            throw new WithOutAbilityException();
        if (usedAbility.getAbility().isEnemyIsNeeded() && attackedEnemy == null)
            throw new WithOutEnemyException();
        turnService.update(turn, getCurrentPlayer(), Phase.HERO_ATTACK);
    }

    public void attackHero(HttpSession session) {
        attackEnemies(session);
        discardOrDeleteAbility(session);
    }

    public List<EnemyInGame> enemiesAttacked(Turn turn, HttpSession session) {
        EnemyInGame enemyInGame = turn.getCurrentEnemy();
        List<EnemyInGame> enemies = cacheManager.getEnemiesAlsoAttacked(session);
        if (enemyInGame != null && !enemies.contains(enemyInGame))
            enemies.add(enemyInGame);
        return enemies;
    }

    public Integer getDamageEnemy(HttpSession session, Turn turn) {
        return cacheManager.getSharpeningStone(session) + cacheManager.getAttack(session) + turn.getCurrentAbility().getAttack();
    }

    public List<Integer> extraDamagePerEnemy(HttpSession session, List<EnemyInGame> enemies) {
        return enemies.stream().map(enemy -> cacheManager.getEnemiesThatReceiveMoreDamageForEnemy(session, enemy)).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void attackEnemies(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Turn turn = currentPlayer.getTurn(Phase.HERO_ATTACK);
        if (turn.getCurrentAbility().getAttack() == 0)
            return;
        List<EnemyInGame> enemies = enemiesAttacked(turn, session);
        List<Integer> extraDamagePerEnemy = extraDamagePerEnemy(session, enemies);
        for (int e = 0; enemies.size() > e; e++) {
            EnemyInGame affectedEnemy = enemies.get(e);
            if (affectedEnemy.isNightLord())
                statisticService.gainGlory(currentPlayer, 1);
            Integer damageToEnemy = getDamageEnemy(session, turn) + extraDamagePerEnemy.get(e);
            enemyService.decreaseLife(currentPlayer, affectedEnemy, damageToEnemy);
        }
    }

    public void discardOrDeleteAbility(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Deck deck = currentPlayer.getDeck();
        Turn turn = currentPlayer.getTurn(Phase.HERO_ATTACK);
        AbilityInGame usedAbility = turn.getCurrentAbility();
        if (cacheManager.hasToBeDeletedAbility(session))
            deckService.deleteAbilityInHand(deck, usedAbility);
        else
            deckService.specificCardFromHandToDiscard(deck, usedAbility);
    }

    // ----------------
    // Fase de evasión.
    // ----------------
    public void discardOneCard(Turn turn) throws WithOutAbilityException {
        Player currentPlayer = getCurrentPlayer();
        AbilityInGame currentAbility = turn.getCurrentAbility();
        if (currentAbility == null)
            throw new WithOutAbilityException();
        Turn oldTurn = turnService.getTurnsByPhaseAndPlayerId(Phase.EVADE, currentPlayer.getId());
        oldTurn.getUsedAbilities().add(currentAbility);
        turnService.saveTurn(oldTurn);
        currentPlayer.getDeck().discardCardOnHand(currentAbility);
        playerService.savePlayer(currentPlayer);
    }

    public void discardAtLeastTwoCards() throws WhenEvasionDiscardAtLeast2Exception {
        Player player = getCurrentPlayer();
        Game game = getGame();
        Turn turn = turnService.getTurnsByPhaseAndPlayerId(Phase.EVADE, player.getId());
        if (turn.getUsedAbilities().size() < 2)
            throw new WhenEvasionDiscardAtLeast2Exception();
        game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.MARKET, player.getId()));
        gameService.saveGame(game);
        // advise.passPhase(game);
    }

    // --------------------
    // Fase de ataque enemigo.
    // --------------------
}
