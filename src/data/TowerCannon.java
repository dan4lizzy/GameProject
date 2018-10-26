package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Artist.QuickLoad;
import static helpers.Clock.Delta;
import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;

public class TowerCannon {
  private float x, y, timeSinceLastShot, firingSpeed;
  private int width, height, damage;
  private Texture baseTexture, cannonTexture;
  private Tile startTile;
  private ArrayList<Projectile> projectiles;

  public TowerCannon(Texture texture, Tile startTile, int damage) {
    this.baseTexture = texture;
    this.cannonTexture = QuickLoad("cannonGun");
    this.startTile = startTile;
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.width = (int) startTile.getWidth();
    this.height = (int) startTile.getHeight();
    this.damage = damage;
    this.firingSpeed = 30;
    this.timeSinceLastShot = 0;
    this.projectiles = new ArrayList<Projectile>();
  }

  public void Shoot() {
    timeSinceLastShot = 0;
    projectiles.add(new Projectile(QuickLoad("bullet"), x + 32, y + 32, 5, 10));
  }

  public void Update() {
    timeSinceLastShot += Delta();
    if (timeSinceLastShot > firingSpeed)
      Shoot();

    for (Projectile p : projectiles)
      p.Update();

    Draw();
  }

  public void Draw() {
    DrawQuadTex(baseTexture, x, y, width, height);
    DrawQuadTexRot(cannonTexture, x, y, width, height, 45);
  }
}
