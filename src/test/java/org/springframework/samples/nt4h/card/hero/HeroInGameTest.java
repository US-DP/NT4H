package org.springframework.samples.nt4h.card.hero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.samples.nt4h.turn.Phase;

class HeroInGameTest {
    @Test
    void testCreateHeroInGame() {
        Deck deck = new Deck();
        deck.setId(1);
        deck.setInDeck(new ArrayList<>());
        deck.setInDiscard(new ArrayList<>());
        deck.setInHand(new ArrayList<>());
        Player player = new Player();
        player.setAlive(true);
        player.setBirthDate(null);
        player.setDamageProtect(1);
        player.setDeck(new Deck());
        player.setGame(new Game());
        player.setHasEvasion(true);
        player.setHeroes(new ArrayList<>());
        player.setHost(true);
        player.setId(1);
        player.setName("Name");
        player.setNextPhase(Phase.START);
        player.setReady(true);
        player.setSequence(1);
        player.setStatistic(new Statistic());
        player.setTurns(new ArrayList<>());
        player.setWounds(1);
        Hero hero = new Hero();
        hero.setHealth(1);

        HeroInGame actualCreateHeroInGameResult = HeroInGame.createHeroInGame(hero, player);
        assertEquals(1, actualCreateHeroInGameResult.getHealth().intValue());
        assertSame(player, actualCreateHeroInGameResult.getPlayer());
        assertSame(hero, actualCreateHeroInGameResult.getHero());
    }


    @Test
    void testOnDeleteSetNull() {
        HeroInGame heroInGame = new HeroInGame();
        heroInGame.onDeleteSetNull();
        assertNull(heroInGame.getPlayer());
        assertNull(heroInGame.getHero());
    }
}

