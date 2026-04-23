package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Board board = Board.getInstance();

		int rows = board.getNumRows();
		int cols = board.getNumColumns();

		int cellWidth = getWidth() / cols;
		int cellHeight = getHeight() / rows;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				board.getCell(r, c).draw(g, cellWidth, cellHeight);
			}
		}

		g.setColor(Color.BLUE);
		for (Room room : board.getRoomMapChar().values()) {
			if (room.getLabelCell() != null) {
				BoardCell label = room.getLabelCell();
				int x = label.getCol() * cellWidth + 4;
				int y = label.getRow() * cellHeight + cellHeight / 2;
				g.drawString(room.getName(), x, y);
			}
		}

		for (Player p : board.getPlayers()) {
			p.draw(g, cellWidth, cellHeight);
		}
	}
}