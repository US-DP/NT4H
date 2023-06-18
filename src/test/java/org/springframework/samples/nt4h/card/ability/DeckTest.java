package org.springframework.samples.nt4h.card.ability;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class DeckTest {
    @Test
    void testCreateEmptyDeck() {
        Deck actualCreateEmptyDeckResult = Deck.createEmptyDeck();
        List<AbilityInGame> inHand = actualCreateEmptyDeckResult.getInHand();
        assertTrue(inHand.isEmpty());
        List<AbilityInGame> inDiscard = actualCreateEmptyDeckResult.getInDiscard();
        assertEquals(inHand, inDiscard);
        assertEquals(inDiscard, actualCreateEmptyDeckResult.getInDeck());
    }

    @Test
    void testOnDeleteSetNull() {
        AbilityInGame abilityInGame = new AbilityInGame();
        abilityInGame.setAbilityCardType(AbilityCardType.WARRIOR);
        abilityInGame.setAttack(1);
        abilityInGame.setId(1);
        abilityInGame.setProduct(true);
        abilityInGame.setTimesUsed(1);
        Deck deck = new Deck();
        deck.setId(1);
        List<AbilityInGame> abilityInGameList = List.of(abilityInGame,abilityInGame,abilityInGame);
        deck.setInDeck(abilityInGameList);
        deck.setInDiscard(abilityInGameList);
        deck.setInHand(abilityInGameList);
        deck.onDeleteSetNull();
        assertEquals(1, deck.getId().intValue());
        assertEquals(abilityInGameList, deck.getInHand());
        List<AbilityInGame> inDiscard = deck.getInDiscard();
        assertEquals(abilityInGameList, inDiscard);
        assertEquals(inDiscard, deck.getInDeck());
    }

}

