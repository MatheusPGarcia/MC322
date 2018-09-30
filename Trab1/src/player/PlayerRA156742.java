package player;

import java.util.ArrayList;
import java.util.List;

import deck.Card;
import deck.Rank;
import deck.Suit;
import engine.Engine;
import engine.Play;
import engine.PlayType;

/**
 * A Classe <code>PlayerRA156742</code> representa um Player.
 * 
 * @author Matheus Garcia matheuspgarcia@gmail.com
 * 
 * --------------------------- // ---------------------------
 * A estratégia aplicada para a realização desse trabalho foi bem simples.
 * 
 * Existem dois possiveis casos a partir do qual iremos filtrar.
 * 
 * Caso 1: Primeiro a jogar.
 * 		Nesse caso basta que a jogada se realize com o pior Card possivel de um naipe diferente
 *  	do seu trunfo.
 *  	Para isso é selecionada a pior carta do naipe com maior cartas somadas em sua mão diferente
 *  	do trunfo, a não ser que só existam cartas do naipe trunfo.
 *  
 *  	Exemplos:
 *   		Caso 1: 
 *   			3 cartas hearts, 2 cartas tiles, 4 cartas clovers e 2 cartas pikes. 
 *   			Trunfo do jogador: pikes.
 *   			Nesse caso seria jogada a pior carta do naipe clovers.
 *   
 *   		Caso 2: 
 *  		 	3 cartas hearts, 2 cartas tiles, 4 cartas clovers e 2 cartas pikes. 
 *   			Trunfo do jogador: clover.
 *   			Nesse caso seria jogada a pior carta do naipe heart.
 *   
 *   		Caso 3:
 *   			0 cartas hearts, 0 cartas tiles, 4 cartas clovers e 0 cartas pikes. 
 *   			Trunfo do jogador: clover.
 *   			Nesse caso seria jogada a pior carta do naipe clover
 * 
 * Caso 2: Segundo a jogar
 * 		Nesse caso não existe muito o que se possa fazer já que a liberdade de jogadas é bem menor.
 * 		Primeiro é verificado se existe alguma carta do mesmo naipe que a carta da mesa que pode 
 * 		vencer o round.
 * 
 * 			Caso sim:
 * 				A pior carta que pode vencer é jogada e o round vencido.
 * 
 * 			Caso não:
 * 				É verificado entre as cartas trunfo do jogador se alguma pode vencer o round.
 * 
 * 					Caso sim:
 * 						A pior carta do naipe trunfo que pode vencer é jogada e o round vencido.
 * 	
 * 					Caso não:
 * 						O round é perdido e todas as cartas da mesa pegas.
 * 
 */
public class PlayerRA156742 extends Player {

	/**
	 * Trump do player
	 */
	private Suit selfTrump;
	
	/**;
	 * Inicializa um objeto do tipo <code>PlayerRA156742</code>
	 * 
	 * @param trump do jogador
	 */
	public PlayerRA156742(Suit trump) {
		super(trump);
		
		this.selfTrump = trump;
	}

	/**
	 * Recupera o trump do player
	 * 
	 * @return trump do Player
	 */
	@Override
	public Suit getTrump() {
		return selfTrump;
	}

	/**
     * Define a jogada que sera realizada pelo player.
     * 
     * @param firstToPlay boolean que indica se o jogador é o primeiro a jogar.
     * @param engine engine do jogo que esta ocorrendo.
     * @return A jogada que sera realizada pelo player.
     */
	@Override
	public Play playRound(boolean firstToPlay, Engine engine) {
		
		if (firstToPlay) {
			
			Card playCard = chooseTheWorstCardPlayingAsFirst(engine);
			PlayType roundType = PlayType.PLAYACARD;
			Play playRound = new Play(roundType, playCard);
			
			return playRound;
			
		} else {
			
			Card tablesCard = engine.peekCardsOnTable();
			
			Card playCard = chooseTheWorstCardPlayingAsSecond(engine, tablesCard);
		
			if (playCard == null) {
				
				PlayType roundType = PlayType.TAKEALLCARDS;
				Play playRound = new Play(roundType);
				
				return playRound;
				
			} else {

				PlayType roundType = PlayType.PLAYACARD;
				Play playRound = new Play(roundType, playCard);
				
				return playRound;
			}
		}
	}
	
// ----------------------------------------------
// -
// -  Nessa seção estarão os métodos usados
// -  para quando você for o primeiro a jogar
// -
// ----------------------------------------------
	
	/**
	 * Método responsável por definir que Card sera jogado quando primeiro na rodada
	 * 
	 * @param engine do jogo que esta ocorrendo.
	 * @return Card a ser jogado.
	 */
	private Card chooseTheWorstCardPlayingAsFirst(Engine engine) {
		List<Card> cardList = getHand(engine);
		Suit cardSuit = findTheBetterSuitToPlay(cardList);
		List<Card> filteredList = filterCardsBySuit(cardList, cardSuit);
		Card playThisCard = findTheWorstCardOfList(filteredList);
		
		return playThisCard;
	}
	
