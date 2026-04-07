package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String name, CardType type) {
		this.cardName = name;
		this.cardType = type;
	}
	
	public boolean equals(Card target) {
		if((cardName == target.getName()) && (cardType == target.getType())) {
			return true;
		} else return false;
	}
	public String getName() {
		return cardName;
	}
	public CardType getType() {
		return cardType;
	}
}
