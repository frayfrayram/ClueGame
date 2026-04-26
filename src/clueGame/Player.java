package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	private String name;
	private String color;
	private int row, col;
	private ArrayList<Card> hand;
	protected Set<Card> seen;

	public Player(String name, String color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
		hand = new ArrayList<>();
		seen = new HashSet<>();
	}

	public void updateHand(Card card) {
		hand.add(card);
	}

	public void updateSeen(Card seenCard) {
		seen.add(seenCard);
	}

	public Set<Card> getSeen() {
		return seen;
	}

	public Card disproveSuggestion(Card p, Card w, Card r) {
		List<Card> matches = new ArrayList<>();

		if (hand.contains(p)) {
			matches.add(p);
		}
		if (hand.contains(w)) {
			matches.add(w);
		}
		if (hand.contains(r)) {
			matches.add(r);
		}

		if (matches.isEmpty()) {
			return null;
		}

		return matches.get(new Random().nextInt(matches.size()));
	}

	public void setLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public void draw(Graphics g, int cellWidth, int cellHeight) {
		int x = col * cellWidth;
		int y = row * cellHeight;
		Board board = Board.getInstance();
		List<Player> playersAtLocation = board.getPlayersAt(row, col);
		int playerIndex = playersAtLocation.indexOf(this);

		Color drawColor = PlayerColors.fromString(color).getColor();
		g.setColor(drawColor);
		if (playersAtLocation.size() <= 1) {
			g.fillOval(x + 2, y + 2, cellWidth - 4, cellHeight - 4);
			g.setColor(Color.BLACK);
			g.drawOval(x + 2, y + 2, cellWidth - 4, cellHeight - 4);
			return;
		}

		int slotWidth = Math.max(8, (cellWidth - 4) / 2);
		int slotHeight = Math.max(8, (cellHeight - 4) / 3);
		int diameter = Math.max(6, Math.min(slotWidth, slotHeight) - 2);
		int drawX = x + 2 + (playerIndex % 2) * slotWidth;
		int drawY = y + 2 + (playerIndex / 2) * slotHeight;
		g.fillOval(drawX, drawY, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(drawX, drawY, diameter, diameter);
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public abstract BoardCell selectTarget();
}
