package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Clock.Delta;
import org.newdawn.slick.opengl.Texture;

public class Projectile {

  private Texture texture;
  private float x, y, speed;
  private int damage;

  public Projectile(Texture texture, float x, float y, float speed, int damage) {
    this.texture = texture;
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.damage = damage;
  }

  public void Update() {
    x += Delta() * speed;
    Draw();
  }

  public void Draw() {
    DrawQuadTex(texture, x, y, 32, 32);
  }
}
