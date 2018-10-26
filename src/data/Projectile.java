package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Clock.Delta;
import org.newdawn.slick.opengl.Texture;

public class Projectile {

  private Texture texture;
  private float x, y, speed, xVelocity, yVelocity;
  private int damage;
  private Enemy target;

  public Projectile(Texture texture, Enemy target, float x, float y, float speed, int damage) {
    this.texture = texture;
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.damage = damage;
    this.target = target;
    this.xVelocity = 0;
    this.yVelocity = 0;
    calculateDirection();
  }

  private void calculateDirection() {
    // this is per the tutorial, but it's incorrect per trigonometry
    // float xDistanceFromTarget = Math.abs(target.getX() - x);
    // float yDistanceFromTarget = Math.abs(target.getY() - y);
    // float totalAllowedMovement = 1.0f; // this is 100%, speed is handled in the draw method
    // float totalDistanceFromTargetInvalid = xDistanceFromTarget + yDistanceFromTarget;
    // float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTargetInvalid;
    // xVelocity = xPercentOfMovement;
    // yVelocity = totalAllowedMovement - xPercentOfMovement;
    // if (target.getX() < x)
    // xVelocity *= -1;
    // if (target.getY() < y)
    // yVelocity *= -1;

    // trigonometric solution using the Pythagorean theorem
    // a^2 + b^2 = c^2 and with a unit circle the hypotenuse is equal
    // to 1 so we don't need the totalAllowedMovement variable
    float xProjectileCenterOfMass = (x + texture.getImageWidth() / 2);
    float yProjectileCenterOfMass = (y + texture.getImageHeight() / 2);
    float xTargetCenterOfMass = (target.getX() + target.getWidth() / 2);
    float yTargetCenterOfMass = (target.getY() + target.getHeight() / 2);
    float xDistanceFromTarget = xTargetCenterOfMass - xProjectileCenterOfMass;
    float yDistanceFromTarget = yTargetCenterOfMass - yProjectileCenterOfMass;
    double offsetAngle = 90;
    // TODO convert angle from global to local variable
    double angle =
        Math.toDegrees(Math.atan2(yDistanceFromTarget, xDistanceFromTarget)) - offsetAngle;
    xVelocity = -1 * (float) Math.sin(Math.toRadians(angle)); // x axis for angles are swapped
    yVelocity = (float) Math.cos(Math.toRadians(angle));
  }

  public void update() {
    x += xVelocity * speed * Delta();
    y += yVelocity * speed * Delta();

    draw();
  }

  public void draw() {
    DrawQuadTex(texture, x, y, 32, 32);
  }
}
