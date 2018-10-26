package UI;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import java.util.ArrayList;

public class UI {

  private ArrayList<Button> buttonList;

  public UI() {
    buttonList = new ArrayList<Button>();
  }

  public void addButton(String name, String textureName, int x, int y) {
    buttonList.add(new Button(name, QuickLoad(textureName), x, y));
  }

  public void draw() {
    for (Button b : buttonList) {
      DrawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
    }
  }
}
