package lab07.model;

import lab07.view.GameScene;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class PlayerAI implements Player {

    private String name;
    private Game game;
    private Action word = null;
    private List<Character> letters = new ArrayList<>();

    private int leftLetters = 0;
    private int minLetters = 7;

    private int score = 0;

    private boolean isAlive = false;
    private boolean isItsTurn = false;

    private GameScene gm;

    public PlayerAI(GameScene gm)
    {
        this.name = "AI_" + Integer.toString(new Random().nextInt(1000));
        this.gm = gm;
    }

    public boolean createSubmitWord() throws InterruptedException {
        leftLetters = letters.size();
        int neededLetters = minLetters - leftLetters;

        if(neededLetters > 0) {
            leftLetters = game.getBag().getLetters().size();

            List<Character> extractedLetters = game.getBag().extractLetters(neededLetters);
            if (extractedLetters.size() > 0) letters.addAll(extractedLetters);
            if (extractedLetters.isEmpty()) return true;

            // add word to board
            String wordToAdd = word.getMessage();
            game.getBoard().addWord(this, wordToAdd);

            // add to interface
            gm.setWordRandomlyOnTheBoard(wordToAdd);
        }

        sleep(100);

        return false;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    @Override
    public void run()
    {
        synchronized (System.out) {
            System.out.println("Thread created for " + name + "!");
        }
        isAlive = true;

        boolean bagEmpty = false;
        while(!bagEmpty)
        {
            try {
                bagEmpty = createSubmitWord();
                sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    @Override
    public boolean createWord(String input) {
        List<String> wordList = getCombinations(letters);

        String[] submittedWord = null;
        for(String word : wordList)
        {
            String w = game.getScrabbleDictionary().containsWordLetters(word);
            if(!w.equals(""))
            {
                submittedWord = new String[] {w};
                break;
            }
        }

        if (submittedWord != null) {
            String str = StringUtils.join(submittedWord, "");
            for(char c : str.toCharArray()) {
                score += game.getBag().getLetterValue(c);
                letters.remove((Character)c);
            }

            word.setMessage(Arrays.toString(submittedWord));
        }
        else if(!letters.isEmpty()) {
            letters.remove(0);
            synchronized (System.out) {
                System.out.println(name + " extracts 1 letter this turn!");
            }
            word.setMessage("");
        }
        else {
            word.setMessage("");
        }

        return true;
    }

    synchronized private static ArrayList<String> getCombinations(List<Character> letters) {
        StringBuilder sb = new StringBuilder();
        for (Character c: letters) {
            sb.append(c);
        }

        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < sb.toString().length(); i++) {
            // Record size as the list will change
            int resultsLength = results.size();
            for (int j = 0; j < resultsLength; j++) {
                results.add(sb.toString().charAt(i) + results.get(j));
            }
            results.add(Character.toString(sb.toString().charAt(i)));
        }
        return results;
    }

    public String getPlayerType() {
        return "AI";
    }

    public void pass() {
        // dummy function for AI
        word.pass();
    }

    public void extract() {
        // dummy function for AI
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
    public boolean isItsTurn() {
        return isItsTurn;
    }

    @Override
    public void setTurn(boolean isItsTurn) {
        this.isItsTurn = isItsTurn;
    }

    @Override
    public void extractMany(String howMany) {}

    @Override
    public List<Character> getLetters() { return letters; }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
