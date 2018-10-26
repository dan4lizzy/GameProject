package data;

import static helpers.Artist.DrawQuadTex;
import org.newdawn.slick.opengl.Texture;

public class TowerCannon {
  private float x, y;
  private int width, height, damage;
  private Texture texture;
  private Tile startTile;

  public TowerCannon(Texture texture, Tile startTile, int damage) {
    this.texture = texture;
    this.startTile = startTile;
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.width = (int) startTile.getWidth();
    this.height = (int) startTile.getHeight();
    this.damage = damage;
  }

  public void update() {

  }

  public void Draw() {
    DrawQuadTex(texture, x, y, width, height);
  }
}
