package data.enemies;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;
import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;
import data.Checkpoint;
import data.Entity;
import data.HealthBar;
import data.Player;
import data.Tile;
import data.TileGrid;

public class Enemy implements Entity {
  private int width, height, currentCheckpoint;
  private float x, y, speed, startHealth, health, hiddenHealth;
  private Texture texture;
  // healthBackground, healthForeground, healthBorder;
  private Tile startTile;
  private TileGrid grid;
  private boolean first, alive;
  private HealthBar healthBar;

  private ArrayList<Checkpoint> checkpoints;
  private int[] directions;

  // New default constructor
  public Enemy(int tileX, int tileY, TileGrid grid) {
    constructorHelper(QuickLoad("enemyFloating_1"), grid.getTile(tileX, tileY), grid, TILE_SIZE,
        TILE_SIZE, 50, 50);
  }

  public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed,
      float health) {
    constructorHelper(texture, startTile, grid, width, height, speed, health);
  }

  public Enemy(Enemy enemy) {
    constructorHelper(enemy.getTexture(), enemy.getStartTile(), enemy.getTileGrid(),
        enemy.getWidth(), enemy.getHeight(), enemy.getSpeed(), enemy.getHealth());
  }

  private void constructorHelper(Texture texture, Tile startTile, TileGrid grid, int width,
      int height, float speed, float health) {
    this.texture = texture;
    this.startTile = startTile;
    this.grid = grid;
    this.first = true;
    this.alive = true;
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.width = width;
    this.height = height;
    this.speed = speed;
    this.startHealth = health;
    this.health = health;
    this.hiddenHealth = health;
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
    // Check if it's the first time this class is updated, if so do nothing
    if (first)
      first = false;
    else {
      if (checkpointReached()) {
        // Check if there are move checkpoints before moving on
        if (currentCheckpoint + 1 == checkpoints.size()) {
          endOfMazeReached();
        } else
          currentCheckpoint++;
      } else {
        // if not at a checkpoint, continue in the current direction
        x += Delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
        y += Delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
      }
      healthBar.setPosition(x, y);
      healthBar.setHealth(health);
    }
  }

  // run when last checkpoint is reached by enemy
  private void endOfMazeReached() {
    Player.modifyLives(-1);
    die();
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
    // Add checkpoing manually based on startTile
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
        // Move counter back 1 to find tile before new TileType
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

    // Check if current inhabited TileType matches TileType above, right, down and left Tiles
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

  // Take damage from external source
  public void damage(int amount) {
    health -= amount;
    if (health <= 0) {
      die();
      Player.modifyCash(5);
    }
  }

  private void die() {
    alive = false;
  }

  @Override
  public void draw() {
    DrawQuadTex(texture, x, y, width, height);
    if (health != startHealth) {
      healthBar.update();
    }
  }

  public void reduceHiddenHealth(float amount) {
    hiddenHealth -= amount;
  }

  public float getHiddenHealth() {
    return hiddenHealth;
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

  public void setTexture(String texture) {
    this.texture = QuickLoad(texture);
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
