package gamestates;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import game.objects.Stick;
import game.helper.Font;
import game.helper.Timer;
import game.objects.Ball;
import game.objects.Block;

public class Play extends BasicGameState {

	public static final int ID = 2;

	private static Stick stick;
	private static Ball ball;
	private static Image stick_img, ball_img, bg_img, bl_img, bl2_img, bl3_img;
	private Input in;
	private static boolean running, beendet;
	private Timer timer;
	private int blockAnzahl = 160;

	private static int time;
	private static int score;
	private float[] speed = { 0.2f, 0.2f };
	private static ArrayList<Block> blocks = new ArrayList<Block>();
	private static ArrayList<Integer> numbers = new ArrayList<Integer>();
	private static org.newdawn.slick.UnicodeFont font;
	private static Sound hitblock, hitStick;

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer app, StateBasedGame main) throws SlickException {
		running = false;
		beendet = false;
		blocks.clear();
		numbers.clear();
		score = 0;
		time = 0;
		// font
		try {
			font = Font.load(20.f);
			font.getEffects().add(new ColorEffect(java.awt.Color.white));
			font.loadGlyphs();
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in = app.getInput();
		timer = new Timer(1000);
		bl_img = new Image("res/Images/block_1.png");
		bl2_img = new Image("res/Images/block_2.png");
		bl3_img = new Image("res/Images/block_3.png");
		bg_img = new Image("res/Images/background.png");
		ball_img = new Image("res/Images/ball.png");
		stick_img = new Image("res/Images/stick.png");
		stick = new Stick(stick_img, Game.getWindowX() / 2 - 100, Game.getWindowY() - 40, 150, 30, 2);
		ball = new Ball(ball_img, (Game.getWindowX() / 2), (Game.getWindowY() / 2) + 50, 8, 1, speed);
		// Sound
		hitblock = new Sound("res/Sounds/hitBlock.wav");
		hitStick = new Sound("res/Sounds/hitStick.wav");
		// map
		String[] string = MainMenu.getMap().toString().split(",");

		for (String number : string) {
			numbers.add(Integer.valueOf(number));
		}
		int x = 0, y = 0, counter = 0;
		for (int i = 0; i < 160; i++) {
			if (i % 16 == 0) {
				y++;
				x = 0;
			}
			if (numbers.get(i) == 0) {
				counter++;
			}
			if (numbers.get(i) == 1) {
				blocks.add(new Block(bl_img, 50 * x, 30 * y, 50, 30, 1));
			}
			if (numbers.get(i) == 2) {
				blocks.add(new Block(bl2_img, 50 * x, 30 * y, 50, 30, 2));
			}
			if (numbers.get(i) == 3) {
				blocks.add(new Block(bl3_img, 50 * x, 30 * y, 50, 30, 3));
			}
			x++;
		}
		blockAnzahl -= counter;
	}

	@Override
	public void update(GameContainer app, StateBasedGame main, int delta) throws SlickException {
		if (in.isKeyPressed(Input.KEY_SPACE)) {
			running = true;
		}
		if (in.isKeyPressed(Input.KEY_ESCAPE)) {
			main.enterState(Game.PAUSE);
		}
		if (in.isKeyPressed(Input.KEY_M)) {
			
			app.setMusicOn(!app.isMusicOn());
		}
		if (running) {
			timer.addTime(delta);
			stick.control(in, delta);
			ball.update(delta, in);
			if (timer.isTimeOver()) {
				ball.setGravitation(ball.getSpeedY() + ball.getMasse() * ball.getOrtF());
				time++;
			}
			blocks.stream().forEach(m -> m.update());
			blocks = blocks.stream().filter(m -> m.getStrength() > 0).collect(Collectors.toCollection(ArrayList::new));
			score = (blockAnzahl - blocks.size()) * 10;
		}
		if (beendet) {
			beendet = true;
			main.getState(Game.GAMEOVER).init(app, main);
			main.enterState(Game.GAMEOVER);
			app.getInput().clearKeyPressedRecord();
		}

	}

	@Override
	public void render(GameContainer app, StateBasedGame main, Graphics g) throws SlickException {
		bg_img.draw(0, 0);
		blocks.stream().forEach(b -> b.getImage().draw(b.getX() + 1, b.getY() + 1, b.getShape().getWidth() - 2,
				b.getShape().getHeight() - 2));
		stick.getImage().draw(stick.getX(), stick.getY(), stick.getShape().getWidth(), stick.getShape().getHeight());

		if (running) {
			ball.getImage().draw(ball.getX(), ball.getY(), ball.getShape().getWidth(), ball.getShape().getWidth());
		}
		if (app.isMusicOn()) {
			MainMenu.getSoundOn().draw(160, 2, 0.05f);
		} else {
			MainMenu.getSoundOff().draw(160, 2, 0.05f);
		}
		font.drawString(5, 5, "SCORE  " + score);
		font.drawString(325, 5, "SPEED  " + (int) ((Math.abs(ball.getSpeedX()) + (Math.abs(ball.getSpeedY())) * 100)));
		font.drawString(600, 5, "TIME  " + Math.round(time / 100) + " SECONDS");

		/*
		 * g.drawString(""+ ball.getX(), 100, 400); g.drawString(""+
		 * ball.getY(), 100, 420); blocks.stream().forEach(b -> g.drawString(""
		 * + b.getStrength(), b.getX()+10, b.getY()+10));
		 */
		g.drawString("" + ball.getSpeedX(), 100, 440);
		g.drawString("" + ball.getSpeedY(), 100, 460);

	}

	public static Sound getHitStick() {
		return hitStick;
	}

	public static Sound getHitBlock() {
		return hitblock;
	}

	public Input getIn() {
		return in;
	}

	public static int getScore() {
		return score;
	}

	public static void setRunning(boolean startet) {
		running = startet;
	}

	public static ArrayList<Block> getBlock() {
		return blocks;
	}

	public int getID() {
		return ID;
	}

	public static Stick getStick() {
		return stick;
	}

	public static Ball getBall() {
		return ball;
	}

	public static void setBeendet(boolean end) {
		beendet = end;
	}

	public static int getTime() {
		return time;

	}

}
