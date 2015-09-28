package game;

import game.graphics.Render;
import game.graphics.Sprite;
import game.input.Input;
import game.level.Level;
import game.tile.Tile;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Engine extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int WIDTH = 2 * (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3;
	public static int HEIGHT = 2 * (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3;

	public static String title;

	private JFrame frame;
	private Thread mainThread;
	private BufferedImage img;
	private BufferedImage bg;
	private Render render;
	private Input input;
	private Level level;

	private boolean running;

	private int[] pixels;
	private int[] bgPixels;
	private int[] bgReset;

	public Engine() {
		frame = new JFrame(title = "Tag Game");
		input = new Input();
		addMouseListener(input);
		addMouseWheelListener(input);
		addKeyListener(input);
		frame.addMouseListener(input);
		frame.addMouseWheelListener(input);
		frame.addKeyListener(input);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(this);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				refreshDisplay();
			}
		});
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(getIcon(Sprite.grassSprite));
		refreshDisplay();
		level = new Level(1000, 500);
		level.yOffs = -850;
		try {
			bg = ImageIO.read(Engine.class.getResource("/sprite/Background.png"));
			BufferedImage bgInt = new BufferedImage(bg.getWidth(), bg.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = bgInt.createGraphics();
			g.drawImage(bg, 0, 0, null);
			g.dispose();
			bg = bgInt;
			bgPixels = ((DataBufferInt) bg.getRaster().getDataBuffer()).getData();
			bgReset = new int[bg.getWidth() * bg.getHeight()];
			for (int i = 0; i < bgPixels.length; i++)
				bgReset[i] = bgPixels[i];
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		start();
	}

	private void refreshDisplay() {
		WIDTH = getWidth();
		HEIGHT = getHeight();
		img = new BufferedImage(getDisplayWidth(), getDisplayHeight(), BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		render = new Render(getDisplayWidth(), getDisplayHeight());
	}

	private synchronized void start() {
		if (running)
			return;
		running = true;
		mainThread = new Thread(this);
		mainThread.start();
	}

	public void run() {
		long last = System.nanoTime();
		long timer = System.currentTimeMillis();
		double ns = 1_000_000_000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int ticks = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - last) / ns;
			last = now;
			try {
				refreshDisplay();
				while (delta >= 1) {
					tick();
					ticks++;
					delta--;
				}
				render();
				frames++;
				if (System.currentTimeMillis() - timer >= 1_000) {
					timer = System.currentTimeMillis();
					frame.setTitle(title + " | FPS: " + frames + " | TPS: " + ticks);
					frames = ticks = 0;
				}
			} catch (Exception e) {
				continue;
			}
		}
	}

	private void tick() {
		if (input.up)
			level.yOffs += Tile.STDSIZE;
		else if (input.down)
			level.yOffs -= Tile.STDSIZE;
		if (input.left)
			level.xOffs += Tile.STDSIZE;
		else if (input.right)
			level.xOffs -= Tile.STDSIZE;
		level.update();
		Time.update();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		render.clear(0);
		level.render(render);
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = render.pixels[i] == 0 ? 0 : Time.darkenForTime(render.pixels[i]);
		for (int i = 0; i < bgPixels.length; i++)
			bgPixels[i] = Time.darkenForTime(bgPixels[i]);
		g.drawImage(bg, 0, 0, WIDTH, HEIGHT, null);
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		for (int i = 0; i < bgPixels.length; i++)
			bgPixels[i] = bgReset[i];
		g.dispose();
		bs.show();
	}

	private int getDisplayWidth() {
		return (int) ((float) WIDTH / input.scale);
	}

	private int getDisplayHeight() {
		return (int) ((float) HEIGHT / input.scale);
	}

	private static BufferedImage getIcon(Sprite sprite) {
		BufferedImage r = new BufferedImage(sprite.size, sprite.size, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < sprite.size; x++)
			for (int y = 0; y < sprite.size; y++)
				r.setRGB(x, y, sprite.pixels[x + y * sprite.size]);
		return r;
	}
}
