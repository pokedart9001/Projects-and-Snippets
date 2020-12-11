/**
 * This is a class that tests the Card class.
 */
public class CardTester {

	/**
	 * The main method in this class checks the Card operations for consistency.
	 *	@param args is not used.
	 */
	public static void main(String[] args) {
		Card card1 = new Card("Spades", 14);
		System.out.println(card1);

		Card card2 = new Card("Diamonds", 4);
		System.out.println(card2);

		Card card3 = new Card("Clubs", 7);
		System.out.println(card3);

		Card card4 = new Card("Hearts", 12);
		System.out.println(card4);

		Card card5 = new Card("Spades", 1);
		System.out.println(card5);
	}
}
