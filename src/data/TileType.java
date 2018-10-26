package data;

public enum TileType {

  Grass("grass64", true), Dirt("dirt64", false), Water("water64", false), NULL("water64", false);

  String textureName;
  boolean buildable; // i.e. can you build towers on this tile;

  TileType(String textureName, boolean buildable) {
    this.textureName = textureName;
    this.buildable = buildable;
  }
}
