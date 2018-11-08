package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.GRID_WIDTH;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.MENU_WIDTH;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.WIDTH;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import UI.UI;
import UI.UI.Menu;
import data.enemies.Enemy;
import data.enemies.EnemyAlien;
import data.enemies.EnemyUFO;
import data.towers.TowerCannonBlue;
import data.towers.TowerCannonIce;
import data.towers.TowerType;

public class Game {

  private TileGrid grid;
  private Player player;
  private WaveManager waveManager;
  private UI gameUI;
  private Menu towerPickerMenu;
  private Texture menuBackground;
  private Enemy[] enemyTypes;

  public Game(TileGrid grid) {
    this.grid = grid;
    this.menuBackground = QuickLoad("tower_menu_background");
    // TODO figure out where to start enemy on grid rather than having starting square hard coded
    // TODO maybe require that a tile is defined as start in the editor?
    // Enemy enemy = new Enemy(QuickLoad("UFO64"), grid.getTile(10, 8), grid, TILE_SIZE, TILE_SIZE,
    // 70, 100);
    // Enemy enemy = new Enemy(QuickLoad("enemyFloating_1"), grid.getTile(3, 0), grid, TILE_SIZE,
    // TILE_SIZE, 70, 100);
    enemyTypes = new Enemy[] {new EnemyAlien(3, 0, grid), new EnemyUFO(3, 0, grid)};
    waveManager = new WaveManager(enemyTypes, 2, 5);
    // waveManager = new WaveManager(new EnemyUFO(3, 0, grid), 2, 2);
    player = new Player(grid, waveManager);
    player.setup();
    setupUI();
  }

  private void setupUI() {
    gameUI = new UI();
    int offset = (int) (TILE_SIZE * 1.5);
    gameUI.createMenu("TowerPicker", GRID_WIDTH, offset, MENU_WIDTH, HEIGHT - offset, 2, 0);
    towerPickerMenu = gameUI.getMenu("TowerPicker");
    towerPickerMenu.quickAdd("CannonBlue", "cannonBlue");
    towerPickerMenu.quickAdd("CannonRed", "cannonRed");
    towerPickerMenu.quickAdd("CannonIce", "cannonIce");
  }

  private void updateUI() {
    gameUI.draw();
    gameUI.drawString(GRID_WIDTH + TILE_SIZE / 4, HEIGHT - TILE_SIZE * 3 / 2,
        "Lives: " + Player.Lives);
    gameUI.drawString(GRID_WIDTH + TILE_SIZE / 4, HEIGHT - TILE_SIZE, "Cash: " + Player.Cash);
    gameUI.drawString(GRID_WIDTH + TILE_SIZE / 4, HEIGHT - TILE_SIZE / 2,
        "Wave: " + waveManager.getWaveNumber());
    gameUI.drawSmallString(GRID_WIDTH + MENU_WIDTH - TILE_SIZE * 3 / 4, HEIGHT - TILE_SIZE / 4,
        "FPS: " + helpers.StateManager.framesInLastSecond);

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

    // Handle keyboard inputs
    // while (Keyboard.next()) {
    // if (Keyboard.getEventKey() == Keyboard.KEY_Q && Keyboard.getEventKeyState()) {
    // StateManager.returnToMenu();
    // }
    // }
  }

  public void update() {
    DrawQuadTex(menuBackground, WIDTH - MENU_WIDTH, 0, MENU_WIDTH, HEIGHT);
    grid.draw();
    waveManager.update();
    player.update();
    updateUI();
  }
}
