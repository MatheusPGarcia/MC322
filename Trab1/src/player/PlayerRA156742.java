package player;

import java.util.ArrayList;
import java.util.List;

import deck.Card;
import deck.Rank;
import deck.Suit;
import engine.Engine;
import engine.Play;
import engine.PlayType;

public class PlayerRA156742 extends Player {

	
	private Suit selfSuit;
	
	public PlayerRA156742(Suit trump) {
		super(trump);
		
		this.selfSuit = trump;
	}

	@Override
	public Suit getTrump() {
		return selfSuit;
	}

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
	
	private Card chooseTheWorstCardPlayingAsFirst(Engine engine) {
		List<Card> cardList = getHand(engine);
		Suit cardSuit = findTheBetterSuitToPlay(cardList);
		List<Card> filteredList = filterCardsBySuit(cardList, cardSuit);
		Card playThisCard = findTheWorstCardOfList(filteredList);
		
		return playThisCard;
	}
	
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
		
		if (this.selfSuit == Suit.HEARTS) {
			mostCount = tilesCount;
			mostSuit = Suit.TILES;
		} else {
			mostCount = heartsCount;
			mostSuit = Suit.HEARTS;
		}
		
		if (mostCount < tilesCount && this.selfSuit != Suit.TILES) {
			mostSuit = Suit.TILES;
			mostCount = tilesCount; 
		}
		if (mostCount < cloversCount && this.selfSuit != Suit.CLOVERS) {
			mostSuit = Suit.CLOVERS;
			mostCount = cloversCount; 
		}
		if (mostCount < pikesCount && this.selfSuit != Suit.PIKES) {
			mostSuit = Suit.PIKES;
			mostCount = pikesCount; 
		}
		
		if (mostCount == 0) {
			mostSuit = this.selfSuit;
		}
		
		return mostSuit;
	}
	
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
	
	private Card chooseTheWorstCardPlayingAsSecond(Engine engine, Card tableCard) {
		
		List<Card> cardList = getHand(engine);
		Suit tableSuit = tableCard.getSuit();
		
		List<Card> filteredList = filterCardsBySuit(cardList, tableSuit);
		Card roundCard = null;
		
		if (!filteredList.isEmpty()) {
			roundCard = findTheWorstCardInListThatCanWinAgainstTable(filteredList, tableCard);
		} 

		if (roundCard == null) {
			filteredList = filterCardsBySuit(cardList, selfSuit);
			roundCard = findTheWorstCardInListThatCanWinAgainstTable(filteredList, tableCard);
		}
		
		return roundCard;
	}
	
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
	
	private List<Card> getHand(Engine engine) {
		List<Card> allCards = engine.getUnmodifiableHandOfPlayer(this);
		
		return allCards;
	}

	
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