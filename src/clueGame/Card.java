package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String name, CardType type) {
		this.cardName = name;
		this.cardType = type;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;

	    // had to look this up
	    Card other = (Card) obj;
	    return cardName.equals(other.cardName) && cardType == other.cardType;
	}

	@Override
	public int hashCode() {
	    return java.util.Objects.hash(cardName, cardType);
	}
	
	
	//------------------------------getters-----------------------
	public String getName() {
		return cardName;
	}
	public CardType getType() {
		return cardType;
	}
}
