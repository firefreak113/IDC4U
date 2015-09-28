package game.tile.gametiles;

import game.graphics.Sprite;
import game.tile.CollisionType;
import game.tile.PhysicsType;
import game.tile.Tile;

public class SolidStoneTile extends Tile {

	public SolidStoneTile() {
		super(Sprite.stoneSprite, CollisionType.SOLID, PhysicsType.NONE);
	}
}
