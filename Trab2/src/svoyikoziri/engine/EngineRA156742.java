package svoyikoziri.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import svoyikoziri.deck.Card;
import svoyikoziri.deck.Color;
import svoyikoziri.deck.Rank;
import svoyikoziri.deck.Suit;
import svoyikoziri.engine.exception.InvalidPlayException;
import svoyikoziri.engine.exception.MaxPlayTimeExceededException;
import svoyikoziri.engine.exception.NullPlayException;
import svoyikoziri.engine.exception.PlayACardNotInHandException;
import svoyikoziri.engine.exception.PlayANullCardException;
import svoyikoziri.engine.exception.PlayAWorseCardException;
import svoyikoziri.engine.exception.SameTrumpColorsException;
import svoyikoziri.engine.exception.TakeAllCardsAsFirstPlayException;
import svoyikoziri.player.Player;

/**
 * 
 * @author Matheus Garcia
 *
 */
public class EngineRA156742 extends Engine {

	// ----------------------------------------------
	// -
	// -  Nessa seção estarão as variáveis
	// -  que serão usadas na engine.
	// -
	// ----------------------------------------------
	
	/**
	 * Número máximo de rounds de uma partida.
	 */
	private int maxRounds;
	
	/**
	 * Tempo máximo em nanosegundos que uma jogada pode demorar.
	 */
	private long maxPlayTime;
	
	/**
	 * Lista de cartas na mesa.
	 */
	private Stack<Card> cardsOnTable;
	
	/**
	 * Contador do round em que a partida esta.
	 */
	private int currentRound;
	
	/**
	 * Primeiro player do jogo.
	 */
	private Player player1;
	
	/**
	 * Lista de cartas do primeiro player.
	 */
	private List<Card> player1Cards;
	
	/**
	 * Segundo player do jogo.
	 */
	private Player player2;
	
	/**
	 * Lista de cartas do segundo player.
	 */
	private List<Card> player2Cards;
	
	/**
	 * Lista de jogadas que aconteceram na partida.
	 */
	private List<Play> playsList;
	
	/**
	 * Informa se os detalhes da partida devem ser impressos.
	 */
	private boolean isVerboseActive;

	// ----------------------------------------------
	// -
	// -  Nessa seção estarão os métodos construtores
	// -
	// ----------------------------------------------
	
	/**
	 * 
	 * @param primeiro player do jogo.
	 * @param segundo player do jogo.
	 * @param carta mais fraca que servira de base para gerar o deck.
	 * @param seed usada pra gerar aleatoriamente a distribuição das cartas.
	 * @param número máximo de rounds na partida.
	 * @param tempo máximo que uma jogada pode levar.
	 * @param informação se a engine deve imprimir os detalhes da partida.
	 */
	public EngineRA156742(Player player1, Player player2, Rank minRank, long seed, int maxRounds, long maxPlayTime, boolean verbose) {
		
		setPlayers(player1, player2);
		startCommonAttributes(maxRounds, maxPlayTime, verbose);
		List<Card> deck = generateDeckWithSeed(minRank, seed);
		dealTheCards(deck);
	}
	
	/**
	 * 
	 * @param primeiro player do jogo.
	 * @param segundo player do jogo.
	 * @param deck que será usado no jogo.
	 * @param número máximo de rounds na partida.
	 * @param tempo máximo que uma jogada pode levar.
	 * @param informação se a engine deve imprimir os detalhes da partida.
	 */
	public EngineRA156742(Player player1, Player player2, List<Card> deck, int maxRounds, long maxPlayTime, boolean verbose) {
		
		setPlayers(player1, player2);
		startCommonAttributes(maxRounds, maxPlayTime, verbose);
		dealTheCards(deck);
	}
	
	// ----------------------------------------------
	// -
	// -  Nessa seção estarão os métodos getter
	// -
	// ----------------------------------------------
	
	/**
	 * Recupera o trunfo do jogador 1
	 * 
	 * @return trunfo do jogador 1
	 */
	@Override
	public Suit getPlayer1Trump() {
		return player1.getTrump();
	}

