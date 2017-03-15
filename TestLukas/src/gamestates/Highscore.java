package gamestates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Highscore extends BasicGameState {

	public static final int ID = 5;

	private ArrayList<Integer> scoreint = new ArrayList<Integer>();

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		game.getState(Game.GAMEOVER).init(container, game);
		for (String key : Gameover.getScorelist().keySet()) {
			scoreint.add(Gameover.getScorelist().get(key).get(0));
		}
		scoreint = scoreint.stream().sorted((s1, s2) -> s1.compareTo(s2))
				.collect(Collectors.toCollection(ArrayList::new));
		Collections.reverse(scoreint);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		for (int i = 0; i < scoreint.size() && i < 10; i++) {
			String t = getKeysByValue(Gameover.getScorelist(), scoreint.get(i)).toString();
			if (t.contains(";"))
				t = t.substring(1, t.length() - 1);
			g.drawString(scoreint.get(i).toString(), 280, 200 + i * 15);
			g.drawString(t, 350, 200 + i * 15);
			g.drawString(Gameover.getScorelist().get(t).get(1) / 100 + "s", 450, 200 + i * 15);
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input in = container.getInput();
		if (in.isKeyPressed(Input.KEY_ESCAPE)) {
			in.clearKeyPressedRecord();
			game.enterState(Game.MAINMENU);
		}
	}

	public static <T, E> Set<T> getKeysByValue(Map<T, ArrayList<Integer>> map, E value) {
		return map.entrySet().stream().filter(entry -> Objects.equals(entry.getValue().get(0), value))
				.map(Map.Entry::getKey).collect(Collectors.toSet());
	}

	@Override
	public int getID() {
		return ID;
	}

}
