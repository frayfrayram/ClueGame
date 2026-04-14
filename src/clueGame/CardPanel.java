package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Set;

public class CardPanel extends JPanel {
	
	private int numSeenRoomCards;
	private int numSeenWeaponCards;
	private int numSeenPlayerCards;
	
	private int numHandRoomCards;
	private int numHandWeaponCards;
	private int numHandPlayerCards;
	
	private Color getCardHolderColor(Board board, Card card) {
		for (Player p : board.getPlayerMap().values()) {
			if (p.getHand().contains(card)) {
				return PlayerColors.fromString(p.getColor()).getColor();
			}
		}
		return Color.LIGHT_GRAY;
	}
	
	
	public CardPanel() {
		Board board = Board.getInstance();
		
		Player player1 = board.getPlayer("Franklin");
		
		Set<Card> seen = player1.getSeen();
		ArrayList<Card> hand = player1.getHand();

		for (Card card : hand) {
			if (card.getType() == CardType.ROOM) {
				numHandRoomCards++;
			} else if (card.getType() == CardType.PLAYER) {
				numHandPlayerCards++;
			} else if (card.getType() == CardType.WEAPON) {
				numHandWeaponCards++;
			}
		}

		for (Card card : seen) {
			if (hand.contains(card)) {
				continue;
			}

			if (card.getType() == CardType.ROOM) {
				numSeenRoomCards++;
			} else if (card.getType() == CardType.PLAYER) {
				numSeenPlayerCards++;
			} else if (card.getType() == CardType.WEAPON) {
				numSeenWeaponCards++;
			}
		}
		
		
		

		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new GridLayout(3, 1));
		cardPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));

		
		// -------people cards---------
		JPanel peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(2, 0));
		peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
			
		JPanel handPeople = new JPanel();
		handPeople.setLayout(new GridLayout(Math.max(1, numHandPlayerCards + 1), 1));
		JLabel handPeopleLabel = new JLabel("In Hand");
		handPeople.add(handPeopleLabel);
		
		boolean hasHandPeople = false;
		for (Card card : hand) {
			if (card.getType() == CardType.PLAYER) {
				JTextField hPeople = new JTextField(card.getName(), 12);
				hPeople.setBackground(Color.WHITE);
				hPeople.setEditable(false);
				handPeople.add(hPeople);
				hasHandPeople = true;
			}
		}
		if (!hasHandPeople) {
			JTextField hPeople = new JTextField("None", 12);
			hPeople.setBackground(Color.WHITE);
			hPeople.setEditable(false);
			handPeople.add(hPeople);
		}
		peoplePanel.add(handPeople);
				
		JPanel seenPeople = new JPanel();
		seenPeople.setLayout(new GridLayout(Math.max(1, numSeenPlayerCards + 1), 1));
		JLabel seenPeopleLabel = new JLabel("Seen");
		seenPeople.add(seenPeopleLabel);
		
		boolean hasSeenPeople = false;
		for (Card card : seen) {
			if (card.getType() == CardType.PLAYER && !hand.contains(card)) {
				JTextField sPeople = new JTextField(card.getName(), 12);
				sPeople.setBackground(getCardHolderColor(board, card));
				seenPeople.add(sPeople);
				sPeople.setEditable(false);
				hasSeenPeople = true;
			}
		}
		if (!hasSeenPeople) {
			JTextField sPeople = new JTextField("None", 12);
			sPeople.setBackground(Color.WHITE);
			sPeople.setEditable(false);
			seenPeople.add(sPeople);
		}
		peoplePanel.add(seenPeople);
		cardPanel.add(peoplePanel);
			
			
		//----------room cards-------------
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(2, 0));
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
			
		JPanel handRooms = new JPanel();
		handRooms.setLayout(new GridLayout(Math.max(1, numHandRoomCards + 1), 1));
		JLabel handRoomsLabel = new JLabel("In Hand");
		handRooms.add(handRoomsLabel);
		
		boolean hasHandRooms = false;
		for (Card card : hand) {
			if (card.getType() == CardType.ROOM) {
				JTextField hRooms = new JTextField(card.getName(), 12);
				hRooms.setBackground(Color.WHITE);
				hRooms.setEditable(false);
				handRooms.add(hRooms);
				hasHandRooms = true;
			}
		}
		if (!hasHandRooms) {
			JTextField hRooms = new JTextField("None", 12);
			hRooms.setBackground(Color.WHITE);
			hRooms.setEditable(false);
			handRooms.add(hRooms);
		}
		roomPanel.add(handRooms);
			
		JPanel seenRooms = new JPanel();
		seenRooms.setLayout(new GridLayout(Math.max(1, numSeenRoomCards + 1), 1));
		JLabel seenRoomsLabel = new JLabel("Seen");
		seenRooms.add(seenRoomsLabel);
		
		boolean hasSeenRooms = false;
		for (Card card : seen) {
			if (card.getType() == CardType.ROOM && !hand.contains(card)) {
				JTextField sRooms = new JTextField(card.getName(), 12);
				sRooms.setBackground(getCardHolderColor(board, card));
				seenRooms.add(sRooms);
				hasSeenRooms = true;
			}
		}
		if (!hasSeenRooms) {
			JTextField sRooms = new JTextField("None", 12);
			sRooms.setBackground(Color.WHITE);
			sRooms.setEditable(false);
			seenRooms.add(sRooms);
		}
		roomPanel.add(seenRooms);
		cardPanel.add(roomPanel);
			
			
		//------weapon cards-------------
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(2, 0));
		weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
			
		JPanel handWeapon = new JPanel();
		handWeapon.setLayout(new GridLayout(Math.max(1, numHandWeaponCards + 1), 1));
		JLabel handWeaponLabel = new JLabel("In Hand");
		handWeapon.add(handWeaponLabel);
		
		boolean hasHandWeapons = false;
		for (Card card : hand) {
			if (card.getType() == CardType.WEAPON) {
				JTextField hWeapon = new JTextField(card.getName(), 12);
				hWeapon.setBackground(Color.WHITE);
				hWeapon.setEditable(false);
				handWeapon.add(hWeapon);
				hasHandWeapons = true;
			}
		}
		if (!hasHandWeapons) {
			JTextField hWeapon = new JTextField("None", 12);
			hWeapon.setBackground(Color.WHITE);
			hWeapon.setEditable(false);
			handWeapon.add(hWeapon);
		}
		weaponPanel.add(handWeapon);
			
		JPanel seenWeapon = new JPanel();
		seenWeapon.setLayout(new GridLayout(Math.max(1, numSeenWeaponCards + 1), 1));
		JLabel seenWeaponLabel = new JLabel("Seen");
		seenWeapon.add(seenWeaponLabel);
		
		boolean hasSeenWeapons = false;
		for (Card card : seen) {
			if (card.getType() == CardType.WEAPON && !hand.contains(card)) {
				JTextField sWeapons = new JTextField(card.getName(), 12);
				sWeapons.setBackground(getCardHolderColor(board, card));
				sWeapons.setEditable(false);
				seenWeapon.add(sWeapons);
				hasSeenWeapons = true;
			}
		}
		if (!hasSeenWeapons) {
			JTextField sWeapon = new JTextField("None", 12);
			sWeapon.setBackground(Color.WHITE);
			sWeapon.setEditable(false);
			seenWeapon.add(sWeapon);
		}
		weaponPanel.add(seenWeapon);
		cardPanel.add(weaponPanel);

		add(cardPanel);
	}
	
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();

		Player franklin = board.getPlayer("Franklin");
		Player avi = board.getPlayer("Avi");
		Player kevin = board.getPlayer("Kevin");
		Player drescher = board.getPlayer("Drescher");

		for (Card card : avi.getHand()) {
			if (card.getType() == CardType.ROOM) {
				franklin.updateSeen(card);
				break;
			}
		}

		for (Card card : kevin.getHand()) {
			if (card.getType() == CardType.PLAYER) {
				franklin.updateSeen(card);
				break;
			}
		}

		for (Card card : drescher.getHand()) {
			if (card.getType() == CardType.WEAPON) {
				franklin.updateSeen(card);
				break;
			}
		}

		CardPanel panel = new CardPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(400, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}