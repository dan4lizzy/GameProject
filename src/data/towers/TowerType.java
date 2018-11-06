package data.towers;

import static helpers.Artist.QuickLoad;
import org.newdawn.slick.opengl.Texture;
import data.projectiles.*;
public enum TowerType {

  CannonRed(new Texture[] {QuickLoad("cannonBaseRed"), QuickLoad("cannonGunRed")}, ProjectileType.CannonBall, 10, 700, 3, 10), 
  CannonBlue(new Texture[] {QuickLoad("cannonBaseBlue"), QuickLoad("cannonGunBlue")}, ProjectileType.CannonBall, 30, 1000, 5, 30),
  CannonIce(new Texture[] {QuickLoad("cannonIceBase"), QuickLoad("cannonIceGun")}, ProjectileType.IceBall, 10, 500, 7, 50);

  Texture[] textures;
  ProjectileType projectileType;
  int damage, range, cost;
  float firingSpeed;

  TowerType(Texture[] textures, ProjectileType projectileType, int damage, int range, float firingSpeed, int cost) {
    this.textures = textures;
    this.damage = damage;
    this.range = range;
    this.firingSpeed = firingSpeed;
    this.projectileType = projectileType;
    this.cost = cost;
  }
}
