package driver;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import deck.Rank;
import deck.Suit;
import engine.DefaultEngine;
import engine.Engine;
import player.DummyPlayer;
import player.Player;
import player.PlayerRA156742;

/**
 * A classe <code>Main</code> representa uma aplicação em Java.
 * 
 * @author Luis H. P. Mendes
 */
public class Main {
    /**
     * Joga uma única partida do jogo de cartas Svoyi Koziri.
     * 
     * @param player1Class a classe do primeiro jogador.
     * @param player2Class a classe do segundo jogador.
     * @return a classe do jogador vencedor.
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    private static Class<? extends Player> playSingleMatch(Class<? extends Player> player1Class, Class<? extends Player> player2Class) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Class<? extends Player> winnerClass = null;

        Player player1, player2, winner;

        long seed = 0;
        boolean verbose = true;

        // Instancia o Jogador1
        if (player1Class.equals(DummyPlayer.class)) {
            player1 = player1Class.getConstructor(new Class[] {Suit.class, long.class}).newInstance(Suit.PIKES, seed);
        } else {
            player1 = player1Class.getConstructor(new Class[] {Suit.class}).newInstance(Suit.PIKES);
        }

        // Instancia o Jogador2
        if (player2Class.equals(DummyPlayer.class)) {
            player2 = player2Class.getConstructor(new Class[] {Suit.class, long.class}).newInstance(Suit.TILES, seed);
        } else {
            player2 = player2Class.getConstructor(new Class[] {Suit.class}).newInstance(Suit.TILES);
        }

        Engine engine = new DefaultEngine(player1, player2, Engine.DEFAULT_MAX_ROUNDS, Rank.TWO, seed, verbose);
        winner = engine.playMatch();

        if (player1.equals(winner)) {
            winnerClass = player1Class;
        } else if (player2.equals(winner)) {
            winnerClass = player2Class;
        }

        return winnerClass;
    }

    /**
     * Joga uma campeonato do jogo de cartas Svoyi Koziri.
     * 
     * @param playerClasses lista de classes de jogadores que vão jogar o campeonato.
     * @return uma lista com o número de vitórias de cada jogador.
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    private static List<Integer> playChampionship(List<Class<? extends Player>> playerClasses) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        List<Integer> victories = new ArrayList<Integer>(playerClasses.size());
        boolean verbose = false;

        // Inicializa o vetor com o número de vitórias de cada jogador
        for (int i = 0; i < playerClasses.size(); i++) {
            victories.add(0);
        }

        for (int i = 0; i < playerClasses.size() - 1; i++) {
            Player player1;

            // Instancia o Jogador1
            if (playerClasses.get(i).equals(DummyPlayer.class)) {
                player1 = playerClasses.get(i).getConstructor(new Class[] {Suit.class, long.class}).newInstance(Suit.HEARTS, i);
            } else {
                player1 = playerClasses.get(i).getConstructor(new Class[] {Suit.class}).newInstance(Suit.HEARTS);
            }

            for (int j = i + 1; j < playerClasses.size(); j++) {
                Player player2;

                // Instancia o Jogador2
                if (playerClasses.get(j).equals(DummyPlayer.class)) {
                    player2 = playerClasses.get(j).getConstructor(new Class[] {Suit.class, long.class}).newInstance(Suit.CLOVERS, j);
                } else {
                    player2 = playerClasses.get(j).getConstructor(new Class[] {Suit.class}).newInstance(Suit.CLOVERS);
                }

                // Para cada par de jogadores
                // joga 2*2 partidas entre eles
                // sendo 2 de "ida"
                // e 2 de "volta"
                // com 2 seeds diferentes
                for (int k = 0; k < 2; k++) {
                    // Joga partida de "ida"
                    Engine engine = new DefaultEngine(player1, player2, 103, Rank.SEVEN, k, verbose);
                    Player winner = engine.playMatch();

                    // Incrementa número de vitórias do jogador vencedor da partida de ida
                    if (player1.equals(winner)) {
                        victories.set(i, victories.get(i) + 1);
                    } else if (player2.equals(winner)) {
                        victories.set(j, victories.get(j) + 1);
                    }

                    // Joga partida de "volta"
                    engine = new DefaultEngine(player2, player1, 103, Rank.SEVEN, k, verbose);
                    winner = engine.playMatch();

                    // Incrementa número de vitórias do jogador vencedor da partida de volta
                    if (player1.equals(winner)) {
                        victories.set(i, victories.get(i) + 1);
                    } else if (player2.equals(winner)) {
                        victories.set(j, victories.get(j) + 1);
                    }
                }
            }
        }

        return victories;
    }

	/**
     * O método main é o ponto de partida para esta aplicação Java.
     * 
     * @param args argumentos passados ao método main.
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
     */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        List<Class<? extends Player>> playerClasses = new ArrayList<Class<? extends Player>>();

        // O primeiro jogador será sempre o DummyPlayer
        playerClasses.add(DummyPlayer.class);
        // Os outros jogadores serão os PlayerRAxxxxxx dos alunos
        playerClasses.add(PlayerRA156742.class);

        // Código para jogar uma única partida entre os dois primeiros jogadores
//        Class<? extends Player> winnerClass = Main.playSingleMatch(playerClasses.get(0), playerClasses.get(1));
//        if (winnerClass == playerClasses.get(0)) {
//            System.out.println("O Jogador1 ganhou!");
//        } else if (winnerClass == playerClasses.get(1)) {
//            System.out.println("O Jogador2 ganhou!");
//        } else {
//            System.out.println("Os jogadores empataram!");
//        }

        // Código para jogar um capeonato entre todos os jogadores
        List<Integer> victories = Main.playChampionship(playerClasses);
        System.out.println("Número de vitórias de cada jogador");
        for (int i = 0; i < playerClasses.size(); i++) {
            System.out.println(playerClasses.get(i).getSimpleName() + ": " + victories.get(i));
        }
    }
}