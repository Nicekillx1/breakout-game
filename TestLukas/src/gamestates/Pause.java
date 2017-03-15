package gamestates;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.helper.Font;

public class Pause extends BasicGameState {

	public static final int ID = 4;
	private static Input in;
	private Image pause_img;
	private static org.newdawn.slick.UnicodeFont font;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		pause_img = new Image("res/Images/pause.png");
		arg0.getInput().clearKeyPressedRecord();
		in = arg0.getInput();
		try {
			font = Font.load(25.f);
			font.loadGlyphs();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		arg2.setColor(new Color(1, 1, 1, 0.5f));
		arg1.getState(Game.PLAY).render(arg0, arg1, arg2);
		arg2.setColor(Color.white);
		pause_img.drawCentered(Game.getWindowX() / 2, 150);
		font.drawString(10, 350, "ESC - Zurück zum Spiel");
		font.drawString(10, 400, "M - Zurück zum Menü");
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		in = arg0.getInput();
		if (in.isKeyPressed(Input.KEY_ESCAPE)) {
			arg0.getInput().clearKeyPressedRecord();

			arg1.enterState(2);
		}
		if (in.isKeyPressed(Input.KEY_M)) {
			arg0.getInput().clearKeyPressedRecord();
			arg1.enterState(1);

		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}