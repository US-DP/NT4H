package org.springframework.samples.nt4h.turn.exceptions;

public class NoCurrentPlayerException extends Exception {
    public NoCurrentPlayerException() {
        super("No current player");
    }

}

