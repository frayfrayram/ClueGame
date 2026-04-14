package clueGame;

import java.awt.Color;

public enum PlayerColors {
	RED(Color.RED),
	GREEN(Color.GREEN),
	BLUE(Color.BLUE),
	YELLOW(Color.YELLOW),
	WHITE(Color.WHITE),
	BLACK(Color.BLACK);

	private Color awtColor;

	PlayerColors(Color color) {
		this.awtColor = color;
	}

	public Color getColor() {
		return awtColor;
	}
	
	public static PlayerColors fromString(String colorStr) {
		return PlayerColors.valueOf(colorStr.trim().toUpperCase());
	}
}