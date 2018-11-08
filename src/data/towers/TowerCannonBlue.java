package data.towers;

import java.util.concurrent.CopyOnWriteArrayList;
import data.Tile;
import data.enemies.Enemy;
import data.projectiles.ProjectileCannonball;

public class TowerCannonBlue extends Tower {

  public TowerCannonBlue(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
    super(type, startTile, enemies);
  }

  @Override
  public void shoot(Enemy target) {
    projectiles.add(new ProjectileCannonball(super.type.projectileType, super.getTarget(),
        super.getCoord(), super.getSize()));
    super.target.reduceHiddenHealth(super.type.projectileType.damage);
  }
}
