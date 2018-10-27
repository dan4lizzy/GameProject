package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;

public class Game {

  private TileGrid grid;
  private Player player;
  private WaveManager waveManager;

  public Game(int[][] map) {
    grid = new TileGrid(map);
    waveManager = new WaveManager(
        new Enemy(QuickLoad("UFO64"), grid.getTile(10, 8), grid, TILE_SIZE, TILE_SIZE, 70, 25), 2,
        2);
    player = new Player(grid, waveManager);
  }

  public void update() {
    // test += Delta();
    // System.out.println(test);
    grid.draw();
    waveManager.update();
    player.update();
  }
}
