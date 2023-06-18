package org.springframework.samples.nt4h.turn.evasion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayerException;
import org.springframework.samples.nt4h.turn.exceptions.WhenEvasionDiscardAtLeast2Exception;
import org.springframework.samples.nt4h.turn.exceptions.WithOutAbilityException;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    public EvasionController(UserService userService, TurnService turnService, PlayerService playerService, GameService gameService, Advise advise) {
        this.userService = userService;
        this.turnService = turnService;
        this.playerService = playerService;
        this.gameService = gameService;
        this.advise = advise;
    }

    @GetMapping
    public String getEvasion(HttpSession session, ModelMap modelMap, HttpServletRequest request) {
        advise.getMessage(session, modelMap);
        advise.keepUrl(session, request);
        return VIEW_EVASION;
    }

    @PostMapping
    public String postEvasion(@Valid Turn turn) throws NoCurrentPlayerException, WithOutAbilityException {
        Player player = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            throw new NoCurrentPlayerException();
        AbilityInGame currentAbility = turn.getCurrentAbility();
        if (currentAbility == null)
            throw new WithOutAbilityException();
        Turn oldTurn = turnService.getTurnsByPhaseAndPlayerId(Phase.EVADE, player.getId());
        oldTurn.addAbility(currentAbility);
        turnService.saveTurn(oldTurn);
        player.getDeck().discardCardOnHand(currentAbility);
        playerService.savePlayer(player);
        advise.discardCard(currentAbility);
        return PAGE_EVASION;
    }

    @GetMapping("/next")
    public String nextTurn() throws WhenEvasionDiscardAtLeast2Exception {
        Player player = getCurrentPlayer();
        Game game = getGame();
        if (player != getGame().getCurrentPlayer())
            return NEXT_TURN;
        Turn turn = turnService.getTurnsByPhaseAndPlayerId(Phase.EVADE, player.getId());
        if (turn.getUsedAbilities().size() < 2)
            throw new WhenEvasionDiscardAtLeast2Exception();
        game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.MARKET, player.getId()));
        gameService.saveGame(game);
        advise.passPhase(game);
        return NEXT_TURN;
    }

}
