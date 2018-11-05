package data.towers;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Artist.QuickLoad;
import static helpers.Clock.Delta;
import java.util.concurrent.CopyOnWriteArrayList;
import org.newdawn.slick.opengl.Texture;
import data.Tile;
import data.enemies.Enemy;
import data.projectiles.Projectile;
import data.projectiles.ProjectileCannonball;
import data.projectiles.ProjectileType;

public class TowerCannon {
  private float x, y, timeSinceLastShot, firingSpeed, angle;
  private int width, height, range;
  private Texture baseTexture, cannonTexture;
  private CopyOnWriteArrayList<Projectile> projectiles;
  private CopyOnWriteArrayList<Enemy> enemies;
  private Enemy target;
  private boolean targeted;

  public TowerCannon(Texture texture, Tile startTile, int damage, int range,
      CopyOnWriteArrayList<Enemy> enemies) {
    this.baseTexture = texture;
    this.cannonTexture = QuickLoad("cannonGun");
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.width = startTile.getWidth();
    this.height = startTile.getHeight();
    this.range = range;
    this.firingSpeed = 3;
    this.timeSinceLastShot = 0;
    this.projectiles = new CopyOnWriteArrayList<Projectile>();
    this.enemies = enemies;
    this.targeted = false;
  }

  private Enemy acquireTarget() {
    Enemy closest = null;
    // this number is so big that any enemy should be closer
    float closestDistance = 10000;
    for (Enemy e : enemies) {
      if (isInRange(e) && findDistance(e) < closestDistance) {
        closestDistance = findDistance(e);
        closest = e;
      }
    }
    // return enemies.get(0);
    if (closest != null)
      targeted = true;
    return closest;
  }

  private boolean isInRange(Enemy e) {
    float distance = findDistance(e);
    if (distance < range)
      return true;
    return false;
  }

  private float findDistance(Enemy e) {
    // Similar issue with this from Indie
    // if the range is 900 pixels, and the xDistance and yDistance
    // are both 900, the actual distance is ~1273 pixels, and
    // therefore out of range; let alone adding them together
    // this also doesn't take into account the center of mass of the target
    // float xDistance = Math.abs(e.getX() - x);
    // float yDistance = Math.abs(e.getY() - y);
    // return xDistance + yDistance;

    // Again using the Pythagorean theorem
    float xCannonCenterOfMass = (x + cannonTexture.getImageWidth() / 2);
    float yCannonCenterOfMass = (y + cannonTexture.getImageHeight() / 2);
    float xEnemyCenterOfMass = (e.getX() + e.getWidth() / 2);
    float yEnemyCenterOfMass = (e.getY() + e.getHeight() / 2);
    float xDistanceFromTarget = xEnemyCenterOfMass - xCannonCenterOfMass;
    float yDistanceFromTarget = yEnemyCenterOfMass - yCannonCenterOfMass;
    float distance =
        (float) Math.sqrt(Math.pow(xDistanceFromTarget, 2) + Math.pow(yDistanceFromTarget, 2));
    return distance;
  }

  private float calculateAngle() {
    float xCannonCenterOfMass = (x + cannonTexture.getImageWidth() / 2);
    float yCannonCenterOfMass = (y + cannonTexture.getImageHeight() / 2);
    float xTargetCenterOfMass = (target.getX() + target.getWidth() / 2);
    float yTargetCenterOfMass = (target.getY() + target.getHeight() / 2);
    float xDistanceFromTarget = xTargetCenterOfMass - xCannonCenterOfMass;
    float yDistanceFromTarget = yTargetCenterOfMass - yCannonCenterOfMass;
    double angleTemp = Math.atan2(yDistanceFromTarget, xDistanceFromTarget);
    return (float) Math.toDegrees(angleTemp) - 90;
  }

  public void shoot() {
    // center of projectile needs to be centered on tower
    float xTileCenter = cannonTexture.getImageWidth() / 2;
    float yTileCenter = cannonTexture.getImageHeight() / 2;
    // Texture bullet = QuickLoad("bulletFilled");
    Texture bullet = QuickLoad("bullet");
    float xProjectileOffset = bullet.getImageWidth() / 2;
    float yProjectileOffset = bullet.getImageWidth() / 2;
    // projectiles.add(new ProjectileBullet(bullet, target, x + xTileCenter - xProjectileOffset,
    // y + yTileCenter - yProjectileOffset, bullet.getImageWidth(), bullet.getImageHeight(),
    // ammoVelocity, damage));
    projectiles.add(new ProjectileCannonball(ProjectileType.CannonBall, target,
        x + xTileCenter - xProjectileOffset, y + yTileCenter - yProjectileOffset));
  }

  public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
    enemies = newList;
  }

  public void update() {
    if (!targeted) {
      target = acquireTarget();
    }

    if (target == null || target.isAlive() == false)
      targeted = false;
    timeSinceLastShot += Delta();
    if (timeSinceLastShot > firingSpeed) {
      shoot();
      timeSinceLastShot = 0;
    }

    for (Projectile p : projectiles)
      p.update();

    angle = calculateAngle();
    draw();
  }

  public void draw() {
    DrawQuadTex(baseTexture, x, y, width, height);
    DrawQuadTexRot(cannonTexture, x, y, width, height, angle);
  }
}
