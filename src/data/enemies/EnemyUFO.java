package data.enemies;

import data.TileGrid;

public class EnemyUFO extends Enemy {

  public EnemyUFO(int tileX, int tileY, TileGrid grid) {
    super(tileX, tileY, grid);
    this.setTexture("UFO64");
    this.setSpeed(80);
  }

}