	/**
	 * Recupera o trunfo do jogador 2
	 * 
	 * @return trunfo do jogador 2
	 */
	@Override
	public Suit getPlayer2Trump() {
		return player2.getTrump();
	}

	/**
	 * Recupera a mão do jogador.
	 * 
	 * @return lista de cartas do jogador requerido.
	 */
	@Override
	public List<Card> getUnmodifiableHandOfPlayer(Player player) {
		if (player == player1) {
			return Collections.unmodifiableList(player1Cards);
		}
		return Collections.unmodifiableList(player2Cards);
	}

	/**
	 * Recupera a lista de cartas na mesa
	 * 
	 * @return lista de cartas na mesa
	 */
	@Override
	public Stack<Card> getCardsOnTable() {
		return cardsOnTable;
	}

	/**
	 * Recupera o numero maximo de rounds da partida
	 * 
	 * @return numero maximo de rounds.
	 */
	@Override
	public int getMaxRounds() {
		return maxRounds;
	}

	/**
	 * Recupera o round atual da partida
	 * 
	 * @return round atual da partida
	 */
	@Override
	public int getCurrentRound() {
		return currentRound;
	}

	/**
	 * Recupera a lista de jogadas realizadas na partida.
	 * 
	 * @returnlista de jogas realizadas.
	 */
	@Override
	public List<Play> getUnmodifiablePlays() {
		return Collections.unmodifiableList(playsList);
	}

	// ----------------------------------------------
	// -
	// -  Nessa seção estarão os métodos herdados
	// -
	// ----------------------------------------------

	/**
	 * Método chamado para realizar uma partida do jogo.
	 * 
	 * @return o player que venceu a partida.
	 */
	@Override
	public Player playMatch() throws NullPlayException, PlayANullCardException, PlayACardNotInHandException,
			TakeAllCardsAsFirstPlayException, PlayAWorseCardException, MaxPlayTimeExceededException,
			InvalidPlayException {
		
		// Imprime as configurações iniciais da partida, se sim, imprime
		println(Engine.getPlayerTrumpMessage(true, getPlayer1Trump()));
		println(Engine.getPlayerTrumpMessage(false, getPlayer2Trump()));
		
		
		boolean gameEnded = false;
		Player firstToPlay = this.player1;
		Player secondToPlay = this.player2;
		
		// Realiza um turno de rounds até que a partida acabe ou então alguma excessão ocorra.
		while (!gameEnded) {
			
			// Realiza o método e depois verifica se algum player venceu o jogo. Caso o jogo continue, é verificado quem ganhou o ultimo round e definido quem irá jogar primeiro e quem irá jogar segundo no próximo round.
			Player winner = playRoundBetweenPlayers(firstToPlay, secondToPlay);
			
			if (player1Cards.isEmpty()) {
				for (Card card : cardsOnTable) {
					player2Cards.add(card);
				}
				return player1;
			} else if (player2Cards.isEmpty()) {
				for (Card card : cardsOnTable) {
					player1Cards.add(card);
				}
				return player2;
			}
			
			if (this.currentRound > this.maxRounds) {
				if (player1Cards.size() < player2Cards.size()) {
					return player1;
				} else {
					return player2;
				}
			}
			
			if (winner == player1) {
				firstToPlay = player1;
				secondToPlay = player2;
			} else {
				firstToPlay = player2;
				secondToPlay = player1;
			}
		}
		
		return null;
	}

	@Override
	protected void println(Object obj) {
		if (!isVerboseActive) {
			return;
		} 
		System.out.println(obj);
	}
	
	// ----------------------------------------------
	// -
	// -  Nessa seção estarão os meus métodos, criados para ajudar na execussão da engine
	// -
	// ----------------------------------------------
	
	/**
	 * Método responsável por fazer a definição inicial de quais são os players e verificar se o trunfo é possível.
	 * 
	 * @param player1
	 * @param player2
	 */
	private void setPlayers(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		
		if (player1.getTrump().getColor() == player2.getTrump().getColor()) {
			throw new SameTrumpColorsException(player1.getTrump(), player2.getTrump());
		}
	}
	
