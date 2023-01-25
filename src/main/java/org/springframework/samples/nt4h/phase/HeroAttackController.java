package org.springframework.samples.nt4h.phase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
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
    private final Advise advise;
    private final CacheManager cacheManager;
    private final PhaseService phaseService;

    @Autowired
    public HeroAttackController(UserService userService, TurnService turnService, GameService gameService, Advise advise, CacheManager cacheManager, PhaseService phaseService) {
        this.userService = userService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.advise = advise;
        this.cacheManager = cacheManager;
        this.phaseService = phaseService;
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
    public String modifyCardAttributes(Turn turn) throws NoCurrentPlayer, WithOutAbilityException, WithOutEnemyException {
        phaseService.isCurrentPlayer();
        phaseService.preAttackHero(turn);
        return getGame().getNightLordOnBattle()
                .map(enemy -> PAGE_ABILITY + "/" + enemy.getName().toLowerCase() + "/" + turn.getCurrentAbility().getId())
                .orElse(PAGE_ABILITY);
    }

    @GetMapping("/makeDamage")
    public String attackEnemy(HttpSession session) throws NoCurrentPlayer {
        Player player = getPlayer();
        Game game = getGame();
        phaseService.isCurrentPlayer();
        phaseService.attackHero(session);
        return cacheManager.getNextUrl(session).orElse(PAGE_HERO_ATTACK);
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
