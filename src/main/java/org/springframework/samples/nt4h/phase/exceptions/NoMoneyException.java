package org.springframework.samples.nt4h.phase.exceptions;

public class NoMoneyException extends Exception {
    public NoMoneyException() {
        super("You don't have enough money");
    }
}
