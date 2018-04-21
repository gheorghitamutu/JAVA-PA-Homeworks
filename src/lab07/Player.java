package lab07;

import java.util.List;
import java.util.stream.IntStream;

public class Player implements Runnable {
    private String name;
    private Game game;

    Player(String name)
    {
        this.name = name;
    }

    private boolean createSubmitWord() throws InterruptedException {
        List extracted = game.getBag().extractLetters(1);
        if (extracted.isEmpty()) return false;

        // build word
        StringBuilder word = new StringBuilder();
        IntStream.range(0, 10).forEachOrdered(n -> word.append(extracted.get(0)));

        // add word to board
        game.getBoard().addWord(this, word.toString());
        Thread.sleep(100);
        return true;
    }

    void setGame(Game game)
    {
        this.game = game;
    }

    @Override
    public void run()
    {
        System.out.println("Thread created for " + name + "!");

        boolean bagEmpty = false;
        while(!bagEmpty)
        {
            try {
                bagEmpty = !createSubmitWord();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Bag empty for " + name + "!");
    }

    @Override
    public String toString() {
        return name;
    }
}
