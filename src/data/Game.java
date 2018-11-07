package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.GRID_WIDTH;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.MENU_WIDTH;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.WIDTH;
import org.lwjgl.input.Mouse;
import UI.UI;
import UI.UI.Menu;
import data.enemies.Enemy;
import data.towers.TowerCannonBlue;
import data.towers.TowerCannonIce;
import data.towers.TowerType;

public class Game {

  private TileGrid grid;
  private Player player;
  private WaveManager waveManager;
  private UI gameUI;
  private Menu towerPickerMenu;

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
    gameUI = new UI();
    gameUI.createMenu("TowerPicker", GRID_WIDTH, 0, MENU_WIDTH, HEIGHT, 2, 0);
    towerPickerMenu = gameUI.getMenu("TowerPicker");
    towerPickerMenu.quickAdd("CannonBlue", "cannonBlue");
    towerPickerMenu.quickAdd("CannonRed", "cannonRed");
    towerPickerMenu.quickAdd("CannonIce", "cannonIce");
  }

  private void updateUI() {
    gameUI.draw();

    if (Mouse.next()) {
      boolean mouseClicked = Mouse.isButtonDown(0);
      if (mouseClicked) {
        if (towerPickerMenu.isButtonClicked("CannonBlue")) {
          player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(0, 0),
              waveManager.getCurrentWave().getEnemyList()));
        } else if (towerPickerMenu.isButtonClicked("CannonRed")) {
          player.pickTower(new TowerCannonBlue(TowerType.CannonRed, grid.getTile(0, 0),
              waveManager.getCurrentWave().getEnemyList()));
        } else if (towerPickerMenu.isButtonClicked("CannonIce")) {
          player.pickTower(new TowerCannonIce(TowerType.CannonIce, grid.getTile(0, 0),
              waveManager.getCurrentWave().getEnemyList()));
        }
      }
    }
  }

  public void update() {
    DrawQuadTex(QuickLoad("tower_menu_background"), WIDTH - MENU_WIDTH, 0, MENU_WIDTH, HEIGHT);
    grid.draw();
    waveManager.update();
    player.update();
    updateUI();
  }
}
