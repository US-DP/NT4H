package org.springframework.samples.nt4h.turn.lobby;

import org.springframework.samples.nt4h.game.exceptions.*;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/lobby")
public class LobbyController {

    private final Advise advise;

    public LobbyController(Advise advise) {
        this.advise = advise;
    }

    // Parida actual
    @GetMapping("/current")
    public String showCurrentGame(HttpSession session, HttpServletRequest request) {
        Game game = getGame();
        advise.keepUrl(session, request);
        return game == null ? PAGE_GAMES : VIEW_GAME_LOBBY;
    }

    // Visualizar una partida.
    @GetMapping("/view/{gameId}")
    public String showGame(@PathVariable("gameId") int gameId, ModelMap model) throws UserInAGameException {
        Game game = gameService.getGameById(gameId);
        model.put("game", game);
        gameService.addSpectatorToGame(game, getLoggedUser());
        return VIEW_GAME_LOBBY;
    }

    /// Unirse a una partida.
    @GetMapping("/{gameId}")
    public String joinGame(@PathVariable("gameId") int gameId, @RequestParam(defaultValue = "null") String password, ModelMap model, HttpSession session, HttpServletRequest request) throws IncorrectPasswordException, UserHasAlreadyAPlayerException, UserInAGameException, FullGameException {
        Game newGame = gameService.getGameById(gameId);
        User loggedUser = getLoggedUser();
        if (!newGame.getPlayers().contains(loggedUser.getPlayer()))
            gameService.addPlayerToGame(newGame, loggedUser, password);
        advise.keepUrl(session, request);
        advise.getMessage(session, model);
        model.put("numHeroes", newGame.isUniClass());
        return PAGE_CURRENT_GAME;
    }

    //Elegir here
    @GetMapping(value = "/heroSelect")
    public String initHeroSelectForm(HttpSession session, ModelMap model, HttpServletRequest request) {
        // Los datos para el formulario
        advise.getMessage(session, model);
        advise.keepUrl(session, request);
        advise.chooseHero();
        return VIEW_GAME_HERO_SELECT;
    }

    // Analizamos la elección del héroe.
    @PostMapping(value = "/heroSelect")
    public String processHeroSelectForm(HeroInGame heroInGame) throws RoleAlreadyChosenException, HeroAlreadyChosenException, PlayerIsReadyException {
        Player loggedPlayer = getPlayer();
        Game game = getGame();
        gameService.addHeroToPlayer(loggedPlayer, heroInGame, game);
        return PAGE_CURRENT_GAME;
    }

    // Llamamos al formulario para crear la partida.
    @GetMapping(value = "/new")
    public String initCreationForm() throws UserInAGameException {
        if (!getGame().isNew())
            throw new UserInAGameException();
        return VIEW_GAME_CREATE;
    }

    // Comprobamos si la partida es correcta y la almacenamos.
    @PostMapping(value = "/new")
    public String processCreationForm(@Valid Game game, BindingResult result) throws FullGameException {
        if (result.hasErrors()) return VIEW_GAME_CREATE;
        User loggedUser = userService.getLoggedUser();
        gameService.createGame(loggedUser, game);
        return PAGE_CURRENT_GAME;
    }

    @GetMapping("/selectOrder")
    public String orderPlayers(HttpSession session, HttpServletRequest request) {
        advise.keepUrl(session, request);
        return VIEW_GAME_PREORDER;
    }

    // Tiene que recibir las cartas de habilidad que desea utilizar el jugador, por tanto, se va a modificar entero.
    @PostMapping("/selectOrder")
    public String processOrderPlayers(HttpSession session, HttpServletRequest request) {
        List<Player> players = getPlayers();
        Game game = getGame();
        if (players.stream().anyMatch(player -> player.getSequence() == -1))
            gameService.orderPlayer(players, game);
        advise.keepUrl(session, request);
        return VIEW_GAME_ORDER;
    }

    @GetMapping("deletePlayer/{playerId}")
    public String deletePlayer(@PathVariable("playerId") int playerId) {
        Game game = getGame();
        if(playerService.getPlayerById(playerId).getHost()) {
            if(game.getCurrentPlayer()==null) {
                gameService.deleteGameById(game.getId());
            }
            playerService.deletePlayerById(playerId);
            userService.removeUserFromGame(userService.getLoggedUser());
            return PAGE_GAMES;
        }else{
            userService.removeUserFromGame(userService.getUserByUsername(playerService.getPlayerById(playerId).getName()));
            playerService.deletePlayerById(playerId);
            return PAGE_GAME_TO_LOBBY.replace("{gameId}", game.getId().toString());
        }
    }

    @GetMapping("deleteGame/{gameId}")
    public String deleteGame(@PathVariable("gameId") int gameId) {
        gameService.deleteGameById(gameId);
        return PAGE_GAMES;
    }
}
