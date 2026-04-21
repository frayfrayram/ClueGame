package clueGame;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;



import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Set;


public class SuggestionPanel extends JDialog {
	
	String[] people = { "Franlin", "Will", "FOid" };
	String[] weapons = { "bar", "text", "slime" };

	

	public SuggestionPanel() {
		// TODO Auto-generated constructor stub
		
		JDialog suggestionDialog = new JDialog();
		suggestionDialog.setTitle("Make a Suggestion");
		
		JPanel suggestionPanel = new JPanel();
		suggestionPanel.setLayout(new GridLayout(4, 2));
		JTextField roomText = new JTextField("Current room", 12);
		roomText.setBorder(null);
		roomText.setEditable(false);
		JTextField personText = new JTextField("Person", 12);
		personText.setBorder(null);
		personText.setEditable(false);
		JTextField weaponText = new JTextField("Weapon", 12);
		weaponText.setBorder(null);
		weaponText.setEditable(false);

		
		//make a getter for the current room and use it to fill this field. temporarily just text
		JTextField currentRoom = new JTextField("Lounge", 12);
		currentRoom.setEditable(false);
		JComboBox<String> personChoice = new JComboBox<>(people);
		
		JComboBox<String> weaponChoice = new JComboBox<>(weapons);
		
		
		JButton submitButton = new JButton("Submit");
		
		JButton cancelButton = new JButton("Cancel");

		
		
		
		suggestionPanel.add(roomText);
		suggestionPanel.add(currentRoom);
		suggestionPanel.add(personText);
		suggestionPanel.add(personChoice);
		suggestionPanel.add(weaponText);
		suggestionPanel.add(weaponChoice);
		suggestionPanel.add(submitButton);
		suggestionPanel.add(cancelButton);

		add(suggestionPanel);
		
		
		
		
	}
	
	public static void main(String[] args) {
		SuggestionPanel panel = new SuggestionPanel();  // create the panel 
		panel.setSize(550, 130);  // size the frame
		panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // allow it to close
		panel.setVisible(true); // make it visible
	}

}
