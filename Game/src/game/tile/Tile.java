package game.tile;

import game.graphics.Sprite;
import game.tile.gametiles.AirTile;
import game.tile.gametiles.DirtTile;
import game.tile.gametiles.GrassTile;
import game.tile.gametiles.SolidStoneTile;
import game.tile.gametiles.StoneTile;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile {

	public static List<Tile> gameTiles = new ArrayList<Tile>();

	public static final AirTile airTile = new AirTile();
	public static final GrassTile grassTile = new GrassTile();
	public static final DirtTile dirtTile = new DirtTile();
	public static final StoneTile stoneTile = new StoneTile();
	public static final SolidStoneTile solidStoneTile=new SolidStoneTile();

	public static final int STDSIZE = 32;

	public int id;

	public Sprite sprite;
	public CollisionType collisionType;
	public PhysicsType physicsType;

	public Tile(Sprite sprite, CollisionType collisionType, PhysicsType physicsType) {
		this.sprite = sprite;
		this.collisionType = collisionType;
		this.physicsType = physicsType;
		gameTiles.add(this);
		id = gameTiles.indexOf(this);
	}
}
