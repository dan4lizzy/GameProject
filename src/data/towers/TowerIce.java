package data.towers;

import java.util.concurrent.CopyOnWriteArrayList;
import data.Tile;
import data.enemies.Enemy;
import data.projectiles.ProjectileIceball;

public class TowerIce extends Tower {

  public TowerIce(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
    super(type, startTile, enemies);
  }

  @Override
  public void shoot(Enemy target) {
    projectiles.add(new ProjectileIceball(super.type.projectileType, super.getTarget(),
        super.getCoord(), super.getSize()));
  }
}
