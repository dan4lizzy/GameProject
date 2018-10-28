package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Artist.QuickLoad;
import static helpers.Clock.Delta;
import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;

public abstract class Tower implements Entity {

  private float x, y, ammoVelocity, timeSinceLastShot, firingSpeed, angle;
  private int width, height, damage, range;
  private Enemy target;
  private Texture[] textures;
  private ArrayList<Enemy> enemies;
  private boolean targeted;
  private ArrayList<Projectile> projectiles;

  public Tower(TowerType type, Tile startTile, ArrayList<Enemy> enemies) {
    this.textures = type.textures;
    this.damage = type.damage;
    this.range = type.range;
    this.firingSpeed = type.firingSpeed;
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.width = startTile.getWidth();
    this.height = startTile.getHeight();
    this.enemies = enemies;
    this.targeted = false;
    this.timeSinceLastShot = 0;
    this.projectiles = new ArrayList<Projectile>();

    // My code modifications
    this.ammoVelocity = 900;
    this.angle = 0;
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
    float xCannonCenterOfMass = (x + textures[0].getImageWidth() / 2);
    float yCannonCenterOfMass = (y + textures[0].getImageHeight() / 2);
    float xEnemyCenterOfMass = (e.getX() + e.getWidth() / 2);
    float yEnemyCenterOfMass = (e.getY() + e.getHeight() / 2);
    float xDistanceFromTarget = xEnemyCenterOfMass - xCannonCenterOfMass;
    float yDistanceFromTarget = yEnemyCenterOfMass - yCannonCenterOfMass;
    float distance =
        (float) Math.sqrt(Math.pow(xDistanceFromTarget, 2) + Math.pow(yDistanceFromTarget, 2));
    return distance;
  }

  private float calculateAngle() {
    float xCannonCenterOfMass = (x + textures[0].getImageWidth() / 2);
    float yCannonCenterOfMass = (y + textures[0].getImageHeight() / 2);
    float xTargetCenterOfMass = (target.getX() + target.getWidth() / 2);
    float yTargetCenterOfMass = (target.getY() + target.getHeight() / 2);
    float xDistanceFromTarget = xTargetCenterOfMass - xCannonCenterOfMass;
    float yDistanceFromTarget = yTargetCenterOfMass - yCannonCenterOfMass;
    double angleTemp = Math.atan2(yDistanceFromTarget, xDistanceFromTarget);
    return (float) Math.toDegrees(angleTemp) - 90;
  }

  protected void shoot() {
    float xTileCenter = textures[0].getImageWidth() / 2;
    float yTileCenter = textures[0].getImageHeight() / 2;
    Texture bullet = QuickLoad("bullet");
    float xProjectileOffset = bullet.getImageWidth() / 2;
    float yProjectileOffset = bullet.getImageWidth() / 2;
    projectiles.add(new Projectile(bullet, target, x + xTileCenter - xProjectileOffset,
        y + yTileCenter - yProjectileOffset, bullet.getImageWidth(), bullet.getImageHeight(),
        ammoVelocity, damage));
  }

  public void updateEnemyList(ArrayList<Enemy> newList) {
    enemies = newList;
  }

  @Override
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

  @Override
  public void draw() {
    DrawQuadTex(textures[0], x, y, width, height);
    if (textures.length > 1)
      for (int i = 1; i < textures.length; i++) {
        DrawQuadTexRot(textures[i], x, y, width, height, angle);
      }
  }

  @Override
  public float getX() {
    return x;
  }

  @Override
  public float getY() {
    return y;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public void setX(float x) {
    this.x = x;
  }

  @Override
  public void setY(float y) {
    this.y = y;
  }

  @Override
  public void setWidth(int width) {
    this.width = width;
  }

  @Override
  public void setHeight(int height) {
    this.height = height;
  }

  public Enemy getTarget() {
    return target;
  }

}
