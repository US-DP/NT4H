package org.springframework.samples.nt4h.phase.exceptions;

public class NoCurrentPlayer extends Exception {
    public NoCurrentPlayer() {
        super("No current player");
    }

}

