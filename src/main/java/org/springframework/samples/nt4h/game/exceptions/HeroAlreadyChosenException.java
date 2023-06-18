package org.springframework.samples.nt4h.game.exceptions;

public class HeroAlreadyChosenException extends Exception {

    public HeroAlreadyChosenException() {
        super("Hero.kt already chosen");
    }
}
