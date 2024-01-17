import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    public static void main(String[] args) throws Exception {

        Random rand = new Random();
        final int guessNumber = rand.nextInt(100);

        Scanner scanner = new Scanner(System.in);
        int num, cnt = 0;

        System.out.println("-----------------------------------------------------\n\t\tNumber Guess Game\n");

        while (true) {
            cnt++;
            System.out.print("Enter your guess number: ");
            num = scanner.nextInt();
            if (num == guessNumber)
                break;
            else if (num > guessNumber)
                System.out.println("You entered a higher value :-(\nTry again");
            else
                System.out.println("You entered a lower value :-(\nTry again");
        }

        scanner.close();

        System.out.println("Yeah!.. Correct\nYour Score: " + (100 - cnt));
        System.out.println("\t\t    Ending Game\n-----------------------------------------------------\n");

    }
}
