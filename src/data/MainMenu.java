package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.WIDTH;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class MainMenu {

  private Texture background;
  private UI menuUI;
  private boolean leftMouseButtonDown;

  public MainMenu() {
    // background = QuickLoad("mainmenu");
    background = QuickLoad("mainmenu-streched");
    menuUI = new UI();
    // buttons are 256 x 64
    int buttonCenter = 256 / 2;
    int centerWidth = WIDTH / 2;
    menuUI.addButton("Play", "playButton", centerWidth - buttonCenter, (int) (HEIGHT * 0.45f));
    menuUI.addButton("Editor", "editorButton", centerWidth - buttonCenter, (int) (HEIGHT * 0.55f));
    menuUI.addButton("Quit", "quitButton", centerWidth - buttonCenter, (int) (HEIGHT * 0.65f));
    leftMouseButtonDown = false;
  }

  // Check if a button is clicked by the user, and if so do an action
  private void updateButtons() {
    if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
      if (menuUI.isButtonClicked("Play"))
        StateManager.setState(GameState.GAME);
      else if (menuUI.isButtonClicked("Editor"))
        StateManager.setState(GameState.EDITOR);
      else if (menuUI.isButtonClicked("Quit"))
        System.exit(0);
    }
    leftMouseButtonDown = Mouse.isButtonDown(0);
  }

  public void update() {
    // opengl has issues with sizes not power of 2
    // which is why the background isn't defined as
    // our game size of 1280 x 960
    DrawQuadTex(background, 0, 0, 2048, 1024);
    menuUI.draw();
    updateButtons();
  }
}
