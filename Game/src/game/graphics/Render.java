package game.graphics;

public class Render {

	public int width;
	public int height;

	public int[] pixels;

	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void clear(int colour) {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = colour;
	}

	public void renderSprite(int x, int y, Sprite sprite) {
		for (int yy = 0; yy < sprite.size; yy++) {
			int yOffs = y + yy;
			if (yOffs < 0 || yOffs >= height)
				continue;
			for (int xx = 0; xx < sprite.size; xx++) {
				int xOffs = x + xx;
				if (xOffs < 0 || xOffs >= width)
					continue;
				pixels[xOffs + yOffs * width] = sprite.pixels[xx + yy * sprite.size];
			}
		}
	}
}
