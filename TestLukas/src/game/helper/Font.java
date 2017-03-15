package game.helper;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Font {

	private static java.awt.Font UIFont;
	private static org.newdawn.slick.UnicodeFont font;

	@SuppressWarnings("unchecked")
	public static UnicodeFont load(float groeﬂe) throws FontFormatException, IOException {
			UIFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
					org.newdawn.slick.util.ResourceLoader.getResourceAsStream("res/Font/airstrike.ttf"));
			UIFont = UIFont.deriveFont(java.awt.Font.PLAIN, groeﬂe);
			font = new org.newdawn.slick.UnicodeFont(UIFont);
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect(java.awt.Color.orange));
			font.addAsciiGlyphs();
			return font;				
	}
}
