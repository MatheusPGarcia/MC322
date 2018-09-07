package com.matheus.util;

import com.matheus.base.Card;

public class Util {
	
	public static final int MAX_CARDS = 30;
	
	public static void modify(Card card, String suit) {
		card.setSuit(suit);
	}
	
	public static void modify(Card card, String suit, char rank) {
		card.setSuit(suit);
		card.setRank(rank);
	}
}
