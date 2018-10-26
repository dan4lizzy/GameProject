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
    background = QuickLoad("mainmenu");
    menuUI = new UI();
    // buttons are 256 x 64
    int buttonCenter = 256 / 2;
    int centerWidth = WIDTH / 2;
    menuUI.addButton("Play", "playButton", centerWidth - buttonCenter, (int) (HEIGHT * 0.45f));
    menuUI.addButton("Editor", "editorButton", centerWidth - buttonCenter, (int) (HEIGHT * 0.55f));
    menuUI.addButton("Quit", "quitButton", centerWidth - buttonCenter, (int) (HEIGHT * 0.65f));
    leftMouseButtonDown = false;
  }

  private void updateButtons() {
    if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
      if (menuUI.isButtonClicked("Play")) {
        System.out.println("Play button clicked");
        StateManager.setState(GameState.GAME);
      }
    }
    leftMouseButtonDown = Mouse.isButtonDown(0);
  }

  public void update() {
    // opengl has issues with sizes not power of 2
    DrawQuadTex(background, 0, 0, 2048, 1024);
    menuUI.draw();
    updateButtons();
  }
}
