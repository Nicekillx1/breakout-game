package gamestates;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.helper.Font;
import game.objects.ScoreEntry;

public class Gameover extends BasicGameState {

	public static final int ID = 3;
	private static Image bg_img;
	private Input in;
	private File scorefile;
	private FileWriter writer;
	private boolean name;
	private TextField text1;
	private static org.newdawn.slick.UnicodeFont font;
	private String[] scorestring;
	private StringBuilder score;
	private static Map<String, ArrayList<Integer>> scorelist = new HashMap<String, ArrayList<Integer>>();
	private static ArrayList<ScoreEntry> liste = new ArrayList<ScoreEntry>();

	@Override
	public void init(GameContainer app, StateBasedGame arg1) throws SlickException {
		try {
			font = Font.load(40.f);
			font.loadGlyphs();
		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		bg_img = new Image("res/Images/Gameover.png");
		app.getInput().clearKeyPressedRecord();
		name = true;
		text1 = new TextField(app, font, 200, 280, 400, 50);
		
		// Highscore
		File highscore = new File("res/Highscore/score.txt");
		try {
			Scanner sc = new Scanner(highscore);
			score = new StringBuilder();
			while (sc.hasNextLine()) {
				score.append(sc.nextLine());
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		scorestring = score.toString().split(",");
		for (int i = 0; i < scorestring.length - 2; i += 3) {
			scorelist.putIfAbsent(scorestring[i], new ArrayList<Integer>());
			scorelist.get(scorestring[i]).add(Integer.valueOf(scorestring[i + 1]));
			scorelist.get(scorestring[i]).add(Integer.valueOf(scorestring[i + 2]));
		}
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		bg_img.draw(0, 0);
		text1.setBorderColor(Color.black);
		text1.render(arg0, arg2);
		arg2.drawString("Username: ", 200, 265);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		in = arg0.getInput();

		text1.setFocus(true);
		if (name && in.isKeyPressed(Input.KEY_ENTER) && !(text1.getText().isEmpty())) {
			liste.add(new ScoreEntry(Play.getScore(), Play.getTime(), text1.getText()));
			try {
				scorefile = new File("res/Highscore/score.txt");
				writer = new FileWriter(scorefile, true);
				writer.write(text1.getText() + "," + Play.getScore() + "," + Play.getTime() + ",");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			arg1.enterState(1);

		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	public static Map<String, ArrayList<Integer>> getScorelist() {
		return scorelist;

	}

}
