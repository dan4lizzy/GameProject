package helpers;

import static helpers.Leveler.LoadMap;
import data.Editor;
import data.Game;
import data.MainMenu;
import data.TileGrid;

public class StateManager {

  public static enum GameState {
    MAINMENU, GAME, EDITOR
  }

  public static GameState gameState = GameState.MAINMENU;
  public static MainMenu mainMenu;
  public static Game game;
  public static Editor editor;

  public static long nextSecond = System.currentTimeMillis() + 1000;
  public static int framesInLastSecond = 0;
  public static int framesInCurrentSecond = 0;

  // initial texture definition
  static TileGrid map = LoadMap("default.map");

  public static void update() {
    switch (gameState) {
      case MAINMENU:
        if (mainMenu == null)
          mainMenu = new MainMenu();
        mainMenu.update();
        break;
      case GAME:
        if (game == null)
          game = new Game(map);
        game.update();
        break;
      case EDITOR:
        if (editor == null) {
          editor = new Editor();
        }
        editor.update();
        break;
    }

    long currentTime = System.currentTimeMillis() + 1000;
    if (currentTime > nextSecond) {
      nextSecond += 1000;
      framesInLastSecond = framesInCurrentSecond;
      framesInCurrentSecond = 0;
    }
    framesInCurrentSecond++;
  }

  public static void setState(GameState newState) {
    if (gameState != newState) {
      switch (gameState) {
        case MAINMENU:
          mainMenu = null;
          break;
        case GAME:
          game = null;
          break;
        case EDITOR:
          editor = null;
          break;
      }
      gameState = newState;
    }
  }

  public static void returnToMenu() {
    setState(GameState.MAINMENU);
  }
}
