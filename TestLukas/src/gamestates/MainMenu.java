package gamestates;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.helper.Font;

public class MainMenu extends BasicGameState {

	public static final int ID = 1;

	private Input in;
	private int cursor = 0;
	private static Image cursor_img, bg_img, entry_img, soundon, soundoff;
	private String[] options = { "Start Game", "Highscore", "Maps", "Quit" };
	private Image beuk, beuk2, roat, roat2;
	private Image[] animation = new Image[2];
	private Image[] animation2 = new Image[2];
	private Animation breakout, breakout2;
	private Music music;
	private JFileChooser fc;
	private static StringBuilder sb;
	private static org.newdawn.slick.UnicodeFont font;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		arg0.getInput().clearKeyPressedRecord();
		//font
		try {
			font = Font.load(40.f);
			font.loadGlyphs();
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File map = new File("res/Maps/level1.map");
		// Startmap
		try {
			Scanner sc = new Scanner(map);
			sb = new StringBuilder();
			while (sc.hasNextLine()) {
				sb.append(sc.nextLine() + ",");
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		music = new Music("res/Sounds/musicg.wav");
		bg_img = new Image("res/Images/menu.png");
		entry_img = new Image("res/Images/entry.png");
		cursor_img = new Image("res/Images/arrow.png");
		beuk = new Image("res/Images/beuk.png");
		beuk2 = new Image("res/Images/beuk2.png");
		roat = new Image("res/Images/roat.png");
		roat2 = new Image("res/Images/roat2.png");
		soundon = new Image("res/Images/sound_on.png");
		soundoff = new Image("res/Images/sound_off.png");
		animation[0] = beuk2;
		animation[1] = beuk;
		animation2[0] = roat2;
		animation2[1] = roat;
		breakout = new Animation(animation, 400, true);
		breakout2 = new Animation(animation2, 400, true);
		music.loop();
		music.setVolume(0.2f);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		in = arg0.getInput();
		if (in.isKeyPressed(Input.KEY_DOWN) && cursor < options.length - 1) {
			cursor++;
		}
		if (in.isKeyPressed(Input.KEY_UP) && cursor > 0) {
			cursor--;
		}
		if (in.isKeyPressed(Input.KEY_M)) {
			;
			arg0.setMusicOn(!arg0.isMusicOn());
		}
		if (in.isKeyPressed(Input.KEY_ENTER)) {
			arg0.getInput().clearKeyPressedRecord();
			switch (cursor) {

			case 0:
				arg1.addState(new Play());
				arg1.getState(Game.PLAY).init(arg0, arg1);
				arg1.enterState(Game.PLAY);
				break;
			case 1:
				arg0.getInput().clearKeyPressedRecord();
				arg1.addState(new Highscore());
				arg1.getState(Game.Highscore).init(arg0, arg1);
				arg1.enterState(Game.Highscore);
				break;

			case 2:
				fc = new JFileChooser();
				fc.showOpenDialog(null);
				if (fc.getSelectedFile() != null) {
					try {
						Scanner sc = new Scanner(fc.getSelectedFile());
						sb = new StringBuilder();
						while (sc.hasNextLine()) {
							sb.append(sc.nextLine() + ",");
						}
						sc.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				arg1.enterState(Game.MAINMENU);
				break;
			case 3:
				arg0.exit();
			}
		}

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		bg_img.draw(0, 0);
		breakout.draw(175, 64);
		breakout2.draw(290, 64);
		entry_img.draw(35, 150);
		entry_img.draw(35, 250);
		entry_img.draw(35, 350);
		entry_img.draw(35, 450);
		if (arg0.isMusicOn()) {
			soundon.draw(700, 500, 0.09f);
		} else {
			soundoff.draw(700, 500, 0.09f);
		}
		font.drawString(95, 180, options[0]);
		font.drawString(95, 280, options[1]);
		font.drawString(95, 380, options[2]);
		font.drawString(95, 480, options[3]);

		switch (cursor) {
		case 0:
			cursor_img.draw(370, 182, 0.07f);
			break;
		case 1:
			cursor_img.draw(370, 282, 0.07f);
			break;
		case 2:
			cursor_img.draw(370, 382, 0.07f);
			break;
		case 3:
			cursor_img.draw(370, 482, 0.07f);
			break;
		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	public static Image getSoundOn() {
		return soundon;
	}

	public static Image getSoundOff() {
		return soundoff;
	}

	public static String getMap() {
		return sb.toString();
	}

}