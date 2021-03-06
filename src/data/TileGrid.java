package data;

import static helpers.Artist.TILES_HIGH;
import static helpers.Artist.TILES_WIDE;
import static helpers.Artist.TILE_SIZE;

public class TileGrid {

  public Tile[][] map;
  private int tilesWide, tilesHigh;

  public TileGrid() {
    setSingleTileGrip(TileType.Grass);
  }

  public TileGrid(TileType type) {
    setSingleTileGrip(type);
  }

  public TileGrid(int[][] newMap) {
    this.tilesWide = newMap[0].length;
    this.tilesHigh = newMap.length;
    map = new Tile[tilesWide][tilesHigh];

    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        TileType set;
        switch (newMap[j][i]) {
          case 0:
            set = TileType.Grass;
            break;
          case 1:
            set = TileType.Dirt;
            break;
          case 2:
            set = TileType.Water;
            break;
          default:
            set = TileType.Grass;
        }
        map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, set);
      }
    }
  }

  private void setSingleTileGrip(TileType type) {
    this.tilesWide = TILES_WIDE;
    this.tilesHigh = TILES_HIGH;
    map = new Tile[tilesWide][tilesHigh];
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
      }
    }
  }

  public void setTile(int xCoord, int yCoord, TileType type) {
    map[xCoord][yCoord] =
        new Tile(xCoord * TILE_SIZE, yCoord * TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
  }

  public Tile getTile(int xPlace, int yPlace) {
    if (xPlace < tilesWide && xPlace > -1 && yPlace < tilesHigh && yPlace > -1)
      return map[xPlace][yPlace];
    else
      return new Tile(0, 0, 0, 0, TileType.NULL);
  }

  public void draw() {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        map[i][j].draw();
      }
    }
  }

  public int getTilesWide() {
    return tilesWide;
  }

  public void setTilesWide(int tilesWide) {
    this.tilesWide = tilesWide;
  }

  public int getTilesHigh() {
    return tilesHigh;
  }

  public void setTilesHigh(int tilesHigh) {
    this.tilesHigh = tilesHigh;
  }
}
