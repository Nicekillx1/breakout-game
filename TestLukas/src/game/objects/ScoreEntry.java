package game.objects;

public class ScoreEntry {
	private int id, score, time;
	private static int totalEntries;
	private String name;

	public ScoreEntry(int score, int time, String name) {
		super();
		this.score = score;
		this.time = time;
		this.id = totalEntries;
		this.name = name;
		totalEntries++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
