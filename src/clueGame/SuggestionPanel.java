package clueGame;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SuggestionPanel extends JDialog {
	private Solution selection;

	private SuggestionPanel(Frame owner, Card roomCard, boolean chooseRoom) {
		super(owner, chooseRoom ? "Make an Accusation" : "Make a Suggestion", true);

		Board board = Board.getInstance();
		JPanel suggestionPanel = new JPanel(new GridLayout(4, 2));

		JTextField roomText = new JTextField("Current room", 12);
		roomText.setBorder(null);
		roomText.setEditable(false);
		JTextField personText = new JTextField("Person", 12);
		personText.setBorder(null);
		personText.setEditable(false);
		JTextField weaponText = new JTextField("Weapon", 12);
		weaponText.setBorder(null);
		weaponText.setEditable(false);

		JComboBox<String> roomChoice = new JComboBox<>(board.getSortedRoomNames().toArray(new String[0]));
		JTextField currentRoom = new JTextField(roomCard == null ? "" : roomCard.getName(), 12);
		currentRoom.setEditable(false);
		JComboBox<String> personChoice = new JComboBox<>(board.getSortedPlayerNames().toArray(new String[0]));
		JComboBox<String> weaponChoice = new JComboBox<>(board.getSortedWeaponNames().toArray(new String[0]));

		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");

		submitButton.addActionListener(e -> {
			Card selectedRoom = chooseRoom
					? board.getCard((String) roomChoice.getSelectedItem())
					: roomCard;
			selection = new Solution(
					board.getCard((String) personChoice.getSelectedItem()),
					board.getCard((String) weaponChoice.getSelectedItem()),
					selectedRoom);
			dispose();
		});
		cancelButton.addActionListener(e -> dispose());

		suggestionPanel.add(roomText);
		suggestionPanel.add(chooseRoom ? roomChoice : currentRoom);
		suggestionPanel.add(personText);
		suggestionPanel.add(personChoice);
		suggestionPanel.add(weaponText);
		suggestionPanel.add(weaponChoice);
		suggestionPanel.add(submitButton);
		suggestionPanel.add(cancelButton);

		add(suggestionPanel, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(owner);
	}

	public static Solution showSuggestionDialog(Frame owner, Card roomCard) {
		SuggestionPanel panel = new SuggestionPanel(owner, roomCard, false);
		panel.setVisible(true);
		return panel.selection;
	}

	public static Solution showAccusationDialog(Frame owner) {
		SuggestionPanel panel = new SuggestionPanel(owner, null, true);
		panel.setVisible(true);
		return panel.selection;
	}

	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();
		board.deal();

		SuggestionPanel panel = new SuggestionPanel(null, board.getCard("Bar"), false);
		panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel.setVisible(true);
	}
}
