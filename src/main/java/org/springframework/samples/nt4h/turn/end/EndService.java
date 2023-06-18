package org.springframework.samples.nt4h.turn.end;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EndService {


    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public EndService(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    public void inactiveGame() {
        Game game = Objects.requireNonNull(userService.getLoggedUser().getPlayer()).getGame();
        if (game != null && game.isActive()) {
            game.setActive(false);
            gameService.saveGame(game);
        }
    }

    public List<Pair<Player, Integer>> playersOrderedByScore() {
        List<Player> players = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(userService.getLoggedUser().getPlayer()).getGame()).getGameParticipants()).getAlivePlayersInTurnOrder();
        List<Pair<Player, Integer>> pairs = new ArrayList<>();
        for (Player player : players) {
            Integer gold = player.getStatistic().getGold();
            Integer glory = player.getStatistic().getGlory();
            Integer kills = player.getStatistic().getNumOrcsKilled() + player.getStatistic().getNumWarLordKilled();
            Integer total = gold + glory + kills;
            pairs.add(new Pair<>(player, total));
        }
        pairs.sort((o1, o2) -> o2.getValue1().compareTo(o1.getValue1()));
        return pairs;
    }

    public User winner() {
        return userService.getUserByUsername(playersOrderedByScore().get(0).getValue0().getName());
    }

    public void upRankToWinner() {
        User winner = winner();
        int won = Objects.requireNonNull(winner.getStatistic()).getNumWonGames();
        winner.getStatistic().setNumWonGames(won+1);
        userService.upRank(winner.getId());
    }
}
