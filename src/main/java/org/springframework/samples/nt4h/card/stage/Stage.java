package org.springframework.samples.nt4h.card.stage;

import org.springframework.samples.nt4h.card.Card;
import org.springframework.samples.nt4h.phase.Phase;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Stage extends Card {

    @Enumerated(EnumType.STRING)
    private Phase phase;

    private boolean isOptional;

    private String pathName;


}
