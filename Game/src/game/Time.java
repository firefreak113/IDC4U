package game;

import java.awt.Color;

public class Time {

	private static int time;
	private static int lengthOfDay = 50000;

	public static void update() {
		if (++time >= lengthOfDay)
			time = 0;
	}

	public static int darkenForTime(int colour) {
		Color c = new Color(colour);
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		double d = Math.cos(((2 * Math.PI) / lengthOfDay) * (double) time);
		return new Color((int) (r * d), (int) (g * d), (int) (b * d)).getRGB();
	}
}
