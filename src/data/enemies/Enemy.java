package data.enemies;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import static helpers.Clock.Delta;
import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;
import data.Checkpoint;
import data.Entity;
import data.HealthBar;
import data.Tile;
import data.TileGrid;

public class Enemy implements Entity {
  private int width, height, currentCheckpoint;
  private float x, y, speed, startHealth, health;
  private Texture texture, healthBackground, healthForeground, healthBorder;
  private Tile startTile;
  private TileGrid grid;
  private boolean first = true, alive = true;
  private HealthBar healthBar;

  private ArrayList<Checkpoint> checkpoints;
  private int[] directions;

  public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed,
      float health) {
    this.texture = texture;
    this.healthBackground = QuickLoad("healthBackground");
    this.healthForeground = QuickLoad("healthForeground");
    this.healthBorder = QuickLoad("healthBorder");
    this.startTile = startTile;
    this.grid = grid;
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.width = width;
    this.height = height;
    this.speed = speed;
    this.startHealth = health;
    this.health = health;
    this.healthBar = new HealthBar(x, y, width, height, startHealth, health);

    this.checkpoints = new ArrayList<Checkpoint>();
    this.directions = new int[2];
    // X Direction
    this.directions[0] = 0;
    // Y Direction
    this.directions[1] = 0;
    this.directions = findNextDirection(startTile);
    this.currentCheckpoint = 0;
    populateCheckpointList();
  }

  public Enemy(Enemy enemy) {
    this.texture = enemy.getTexture();
    this.healthBackground = QuickLoad("healthBackground");
    this.healthForeground = QuickLoad("healthForeground");
    this.healthBorder = QuickLoad("healthBorder");
    this.startTile = enemy.getStartTile();
    this.grid = enemy.getTileGrid();
    this.x = enemy.getStartTile().getX();
    this.y = enemy.getStartTile().getY();
    this.width = enemy.getWidth();
    this.height = enemy.getHeight();
    this.speed = enemy.getWidth();
    this.health = enemy.getHealth();
    this.startHealth = enemy.getHealth();
    this.healthBar = new HealthBar(x, y, width, height, startHealth, health);

    this.checkpoints = new ArrayList<Checkpoint>();
    this.directions = new int[2];
    // X Direction
    this.directions[0] = 0;
    // Y Direction
    this.directions[1] = 0;
    this.directions = findNextDirection(startTile);
    this.currentCheckpoint = 0;
    populateCheckpointList();
  }

  @Override
  public void update() {
    if (first)
      first = false;
    else {
      if (checkpointReached()) {
        if (currentCheckpoint + 1 == checkpoints.size()) {
          die();
        } else
          currentCheckpoint++;
      } else {
        x += Delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
        y += Delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
      }
      healthBar.setPosition(x, y);
      healthBar.setHealth(health);
    }
  }

  private boolean checkpointReached() {
    boolean reached = false;
    Tile t = checkpoints.get(currentCheckpoint).getTile();
    // Check if position reached tile within variance of 3 (arbitrary)
    if (x > t.getX() - 3 && x < t.getX() + 3 && y > t.getY() - 3 && y < t.getY() + 3) {
      reached = true;
      x = t.getX();
      y = t.getY();
    }
    return reached;
  }

  private void populateCheckpointList() {
    checkpoints.add(findNextCheckpoint(startTile, directions = findNextDirection(startTile)));
    int counter = 0;
    boolean cont = true;
    while (cont) {
      int[] currentD = findNextDirection(checkpoints.get(counter).getTile());
      // Check if the next direction/checkpoint exists; end after 20 checkpoints (arbitrary)
      if (currentD[0] == 2 || counter == 20) {
        cont = false;
      } else {
        checkpoints.add(findNextCheckpoint(checkpoints.get(counter).getTile(),
            directions = findNextDirection(checkpoints.get(counter).getTile())));
      }
      counter++;
    }
  }

  private Checkpoint findNextCheckpoint(Tile s, int[] dir) {
    Tile next = null;
    Checkpoint c = null;

    // Boolean to determine if next checkpoint has been found
    boolean found = false;
    int counter = 1;
    while (!found) {
      if (s.getXPlace() + dir[0] * counter == grid.getTilesWide()
          || s.getYPlace() + dir[0] * counter == grid.getTilesHigh()
          || s.getType() != grid
              .getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter)
              .getType()) {
        found = true;
        // Move counter back 1 to find tile before new tiletype
        counter--;
        next = grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter);
      }
      counter++;
    }
    c = new Checkpoint(next, dir[0], dir[1]);
    return c;
  }

  private int[] findNextDirection(Tile s) {
    int[] dir = new int[2];
    Tile up = grid.getTile(s.getXPlace(), s.getYPlace() - 1);
    Tile right = grid.getTile(s.getXPlace() + 1, s.getYPlace());
    Tile down = grid.getTile(s.getXPlace(), s.getYPlace() + 1);
    Tile left = grid.getTile(s.getXPlace() - 1, s.getYPlace());

    if (s.getType() == up.getType() && directions[1] != 1) {
      dir[0] = 0;
      dir[1] = -1;
    } else if (s.getType() == right.getType() && directions[0] != -1) {
      dir[0] = 1;
      dir[1] = 0;
    } else if (s.getType() == down.getType() && directions[1] != -1) {
      dir[0] = 0;
      dir[1] = 1;
    } else if (s.getType() == left.getType() && directions[0] != 1) {
      dir[0] = -1;
      dir[1] = 0;
    } else {
      // NO DIRECTIONS FOUND: end of line
      dir[0] = 2;
      dir[1] = 2;
    }
    return dir;
  }

  public void damage(int amount) {
    health -= amount;
    System.out.println("Enemy health: " + health);
    if (health <= 0)
      die();
  }

  private void die() {
    alive = false;
  }

  @Override
  public void draw() {
    DrawQuadTex(texture, x, y, width, height);
    if (health != startHealth) {
      // float healthPercentage = health / startHealth;
      // DrawQuadTex(healthBackground, x, y - 16, width, 8);
      // DrawQuadTex(healthForeground, x, y - 16, width * healthPercentage, 8);
      // DrawQuadTex(healthBorder, x, y - 16, width, 8);
      healthBar.update();
    }
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public void setWidth(int width) {
    this.width = width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public void setHeight(int height) {
    this.height = height;
  }

  public float getHealth() {
    return health;
  }

  public void setHealth(float health) {
    this.health = health;
  }

  @Override
  public float getX() {
    return x;
  }

  @Override
  public void setX(float x) {
    this.x = x;
  }

  @Override
  public float getY() {
    return y;
  }

  @Override
  public void setY(float y) {
    this.y = y;
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public Texture getTexture() {
    return texture;
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  public Tile getStartTile() {
    return startTile;
  }

  public void setStartTile(Tile startTile) {
    this.startTile = startTile;
  }

  public boolean isFirst() {
    return first;
  }

  public void setFirst(boolean first) {
    this.first = first;
  }

  public TileGrid getTileGrid() {
    return grid;
  }

  public void setTileGrid(TileGrid grid) {
    this.grid = grid;
  }

  public boolean isAlive() {
    return alive;
  }
}
