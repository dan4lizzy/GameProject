package UI;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import java.util.ArrayList;
import org.lwjgl.input.Mouse;

public class UI {

  private ArrayList<Button> buttonList;
  private ArrayList<Menu> menuList;

  public UI() {
    buttonList = new ArrayList<Button>();
    menuList = new ArrayList<Menu>();
  }

  public void addButton(String name, String textureName, int x, int y) {
    buttonList.add(new Button(name, QuickLoad(textureName), x, y));
  }

  public boolean isButtonClicked(String buttonName) {
    Button b = getButton(buttonName);
    float mouseY = HEIGHT - Mouse.getY() - 1;
    if (Mouse.getX() > b.getX() & Mouse.getX() < b.getX() + b.getWidth()
        && mouseY > b.getY() & mouseY < b.getY() + b.getHeight()) {
      return true;
    }
    return false;
  }

  private Button getButton(String buttonName) {
    for (Button b : buttonList) {
      if (b.getName().equals(buttonName)) {
        return b;
      }
    }
    return null;
  }

  public void createMenu(String name, int x, int y) {
    menuList.add(new Menu(name, x, y));
  }

  public Menu getMenu(String name) {
    for (Menu m : menuList) {
      if (name.equals(m.getName())) {
        return m;
      }
    }
    return null;
  }

  public void draw() {
    for (Button b : buttonList)
      DrawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());

    for (Menu m : menuList)
      m.draw();
  }

  public class Menu {

    private String name;
    private ArrayList<Button> menuButtons;
    private int x, y, buttonAmount;

    public Menu(String name, int x, int y) {
      this.name = name;
      this.x = x;
      this.y = y;
      this.buttonAmount = 0;
      this.menuButtons = new ArrayList<Button>();
    }

    public void addButton(Button b) {
      b.setX(x + buttonAmount * TILE_SIZE);
      menuButtons.add(b);
    }

    public String getName() {
      return name;
    }

    public boolean isButtonClicked(String buttonName) {
      Button b = getButton(buttonName);
      float mouseY = HEIGHT - Mouse.getY() - 1;
      if (Mouse.getX() > b.getX() & Mouse.getX() < b.getX() + b.getWidth()
          && mouseY > b.getY() & mouseY < b.getY() + b.getHeight()) {
        return true;
      }
      return false;
    }

    private Button getButton(String buttonName) {
      for (Button b : menuButtons) {
        if (b.getName().equals(buttonName)) {
          return b;
        }
      }
      return null;
    }

    public void draw() {
      for (Button b : menuButtons) {
        DrawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
      }
    }
  }
}
