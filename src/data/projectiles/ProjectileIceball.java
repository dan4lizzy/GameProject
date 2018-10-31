package data.projectiles;

import data.enemies.Enemy;

public class ProjectileIceball extends Projectile {

  public ProjectileIceball(ProjectileType type, Enemy target, float x, float y) {
    super(type, target, x, y);
  }

  public ProjectileIceball(ProjectileType type, Enemy target, float[] towerCoord,
      float[] towerSize) {
    super(type, target, towerCoord, towerSize);
  }

  @Override
  public void damage() {
    // TODO should this be a reduction to value or by a percentage down?
    super.getTarget().setSpeed(4f);
    // comment the following line if desirable to just slow down enemy
    super.damage();
  }
}
