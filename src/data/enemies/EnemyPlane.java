package data.enemies;

import data.TileGrid;

public class EnemyPlane extends Enemy {

  public EnemyPlane(int tileX, int tileY, TileGrid grid) {
    super(tileX, tileY, grid);
    this.setTexture("enemyBlack1");
    this.setSpeed(150);
  }

}
