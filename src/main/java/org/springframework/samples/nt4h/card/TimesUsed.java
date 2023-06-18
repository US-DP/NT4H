package org.springframework.samples.nt4h.card;

public enum TimesUsed {
    ONE, TWO, INFINITE, UNDEFINED;

    public boolean haveMoreUses(int uses) {
        return (uses == 0 ) || (uses == 1 && this == ONE) || this == INFINITE;
    }
}
