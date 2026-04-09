package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;


public class CardPanel extends JPanel {
	
	public int numHandCards;
	
	public CardPanel() {
		
		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new GridLayout(3, 0));
		cardPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));

		
		// -------people cards---------
			JPanel peoplePanel = new JPanel();
			peoplePanel.setLayout(new GridLayout(2, 0));
			peoplePanel.setLayout(new GridLayout(numHandCards,0));

		
	}
	
	
	
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(130, 550);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}