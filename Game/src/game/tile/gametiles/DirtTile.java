package game.tile.gametiles;

import game.graphics.Sprite;
import game.tile.CollisionType;
import game.tile.PhysicsType;
import game.tile.Tile;

public class DirtTile extends Tile {

	public DirtTile() {
		super(Sprite.dirtSprite, CollisionType.SOLID, PhysicsType.STD);
	}
}
