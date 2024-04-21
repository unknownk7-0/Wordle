import java.util.Scanner;

public class GameLogic {

    public static final String RESET = "\u001B[0m";
    public static final String RED_TEXT = "\u001B[31m";
    public static final String GREEN_TEXT = "\u001B[32m";
    public static final String YELLOW_TEXT = "\u001B[33m";
    private Scanner scanner;

    public GameLogic() {
        scanner = new Scanner(System.in);
    }

    public void playWordle() {
        Display display = new Display();
        boolean gameEnded = false;
        while (!gameEnded) {
            int num = display.getDifficulty();
            boolean exit = false;
            while (!exit) {
                if ((num >= 5 && num <= 8) || num == 0)
                    exit = true;
                else
                    num = display.getDifficulty();
            }

            if (num != 0) {
                Words dictionary = new Words(num);

                String randomWord = dictionary.getRandomWord();
                char[] wordArray = splitToArray(randomWord);
                boolean done = false;
                int attempts = 0;
                while (!done && attempts < 6 && num != 0) {
                    String guess = null;
                    boolean valid = false;
                    while (!valid) {
                        guess = display.getUserGuess(attempts + 1).trim().toUpperCase();
                        if (guess.length() == num) {
                            if (checkForInts(guess)) {
                                System.out.println(RED_TEXT + "UNABLE TO ENTER NUMBER" + RESET);
                            } else {
                                valid = true;
                                attempts += 1;
                            }
                        } else {
                            if ((guess).equals("0")) {
                                valid = true;
                                done = true;
                                num = 0;
                            } else {
                                System.out.println(RED_TEXT + "INCORRECT NUMBER OF LETTERS.TRY AGAIN" + RESET);
                            }
                        }
                    }

                    if (valid && !done) {
                        char[] userGuess = splitToArray(guess);
                        int numCorrect = 0;
                        for (int i = 0; i < userGuess.length; i++) {
                            boolean exists = false;
                            int j = 0;

                            for (j = 0; j < wordArray.length && !exists; j++) {
                                if (userGuess[i] == wordArray[j]) {
                                    exists = true;
                                }
                            }

                            if (exists) {
                                j -= 1;
                                if (userGuess[i] == wordArray[i]) {
                                    // print green
                                    System.out.print(GREEN_TEXT + userGuess[i] + RESET + " ");
                                    numCorrect += 1;
                                } else if (i != j) {
                                    // print yellow
                                    System.out.print(YELLOW_TEXT + userGuess[i] + RESET + " ");
                                }
                            } else {
                                // print red
                                System.out.print(RED_TEXT + userGuess[i] + RESET + " ");
                            }
                        }

                        System.out.println();
                        if (numCorrect == num) {
                            done = true;
                            System.out.println(GREEN_TEXT + "\nCONGRATULATIONS,WELL DONE" + RESET);
                        }

                    }

                }

                if (!done && attempts == 6) {
                    System.out.println("YOU HAVE RUN OUT OF ATTEMPTS");
                    System.out.println(
                            String.format("The correct answer is %s", GREEN_TEXT + randomWord + RESET));
                    num = 0;
                }

                if (num == 0) {
                    System.out.println("Would you Like to Start Over? (Y/N)");
                    boolean tryagain = true;
                    while (tryagain) {
                        String iString = scanner.next();
                        if (iString.equalsIgnoreCase("N")) {
                            gameEnded = true;
                            tryagain = false;
                            System.out.println("Ending Game...");
                        } else if (iString.equalsIgnoreCase("Y")) {
                            System.out.println("Setting up new game...");
                            tryagain = false;
                        } else {
                            System.out.println("Unable to interpret entered character. Try Again");
                        }
                    }
                }
            } else {
                System.out.println("Ending Game...");
                gameEnded = true;
            }
        }
    }

    private char[] splitToArray(String word) {
        char[] retArray = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            retArray[i] = word.charAt(i);
        }
        return retArray;
    }

    private boolean checkForInts(String input) {
        boolean result = false;
        for (int i = 0; i < input.length() && !result; i++) {
            try {
                Integer.parseInt(input.charAt(i) + "");
                result = true;
            } catch (NumberFormatException nfe) {

            }
        }
        return result;
    }
}
