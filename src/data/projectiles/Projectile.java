package data.projectiles;

import static helpers.Artist.CheckCollision;
import static helpers.Artist.DrawQuadTex;
import static helpers.Clock.Delta;
import org.newdawn.slick.opengl.Texture;
import data.Entity;
import data.enemies.Enemy;

public abstract class Projectile implements Entity {

  private Texture texture;
  private float x, y, speed, xVelocity, yVelocity;
  private int width, height, damage;
  private Enemy target;
  private boolean alive;

  public Projectile(ProjectileType type, Enemy target, float x, float y) {
    this.x = x;
    this.y = y;
    helper(type, target);
  }

  public Projectile(ProjectileType type, Enemy target, float[] towerCoord, float[] towerSize) {
    this.x = towerCoord[0] + towerSize[0] / 2 - type.texture.getImageWidth() / 2;
    this.y = towerCoord[1] + towerSize[1] / 2 - type.texture.getImageHeight() / 2;
    helper(type, target);
  }

  private void helper(ProjectileType type, Enemy target) {
    this.texture = type.texture;
    this.width = this.texture.getImageWidth();
    this.height = this.texture.getImageHeight();
    this.speed = type.speed;
    this.damage = type.damage;
    this.target = target;
    this.alive = true;
    this.xVelocity = 0;
    this.yVelocity = 0;
    calculateDirection();
  }

  private void calculateDirection() {
    float xProjectileCenterOfMass = (x + texture.getImageWidth() / 2);
    float yProjectileCenterOfMass = (y + texture.getImageHeight() / 2);
    float xTargetCenterOfMass = (target.getX() + target.getWidth() / 2);
    float yTargetCenterOfMass = (target.getY() + target.getHeight() / 2);
    float xDistanceFromTarget = xTargetCenterOfMass - xProjectileCenterOfMass;
    float yDistanceFromTarget = yTargetCenterOfMass - yProjectileCenterOfMass;
    double offsetAngle = 90;
    double angle =
        Math.toDegrees(Math.atan2(yDistanceFromTarget, xDistanceFromTarget)) - offsetAngle;
    xVelocity = -1 * (float) Math.sin(Math.toRadians(angle)); // x axis for angles are swapped
    yVelocity = (float) Math.cos(Math.toRadians(angle));
  }

  // TODO rename to applyEffect
  // Deals damage to Enemy
  public void damage() {
    // it may look like it missed, but using the filled bullet
    // and UFO tiles, they do intersect, though the circles themselves
    // don't. not sure how to handle that - it basically gives the bullet
    // a bit of an area of effect rather that a exact collision
    target.damage(damage);
    alive = false;
    // System.out.println("Target collision! Health is down to " + target.getHealth());
  }

  @Override
  public void update() {
    if (alive) {
      calculateDirection();
      x += xVelocity * speed * Delta();
      y += yVelocity * speed * Delta();
      if (CheckCollision(x, y, width, height, target.getX(), target.getY(), target.getWidth(),
          target.getHeight()))
        damage();
      draw();
    }
  }

  @Override
  public void draw() {
    DrawQuadTex(texture, x, y, width, height);
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

  public void setAlive(boolean alive) {
    this.alive = alive;
  }
}
