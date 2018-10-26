package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Artist.QuickLoad;
import static helpers.Clock.Delta;
import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;

public class TowerCannon {
  private float x, y, timeSinceLastShot, firingSpeed, angle, ammoVelocity;
  private int width, height, damage;
  private Texture baseTexture, cannonTexture;
  // does startTile really need to be a global? we are only using it to get X & Y in constructor
  private Tile startTile;
  private ArrayList<Projectile> projectiles;
  private ArrayList<Enemy> enemies;
  private Enemy target;

  public TowerCannon(Texture texture, Tile startTile, int damage, ArrayList<Enemy> enemies) {
    this.baseTexture = texture;
    this.cannonTexture = QuickLoad("cannonGun");
    this.startTile = startTile;
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.width = (int) startTile.getWidth();
    this.height = (int) startTile.getHeight();
    this.damage = damage;
    this.firingSpeed = 3;
    this.timeSinceLastShot = 0;
    this.projectiles = new ArrayList<Projectile>();
    this.enemies = enemies;
    this.target = acquireTarget();
    this.angle = calculateAngle();

    // My code modifications
    this.ammoVelocity = 900;
    this.damage = 2;
  }

  private Enemy acquireTarget() {
    // TODO determine which enemy we should be targeting
    return enemies.get(0);
  }

  private float calculateAngle() {
    // TODO converts these to local variables
    float xCannonCenterOfMass = (x + cannonTexture.getImageWidth() / 2);
    float yCannonCenterOfMass = (y + cannonTexture.getImageHeight() / 2);
    float xTargetCenterOfMass = (target.getX() + target.getWidth() / 2);
    float yTargetCenterOfMass = (target.getY() + target.getHeight() / 2);
    float xDistanceFromTarget = xTargetCenterOfMass - xCannonCenterOfMass;
    float yDistanceFromTarget = yTargetCenterOfMass - yCannonCenterOfMass;
    double angleTemp = Math.atan2(yDistanceFromTarget, xDistanceFromTarget);
    // double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
    return (float) Math.toDegrees(angleTemp) - 90;
  }

  public void shoot() {
    // center of projectile needs to be centered on tower
    Texture bullet = QuickLoad("bullet");
    float xOffset = bullet.getImageWidth() / 2;
    float yOffset = bullet.getImageWidth() / 2;
    projectiles.add(new Projectile(bullet, target, x + xOffset, y + yOffset, ammoVelocity, damage));
  }

  public void update() {
    // moved here for troubleshooting projectile velocity vector
    angle = calculateAngle();

    timeSinceLastShot += Delta();
    if (timeSinceLastShot > firingSpeed) {
      shoot();
      timeSinceLastShot = 0;
    }

    for (Projectile p : projectiles)
      p.update();

    draw();
  }

  public void draw() {
    DrawQuadTex(baseTexture, x, y, width, height);
    DrawQuadTexRot(cannonTexture, x, y, width, height, angle);
  }


}