	/**
	 * Método responsável por inicializar os atributos iniciais da engine.
	 * 
	 * @param maxRounds número máximo de rounds da partida.
	 * @param maxPlayTime tempo máximo de cada jogado em nanosegundos.
	 * @param verbose se deve ser impresso os detalhes da partida.
	 */
	private void startCommonAttributes(int maxRounds, long maxPlayTime, boolean verbose) {
		
		this.cardsOnTable = new Stack<Card>();
		this.currentRound = 1;
		
		this.maxRounds = maxRounds;
		this.maxPlayTime = maxPlayTime;
		this.isVerboseActive = verbose;
		this.playsList = new ArrayList<>();
	}
	
	/**
	 * Método responsável por gerar um deck a partir de uma carta mínima e também de embaralhar com base em um seed.
	 * 
	 * @param minRank menor rank de base para o baralho
	 * @param seed gerador aleatório para embaralhar
	 * @return deck já embaralhado.
	 */
	private List<Card> generateDeckWithSeed(Rank minRank, long seed) {
		
		List<Card> deck = getDeckWithAllCards(minRank);
		deck = shuffleCards(deck, seed);
		return deck;
	}
	
	/**
	 * Método responsável por gerar um deck com todas as cartas a partir do rank minímo.
	 * 
	 * @param minRank rank minímo a partir do qual as cartas devem ser geradas.
	 * @return deck com todas as cartas da mínima em diante.
	 */
	private List<Card> getDeckWithAllCards(Rank minRank) {
		
		List<Card> deck = new ArrayList<>();
		
		for (Rank rank : Rank.values()) {
			
			int compare = rank.compareTo(minRank);
			
			if (compare < 0) {
				continue;
			}
			
			for (Suit suit : Suit.values()) {
				Card newCard = new Card(suit, rank);
				deck.add(newCard);
			}
		}
		
		return deck;
	}
	
	/**
	 * Método responsável por embaralhar um deck baseado em um seed.
	 * 
	 * @param deck deck a ser embaralhado.
	 * @param seed seed que auxilia no embaralhamento.
	 * @return deck embaralhado.
	 */
	private List<Card> shuffleCards(List<Card> deck, long seed) {
		Collections.shuffle(deck, new Random(seed));
		return deck;
	}
	
	/**
	 * Método responsável por distribuir o deck entre os players.
	 * 
	 * @param deck deck a ser distribuido.
	 */
	private void dealTheCards(List<Card> deck) {
		List<Card> player1Hand = new ArrayList<>();
		List<Card> player2Hand = new ArrayList<>();
		
		// cria um deck com a primeira metade das cartas.
		int halfCardsCount = deck.size() / 2;
		
		List<Card> halfDeck = new ArrayList<>();
		
		for (int index = 0; index < halfCardsCount; index++) {
			Card newCard = deck.get(index);
			halfDeck.add(newCard);
		}
		
		// distribui todas as cartas do halfDeck que são da mesma cor que do player1 ao mesmo, simultaneamente gera as espelhadas ao player2.
		for (Card card : halfDeck) {
			if (card.getSuit().getColor() == player1.getTrump().getColor()) {
				player1Hand.add(card);
				
				Suit player2Suit;
				
				if (card.getSuit() == player1.getTrump()) {
					player2Suit = player2.getTrump();
				} else {
					if (player2.getTrump().getColor() == Color.BLACK) {
						if (player2.getTrump() == Suit.PIKES) {
							player2Suit = Suit.CLOVERS;
						} else {
							player2Suit = Suit.PIKES;
						}
					} else {
						if (player2.getTrump() == Suit.HEARTS) {
							player2Suit = Suit.TILES;
						} else {
							player2Suit = Suit.HEARTS;
						}
					}
				}
				
				Card player2Card = new Card(player2Suit, card.getRank());
				player2Hand.add(player2Card);
			}
		}
		
		// filtra o deck baseado nas cartas que já foram distribuidas
		deck = filterDeckByHand(deck, player1Hand);
		deck = filterDeckByHand(deck, player2Hand);
		
		// distribui o restante das cartas
		for (Card card : deck) {
			if (card.getSuit().getColor() == player1.getTrump().getColor()) {
				player2Hand.add(card);
			} else {
				player1Hand.add(card);
			}
		}
		
		// atribui o baralho já distribuido a cada um dos players
		this.player1Cards = player1Hand;
		this.player2Cards = player2Hand;
	}
	
