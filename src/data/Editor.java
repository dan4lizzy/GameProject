package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.GRID_WIDTH;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.MENU_WIDTH;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.WIDTH;
import static helpers.Leveler.LoadMap;
import static helpers.Leveler.SaveMap;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import UI.UI;
import UI.UI.Menu;

public class Editor {
  private TileGrid grid;
  private int index;
  private TileType[] types;
  private UI editorUI;
  private Menu tilePickerMenu;

  public Editor() {
    this.grid = LoadMap("default.map");
    this.index = 0;
    this.types = new TileType[3];
    this.types[0] = TileType.Grass;
    this.types[1] = TileType.Dirt;
    this.types[2] = TileType.Water;
    setupUI();
  }

  private void setupUI() {
    editorUI = new UI();
    int offset = (int) (TILE_SIZE * 1.5);
    editorUI.createMenu("TilePicker", GRID_WIDTH, offset, MENU_WIDTH, HEIGHT - offset, 2, 0);
    tilePickerMenu = editorUI.getMenu("TilePicker");
    tilePickerMenu.quickAdd("Grass", "grass64");
    tilePickerMenu.quickAdd("Dirt", "dirt64");
    tilePickerMenu.quickAdd("Water", "water64");
  }

  public void update() {
    draw();

    if (Mouse.next()) {
      boolean mouseClicked = Mouse.isButtonDown(0);
      if (mouseClicked) {
        if (tilePickerMenu.isButtonClicked("Grass"))
          index = 0;
        else if (tilePickerMenu.isButtonClicked("Dirt"))
          index = 1;
        else if (tilePickerMenu.isButtonClicked("Water"))
          index = 2;
        else
          setTile();
      }
    }

    // Handle keyboard inputs
    while (Keyboard.next()) {
      if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
        moveIndex();
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_C && Keyboard.getEventKeyState()) {
        grid = new TileGrid();
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_L && Keyboard.getEventKeyState()) {
        grid = LoadMap("default.map");
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
        SaveMap("default.map", grid);
      }
    }
  }

  private void draw() {
    DrawQuadTex(QuickLoad("tile_menu_background"), WIDTH - MENU_WIDTH, 0, MENU_WIDTH, HEIGHT);
    grid.draw();
    editorUI.draw();
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
