package org.springframework.samples.nt4h.card.ability.deck;

import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.samples.nt4h.card.ability.inGame.AbilityInGame;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Deck extends BaseEntity {

    public Deck() {
        inHand = Lists.newArrayList();
        inDeck = Lists.newArrayList();
        inDiscard = Lists.newArrayList();
    }

    @OneToMany(cascade = CascadeType.ALL)
    private List<AbilityInGame> inHand;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AbilityInGame> inDeck;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AbilityInGame> inDiscard;

    public void onDeleteSetNull() {
        inHand.forEach(AbilityInGame::onDeleteSetNull);
        inDeck.forEach(AbilityInGame::onDeleteSetNull);
        inDiscard.forEach(AbilityInGame::onDeleteSetNull);
    }

    public void discardCardOnHand(AbilityInGame abilityInGame) {
        inHand.remove(abilityInGame);
        inDiscard.add(abilityInGame);
    }
}
