package data.towers;

import java.util.concurrent.CopyOnWriteArrayList;
import data.Tile;
import data.enemies.Enemy;

public class TowerIce extends Tower {

  public TowerIce(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
    super(type, startTile, enemies);
  }

  @Override
  protected void shoot() {
    super.shoot();
  }
}
