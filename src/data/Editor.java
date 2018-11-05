package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.Leveler.loadMap;
import static helpers.Leveler.saveMap;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Editor {
  private TileGrid grid;
  private int index;
  private TileType[] types;

  public Editor() {
    this.grid = loadMap("mapTest.map");
    this.index = 0;
    this.types = new TileType[3];
    this.types[0] = TileType.Grass;
    this.types[1] = TileType.Dirt;
    this.types[2] = TileType.Water;
  }

  public void update() {
    grid.draw();

    // Handle mouse inputs
    if (Mouse.isButtonDown(0)) {
      setTile();
    }

    // Handle keyboard inputs
    while (Keyboard.next()) {
      if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
        moveIndex();
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_C && Keyboard.getEventKeyState()) {
        grid = new TileGrid();
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
        saveMap("mapTest.map", grid);
      }
    }
  }

  private void setTile() {
    grid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE),
        (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE), types[index]);
  }

  // Allows editor to change which TileType is selected
  private void moveIndex() {
    index++;
    if (index > types.length - 1) {
      index = 0;
    }
  }
}
