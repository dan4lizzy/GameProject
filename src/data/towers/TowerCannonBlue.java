package data.towers;

import java.util.concurrent.CopyOnWriteArrayList;
import data.Tile;
import data.enemies.Enemy;

public class TowerCannonBlue extends Tower {

  public TowerCannonBlue(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
    super(type, startTile, enemies);
  }
}
