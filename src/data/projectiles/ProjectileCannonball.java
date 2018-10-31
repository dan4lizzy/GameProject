package data.projectiles;

import data.enemies.Enemy;

public class ProjectileCannonball extends Projectile {

  public ProjectileCannonball(ProjectileType type, Enemy target, float x, float y) {
    super(type, target, x, y);
  }

  public ProjectileCannonball(ProjectileType type, Enemy target, float[] towerCoord,
      float[] towerSize) {
    super(type, target, towerCoord, towerSize);
  }

  @Override
  public void damage() {
    super.damage();
  }
}
