/**
 * This is a class that tests the Deck class.
 */
public class DeckTester {

    /**
     * The main method in this class checks the Deck operations for consistency.
     *	@param args is not used.
     */
    public static void main(String[] args) {
        String[] suits = {"Clubs", "Spades", "Hearts", "Diamonds"};
        int[] valuesLow = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        int[] valuesHigh = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        String[] ranks = {"Test1", "Test2", "Test3", "Test4", "Test5"};

        Deck standardDeck = new Deck(suits, valuesHigh);
        Deck testDeck = new Deck(suits, valuesLow, ranks);

        standardDeck.shuffle(1);
        System.out.println(standardDeck);
    }
}
