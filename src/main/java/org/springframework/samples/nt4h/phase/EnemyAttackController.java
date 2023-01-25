package org.springframework.samples.nt4h.phase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.exceptions.AllDeadException;
import org.springframework.samples.nt4h.player.exceptions.PlayerIsDeadException;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    private final PhaseService phaseService;

    private Integer damage;

    @ModelAttribute("loggedUser")
    public User getUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("currentPlayer")
    public Player getCurrentPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("loggedPlayer")
    public Player getLoggedPlayer() {
        User loggedUser = getUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(new Statistic()).build();
    }

    @ModelAttribute("game")
    public Game getGame() {
        return getUser().getGame();
    }

    @ModelAttribute("newTurn")
    public Turn getNewTurn() {
        return new Turn();
    }

    @ModelAttribute("chat")
    public Message getChat() {
        return new Message();
    }



    @Autowired
    public EnemyAttackController(UserService userService, TurnService turnService, GameService gameService, CacheManager cacheManager, Advise advise, PhaseService phaseService) {
        this.userService = userService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.cacheManager = cacheManager;
        this.advise = advise;
        this.phaseService = phaseService;
        this.damage = null;
    }

    @GetMapping
    public String getEnemyAttack(ModelMap model, HttpSession session, HttpServletRequest request) throws PlayerIsDeadException, AllDeadException {
        if (getCurrentPlayer() == getLoggedPlayer() && damage == null)
            damage = phaseService.attackEnemy(session);
        model.put("damage", damage);
        advise.keepUrl(session, request);
        return VIEW_ATTACK;
    }

    @GetMapping("/next")
    public String nextTurn(HttpSession session) {
        cacheManager.deleteEndAttackEnemy(session);
        cacheManager.deleteEndAttackHero(session);
        phaseService.doIfCurrentPlayer(
                () -> {
                    damage = null;
                    phaseService.setPhaseInGame(Phase.MARKET);
                }
        );
        return NEXT_TURN;
    }
}
