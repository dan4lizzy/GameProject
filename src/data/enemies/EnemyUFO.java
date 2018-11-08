package data.enemies;

import static helpers.Artist.QuickLoad;
import data.TileGrid;

public class EnemyUFO extends Enemy {

  public EnemyUFO(int tileX, int tileY, TileGrid grid) {
    super(tileX, tileY, grid);
    this.setTexture(QuickLoad("UFO64"));
    this.setSpeed(80);
  }

}
