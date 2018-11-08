package data;

import data.enemies.Enemy;

public class WaveManager {

  private float timeSinceLastWave, timeBetweenEnemies;
  private int waveNumber, enemiesPerWave;
  private Enemy[] enemyTypes;
  private Wave currentWave;

  public WaveManager(Enemy[] enemyTypes, float timeBetweenEnemies, int enemiesPerWave) {
    this.enemyTypes = enemyTypes;
    this.timeBetweenEnemies = timeBetweenEnemies;
    this.enemiesPerWave = enemiesPerWave;
    this.timeSinceLastWave = 0;
    this.waveNumber = 0;
    this.currentWave = null;
    newWave();
  }

  public void update() {
    if (!currentWave.isCompleted())
      currentWave.update();
    else
      newWave();
  }

  private void newWave() {
    currentWave = new Wave(enemyTypes, timeBetweenEnemies, enemiesPerWave);
    waveNumber++;
    // TODO Remove/comment out the following line
    System.out.println("Beginning Wave " + waveNumber);
  }

  public Wave getCurrentWave() {
    return currentWave;
  }

  public int getWaveNumber() {
    return waveNumber;
  }
}
