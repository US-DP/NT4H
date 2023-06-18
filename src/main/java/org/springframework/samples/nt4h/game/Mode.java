package org.springframework.samples.nt4h.game;

import org.jetbrains.annotations.NotNull;

public enum Mode {
    UNI_CLASS(1), MULTI_CLASS(2), UNDEFINED(0);

    private final int numHeroes;

    Mode(int numHeroes) {
        this.numHeroes = numHeroes;
    }

    public int getNumHeroes() {
        return numHeroes;
    }
}
