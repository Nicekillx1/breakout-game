package gamestates;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

	public static final int MAINMENU = 1;
	public static final int PLAY = 2;
	public static final int GAMEOVER = 3;
	public static final int PAUSE = 4;
	public static final int Highscore = 5;

	private static int sizex = 800, sizey = 600;

	public Game(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new MainMenu());
		this.addState(new Gameover());
		this.addState(new Pause());
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Game("GreatTest"));
			app.setDisplayMode(sizex, sizey, false);
			app.setTargetFrameRate(120);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static int getWindowX() {
		return sizex;
	}

	public static int getWindowY() {
		return sizey;
	}
}
