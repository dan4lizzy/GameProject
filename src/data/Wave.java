package data;

import static helpers.Clock.Delta;
import java.util.ArrayList;

public class Wave {
  private float timeSinceLastSpawn, spawnTime;
  private Enemy enemyType;
  private ArrayList<Enemy> enemyList;

  public Wave(float spawnTime, Enemy enemyType) {
    this.enemyType = enemyType;
    this.spawnTime = spawnTime;
    timeSinceLastSpawn = 0;
    enemyList = new ArrayList<Enemy>();
  }

  public void Update() {
    timeSinceLastSpawn += Delta();
    if (timeSinceLastSpawn > spawnTime) {
      Spawn();
      timeSinceLastSpawn = 0;
    }

    for (Enemy e : enemyList) {
      e.Update();
      e.Draw();
    }
  }

  private void Spawn() {
    enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getGrid(),
        enemyType.getWidth(), enemyType.getHeight(), enemyType.getSpeed()));
  }
}
