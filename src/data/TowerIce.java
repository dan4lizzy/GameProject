package data;

import java.util.ArrayList;

public class TowerIce extends Tower {

  public TowerIce(TowerType type, Tile startTile, ArrayList<Enemy> enemies) {
    super(type, startTile, enemies);
  }

  @Override
  protected void shoot() {
    super.shoot();
    // TODO should this be a reduction to value or by a percentage down?
    // TODO should be moved to projectile class, and should only be called once it achieves a collision
    super.getTarget().setSpeed(4);
  }
}
