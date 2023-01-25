package org.springframework.samples.nt4h.turn;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.nt4h.card.ability.inGame.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.inGame.EnemyInGame;
import org.springframework.samples.nt4h.card.product.inGame.ProductInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.phase.Phase;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TurnService {
    private final TurnRepository turnRepository;

    @Transactional
    public void saveTurn(Turn turn) throws DataAccessException {
        turnRepository.save(turn);
    }

    @Transactional
    public void createAllTurnForAPlayer(Player player) {
        for (Phase phase : Phase.values())
            saveTurn(new Turn(player, phase));
    }

    @Transactional(readOnly = true)
    public List<Turn> getAllTurns() {
        return turnRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Turn getTurnsByPhaseAndPlayerId(Phase phase, int playerId) {
        return getAllTurns().stream().filter(turn -> turn.getPhase().equals(phase) && turn.getPlayer().getId() == playerId)
            .findFirst().orElseThrow(() -> new NotFoundException("Turn not found"));
    }

    // Dependiendo de la fase.
    @Transactional
    public void chooseAttackOrEvasion(Player player, Phase phase, Game game) {
        Turn turn = getTurnsByPhaseAndPlayerId(phase, player.getId());
        game.setCurrentTurn(turn);
        if ((phase == Phase.EVADE) && player.getHasEvasion()) {
            player.setHasEvasion(false);
            player.setNextPhase(Phase.EVADE);
        } else
            player.setNextPhase(Phase.HERO_ATTACK);
        saveTurn(turn);
    }

    @Transactional
    public void update(Turn newTurn, Player player, Phase phase) {
        Turn oldTurn = getTurnsByPhaseAndPlayerId(phase, player.getId());
        EnemyInGame enemyInGame = newTurn.getCurrentEnemy();
        ProductInGame productInGame = newTurn.getCurrentProduct();
        AbilityInGame abilityInGame = newTurn.getCurrentAbility();
        if (enemyInGame != null) {
            oldTurn.getUsedEnemies().add(enemyInGame);
            oldTurn.setCurrentEnemy(enemyInGame);
        }
        if (productInGame != null) {
            oldTurn.getUsedProducts().add(productInGame);
            oldTurn.setCurrentProduct(productInGame);
        }
        if (abilityInGame != null) {
            oldTurn.getUsedAbilities().add(abilityInGame);
            oldTurn.setCurrentAbility(abilityInGame);
        }
        saveTurn(oldTurn);
    }

    public void passTurn() {

    }
}
