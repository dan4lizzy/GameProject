package data.projectiles;

import org.newdawn.slick.opengl.Texture;
import data.enemies.Enemy;

public class ProjectileBullet extends Projectile {

  public ProjectileBullet(Texture texture, Enemy target, float x, float y, int width, int height,
      float speed, int damage) {
    super(texture, target, x, y, width, height, speed, damage);
    // TODO Auto-generated constructor stub
  }

}
