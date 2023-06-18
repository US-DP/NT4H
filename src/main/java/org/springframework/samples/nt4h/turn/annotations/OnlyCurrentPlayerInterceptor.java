package org.springframework.samples.nt4h.turn.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayerException;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class OnlyCurrentPlayerInterceptor implements HandlerInterceptor {

    private final UserService userService;


    @Autowired
    public OnlyCurrentPlayerInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            if (method.isAnnotationPresent(OnlyCurrentPlayer.class)) {
                Player player = getCurrentPlayer();
                Player loggedPlayer = getLoggedPlayer();
                if (loggedPlayer != player) {
                    throw new NoCurrentPlayerException();
                }
            }
        }

        return true;
    }

    public Player getCurrentPlayer() {
        return getGame().getCurrentPlayer();
    }

    public Player getLoggedPlayer() {
        User loggedUser = getLoggedUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(Statistic.createStatistic()).build();
    }

    public Game getGame() {
        return getLoggedUser().getGame();
    }

    private User getLoggedUser() {
        return userService.getLoggedUser();
    }
}
