package data;

import static helpers.Clock.Delta;
import java.util.ArrayList;

public class Wave {
  private float timeSinceLastSpawn, spawnTime;
  private Enemy enemyType;
  private ArrayList<Enemy> enemyList;
  private int enemiesPerWave;
  private boolean waveCompleted;

  public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
    this.enemyType = enemyType;
    this.spawnTime = spawnTime;
    this.enemiesPerWave = enemiesPerWave;
    this.timeSinceLastSpawn = 0;
    this.enemyList = new ArrayList<Enemy>();
    this.waveCompleted = false;

    Spawn();
  }

  public void Update() {
    boolean allEnemiesDead = true;
    if (enemyList.size() < enemiesPerWave) {
      timeSinceLastSpawn += Delta();
      if (timeSinceLastSpawn > spawnTime) {
        Spawn();
        timeSinceLastSpawn = 0;
      }
    }

    for (Enemy e : enemyList) {
      if (e.isAlive()) {
        allEnemiesDead = false;
        e.Update();
        e.Draw();
      }
    }

    if (allEnemiesDead)
      waveCompleted = true;
  }

  private void Spawn() {
    enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getGrid(),
        enemyType.getWidth(), enemyType.getHeight(), enemyType.getSpeed()));
  }

  public boolean isCompleted() {
    return waveCompleted;
  }
}
