package com.matheus.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.matheus.util.Util;

public class DeckArrayList {
	
	private List<Card> cards;
	
	public DeckArrayList() {
		this.cards = new ArrayList<Card>();
	}
	
	public void addCard(Card card) {
		
		if (this.cards.size() == Util.MAX_CARDS) {
			System.out.println("O baralho já esta cheio!");
			return;
		}
		
		this.cards.add(card);
	}

	public Card removeCard() {
		int index = this.cards.size() - 1;
		Card wanted = this.cards.get(index);
		this.cards.remove(index);
		return wanted;
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
		
		int nMinus = this.cards.size() - 1;
		
		for (int i = nMinus; i >= 0; i--) {
			Card currentCard = this.cards.get(i);
			System.out.println(currentCard);
		}
	}
	
	@Override
	public String toString() {
		
		String out = "";
		
		for (int i = 0; i < this.cards.size(); i++) {
			Card currentCard = this.cards.get(i);
			out = out + currentCard;
		}
		
		return out;
	}
}
