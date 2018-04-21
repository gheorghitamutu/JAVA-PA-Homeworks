package lab07;

import java.util.LinkedList;
import java.util.Queue;

class Board {
    // board should be 15x15
    private final Queue<String> words = new LinkedList<>();

    synchronized void addWord(Player player, String word)
    {
        words.add(word);
        System.out.println("Player " + player + " submitted the word " + word + "!");
    }

}
