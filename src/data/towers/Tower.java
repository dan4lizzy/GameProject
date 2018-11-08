package data.towers;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Clock.Delta;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import org.newdawn.slick.opengl.Texture;
import data.Entity;
import data.Tile;
import data.enemies.Enemy;
import data.projectiles.Projectile;

public abstract class Tower implements Entity {

  private float x, y, timeSinceLastShot, firingSpeed, azimuth, maxRotationSpeed;
  private int width, height, range, cost;
  protected Enemy target;
  protected Texture[] textures;
  private CopyOnWriteArrayList<Enemy> enemies;
  private boolean targeted, initialAcquire;
  protected ArrayList<Projectile> projectiles;
  protected TowerType type;

  public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
    this.type = type;
    this.textures = type.textures;
    this.range = type.range;
    this.setCost(type.cost);
    this.firingSpeed = type.firingSpeed;
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.width = startTile.getWidth();
    this.height = startTile.getHeight();
    this.enemies = enemies;
    this.targeted = false;
    this.timeSinceLastShot = 0;
    this.projectiles = new ArrayList<Projectile>();
    // don't want turrets to instantly point to target,
    // but to swing to it relatively quickly
    this.initialAcquire = false;
    this.maxRotationSpeed = 5; // degrees

    // My code modifications
    this.azimuth = 0;
  }

  private Enemy acquireTarget() {
    Enemy closest = null;
    // Arbitrary distance (larger than map, to help with sorting Enemy distances
    float closestDistance = 10000;
    // Go through each Enemy in 'enemies' and return nearest one
    for (Enemy e : enemies) {
      if (e.getHiddenHealth() > 0 && isInRange(e) && findDistance(e) < closestDistance) {
        closestDistance = findDistance(e);
        closest = e;
        initialAcquire = true;
      }
    }
    // If an enemy exists and is returned, targeted == true
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
    if (target == null) {
      return azimuth;
    }
    float xCannonCenterOfMass = (x + textures[0].getImageWidth() / 2);
    float yCannonCenterOfMass = (y + textures[0].getImageHeight() / 2);
    float xTargetCenterOfMass = (target.getX() + target.getWidth() / 2);
    float yTargetCenterOfMass = (target.getY() + target.getHeight() / 2);
    float xDistanceFromTarget = xTargetCenterOfMass - xCannonCenterOfMass;
    float yDistanceFromTarget = yTargetCenterOfMass - yCannonCenterOfMass;
    // default: -90 gets turret pointing in the correct direction
    float azimuthToTarget =
        (float) Math.toDegrees(Math.atan2(yDistanceFromTarget, xDistanceFromTarget)) - 90;

    if (initialAcquire) { // only limit speed to track to target on initial acquire
      float changeInAngle = azimuthToTarget - azimuth;
      if (Math.abs(changeInAngle) < maxRotationSpeed) {
        initialAcquire = false;
      } else {
        if (Math.abs(changeInAngle) > 180) {
          float magnitude = 1;
          if (changeInAngle > 0) { // e.g. 181 to 359
            magnitude = -1;
          }
          changeInAngle = magnitude * (360 - Math.abs(changeInAngle));
        }
        if (Math.abs(changeInAngle) > maxRotationSpeed) {
          if (changeInAngle > 0) { // e.g. 0 < x <= 180
            changeInAngle = maxRotationSpeed;
          } else { // e.g. -180 <= x <= 0
            changeInAngle = -1 * maxRotationSpeed;
          }
        }
        azimuthToTarget = azimuth + changeInAngle;
        if (azimuthToTarget > 90) //
          azimuthToTarget -= 360;
      }
    }
    return azimuthToTarget;
  }

  // Abstract method for 'shoot', must be overridden in subclasses
  public abstract void shoot(Enemy target);

  public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
    enemies = newList;
  }

  @Override
  public void update() {
    timeSinceLastShot += Delta();

    if (!targeted || target.getHiddenHealth() < 0) {
      target = acquireTarget();
    } else {
      azimuth = calculateAngle();
      if (timeSinceLastShot > firingSpeed && !initialAcquire) {
        shoot(target);
        timeSinceLastShot = 0;
      }
    }

    if (target == null || target.isAlive() == false)
      targeted = false;

    for (Projectile p : projectiles)
      p.update();

    draw();
  }

  @Override
  public void draw() {
    DrawQuadTex(textures[0], x, y, width, height);
    if (textures.length > 1)
      for (int i = 1; i < textures.length; i++) {
        DrawQuadTexRot(textures[i], x, y, width, height, azimuth);
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

  public float[] getCoord() {
    return new float[] {x, y};
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  public float[] getSize() {
    return new float[] {textures[0].getImageWidth(), textures[0].getImageWidth()};
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

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

}
