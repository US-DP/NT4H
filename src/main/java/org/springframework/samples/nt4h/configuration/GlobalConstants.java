package org.springframework.samples.nt4h.configuration;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.achievement.Achievement;
import org.springframework.samples.nt4h.achievement.AchievementService;
import org.springframework.samples.nt4h.achievement.AchievementType;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.Accessibility;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

@ControllerAdvice
public class GlobalConstants {

    // Services ---------------------------------------------------------------
    private final UserService userService;
    private final HeroService heroService;
    private final AchievementService achievementService;

    // Constructor ------------------------------------------------------------
    @Autowired
    public GlobalConstants(UserService userService, HeroService heroService, AchievementService achievementService) {
        this.userService = userService;
        this.heroService = heroService;
        this.achievementService = achievementService;
    }

    // Constants --------------------------------------------------------------

    @ModelAttribute("currentPlayer")
    public Player getCurrentPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("loggedPlayer")
    public Player getLoggedPlayer() {
        User loggedUser = getUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(Statistic.createStatistic()).build();
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



    @ModelAttribute("mode")
    private List<Mode> getMode() {
        return Lists.newArrayList(Mode.UNI_CLASS, Mode.MULTI_CLASS);
    }

    @ModelAttribute("heroes")
    private List<Hero> getHeroes() {
        return heroService.getAllHeroes();
    }

    @ModelAttribute("accessibility")
    private List<Accessibility> getAccessibility() {
        return Lists.newArrayList(Accessibility.PUBLIC, Accessibility.PRIVATE);
    }

    @ModelAttribute("newHero")
    private HeroInGame getHero() {
        return new HeroInGame();
    }


    @ModelAttribute("loggedPlayer")
    private Player getPlayer() {
        User loggedUser = getLoggedUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(Statistic.createStatistic()).build();
    }

    @ModelAttribute("players")
    private List<Player> getPlayers() {
        Game game = getGame();
        return game != null ? game.getPlayers() : null;
    }

    @ModelAttribute("loggedUser")
    private User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("achievements")
    public List<Achievement> getAchievements() {
        return this.achievementService.getAllAchievements();
    }

    @ModelAttribute("achievementType")
    public List<AchievementType> getAchievementType() {
        return Lists.newArrayList(AchievementType.PLAYED_GAMES,
            AchievementType.WON_GAMES, AchievementType.DURATION,
            AchievementType.TOTAL_GOLD, AchievementType.TOTAL_GLORY,
            AchievementType.DMG_TO_ORCS, AchievementType.KILLED_ORCS,
            AchievementType.KILLED_WARLORDS);
    }

    @ModelAttribute("message")
    public String getMessage(HttpSession session) {
        return session.getAttribute("message").toString();
    }

    @ModelAttribute("messageType")
    public String getMessageType(HttpSession session) {
        return session.getAttribute("messageType").toString();
    }




}
