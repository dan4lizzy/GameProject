package data;

import static helpers.Artist.DrawQuadTex;
import org.newdawn.slick.opengl.Texture;

public abstract class Tower implements Entity {

  private float x, y;
  private int width, height, damage;
  private Enemy target;
  private Texture texture;

  public Tower(Texture texture, float x, float y, int width, int height) {
    this.texture = texture;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
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

  @Override
  public void update() {
    // TODO Auto-generated method stub
  }

  @Override
  public void draw() {
    DrawQuadTex(texture, x, y, width, height);
  }

}
