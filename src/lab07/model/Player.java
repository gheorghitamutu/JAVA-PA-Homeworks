package lab07.model;

import java.util.List;

public class Player implements Runnable {
    private String name;
    private Game game;
    private Action word = null;

    public Player(String name)
    {
        this.name = name;
    }

    private boolean createSubmitWord() throws InterruptedException {
        List extracted = game.getBag().extractLetters(1);
        if (extracted.isEmpty()) return false;


        // add word to board
        game.getBoard().addWord(this, word.getMessage());
        Thread.sleep(200);

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


    public Action getWord() {
        return word;
    }

    void setWord(Action word) {
        this.word = word;
    }
}
