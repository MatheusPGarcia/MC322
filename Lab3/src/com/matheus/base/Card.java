package com.matheus.base;

public class Card {

	private String suit;
	private char rank;

	public Card(String suit, char rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public Card(char rank) {
		this.rank = rank;
	}

	public Card(Card card) {
		this.suit = card.suit;
		this.rank = card.rank;
	}

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
	
	@Override
	public String toString() {
		String out = "";
		out = out + "Suit: " + this.getSuit() + "\n";
		out = out + "Rank: " + this.getRank() + "\n";
		return out;
	}
}