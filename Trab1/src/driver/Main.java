package driver;

import deck.Rank;
import deck.Suit;
import engine.DefaultEngine;
import engine.Engine;
import player.DummyPlayer;
import player.Player;

/**
 * A classe <code>Main</code> representa uma aplicação em Java.
 * 
 * @author Luis H. P. Mendes
 */
public class Main {
	/**
     * O método main é o ponto de partida para esta aplicação Java.
     * 
     * @param args argumentos passados ao método main.
     */
    public static void main(String[] args) {
        long seed = 0;
        boolean verbose = true;

        Player player1 = new DummyPlayer(Suit.PIKES, seed);
        Player player2 = new DummyPlayer(Suit.TILES, seed);
        
        Engine engine = new DefaultEngine(player1, player2, Engine.DEFAULT_MAX_ROUNDS, Rank.TWO, seed, verbose);

        Player winner = engine.playMatch();

        if (player1 == winner) {
            System.out.println("O Jogador1 ganhou!");
        } else if (player2 == winner) {
            System.out.println("O Jogador2 ganhou!");
        } else {
            System.out.println("Os jogadores empataram!");
        }
    }
}
