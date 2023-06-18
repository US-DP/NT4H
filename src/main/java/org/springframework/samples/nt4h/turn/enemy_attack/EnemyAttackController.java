package org.springframework.samples.nt4h.turn.enemy_attack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.function.Predicate;

@Controller
@RequestMapping("/enemyAttack")
public class EnemyAttackController {


    private final UserService userService;
    private final TurnService turnService;
    private final GameService gameService;
    private final CacheManager cacheManager;
    private final static String VIEW_ATTACK = "turns/attackPhase";
    private final static String NEXT_TURN = "redirect:/turns";
    private final Advise advise;

    private Integer damage;



    @Autowired
    public EnemyAttackController(UserService userService, TurnService turnService, GameService gameService, CacheManager cacheManager, Advise advise) {
        this.userService = userService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.cacheManager = cacheManager;
        this.advise = advise;
        this.damage = null;
    }

    @GetMapping
    public String getEnemyAttack(ModelMap model, HttpSession session, HttpServletRequest request) {
        Game game = getGame();
        if (getCurrentPlayer() == getLoggedPlayer() && damage == null) {
            int defendedDmg = cacheManager.getDefend(session);
            Predicate<EnemyInGame> hasPreventedDamage = enemy -> !(cacheManager.hasPreventDamageFromEnemies(session, enemy));
            List<EnemyInGame> enemiesInATrap = cacheManager.getCapturedEnemies(session);
            damage = gameService.attackEnemyToActualPlayer(game, hasPreventedDamage, defendedDmg, enemiesInATrap);
            advise.playerIsAttacked(damage);
        }
        model.put("damage", damage);
        advise.keepUrl(session, request);
        return VIEW_ATTACK;
    }

    @GetMapping("/next")
    public String nextTurn(HttpSession session) {
        cacheManager.deleteEndAttackEnemy(session);
        cacheManager.deleteEndAttackHero(session);
        Player player = getLoggedPlayer();
        Game game = getGame();
        if(player == game.getCurrentPlayer()) {
            damage = null;
            game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.MARKET, player.getId()));
            gameService.saveGame(game);
            advise.passPhase(game);
        }
        return NEXT_TURN;
    }
}
