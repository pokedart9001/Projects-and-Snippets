import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner input = new Scanner(System.in);
        String str;
        boolean keepGoing = true;

        HiddenWordGame puzzle = new HiddenWordGame();

        System.out.print("The answer will be a word between 4 & 10 letters.\nChoosing.");
        for (int i = 0; i < 15; i++) {
            Thread.sleep(Math.round(Math.random()*700));
            System.out.print(".");
        }
        System.out.println("\n\nThe answer is a " + puzzle.answer.length() + "-letter word. Try to guess what it is!\n");

        while (keepGoing) {

            System.out.print("Enter a guess: ");

            str = input.nextLine();
            while (str.length() != puzzle.answer.length()) {
                System.out.print("Guess must be same length as answer.\n\nEnter a guess: ");
                str = input.nextLine();
            }

            System.out.println(puzzle.guess(str.toUpperCase()) + "\n");

            if (str.toUpperCase().equals(puzzle.answer)) {
                System.out.println("Congratulations! You guessed the answer!");
                keepGoing = false;
            }

        }

    }
}