package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.WIDTH;
import org.newdawn.slick.opengl.Texture;
import UI.UI;

public class MainMenu {

  private Texture background;
  private UI menuUI;

  public MainMenu() {
    background = QuickLoad("mainmenu");
    menuUI = new UI();
    menuUI.addButton("Play", "playButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f));
  }

  public void update() {
    // opengl has issues with sizes not power of 2
    DrawQuadTex(background, 0, 0, 2048, 1024);
    menuUI.draw();
  }
}
