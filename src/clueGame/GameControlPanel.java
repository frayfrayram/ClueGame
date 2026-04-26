package clueGame;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;


public class GameControlPanel extends JPanel {
	
	private JTextField turnText;
	private JTextField rollText;
	private JTextField guessText;
	private JTextField guessResultText;

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 0));

		
		//-----------------------------------SECONDPANEL---------------------
			JPanel secondPanel = new JPanel();
			secondPanel.setLayout(new GridLayout(1, 4));
			
			//----------TURNPANEL-----------
			JPanel turnPanel = new JPanel();
			turnPanel.setLayout(new GridLayout(2, 1));

				
				
					JLabel turnLabel = new JLabel("        Whose turn?");
					turnText = new JTextField();
					turnText.setBackground(Color.YELLOW);
					turnText.setEditable(false);
					
					turnPanel.add(turnLabel);
					turnPanel.add(turnText);
					
					secondPanel.add(turnPanel);
				
					
			//-------------ROLLPANEL----------------
				JPanel rollPanel = new JPanel();
				rollPanel.setLayout(new GridLayout(0, 2));

				
					JLabel rollLabel = new JLabel("         Roll:");
					rollText = new JTextField();
					rollText.setEditable(false);
					
					rollPanel.add(rollLabel);
					rollPanel.add(rollText);
				
				secondPanel.add(rollPanel);
				
			
			//----------------BUTTONS----------------------
				JButton accusationButton = new JButton("Make Accusation");
				accusationButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
						"Accusations will be added in the next assignment."));
				secondPanel.add(accusationButton);
				
				JButton nextButton = new JButton("NEXT!");
				nextButton.addActionListener(e -> Board.getInstance().nextPlayerFlow());
				secondPanel.add(nextButton);
				

			mainPanel.add(secondPanel);
				
				
				
			//----------------THIRDPANEL-----------------------
			JPanel thirdPanel = new JPanel();
			thirdPanel.setLayout(new GridLayout(0, 2));
			
			
			//----------GUESSPANEL------------
				JPanel guessPanel = new JPanel();
				guessPanel.setLayout(new GridLayout(1, 0));
				guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
				
				guessText = new JTextField();
				guessText.setEditable(false);
				guessPanel.add(guessText);
				
				thirdPanel.add(guessPanel);
				
				
			//----------GUESSRESULTPANEL---------
				JPanel guessResultPanel = new JPanel();
				guessResultPanel.setLayout(new GridLayout(1, 0));
				guessResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
				
				guessResultText = new JTextField();
				guessResultText.setEditable(false);
				guessResultPanel.add(guessResultText);
				
				thirdPanel.add(guessResultPanel);
			
			
			mainPanel.add(thirdPanel);
			
		
		add(mainPanel);
		
		
	}
	
	
	public void setTurn(String player) {
		turnText.setText(player);
	}

	public void setRoll(int roll) {
		rollText.setText(Integer.toString(roll));
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
		frame.setSize(550, 130);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}
