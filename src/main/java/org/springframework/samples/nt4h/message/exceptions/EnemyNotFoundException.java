package org.springframework.samples.nt4h.message.exceptions;

public class EnemyNotFoundException extends Exception {

        public EnemyNotFoundException() {
            super("Enemy not found");
        }
}
