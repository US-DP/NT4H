package org.springframework.samples.nt4h.turn.end;

import org.javatuples.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/end")
public class EndController {

    private final static String VIEW_END = "turns/endPhase";
    private final static String VIEW_WELCOME =  "redirect:/";

    private final EndService endService;

    public EndController(EndService endService) {
        this.endService = endService;
    }

    @GetMapping
    public String showEnd(ModelMap model) {
        endService.upRankToWinner();
        model.addAttribute("punctuations", endService.playersOrderedByScore());
        return VIEW_END;
    }

    @GetMapping("/finish")
    public String finishGame() {
        endService.inactiveGame();
        return VIEW_WELCOME;
    }


}
