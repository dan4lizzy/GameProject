package data;

import static helpers.Artist.BeginSession;
import static helpers.Artist.QuickLoad;
import org.lwjgl.opengl.Display;
import helpers.Clock;

public class Boot {

  public Boot() {
    BeginSession();

    // Test loading textures
    // Texture t = QuickLoad("dirt64");
    // Texture t2 = QuickLoad("grass64");

    // Test building individual tiles
    // Tile tile = new Tile(0,0,64,64,TileType.Grass);
    // Tile tile2 = new Tile(0,64,64,64,TileType.Dirt);

    // initial texture definition
    int[][] map = {{0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 2, 2, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 2, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0},
        {0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};

    TileGrid grid = new TileGrid(map);
    grid.SetTile(3, 4, grid.GetTile(3, 5).getType());
    Enemy e = new Enemy(QuickLoad("UFO64"), grid.GetTile(10, 8), grid, 64, 64, 3);
    Wave wave = new Wave(20, e);
    Player player = new Player(grid);

    while (!Display.isCloseRequested()) {
      Clock.update();

      grid.Draw();
      wave.Update();
      player.Update();

      Display.update();
      Display.sync(60);
    }

    Display.destroy();
  }

  public static void main(String[] args) {
    new Boot();
  }
}
