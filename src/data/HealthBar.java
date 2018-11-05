package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import org.newdawn.slick.opengl.Texture;

public class HealthBar implements Entity {
  private float x, y, percentage, startHealth, currentHealth;
  private int width, height;
  private Texture healthBackground, healthForeground, healthBorder;

  // TODO create a health bar queue for drawing so that all health bars are rendered above other
  // objects (or give it a z factor that ensures this is the case)
  public HealthBar(float x, float y, int width, int height, float startHealth,
      float currentHealth) {
    super();
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.startHealth = startHealth;
    this.currentHealth = currentHealth;
    this.healthBackground = QuickLoad("healthBackground");
    this.healthForeground = QuickLoad("healthForeground");
    this.healthBorder = QuickLoad("healthBorder");
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

  public void setPosition(float x, float y) {
    this.x = x;
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

  public void setHealth(float health) {
    this.currentHealth = health;
  }

  @Override
  public void update() {
    percentage = currentHealth / startHealth;
    draw();
  }

  @Override
  public void draw() {
    DrawQuadTex(healthBackground, x, y - 16, width, 8);
    DrawQuadTex(healthForeground, x, y - 16, width * percentage, 8);
    DrawQuadTex(healthBorder, x, y - 16, width, 8);
  }

}
