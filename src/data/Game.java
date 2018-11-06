package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import org.lwjgl.input.Mouse;
import UI.UI;
import data.enemies.Enemy;
import data.towers.TowerCannonBlue;
import data.towers.TowerIce;
import data.towers.TowerType;

public class Game {

  private TileGrid grid;
  private Player player;
  private WaveManager waveManager;
  private UI towerPickerUI;

  public Game(int[][] map) {
    grid = new TileGrid(map);
    waveManager = new WaveManager(
        new Enemy(QuickLoad("UFO64"), grid.getTile(10, 8), grid, TILE_SIZE, TILE_SIZE, 70, 100), 2,
        2);
    player = new Player(grid, waveManager);
    player.setup();
    setupUI();
  }

  private void setupUI() {
    towerPickerUI = new UI();
    towerPickerUI.addButton("CannonBlue", "cannonBlue", 0, 0);
    towerPickerUI.addButton("CannonRed", "cannonRed", 0, 64);
    towerPickerUI.addButton("CannonIce", "cannonIce", 0, 128);
  }

  private void updateUI() {
    towerPickerUI.draw();

    if (Mouse.next()) {
      boolean mouseClicked = Mouse.isButtonDown(0);
      if (mouseClicked) {
        if (towerPickerUI.isButtonClicked("CannonBlue")) {
          player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(0, 0),
              waveManager.getCurrentWave().getEnemyList()));
        } else if (towerPickerUI.isButtonClicked("CannonRed")) {
          player.pickTower(new TowerCannonBlue(TowerType.CannonRed, grid.getTile(0, 0),
              waveManager.getCurrentWave().getEnemyList()));
        } else if (towerPickerUI.isButtonClicked("CannonIce")) {
          player.pickTower(new TowerIce(TowerType.CannonIce, grid.getTile(0, 0),
              waveManager.getCurrentWave().getEnemyList()));
        }
      }
    }
  }

  public void update() {
    grid.draw();
    waveManager.update();
    player.update();
    updateUI();
  }
}
