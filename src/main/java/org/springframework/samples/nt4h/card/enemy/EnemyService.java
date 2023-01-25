package org.springframework.samples.nt4h.card.enemy;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.enemy.inGame.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.inGame.EnemyInGameRepository;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.statistic.StatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EnemyService {
    private final EnemyInGameRepository enemyInGameRepository;
    private final EnemyRepository enemyRepository;
    private final StatisticService statisticService;
    private static final Integer NUM_NIGHTLORDS = 3;


    // EnemyInGame
    @Transactional(readOnly = true)
    public EnemyInGame getEnemyInGameById(int id) {
        return enemyInGameRepository.findById(id).orElseThrow(() -> new NotFoundException("EnemyInGame not found"));
    }

    @Transactional
    public void saveEnemyInGame(EnemyInGame enemyInGame) {
        enemyInGameRepository.save(enemyInGame);
    }

    @Transactional
    public Enemy getNightLord() {
        int randomNumber = (int) (Math.random() * NUM_NIGHTLORDS);
        List<Enemy> allNightLords = getAllNightLords();
        return allNightLords.get(randomNumber);
    }

    @Transactional(readOnly = true)
    public List<Enemy> getAllNightLords() {
        return enemyRepository.findAllNightLords();
    }

    @Transactional(readOnly = true)
    public List<Enemy> getAllNotNightLords() {
        return enemyRepository.findAllNotNightLords();
    }

    @Transactional
    public List<EnemyInGame> addOrcsToGame(Integer numPlayers) {
        int limitEnemies = 19;
        if(numPlayers == 2) limitEnemies = 17;
        else if(numPlayers == 3) limitEnemies = 23;
        else if(numPlayers == 4) limitEnemies = 27;
        List<EnemyInGame> orcs = getAllNotNightLords().stream().map(EnemyInGame::new).collect(Collectors.toList());
        Collections.shuffle(orcs);
        List<EnemyInGame> limitedOrcs = orcs.subList(0, limitEnemies);
        saveEnemyInGame(limitedOrcs.get(0));
        limitedOrcs.forEach(this::saveEnemyInGame);
        return limitedOrcs;
    }

    @Transactional
    public EnemyInGame addNightLordToGame() {
        Enemy nightLord = getNightLord();
        EnemyInGame nightLordInGame = new EnemyInGame(nightLord);
        saveEnemyInGame(nightLordInGame);
        return nightLordInGame;
    }

    @Transactional(rollbackFor = Exception.class)
    public void increaseLife(EnemyInGame enemy) {
        enemy.setActualHealth(enemy.getActualHealth() + 1);
        saveEnemyInGame(enemy);
    }

    public void decreaseLife(Player player, EnemyInGame enemy, Integer damage) {
        enemy.setActualHealth(enemy.getActualHealth() - damage);
        statisticService.damageDealt(player, damage);
        if (enemy.getActualHealth() <= 0) {
            enemy.setActualHealth(0);
            getTreasures(player, enemy);
            enemyInGameRepository.delete(enemy);
        }
        saveEnemyInGame(enemy);
    }

    public void getTreasures(Player player, EnemyInGame enemy) {
        statisticService.gainGold(player, enemy.getEnemy().getGold());
        statisticService.gainGlory(player, enemy.getEnemy().getGlory());
    }


}
