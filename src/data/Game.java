package data;

import static helpers.Artist.QuickLoad;

public class Game {

  private TileGrid grid;
  private Player player;
  private WaveManager waveManager;

  // Temp variables
  private float test;
  TowerCannon tower;

  public Game(int[][] map) {
    grid = new TileGrid(map);
    player = new Player(grid);
    waveManager =
        new WaveManager(new Enemy(QuickLoad("UFO64"), grid.GetTile(10, 8), grid, 64, 64, 70), 2, 2);

    tower = new TowerCannon(QuickLoad("cannonBase"), grid.GetTile(14, 7), 10);
  }

  public void Update() {
    // test += Delta();
    // System.out.println(test);
    grid.Draw();
    waveManager.Update();
    player.Update();

    tower.Update();
  }
}
