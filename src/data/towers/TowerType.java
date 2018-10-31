package data.towers;

import static helpers.Artist.QuickLoad;
import org.newdawn.slick.opengl.Texture;
import data.projectiles.*;
public enum TowerType {

  CannonRed(new Texture[] {QuickLoad("cannonBaseRed"), QuickLoad("cannonGunRed")}, ProjectileType.CannonBall, 10, 1000, 3), 
  CannonBlue(new Texture[] {QuickLoad("cannonBaseBlue"), QuickLoad("cannonGunBlue")}, ProjectileType.CannonBall, 30, 1000, 3),
  CannonIce(new Texture[] {QuickLoad("cannonIceBase"), QuickLoad("cannonIceGun")}, ProjectileType.IceBall, 10, 1000, 3);

  Texture[] textures;
  ProjectileType projectileType;
  int damage, range;
  float firingSpeed;

  TowerType(Texture[] textures, ProjectileType projectileType, int damage, int range, float firingSpeed) {
    this.textures = textures;
    this.damage = damage;
    this.range = range;
    this.firingSpeed = firingSpeed;
    this.projectileType = projectileType;
  }
}
