package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import helpers.Clock;

public class Player {

  private TileGrid grid;
  private TileType[] types;
  private WaveManager waveManager;
  private ArrayList<TowerCannon> towerList;
  private boolean leftMouseButtonDown;

  public Player(TileGrid grid, WaveManager waveManager) {
    this.grid = grid;
    this.types = new TileType[3];
    this.types[0] = TileType.Grass;
    this.types[1] = TileType.Dirt;
    this.types[2] = TileType.Water;
    this.waveManager = waveManager;
    this.towerList = new ArrayList<TowerCannon>();
    this.leftMouseButtonDown = false;
  }

  public void update() {
    // Update towers
    for (TowerCannon t : towerList) {
      t.update();
      t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
    }

    // Handle mouse inputs
    if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
      System.out.println("Mouse button 0 down");
      towerList.add(new TowerCannon(QuickLoad("cannonBase"),
          grid.GetTile(Mouse.getX() / 64, (HEIGHT - Mouse.getY() - 1) / 64), 10, 1000,
          waveManager.getCurrentWave().getEnemyList()));
      // this is for editor mode
      // setTile();
    }

    leftMouseButtonDown = Mouse.isButtonDown(0);

    // Handle keyboard inputs
    while (Keyboard.next()) {
      if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
        Clock.ChangeMultiplier(0.2f);
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
        Clock.ChangeMultiplier(-0.2f);
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()) {

        towerList.add(new TowerCannon(QuickLoad("cannonBase"), grid.GetTile(18, 9), 10, 1000,
            waveManager.getCurrentWave().getEnemyList()));
      }
    }
  }

}
