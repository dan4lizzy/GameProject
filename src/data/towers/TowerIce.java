package data.towers;

import java.util.ArrayList;
import data.Tile;
import data.enemies.Enemy;

public class TowerIce extends Tower {

  public TowerIce(TowerType type, Tile startTile, ArrayList<Enemy> enemies) {
    super(type, startTile, enemies);
  }

  @Override
  protected void shoot() {
    super.shoot();
  }
}
