package data.projectiles;

import static helpers.Artist.QuickLoad;
import org.newdawn.slick.opengl.Texture;

public enum ProjectileType {

  // TODO test Texture[] so there can be projectile effects
  CannonBall(QuickLoad("bullet"), 10, 800), IceBall(QuickLoad("iceball"), 6, 450);

  public Texture texture;
  public int damage, range;
  public float speed;

  ProjectileType(Texture textures, int damage, float speed) {
    this.texture = textures;
    this.damage = damage;
    this.speed = speed;
  }


}
