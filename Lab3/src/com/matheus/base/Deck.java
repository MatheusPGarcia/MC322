package com.matheus.base;
import java.util.Random;

public class Deck {
	
	private static Random rnd = new Random();
	private Card[] cards;
	private int size;
	
	public Deck() {
		this.cards = new Card[10];
		this.size = 0;
	}
	
	public void addCard (Card card) {
		this.cards[this.size] = card;
		this.size++;
	}

	public Card removeCard() {
		this.size--;
		return this.cards[this.size];
	}
	
	public void shuffle() {
		for (int i = 1; i < this.size; i++) {
			int j = Deck.rnd.nextInt(i + 1);
			
			if (j != i) {
				Card aux = this.cards[i];
				this.cards[i] = this.cards[j];
				this.cards[j] = aux;
			}
		}
		
		int nMinus = this.size - 1;
		for (int i = nMinus; i >= 0; i--) {
			Card currentCard = this.cards[i];
			System.out.println(currentCard);
		}
	}
	
	@Override
	public String toString() {
		
		String out = "";
		
		for (int i = 0; i < size; i++) {
			Card currentCard = this.cards[i];
			out = out + currentCard;
		}
		
		return out;
	}
}
