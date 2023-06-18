package org.springframework.samples.nt4h.turn.start;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayerException;
import org.springframework.samples.nt4h.turn.exceptions.WithOutPhaseException;
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
@RequestMapping("/start")
public class StartController {

    private static final String VIEW_CHOOSE_EVASION = "turns/actionDecision";
    private static final String NEXT_TURN = "redirect:/turns";
    private final UserService userService;
    private final TurnService turnService;
    private final Advise advise;
    private Boolean isChosen = false;
    private final CacheManager cacheManager;


    @Autowired
    public StartController(UserService userService, TurnService turnService, Advise advise, CacheManager cacheManager) {
        this.userService = userService;
        this.turnService = turnService;
        this.advise = advise;
        this.cacheManager = cacheManager;
    }

    @ModelAttribute("turns")
    public List<Phase> getTurns() {
        return Lists.newArrayList(Phase.EVADE, Phase.HERO_ATTACK);
    }

    @GetMapping
    public String chooseEvasion(HttpSession session, ModelMap modelMap, HttpServletRequest request) {
        advise.getMessage(session, modelMap);
        advise.keepUrl(session, request);
        cacheManager.deleteEndAttackHero(session);
        cacheManager.deleteEndAttackEnemy(session);
        if (!isChosen) {
            advise.choose(getGame());
            isChosen = true;
        }
        return VIEW_CHOOSE_EVASION;
    }

    @PostMapping
    public String selectEvasion(Turn turn, HttpSession session) throws NoCurrentPlayerException, WithOutPhaseException {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        Game game = getGame();
        if (loggedPlayer != player)
            throw new NoCurrentPlayerException();
        if (turn.getPhase() == null)
            throw new WithOutPhaseException();
        isChosen = false;
        turnService.chooseAttackOrEvasion(player, turn.getPhase(), game);
        advise.passPhase(game);
        return NEXT_TURN;
    }
}
