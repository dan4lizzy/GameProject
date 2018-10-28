package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import data.towers.Tower;
import data.towers.TowerCannonBlue;
import data.towers.TowerIce;
import data.towers.TowerType;
import helpers.Clock;

public class Player {

  private TileGrid grid;
  private TileType[] types;
  private WaveManager waveManager;
  private ArrayList<Tower> towerList;
  private boolean leftMouseButtonDown, rightMouseButtonDown;

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
  }

  public void update() {
    for (Tower t : towerList) {
      t.update();
      t.draw();
      t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
    }

    // Handle mouse inputs
    if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
      System.out.println("Mouse button 0 down");
      towerList.add(new TowerCannonBlue(TowerType.CannonRed,
          grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE),
          waveManager.getCurrentWave().getEnemyList()));
    }
    if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
      System.out.println("Mouse button 0 down");
      towerList.add(new TowerIce(TowerType.CannonIce,
          grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE),
          waveManager.getCurrentWave().getEnemyList()));
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
}
