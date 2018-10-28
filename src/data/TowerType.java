package data;

import static helpers.Artist.QuickLoad;
import org.newdawn.slick.opengl.Texture;

public enum TowerType {

  CannonRed(new Texture[] {QuickLoad("cannonBase"), QuickLoad("cannonGun")}, 10, 1000,
      3), CannonBlue(new Texture[] {QuickLoad("cannonBaseBlue"), QuickLoad("cannonGunBlue")}, 30,
          1000, 3);

  Texture[] textures;
  int damage, range;
  float firingSpeed;

  TowerType(Texture[] textures, int damage, int range, float firingSpeed) {
    this.textures = textures;
    this.damage = damage;
    this.range = range;
    this.firingSpeed = firingSpeed;
  }


}
