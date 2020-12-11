import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Hearts {

    private static Player[] players = new Player[4];
    private static int maxPoints;
    private static Deck deck;

    public static void main(String[] args) {

        String[] suits = {"Clubs", "Spades", "Hearts", "Diamonds"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        deck = new Deck(suits, values);

        Scanner input = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            System.out.printf("Player %d: ", i+1);
            players[i] = new Player(input.nextLine());
        }
        System.out.print("Max # of points: ");
        maxPoints = input.nextInt();
        System.out.println();

        int turn = 0;

        while (scoreBelowMax()) {
            deck.shuffle(5);
            setHands();
            boolean passing = true;

            System.out.print("Passing order: ");
            switch(turn % 4) {
                case 0: System.out.println("P1 -> P2 -> P3 -> P4 -> P1\n"); break;
                case 1: System.out.println("P1 <-> P3, P2 <-> P4\n"); break;
                case 2: System.out.println("P1 <- P2 <- P3 <- P4 <- P1\n"); break;
                case 3: System.out.println("Hold\n"); passing = false; break;
            }

            if (passing) {
                Card[][] cardsPassed = new Card[4][3];
                for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 3; j++) {
                        System.out.println(players[i].handToString());
                        System.out.printf("%s, choose Card %d (0-%d): ", players[i].getName(), j+1, players[i].getHand().size()-1);
                        cardsPassed[i][j] = players[i].getHand().remove(input.nextByte());
                        System.out.println();
                    }
                for (int i = 0; i < 4; i++) {
                    int current = (i+turn+1)%4;
                    System.out.println(players[current].getName() + " receives: " + Arrays.toString(cardsPassed[i]));
                    for (int j = 0; j < 3; j++)
                        players[current].getHand().add(cardsPassed[i][j]);
                }
                System.out.println();
            }

            int first = findFirst();
            boolean heartsPlayed = false;

            for (int k = 0; k < 13; k++) {
                Card[] tableCards = new Card[4];
                if (k == 0)
                    System.out.println(players[first].firstHandToString(players[first].handContainsTwoOfClubs()));
                else
                    System.out.println(players[first].noHeartsHandToString(!heartsPlayed));
                System.out.printf("%s, choose Card: ", players[first].getName());
                tableCards[first] = players[first].getHand().remove(input.nextByte());
                String firstCardSuit = tableCards[first].suit();
                System.out.println();
                for (int i = 1; i < 4; i++) {
                    int current = (i + first) % 4;
                    System.out.println(players[current].handToString(firstCardSuit, players[current].handContains(firstCardSuit)));
                    System.out.printf("%s, choose Card: ", players[current].getName());
                    tableCards[current] = (players[current].getHand().remove(input.nextByte()));
                    if (tableCards[current].suit().equals("Hearts"))
                        heartsPlayed = true;
                    System.out.println();
                }
                int max = first;
                int points = 0;
                for (int i = 0; i < 4; i++) {
                    int current = (i + first) % 4;
                    if (tableCards[current].pointValue() > tableCards[max].pointValue() && tableCards[current].suit().equals(firstCardSuit))
                        max = current;
                    if (tableCards[current].suit().equals("Hearts"))
                        points++;
                    if (tableCards[current].toString().equals("Queen of Spades (12)"))
                        points += 13;
                }

                System.out.println(players[max].getName() + " wins the trick with the " + tableCards[max] + "\n+" + points + " points\n");
                players[max].setTrick(tableCards);
                first = max;
            }
            clear();
            if (scoreBelowMax()) {
                input.nextLine();
                input.nextLine();
                System.out.println();
                turn++;
            }
        }
    }

    private static int findFirst() {
        int first = 0;
        for (int i = 0; i < 4; i++) {
            players[i].sortHandByValue();
            if (players[i].handContainsTwoOfClubs())
                first = i;
        }
        return first;
    }

    private static void clear() {
        setScores();
        for (Player player : players) {
            player.clearHand();
            player.clearTrick();
        }
        printScores(sortedScores(), scoreBelowMax());
    }

    private static Player[] sortedScores() {
        ArrayList<Player> sorted = new ArrayList<>(Arrays.asList(players));
        for (int i = 1; i < sorted.size(); i++)
            for (int j = 0; j < i; j++)
                if (sorted.get(j).getScore() > sorted.get(i).getScore())
                    sorted.add(j, sorted.remove(i));
        return sorted.toArray(players);
    }

    private static void printScores(Player[] players, boolean last) {
        System.out.println("Scores:");
        for (int i = 0; i < 4; i++)
            System.out.println(place(i + 1) + ": " + players[i].scoreToString());
        System.out.print("\n" + players[0].getName());
        System.out.println(last ? " is winning\nPress any key to continue" : " wins!");
    }

    private static void setScores() {
        ArrayList<Integer> scores = new ArrayList<>(4);
        for (Player player : players)
            scores.add(player.evaluateScore());
        if (scores.contains(26))
            for (int i = 0; i < 4; i++)
                if (scores.get(i).equals(0))
                    scores.set(i, 26);
                else if (scores.get(i).equals(26))
                    scores.set(i, 0);
        for (int i = 0; i < 4; i++)
            players[i].addScore(scores.get(i));
    }

    private static void setHands() {
        for (Player player : players) {
            for (int i = 0; i < 13; i++)
                player.setHand(deck.deal());
            player.sortHandByValue();
        }
    }

    private static boolean scoreBelowMax() {
        for (Player player : players)
            if (player.getScore() >= maxPoints)
                return false;
        return true;
    }

    private static String place(int x) {
        switch (x % 10) {
            case 1: return x + "st";
            case 2: return x + "nd";
            case 3: return x + "rd";
            default: return x + "th";
        }
    }
}