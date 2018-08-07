
public class Card {

	private String suit;
	private char rank;
	
	// Método construtor
	public Card(String suit, char rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	// Métodos get e set
	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public char getRank() {
		return rank;
	}

	public void setRank(char rank) {
		this.rank = rank;
	}
	
	// Método toString
	@Override
	public String toString() {
		String out = "";
		out = out + "Suit: " + this.getSuit() + "\n";
		out = out + "Rank: " + this.getRank() + "\n";
		return out;
	}
}
