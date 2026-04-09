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
	
	
	public CardPanel() {
		Board board = Board.getInstance();
		
		int seenRoomCards = board.getRoomMap().size();
		int seenPeopleCards = board.getPlayerMap().size();
		int seenWeaponsCards = board.getWeaponMap().size();
		
		
		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new GridLayout(3, 1));
		cardPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));

		
		// -------people cards---------
			JPanel peoplePanel = new JPanel();
			peoplePanel.setLayout(new GridLayout(2, 0));
			peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
			
				JPanel handPeople = new JPanel();
				handPeople.setLayout(new GridLayout(2,0));
				JTextField hPeople = new JTextField("None", 12);
				JLabel handPeopleLabel = new JLabel("In Hand");
				
				handPeople.add(handPeopleLabel);
				handPeople.add(hPeople);
			
			peoplePanel.add(handPeople);
				
				
			
				JPanel seenPeople = new JPanel();
				seenPeople.setLayout(new GridLayout(2,0));
				JTextField sPeople = new JTextField("None", 12);
				JLabel seenPeopleLabel = new JLabel("Seen");
				
				seenPeople.add(seenPeopleLabel);
				seenPeople.add(sPeople);
				
			peoplePanel.add(seenPeople);
			cardPanel.add(peoplePanel);
			
			
		//----------room cards-------------
			JPanel roomPanel = new JPanel();
			roomPanel.setLayout(new GridLayout(2, 0));
			roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
			
			JPanel handRooms = new JPanel();
			handRooms.setLayout(new GridLayout(2,0));
			JTextField hRooms = new JTextField("None", 12);
			JLabel handRoomsLabel = new JLabel("In Hand");
			
			handRooms.add(handRoomsLabel);
			handRooms.add(hRooms);
		
		roomPanel.add(handRooms);
			
			
		
			JPanel seenRooms = new JPanel();
			seenRooms.setLayout(new GridLayout(2,0));
			JTextField sRooms = new JTextField("None", 12);
			JLabel seenRoomsLabel = new JLabel("Seen");
			
			seenRooms.add(seenRoomsLabel);
			seenRooms.add(sRooms);
			
		roomPanel.add(seenRooms);
		cardPanel.add(roomPanel);
			
			
			
			
		//------weapon cards-------------
			JPanel weaponPanel = new JPanel();
			weaponPanel.setLayout(new GridLayout(2, 0));
			weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
			
			JPanel handWeapon = new JPanel();
			handWeapon.setLayout(new GridLayout(2,0));
			JTextField hWeapon = new JTextField("None", 12);
			JLabel handWeaponLabel = new JLabel("In Hand");
			
			handWeapon.add(handWeaponLabel);
			handWeapon.add(hWeapon);
		
		weaponPanel.add(handWeapon);
			
			
		
			JPanel seenWeapon = new JPanel();
			seenWeapon.setLayout(new GridLayout(2,0));
			JTextField sWeapon = new JTextField("None", 12);
			JLabel seenWeaponLabel = new JLabel("Seen");
			
			seenWeapon.add(seenWeaponLabel);
			seenWeapon.add(sWeapon);
			
		weaponPanel.add(seenWeapon);
		cardPanel.add(weaponPanel);
			
			
		

		add(cardPanel);
	}
	
	
	
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();
		
		CardPanel panel = new CardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(400, 550);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}