package game.tile.gametiles;

import game.graphics.Sprite;
import game.tile.CollisionType;
import game.tile.PhysicsType;
import game.tile.Tile;

public class GrassTile extends Tile {

	public GrassTile() {
		super(Sprite.grassSprite, CollisionType.SOLID, PhysicsType.STD);
	}
}
