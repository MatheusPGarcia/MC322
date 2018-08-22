/**
 * A Classe <code>Card</code> representa um card.
 * 
 * @author Matheus Garcia matheuspgarcia@gmail.com
 */
public class Card {

	/**
	 * suit do card
	 */
	private String suit;
	/**
	 * rank do card
	 */
	private char rank;
	
	/**
	 * Inicializa um objeto do tipo <code>Card</code>
	 * 
	 * @param suit do card
	 * @param rank do card
	 */
	public Card(String suit, char rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	/**
	 * Inicializa um objeto do tipo card de forma reduzida
	 * 
	 * @param rank do card
	 */
	public Card(char rank) {
		this.rank = rank;
	}
		
	/**
	 * Inicializa um objeto do tipo card copiando os atributos de outro card
	 * 
	 * @param card que se deseja copiar
	 */
	public Card(Card card) {
		this.suit = card.suit;
		this.rank = card.rank;
	}
	
	/**
	 * Recupera o suit do Card
	 * 
	 * @return o suit do card
	 */
	public String getSuit() {
		return suit;
	}

	/**
	 * Define o suit do Card
	 * 
	 * @param suit o suit do Card
	 */
	public void setSuit(String suit) {
		this.suit = suit;
	}

	/**
	 * Recupera o rank do Card
	 * 
	 * @return o rank do card
	 */
	public char getRank() {
		return rank;
	}

	/**
	 * Define o rank do Card
	 * 
	 * @param rank o rank do Card
	 */
	public void setRank(char rank) {
		this.rank = rank;
	}
	
	/**
	 * Metodo que modifica o suit de um card, o mesmo metodo que o set, também não entendi a necessidade.
	 * 
	 * @param suit = novo suit do card.
	 */
	void modify(String suit) {
		setSuit(suit);
	}

	/**
	 * Metodo que altera o suit e o rank de um card
	 * 
	 * @param suit = novo suit do card
	 * @param rank = novo rank do card
	 */
	void modify(String suit, char rank) {
		setSuit(suit);
		setRank(rank);
	}
	
	/**
	* Retorna uma representacao em String do card
	*
	* @return uma representacao em String do card
	*/
	@Override
	public String toString() {
		String out = "";
		out = out + "Suit: " + this.getSuit() + "\n";
		out = out + "Rank: " + this.getRank() + "\n";
		return out;
	}
}
