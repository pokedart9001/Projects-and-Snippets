import java.util.ArrayList;
import java.util.Collections;

public class Player {

    private ArrayList<Card> hand, trick;
    private String name;
    private int score;

    Player(String name) {
        hand = new ArrayList<>(0);
        trick = new ArrayList<>(0);
        this.name = name;
        score = 0;
    }

    String getName() {
        return name;
    }

    ArrayList<Card> getHand() {
        return hand;
    }

    void setHand(Card... cards) {
        Collections.addAll(hand, cards);
    }

    void clearHand() {
        hand.clear();
    }

    void sortHandByValue() {
        for (int i = 1; i < hand.size(); i++)
            for (int j = 0; j < i; j++)
                if (hand.get(j).pointValue() > hand.get(i).pointValue())
                    hand.add(j, hand.remove(i));
    }
    
    void setTrick(Card... cards) {
        Collections.addAll(trick, cards);
    }

    void clearTrick() {
        trick.clear();
    }

    int getScore() {
        return score;
    }

    void addScore(int score) {
        this.score += score;
    }

    int evaluateScore() {
        int score = 0;
        for (Card c : trick)
            if (c.toString().equals("Queen of Spades (12)"))
                score += 13;
            else if (c.suit().equals("Hearts"))
                score++;
        return score;
    }
    
    boolean handContains(String suit) {
        for (Card c : hand)
            if (c.suit().equals(suit))
                return true;
        return false;
    }

    boolean handContainsTwoOfClubs() {
        for (Card c : hand)
            if (c.rank().equals("Two") && c.suit().equals("Clubs"))
                return true;
        return false;
    }

    String handToString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hand.size(); i++)
            result.append(String.format("%d: %s\n", i, hand.get(i)));
        return result.toString();
    }

    String firstHandToString(boolean restrict) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hand.size(); i++)
            if (!restrict || hand.get(i).rank().equals("Two") && hand.get(i).suit().equals("Clubs"))
                result.append(String.format("%d: %s\n", i, hand.get(i)));
        return result.toString();
    }

    String handToString(String suit, boolean restrict) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hand.size(); i++)
            if (!restrict || hand.get(i).suit().equals(suit))
                result.append(String.format("%d: %s\n", i, hand.get(i)));
        return result.toString();
    }

    String noHeartsHandToString(boolean restrict) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hand.size(); i++)
            if (!restrict || !hand.get(i).suit().equals("Hearts"))
                result.append(String.format("%d: %s\n", i, hand.get(i)));
        return result.toString();
    }

    String scoreToString() {
        return name + " - " + score + " points";
    }

    public String toString() {
        return "Name: " + name + "\nHand: " + hand.toString();
    }
}