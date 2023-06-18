package org.springframework.samples.nt4h.phase.exceptions;

public class NoEnoughCardsException extends Exception {
    public NoEnoughCardsException() {
        super("There are enough cards.");
    }
}
