package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import org.newdawn.slick.opengl.Texture;

public class MainMenu {

  private Texture background;

  public MainMenu() {
    background = QuickLoad("mainmenu");
  }

  public void update() {
    // opengl has issues with sizes not power of 2
    DrawQuadTex(background, 0, 0, 2048, 1024);
  }
}
