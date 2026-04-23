package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

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

		add(new BoardPanel(), BorderLayout.CENTER);
		add(new GameControlPanel(), BorderLayout.SOUTH);
		add(new CardPanel(), BorderLayout.EAST);

		setVisible(true);
	}

	public static void main(String[] args) {
		new ClueGame();
	}
}