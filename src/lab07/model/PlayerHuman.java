package lab07.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerHuman
        implements Player {
    private String name;
    private Game game;
    private Action word = null;

    private boolean bagEmpty = false;

    private List<Character> letters = new ArrayList<>();

    private int leftLetters = 0;
    private int minLetters = 7;

    private boolean isAlive = false;

    private int score = 0;

    private volatile boolean isItsTurn = false;

    public PlayerHuman(String name) {
        this.name = name;
    }

    public boolean createSubmitWord() throws InterruptedException {
        leftLetters = letters.size();
        int neededLetters = minLetters - leftLetters;

        if(neededLetters > 0) {
            leftLetters = game.getBag().getLetters().size();

            List<Character> extractedLetters = game.getBag().extractLetters(neededLetters);
            if (extractedLetters.size() > 0) letters.addAll(extractedLetters);
            if (extractedLetters.isEmpty()) return true;

        }

        // add word to board
        System.out.println(name + " waiting for message!");
        String w = word.getMessage();
        if(w != null && w.length() != 0) {
            for (char c : w.toCharArray()) {
                score += game.getBag().getLetterValue(c);
                //letters.remove((Character) c);
            }
        }
        game.getBoard().addWord(this, w);


        Thread.sleep(200);

        return false;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        synchronized (System.out) {
            System.out.println("Thread created for " + name + "!");
        }
        isAlive = true;

        while (!bagEmpty && game.getBag().getLetters().size() > 0) {
            try {
                bagEmpty = createSubmitWord();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                bagEmpty = false;
            }
        }

        synchronized (System.out) {
            System.out.println("Bag empty for " + name + "!");
            System.out.println(name + " score was " + score + "!");
        }
        isAlive = false;
    }

    @Override
    public String toString() {
        return name;
    }

    public Action getWord() {
        return word;
    }

    public void setWord(Action word) {
        this.word = word;
    }

    public boolean createWord(String input) {
        input = input.toLowerCase();
        System.out.println("Word to check: " + input);
        String w = game.getScrabbleDictionary().containsWordLetters(input);
        for(char c : w.toCharArray()) {
            score += game.getBag().getLetterValue(c);
            letters.remove((Character)c);
        }
        if(w.length() == 0)
            synchronized (System.out) {
                System.out.println("This word does not exist!");
                return false;
        }

        word.setMessage(w);
        isItsTurn = false;
        return true;
    }

    public String getPlayerType() {
        return "Human";
    }

    public void pass() {
        if (game.getBag().getLetters().size() == 0) {
            word.setPass(false);
        }
        word.pass();
        isItsTurn = false;
    }

    public void extract() {
        // handle extraction here
        leftLetters = letters.size();
        int neededLetters = minLetters - leftLetters;

        if(neededLetters > 0) {
            leftLetters = game.getBag().getLetters().size();

            List<Character> extractedLetters = game.getBag().extractLetters(neededLetters);
            if (extractedLetters.size() > 0) letters.addAll(extractedLetters);
        }

        word.setMessage("");
        isItsTurn = false;
    }

    @Override
    public void extractMany(String howMany) {
        int extractMany;
        if (!howMany.isEmpty()) extractMany = Integer.parseInt(howMany);
        else return;

        for(int i = 0; i < extractMany && i < letters.size(); i++) letters.remove(0);

        extract();
        isItsTurn = false;
    }

    public Game getGame() {
        return game;
    }

    public void decreaseCountDown() {
        game.getTimeKeeper().decreaseCountDown(this);
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    synchronized public boolean isItsTurn() {
        return isItsTurn;
    }

    @Override
    synchronized public void setTurn(boolean isItsTurn) {
        this.isItsTurn = isItsTurn;
    }

    public synchronized List<Character> getLetters() {
        return letters;
    }

    public synchronized void setLetters(List<Character> letters) {
        this.letters = letters;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
