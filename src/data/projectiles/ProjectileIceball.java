package data.projectiles;

import org.newdawn.slick.opengl.Texture;
import data.enemies.Enemy;

public class ProjectileIceball extends Projectile {

  public ProjectileIceball(Texture texture, Enemy target, float x, float y, int width, int height,
      float speed, int damage) {
    super(texture, target, x, y, width, height, speed, damage);
  }

  @Override
  public void damage() {
    // TODO should this be a reduction to value or by a percentage down?
    super.getTarget().setSpeed(4f);
    // super.setAlive(false); // uncomment this line if desirable to just slow down enemy
    super.damage();
  }
}
