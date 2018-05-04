package lab07.model;

import java.util.LinkedList;
import java.util.Queue;

public class Board {
    private final int boardSize = 15;
    private final Queue<String> words = new LinkedList<>();

    synchronized void addWord(Player player, String word)
    {
        if(word.length() > 1 ) {
            words.add(word);
            synchronized (System.out) {
                System.out.println(player + " submitted the word " + word + "!");
            }
            player.getGame().getTimeKeeper().resetTimeKeeperCountDown(player);
        }
        else {
            if(word.equals("")) {
                player.getGame().getTimeKeeper().resetTimeKeeperCountDown(player);
            }
        }
    }

}
