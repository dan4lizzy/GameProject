package data;

import static helpers.Clock.Delta;
import java.util.ArrayList;

public class Wave {
  private float timeSinceLastSpawn, spawnTime;
  private Enemy enemyType;
  private ArrayList<Enemy> enemyList;
  private int enemiesPerWave;

  public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
    this.enemyType = enemyType;
    this.spawnTime = spawnTime;
    this.enemiesPerWave = enemiesPerWave;
    this.timeSinceLastSpawn = 0;
    enemyList = new ArrayList<Enemy>();

    Spawn();
  }

  public void Update() {
    timeSinceLastSpawn += Delta();
    if (timeSinceLastSpawn > spawnTime) {
      Spawn();
      timeSinceLastSpawn = 0;
    }

    for (Enemy e : enemyList) {
      if (e.isAlive()) {
        e.Update();
        e.Draw();
      }
      // TODO Remove dead enemy
    }
  }

  private void Spawn() {
    enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getGrid(),
        enemyType.getWidth(), enemyType.getHeight(), enemyType.getSpeed()));
  }
}
