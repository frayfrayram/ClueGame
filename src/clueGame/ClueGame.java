package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ClueGame extends JFrame {
	public ClueGame() {
		setTitle("Clue Game - CSCI306");
		setSize(900, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();
		board.deal();

		BoardPanel boardPanel = new BoardPanel();
		GameControlPanel controlPanel = new GameControlPanel();
		board.setBoardPanel(boardPanel);
		board.setControlPanel(controlPanel);

		add(boardPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		add(new CardPanel(), BorderLayout.EAST);

		setVisible(true);

		SwingUtilities.invokeLater(() -> {
			board.splashScreen();
			board.startGame();
		});
	}

	public static void main(String[] args) {
		new ClueGame();
	}
}