	/**
	 * Método responsável por checar qual o naipe mais decorrente na mão do player que não o naipe trunfo.
	 * 
	 * @param cardList lista de cards na mão do player.
	 * @return Naipe mais decorrente na mão do player que não o naipe trunfo.
	 */
	private Suit findTheBetterSuitToPlay(List<Card> cardList) {
		
		int heartsCount = 0;
		int tilesCount = 0;
		int cloversCount = 0;
		int pikesCount = 0;
		
		for (Card card : cardList) {
		
			Suit cardSuit = card.getSuit();
			
			if (cardSuit == Suit.HEARTS) {
				heartsCount += 1;
			} else if (cardSuit == Suit.TILES) {
				tilesCount += 1;
			} else if (cardSuit == Suit.CLOVERS) {
				cloversCount += 1;
			} else {
				pikesCount += 1;
			}
		}
		
		int mostCount;
		Suit mostSuit;
		
		if (this.selfTrump == Suit.HEARTS) {
			mostCount = tilesCount;
			mostSuit = Suit.TILES;
		} else {
			mostCount = heartsCount;
			mostSuit = Suit.HEARTS;
		}
		
		if (mostCount < tilesCount && this.selfTrump != Suit.TILES) {
			mostSuit = Suit.TILES;
			mostCount = tilesCount; 
		}
		if (mostCount < cloversCount && this.selfTrump != Suit.CLOVERS) {
			mostSuit = Suit.CLOVERS;
			mostCount = cloversCount; 
		}
		if (mostCount < pikesCount && this.selfTrump != Suit.PIKES) {
			mostSuit = Suit.PIKES;
			mostCount = pikesCount; 
		}
		
		if (mostCount == 0) {
			mostSuit = this.selfTrump;
		}
		
		return mostSuit;
	}
	
	/**
	 * Método responsável por achar o Card de rank mais baixo em uma lista.
	 * 
	 * @param cardList lista de onde deseja se encontrar a menor carta.
	 * @return card de menor rank da lista de Cards da entrada.
	 */
	private Card findTheWorstCardOfList(List<Card> cardList) {
		
		Card worstCard = cardList.get(0);
		
		for (Card card : cardList) {
			Rank cardListRank = card.getRank();
			Rank worstRank = worstCard.getRank();
			int compare = cardListRank.compareTo(worstRank);
			
			if (compare < 0) {
				worstCard = card;
			}
		}
		
		return worstCard;
	}
	
// ----------------------------------------------
// -
// -  Nessa seção estarão os métodos usados
// -  para quando você for o segundo a jogar
// -
// ----------------------------------------------
	
	/**
	 * Método responsável por definir que Card sera jogado quando segundo na rodada.
	 * 
	 * @param engine engine do jogo que esta ocorrendo.
	 * @param tableCard card jogado pelo adversario.
	 * @return Card card a ser jogado.
	 * @return Null caso a rodada vá ser perdida
	 */
	private Card chooseTheWorstCardPlayingAsSecond(Engine engine, Card tableCard) {
		
		List<Card> cardList = getHand(engine);
		Suit tableSuit = tableCard.getSuit();
		
		List<Card> filteredList = filterCardsBySuit(cardList, tableSuit);
		Card roundCard = null;
		
		if (!filteredList.isEmpty()) {
			roundCard = findTheWorstCardInListThatCanWinAgainstTable(filteredList, tableCard);
		} 

		if (roundCard == null) {
			filteredList = filterCardsBySuit(cardList, selfTrump);
			roundCard = findTheWorstCardInListThatCanWinAgainstTable(filteredList, tableCard);
		}
		
		return roundCard;
	}
	
	/**
	 * Método responsável por achar o Card de rank mais baixo, que vença o card da mesa, em uma lista.
	 * 
	 * @param cardList Lista de cards onde se deseja encontrar o de menor rank.
	 * @param tableCard card jogado pelo adversario.
	 * @return Card o pior card da lista que pode vencer o round.
	 * @return Null caso nenhum card da lista consiga vencer o round.
	 */
	private Card findTheWorstCardInListThatCanWinAgainstTable(List<Card> cardList, Card tableCard) {
		
		Card worstPossibleCard = null;
		Rank tableCardRank = tableCard.getRank();
		
		for (Card card : cardList) {
			
			Rank cardListRank = card.getRank();
			
			int compare = tableCardRank.compareTo(cardListRank);
			
			if (compare < 0) {
				
				if (worstPossibleCard == null) {
					worstPossibleCard = card;
				}

				Rank currentWorstRank = worstPossibleCard.getRank();
				int newCompare = cardListRank.compareTo(currentWorstRank);
				
				if (newCompare < 0) {
					worstPossibleCard = card;
				}
			}
		}
		
		return worstPossibleCard;
	}

// ----------------------------------------------
// -
// -  Nessa seção estarão os métodos usados
// -  para as duas situações
// -
// ----------------------------------------------
	
	/**
	 * Método responsável por conseguir os cards na mão do player.
	 * 
	 * @param engine engine engine do jogo que esta ocorrendo.
	 * @return Lista de cards que o player possui.
	 */
	private List<Card> getHand(Engine engine) {
		List<Card> allCards = engine.getUnmodifiableHandOfPlayer(this);
		
		return allCards;
	}

	/**
	 * Método responsável por filtrar uma lista de cards por naipe.
	 * 
	 * @param cardList lista a qual se deseja aplicar o filtro.
	 * @param cardSuit O naipe o qual deseja que seja filtrado.
	 * @return Lista de cards filtrada.
	 */
	private List<Card> filterCardsBySuit(List<Card> cardList, Suit cardSuit) {
		
		List<Card> filteredList = new ArrayList<>();
		
		for (Card card : cardList) {
			if (card.getSuit() == cardSuit) {
				filteredList.add(card);
			}
		}
		
		return filteredList;
	}
}