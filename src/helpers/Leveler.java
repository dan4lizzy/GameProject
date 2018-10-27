package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import data.Tile;
import data.TileGrid;

public class Leveler {

  public static void saveMap(String mapName, TileGrid grid) {
    String mapData = "";
    for (int i = 0; i < grid.getTilesWide(); i++) {
      for (int j = 0; j < grid.getTilesHigh(); j++) {
        mapData += getTileID(grid.GetTile(i, j));
      }
    }
    try {
      File file = new File(mapName);
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      bw.write(mapData);
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String getTileID(Tile t) {
    String ID = "E";
    switch (t.getType()) {
      case Grass:
        ID = "0";
        break;
      case Dirt:
        ID = "1";
        break;
      case Water:
        ID = "2";
        break;
      case NULL:
        ID = "3";
        break;
    }
    return ID;
  }
}
