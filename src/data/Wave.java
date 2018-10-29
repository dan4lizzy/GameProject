package data;

import static helpers.Clock.Delta;
import java.util.concurrent.CopyOnWriteArrayList;
import data.enemies.Enemy;

public class Wave {
  private float timeSinceLastSpawn, spawnTime;
  private Enemy enemyType;
  private CopyOnWriteArrayList<Enemy> enemyList;
  private int enemiesPerWave;
  private boolean waveCompleted;

  public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
    this.enemyType = enemyType;
    this.spawnTime = spawnTime;
    this.enemiesPerWave = enemiesPerWave;
    this.timeSinceLastSpawn = 0;
    this.enemyList = new CopyOnWriteArrayList<Enemy>();
    this.waveCompleted = false;

    spawn();
  }

  public void update() {
    boolean allEnemiesDead = true;
    if (enemyList.size() < enemiesPerWave) {
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
    // enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(),
    // enemyType.getTileGrid(), enemyType.getWidth(), enemyType.getHeight(), enemyType.getSpeed(),
    // enemyType.getHealth()));
    enemyList.add(new Enemy(enemyType));
  }

  public boolean isCompleted() {
    return waveCompleted;
  }

  public CopyOnWriteArrayList<Enemy> getEnemyList() {
    return enemyList;
  }
}
