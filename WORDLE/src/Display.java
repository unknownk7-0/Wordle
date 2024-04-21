import java.util.Scanner;

public class Display {
    Scanner scanner;

    public Display() {
        scanner = new Scanner(System.in);
    }

    public int getDifficulty() {
        int diff = -1;
        try {
            System.out.print("Enter a number of words to play for(Enter 0 to exit): ");
            diff = Integer.parseInt(scanner.next());
        } catch (NumberFormatException exception) {
            System.out.println(exception);
        }
        return diff;
    }

    public String getUserGuess(int number) {
        System.out.print("ENTER YOUR GUESS (number " + number + "): ");
        return scanner.next();
    }
}
