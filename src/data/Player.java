package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import data.towers.Tower;
import helpers.Clock;

public class Player {

  private TileGrid grid;
  private TileType[] types;
  private WaveManager waveManager;
  private ArrayList<Tower> towerList;
  private boolean leftMouseButtonDown, rightMouseButtonDown, holdingTower;
  private Tower tempTower;
  public static int Cash, Lives;

  public Player(TileGrid grid, WaveManager waveManager) {
    this.grid = grid;
    this.types = new TileType[3];
    this.types[0] = TileType.Grass;
    this.types[1] = TileType.Dirt;
    this.types[2] = TileType.Water;
    this.waveManager = waveManager;
    this.towerList = new ArrayList<Tower>();
    this.leftMouseButtonDown = false;
    this.rightMouseButtonDown = false;
    this.holdingTower = false;
    this.tempTower = null;
    Cash = 0;
    Lives = 0;
  }

  // Initialize Cash and Lives values for Player
  public void setup() {
    Cash = 200;
    Lives = 10;
  }

  public static boolean modifyCash(int amount) {
    if (Cash + amount >= 0) {
      Cash += amount;
      // TODO Remove/comment out the following line
      System.out.println("Cash: " + Cash);
      return true;
    }
    return false;
  }

  // Yuk, most of the time this is going to be up/down by one,
  // so it should be formatted similar to modify cash and then add
  // increment/decrementLife methods
  public static void modifyLives(int amount) {
    Lives += amount;
    // TODO Remove/comment out the following line
    System.out.println("Lives: " + Lives);
  }

  public void update() {

    // Update holding tower
    if (holdingTower) {
      tempTower.setX(getMouseTile().getX());
      tempTower.setY(getMouseTile().getY());
      tempTower.draw();
    }

    // Update all towers in the game
    for (Tower t : towerList) {
      t.update();
      t.draw();
      t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
    }

    // Handle mouse inputs
    if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
      // System.out.println("Mouse button 0 down");
      placeTower();

    }
    if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
      System.out.println("Mouse button 1 down");
    }

    leftMouseButtonDown = Mouse.isButtonDown(0);
    rightMouseButtonDown = Mouse.isButtonDown(1);

    // Handle keyboard inputs
    while (Keyboard.next()) {
      if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
        Clock.ChangeMultiplier(0.2f);
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
        Clock.ChangeMultiplier(-0.2f);
      }
    }
  }

  private void placeTower() {
    if (holdingTower)
      if (modifyCash(-tempTower.getCost()))
        towerList.add(tempTower);
    holdingTower = false;
    tempTower = null;
  }

  public void pickTower(Tower t) {
    tempTower = t;
    holdingTower = true;
  }

  private Tile getMouseTile() {
    return grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE);
  }
}