	/**
	 * Remove todos os elementos do deck que estão na mão de um player.
	 * 
	 * @param deck deck de onde será retirada as cartas.
	 * @param hand a mão a qual deve filtrar o deck.
	 * @return deck já filtrado.
	 */
	private List<Card> filterDeckByHand(List<Card> deck, List<Card> hand) {
		
		List<Card> filteredDeck = new ArrayList<>();
		
		for (Card card : deck) {
			if (!hand.contains(card)) {
				filteredDeck.add(card);
			}
		}
		
		return filteredDeck;
	}
	
	/**
	 * Método responsável por jogar rounds entre dois players até existir um takeAllCards.
	 * 
	 * @param firstToPlay primeiro a jogar.
	 * @param secondToPlay segundo a jogar.
	 * @return o jogador que venceu o round.
	 */
	private Player playRoundBetweenPlayers(Player firstToPlay, Player secondToPlay) {
		
		boolean roundEnded = false;
		
		while (!roundEnded) {
			
			if (this.currentRound > this.maxRounds) {
				if (player1Cards.size() < player2Cards.size()) {
					return player1;
				} else {
					return player2;
				}
			}
			
			// realiza a impressão dos atributos do round.
			printRoundOutputs(firstToPlay, secondToPlay);
			
			// realiza a primeira jogada, do primeiro a jogar
			Play firstPlay = playTurnForPlayer(true, firstToPlay, null);
			
			printPlayMessage(firstToPlay, firstPlay);

			// adiciona a carta jogada pelo player 1 como a primeira carta na mesa
			Card topCard = firstPlay.getCard();
			cardsOnTable.add(topCard);
			playsList.add(firstPlay);
						
			// verifica se algum jogador venceu
			if (player1Cards.isEmpty() || player2Cards.isEmpty()) {
				return null;
			}
			
			// realiza a segundo jogada, do segundo jogador
			Play secondPlay = playTurnForPlayer(false, secondToPlay, topCard);
			
			printPlayMessage(secondToPlay, secondPlay);
			
			playsList.add(secondPlay);
			
			// adiciona a carta jogada pelo player 2 como a primeira carta na mesa caso a jogada diferente de TAKEALLCARDS, ou atribui todas as cartas ao player2
			
			if (secondPlay.getType() == PlayType.TAKEALLCARDS) {
				
				roundEnded = true;
				
				for (Card card : cardsOnTable) {
					if (secondToPlay == player1) {
						player1Cards.add(card);
					} else {
						player2Cards.add(card);
					}
				}
				
				
				if (firstToPlay == player1) {
					println(Engine.getPlayerWinsRoundMessage(true));
				} else {
					println(Engine.getPlayerWinsRoundMessage(false));
				}
				
				cardsOnTable.removeAllElements();
				
			} else {

				if (secondToPlay == player1) {
					println(Engine.getPlayerWinsRoundMessage(true));
				} else {
					println(Engine.getPlayerWinsRoundMessage(false));
				}
				
				cardsOnTable.add(secondPlay.getCard());
				
				Player switchPlayer = firstToPlay;
				firstToPlay = secondToPlay;
				secondToPlay = switchPlayer;
			}
			
			// verifica se algum dos players venceu a partida
			if (player1Cards.isEmpty() || player2Cards.isEmpty()) {
				return null;
			}
			
			// aumenta o número de rounds
			currentRound++;
		}
		
		return firstToPlay;
	}
	
