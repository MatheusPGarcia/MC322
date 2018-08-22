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
		Card card1 = new Card("Hearts", 'A');
		Card card2 = new Card("Diamonds", '7');
		Card card3 = new Card("Clubs", 'Q');
		Card card4 = new Card("Spades", 'T');
		
		// Tarefa: 
		
		// 1
		Card card5 = new Card('R');
		System.out.println("Estado do card 5:\n" + card5);
		
		// Ou seja, o atributo não inicianalizado é tido como null e o atributo instânnciado
		// esta com o atrito no valor que foi inicializado.
		
		
		// 3
		System.out.println("Antes o card1 era:\n" + card1);
		
		String newSuit = card3.getSuit();
		
		card1.modify(newSuit);
		
		System.out.println("Agora o card1 eh:\n" + card1);
		
		// 4
		
		// A saida recebida é:
		//	Estado do card 5:
		//	Card@43556938
		//	Antes o card1 era:
		//	Card@3d04a311
		//	Agora o card1 eh:
		//	Card@3d04a311
		
		
		// 5
		Card card6 = new Card(card2);
		
		System.out.println("Card 6 eh:\n" + card6 + "\nCard2 eh:\n" + card2);
		
		// Ou seja, não a diferença na impressão dos objetos.
		
		
		// 6
//		System.out.println(card3.rank);
		System.out.println(card3.getRank());
		
		// A linha comentada não builda caso a visibilidade de rank seja private
		// O motivo de se utilizar getter e setters eh para garantir o encapsulamento
		// e a segurança na modificação nos atributos de um objeto
	}

}
