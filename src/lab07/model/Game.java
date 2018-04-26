package lab07.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Bag bag;
    private Board board;
    private ScrabbleDictionary scrabbleDictionary = new ScrabbleDictionary();
    private final List<Player> players = new ArrayList<>();
    private List<Thread> playerThreads = new ArrayList<>();

    private List<Action> words = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    public void start()
    {
        players.forEach(player -> {
            Action word = new Action();
            words.add(word);
            player.setWord(word);
            playerThreads.add(new Thread(player));
        });
        playerThreads.forEach(Thread::start);

        boolean threadsAlive = true;

        synchronized (words)
        {
            while(threadsAlive) {
                players.forEach(player -> {
                    // TO DO: replace the function below with an actual function to player [pass, replace, create]
                    player.getWord().setMessage("test");
                });

                for(Thread player : playerThreads)
                {
                    if(!player.isAlive()) threadsAlive = false;
                }
            }
        }
    }

    Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
