package org.springframework.samples.nt4h.phase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.phase.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.phase.exceptions.WhenEvasionDiscardAtLeast2Exception;
import org.springframework.samples.nt4h.phase.exceptions.WithOutAbilityException;
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
import javax.validation.Valid;

@Controller
@RequestMapping("/evasion")
public class EvasionController {

    private final static String PAGE_EVASION = "redirect:/evasion";
    private final static String VIEW_EVASION = "turns/evasionPhase";
    private final static String NEXT_TURN = "redirect:/turns";
    private final UserService userService;
    private final TurnService turnService;
    private final PlayerService playerService;
    private final GameService gameService;
    private final Advise advise;
    private final PhaseService phaseService;

    @Autowired
    public EvasionController(UserService userService, TurnService turnService, PlayerService playerService, GameService gameService, Advise advise, PhaseService phaseService) {
        this.userService = userService;
        this.turnService = turnService;
        this.playerService = playerService;
        this.gameService = gameService;
        this.advise = advise;
        this.phaseService = phaseService;
    }

    @ModelAttribute("game")
    public Game getGame() {
        return userService.getLoggedUser().getGame();
    }

    @ModelAttribute("currentPlayer")
    public Player getCurrentPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("loggedPlayer")
    public Player getLoggedPlayer() {
        User loggedUser = getLoggedUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(new Statistic()).build();
    }



    @ModelAttribute("newTurn")
    public Turn getNewTurn() {
        return new Turn();
    }

    @ModelAttribute("chat")
    public Message getChat() {
        return new Message();
    }

    @GetMapping
    public String getEvasion(HttpSession session, ModelMap modelMap, HttpServletRequest request) {
        advise.getMessage(session, modelMap);
        advise.keepUrl(session, request);
        return VIEW_EVASION;
    }

    @PostMapping
    public String postEvasion(@Valid Turn turn) throws NoCurrentPlayer, WithOutAbilityException {
        Player currentPlayer = getCurrentPlayer();
        phaseService.isCurrentPlayer();
        phaseService.discardOneCard(turn);
        // advise.discardCard(currentAbility);
        return PAGE_EVASION;
    }

    @GetMapping("/next")
    public String nextTurn() throws WhenEvasionDiscardAtLeast2Exception {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer == loggedPlayer)
            phaseService.discardAtLeastTwoCards();
        return NEXT_TURN;
    }

}
