package lab07.model;

import java.util.LinkedList;
import java.util.Queue;

public class Board {
    private final int boardSize = 15;
    private final Queue<String> words = new LinkedList<>();

    synchronized void addWord(Player player, String word)
    {
        words.add(word);
        System.out.println("Player " + player + " submitted the word " + word + "!");
    }

}
