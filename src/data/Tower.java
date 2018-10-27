package data;

import static helpers.Artist.DrawQuadTex;
import org.newdawn.slick.opengl.Texture;

public abstract class Tower implements Entity {

  private float x, y;
  private int width, height, damage;
  private Enemy target;
  private Texture[] textures;

  public Tower(TowerType type, Tile startTile) {
    this.textures = type.textures;
    this.damage = type.damage;
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.width = startTile.getWidth();
    this.height = startTile.getHeight();
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
    for (int i = 0; i < textures.length; i++) {
      DrawQuadTex(textures[i], x, y, width, height);
    }
  }

}
