package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public BoardPanel() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Board board = Board.getInstance();
				int cols = board.getNumColumns();
				int rows = board.getNumRows();

				if (cols == 0 || rows == 0) {
					return;
				}

				int cellWidth = getWidth() / cols;
				int cellHeight = getHeight() / rows;

				if (cellWidth == 0 || cellHeight == 0) {
					return;
				}

				int col = e.getX() / cellWidth;
				int row = e.getY() / cellHeight;

				if (row < 0 || row >= rows || col < 0 || col >= cols) {
					return;
				}

				board.handleBoardClick(board.getCell(row, col));
			}
		});
	}

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
