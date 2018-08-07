/**
 * A Classe <code>Main</code> é a base do programa.
 * 
 * @author Matheus Garcia matheuspgarcia@gmail.com
 */
public class Main {

	/**
	 * Classe inicial do programa
	 * 
	 * @param args são os argumentos de inicializacao do programa.
	 */
	public static void main(String[] args) {
		// Instanciando objetos
		Card cardOne = new Card("First Example", 'S');
		Card cardTwo = new Card("Second Example", 'A');
		Card cardThree = new Card("Third Example", 'B');
				
		// Impressão dos objetos
		System.out.println("Card one\n" + cardOne);
		System.out.println("Card two\n" + cardTwo);
		System.out.println("Card three\n" + cardThree);
	}
}
