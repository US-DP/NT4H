package org.springframework.samples.nt4h.phase.exceptions;

public class TooManyAbilitiesException extends Exception {
    public TooManyAbilitiesException() {
        super("You have to discard more abilities.");
    }
}