	/**
	 * Método responsável por verificar uma jogada de um player e todas suas excessões.
	 * 
	 * @param firstToPlay se o jogador é o primeiro ou o segundo a jogar.
	 * @param player o jogador.
	 * @param topCard a carta no topo da mesa.
	 * @return
	 */
	private Play playTurnForPlayer(boolean firstToPlay, Player player, Card topCard) {
		
		long startTime = System.nanoTime();
		
		Play play;
		
		// Excessão InvalidPlayException
		try {
			play = player.playRound(firstToPlay, this);
		} catch(Exception e) {
			throw new InvalidPlayException(firstToPlay);
		}
		
		long stopTime = System.nanoTime();
		
		long totalTime = stopTime - startTime;
		
		// Excessão MaxPlayTimeExceededException
		if (totalTime > maxPlayTime) {
			throw new MaxPlayTimeExceededException(firstToPlay);
		}
		
		// Excessão NullPlayException
		if (play == null) {
			throw new NullPlayException(firstToPlay);
		}
		if (play.getType() != PlayType.TAKEALLCARDS) {
			// Excessão PlayANullCardException
			if (play.getCard() == null) {
				throw new PlayANullCardException(firstToPlay);
			} 
			
			List<Card> playerHand = this.getUnmodifiableHandOfPlayer(player);
			Card cardPlayed = play.getCard();
			
			// Excessão PlayACardNotInHandException
			if (!playerHand.contains(cardPlayed)) {
				throw new PlayACardNotInHandException(firstToPlay, cardPlayed);
			}
		} else if (play.getType() == PlayType.TAKEALLCARDS) {
			// Excessão TakeAllCardsAsFirstPlayException
			if (firstToPlay) {
				throw new TakeAllCardsAsFirstPlayException(firstToPlay);
			}
		}
		
		if (!firstToPlay && play.getType() != PlayType.TAKEALLCARDS) {
			int compare = play.getCard().compareTo(topCard);
			
			// Excessão PlayAWorseCardException
			if (compare <= 0 && (play.getCard().getSuit() != player.getTrump())) {
				throw new PlayAWorseCardException(firstToPlay, play.getCard(), topCard);
			} else if (play.getCard().getRank() == topCard.getRank() && (play.getCard().getSuit() != player.getTrump())) {
				throw new PlayAWorseCardException(firstToPlay, play.getCard(), topCard);
			}
		}
		
		// retira a carta jogada da mão do player
		if (player == player1 && play.getType() != PlayType.TAKEALLCARDS) {
			player1Cards.remove(play.getCard());
		} else {
			player2Cards.remove(play.getCard());
		}
		
		return play;
	}
	
	/**
	 * Método responsável por imprimir uma rodada
	 * 
	 * @param player jogador que realizou a jogada
	 * @param play jogada
	 */
	private void printPlayMessage(Player player, Play play) {
		
		if (player == player1) {
			println(Engine.getValidPlayMessage(true, play));
		} else {
			println(Engine.getValidPlayMessage(false, play));
		}
	}
	
	/**
	 * Método responsável por imprimir detalhes prévios do round
	 * 
	 * @param firstToPlay primeiro a jogar.
	 * @param secondToPlay segundo a jogar.
	 */
	private void printRoundOutputs(Player firstToPlay, Player secondToPlay) {
		
		println(Engine.getRoundNumberMessage(currentRound, maxRounds));
		
		Collections.sort(player1Cards);
		Collections.sort(player2Cards);
		
		println(Engine.getNumberOfCardsOnPlayersHandMessage(true, player1Cards.size()));
		for (Card card : player1Cards) {
			println(card);
		}
		
		println(Engine.getNumberOfCardsOnPlayersHandMessage(false, player2Cards.size()));
		for (Card card : player2Cards) {
			println(card);
		}
		
		println(Engine.getNumberOfCardsOnCardsOnTableMessage(cardsOnTable.size()));
		if (cardsOnTable.size() > 0) {
			for (Card card : cardsOnTable) {
				println(card);
			}
		}
		
		if (firstToPlay == player1) {
			println(Engine.getPlayerStartsRoundMessage(true));
		} else {
			println(Engine.getPlayerStartsRoundMessage(false));
		}
	}
}