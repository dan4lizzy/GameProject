package data;

import static helpers.Clock.Delta;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import data.enemies.Enemy;

public class Wave {
  private float timeSinceLastSpawn, spawnTime;
  private Enemy[] enemyTypes;
  private CopyOnWriteArrayList<Enemy> enemyList;
  private int enemyTypeCount, enemiesPerWave, enemiesSpawned;
  private boolean waveCompleted;

  public Wave(Enemy[] enemyTypes, float spawnTime, int enemiesPerWave) {
    this.enemyTypes = enemyTypes;
    this.spawnTime = spawnTime;
    this.enemiesPerWave = enemiesPerWave;
    this.enemiesSpawned = 0;
    this.timeSinceLastSpawn = 0;
    this.enemyList = new CopyOnWriteArrayList<Enemy>();
    this.waveCompleted = false;
    this.enemyTypeCount = enemyTypes.length;
    spawn();
  }

  public void update() {
    // Assume all enemies are dead, until for loop proves otherwise
    boolean allEnemiesDead = true;
    if (enemiesSpawned < enemiesPerWave) {
      timeSinceLastSpawn += Delta();
      if (timeSinceLastSpawn > spawnTime) {
        spawn();
        timeSinceLastSpawn = 0;
      }
    }

    for (Enemy e : enemyList) {
      if (e.isAlive()) {
        allEnemiesDead = false;
        e.update();
        e.draw();
      } else
        enemyList.remove(e);
    }

    if (allEnemiesDead)
      waveCompleted = true;
  }

  private void spawn() {
    int enemyIndex = new Random().nextInt(enemyTypeCount);
    enemyList.add(new Enemy(enemyTypes[enemyIndex]));
    enemiesSpawned++;
  }

  public boolean isCompleted() {
    return waveCompleted;
  }

  public CopyOnWriteArrayList<Enemy> getEnemyList() {
    return enemyList;
  }
}
