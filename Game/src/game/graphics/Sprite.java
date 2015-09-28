package game.graphics;

import game.tile.Tile;

public class Sprite {

	public static final Sprite grassSprite = new Sprite(Tile.STDSIZE, 0, 0, SpriteSheet.terrain);
	public static final Sprite dirtSprite = new Sprite(Tile.STDSIZE, 1, 0, SpriteSheet.terrain);
	public static final Sprite stoneSprite = new Sprite(Tile.STDSIZE, 2, 0, SpriteSheet.terrain);

	public int size;
	public int[] pixels;

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.size = size;
		pixels = new int[size * size];
		for (int yy = 0; yy < size; yy++) {
			int yOffs = y * size + yy;
			if (yOffs < 0 || yOffs >= sheet.height)
				continue;
			for (int xx = 0; xx < size; xx++) {
				int xOffs = x * size + xx;
				if (xOffs < 0 || xOffs >= sheet.width)
					continue;
				pixels[xx + yy * size] = sheet.pixels[xOffs + yOffs * sheet.width];
			}
		}
	}

	public Sprite(int size, int colour) {
		this.size = size;
		pixels = new int[size * size];
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = colour;
	}

	public int getAverageColour() {
		int c = 0;
		for (int i = 0; i < pixels.length; i++)
			c += pixels[i];
		return c / pixels.length;
	}
}