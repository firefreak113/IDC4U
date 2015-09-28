package game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public static final SpriteSheet terrain = new SpriteSheet("/sprite/Terrain.png", 320, 320);

	public String path;

	public int width;
	public int height;
	public int[] pixels;

	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		try {
			BufferedImage in = ImageIO.read(SpriteSheet.class.getResource(path));
			for (int y = 0; y < height; y++)
				for (int x = 0; x < width; x++)
					pixels[x + y * width] = in.getRGB(x, y);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
