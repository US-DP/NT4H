package org.springframework.samples.nt4h.game;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.nt4h.constants.GlobalConstants;
import org.springframework.samples.nt4h.constants.ViewConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {

    // Services ---------------------------------------------------------------
    private final GameService gameService;


    // Constructor ------------------------------------------------------------
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/gameInProgres")
    private String getGamesInProgress() {
        return ViewConstants.IN_PROGRESS_GAMES;
    }

    // Obtener todas las partidas.
    @GetMapping
    public String showGames(@RequestParam(defaultValue = "0") int page, ModelMap model) {
        page = page < 0 ? 0 : page;
        Pageable pageable = PageRequest.of(page, GlobalConstants.PAGE_SIZE);
        List<Game> games = gameService.getAllGames();
        Page<Game> gamePage = gameService.getAllGames(pageable);
        if (!games.isEmpty() && gamePage.isEmpty()) {
            page = games.size() / GlobalConstants.PAGE_SIZE;
            pageable = PageRequest.of(page, GlobalConstants.PAGE_SIZE);
            gamePage = gameService.getAllGames(pageable);
        }
        model.put("isNext", gamePage.hasNext());
        model.put("games", gamePage.getContent());
        model.put("page", page);
        return ViewConstants.LIST_GAMES;
    }
}
