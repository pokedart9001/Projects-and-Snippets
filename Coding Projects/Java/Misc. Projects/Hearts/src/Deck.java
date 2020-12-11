import java.util.*;

/**
 * The Deck class represents a shuffled deck of cards.
 * It provides several operations including
 *      initialize, shuffle, deal, and check if empty.
 */
public class Deck {

	/**
	 * cards contains all the cards in the deck.
	 */
	private ArrayList<Card> cards;

	/**
	 * size is the number of not-yet-dealt cards.
	 * Cards are dealt from the top (highest index) down.
	 * The next card to be dealt is at size - 1.
	 */
	private int size;


	public Deck(Card... cards) {
		this.cards = new ArrayList<Card>(Arrays.asList(cards));
		size = this.cards.size();
	}

	public Deck(String[] suits, int[] values) {
		cards = new ArrayList<Card>(0);
		for (String suit : suits) {
			for (int value : values) {
				cards.add(new Card(suit, value));
			}
		}
		size = cards.size();
	}

	/**
	 * Creates a new <code>Deck</code> instance.<BR>
	 * It pairs each element of ranks with each element of suits,
	 * and produces one of the corresponding card.
	 * @param ranks is an array containing all of the card ranks.
	 * @param suits is an array containing all of the card suits.
	 * @param values is an array containing all of the card point values.
	 */
	public Deck(String[] suits, int[] values, String[] ranks) {
		cards = new ArrayList<Card>(0);
		for (String suit : suits) {
			for (int i = 0; i < values.length && i < ranks.length; i++) {
				cards.add(new Card(suit, values[i], ranks[i]));
			}
		}
		size = cards.size();
	}


	/**
	 * Determines if this deck is empty (no undealt cards).
	 * @return true if this deck is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Accesses the number of undealt cards in this deck.
	 * @return the number of undealt cards in this deck.
	 */
	public int size() {
		return size;
	}

	/**
	 * Randomly permute the given collection of cards
	 * and reset the size to represent the entire deck.
	 */
    void shuffle(int howMany) {
		if (howMany > 1) {
            Collections.shuffle(cards);
            shuffle(howMany-1);
        }
		size = cards.size();
	}

	private void swap(int index1, int index2) {
		cards.set(index1, cards.set(index2, cards.get(index1)));
	}

	/**
	 * Deals a card from this deck.
	 * @return the card just dealt, or null if all the cards have been
	 *         previously dealt.
	 */
    Card deal() {
		if (isEmpty())
			return null;
		else {
			size--;
			return cards.get(size);
		}
	}

	/**
	 * Generates and returns a string representation of this deck.
	 * @return a string representation of this deck.
	 */
	@Override
	public String toString() {
		String rtn = "size = " + size + "\nUndealt cards: \n";

		for (int k = size - 1; k >= 0; k--) {
			rtn += cards.get(k);
			if (k != 0) {
				rtn += ", ";
			}
			if ((size - k) % 2 == 0) {
				// Insert carriage returns so entire deck is visible on console.
				rtn += "\n";
			}
		}

		rtn += "\nDealt cards: \n";
		for (int k = cards.size() - 1; k >= size; k--) {
			rtn += cards.get(k);
			if (k != size) {
				rtn += ", ";
			}
			if ((k - cards.size()) % 2 == 0) {
				// Insert carriage returns so entire deck is visible on console.
				rtn += "\n";
			}
		}

		rtn = rtn + "\n";
		return rtn;
	}
}