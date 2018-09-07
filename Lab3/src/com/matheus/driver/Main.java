package com.matheus.driver;

import com.matheus.base.Card;
import com.matheus.base.Deck;
import com.matheus.base.DeckArrayList;
import com.matheus.util.Util;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Gerando o deck
		
		Card card1 = new Card("Hearts", 'A');
		Card card2 = new Card("Diamonds", '7');
		Card card3 = new Card("Clubs", 'Q');
		
		Deck deckCards = new Deck();
		
		deckCards.addCard(card1);
		deckCards.addCard(card2);
		deckCards.addCard(card3);
		
		System.out.println("Antes do shuffle em deck:\n" + deckCards);
		
		deckCards.shuffle();
		
		System.out.println("Depois do shuffle em deck:\n" + deckCards);
		
		
		// Repetindo o mesmo de antes, agora para ArrayList
		
		DeckArrayList deckArrayList = new DeckArrayList();
		
		deckArrayList.addCard(card1);
		deckArrayList.addCard(card2);
		deckArrayList.addCard(card3);
		
		System.out.println("Antes do shuffle em deckArrayList:\n" + deckArrayList);
		
		deckArrayList.shuffle();
		
		System.out.println("Depois do shuffle em deckArrayList:\n" + deckArrayList);
		
		// Removendo card do ArrayList
		deckArrayList.removeCard();
		System.out.println("Depois de remover card:\n" + deckArrayList);
		
		// Modificando cards com a classe Util
		System.out.println("Antes da modificação:\nCard 1:\n" + card1 + "Card2:\n" + card2);
		
		Util.modify(card1, "modified");
		Util.modify(card2, "Modified Too", 'C');
		
		System.out.println("Depois da modificação:\nCard 1:\n" + card1 + "Card2:\n" + card2);
	}
}
