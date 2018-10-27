package data;

public abstract class Tower implements Entity {

  private float x, y;
  private int width, height;

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
    // TODO Auto-generated method stub
  }

}
