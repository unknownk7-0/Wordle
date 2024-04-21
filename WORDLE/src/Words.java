import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Words {
    private static int num = 0;
    private final int SIZE = 1009;
    private Node[] table = new Node[SIZE];
    File file;

    public Words(int num) {
        instantiate();
        String filename = null;
        if (num > 4 && num < 9) {
            filename = num + "-lettered-words.txt";
            file = new File("words/" + filename);

        }
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                this.insert(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }

    private void instantiate() {
        for (int i = 0; i < SIZE; i++) {
            table[i] = null;
        }
    }

    public void printFirst() {
        Node curr = table[0];
        while (curr != null) {
            System.out.print(curr.getWord() + " ");
            curr = curr.getNext();
        }
    }

    private int key(String word) {
        int a = 13;
        char[] array = new char[word.length()];

        for (int i = 0; i < array.length; i++) {// convert to array
            array[i] = (char) word.charAt(i);
        }

        int result = ((int) (array[array.length - 1])) % SIZE;

        for (int i = array.length - 2; i >= 0; i--) {// convert to hash and then key
            result = (a * result + (int) array[i]) % SIZE;
        }

        return result;
    }

    public void insert(String word) {
        word = word.trim().toUpperCase();
        if (!search(word)) {
            num = num + 1;
            int index = key(word);
            Node node = table[index];
            table[index] = new Node(word, num);
            table[index].setNext(node);
        }

    }

    public boolean search(String word) {
        word = word.trim().toUpperCase();
        boolean found = false;
        Node curr = table[key(word)];
        while (curr != null && !found) {
            if (word.equalsIgnoreCase(curr.getWord())) {
                found = true;
            }
            curr = curr.getNext();
        }
        return found;
    }

    public String getRandomWord() {
        Random random = new Random();
        int randInt = random.nextInt(1, num + 1);
        // System.out.println(randInt);
        String returnString = "";
        int index = 0;
        boolean found = false;
        while (index < SIZE && !found) {
            Node curr = table[index];
            while (curr != null && !found) {
                if (randInt == curr.getEntry()) {
                    returnString = curr.getWord();
                    found = true;
                }
                curr = curr.getNext();
            }
            index++;
        }
        // System.out.println("Return String: " + returnString);
        return returnString;
    }

    private class Node {
        private int entry;
        private String word;
        private Node next;

        public Node(String word, int entry) {
            this.word = word;
            this.entry = entry;
            this.next = null;
        }

        public String getWord() {
            return word;
        }

        public int getEntry() {
            return entry;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node node) {
            this.next = node;
        }
    }
}