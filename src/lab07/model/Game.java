package lab07.model;

import javafx.application.Platform;
import lab07.view.EndGameScene;
import lab07.view.MainWindow;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Game implements Runnable {
    private final MainWindow mw;
    private Bag bag;
    private Board board;
    private ScrabbleDictionary scrabbleDictionary = new ScrabbleDictionary();
    private final List<Player> players = new ArrayList<>();
    private List<Thread> playerThreads = new ArrayList<>();

    private final List<Action> words = new ArrayList<>();

    private TimeKeeper tk = new TimeKeeper();

    private Player currentPlayer = null;

    static String gameOver = "";

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    public Game(MainWindow mw) {
        this.mw = mw;
    }

    public void run() {
        players.forEach(player -> {
            Action word = new Action();
            words.add(word);
            player.setWord(word);

            tk.addPlayer(player);

            playerThreads.add(new Thread(player));

            tk.addPlayerThread(playerThreads.get(playerThreads.size() - 1));
        });
        playerThreads.forEach(Thread::start);

        Thread tkThread = new Thread(tk);
        tkThread.start();

        boolean threadsAlive = true;

        synchronized (words) {
            while (threadsAlive) {
                players.forEach(player -> {
                    player.setTurn(true);
                    currentPlayer = player;

                    if (!player.getPlayerType().equals("AI")) {
                        while (player.isItsTurn()) {
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        player.createWord("");
                    }

                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        player.setTurn(false);
                    }
                });

                threadsAlive = false;
                for (Thread player : playerThreads) {
                    if (player.isAlive()) threadsAlive = true;
                }
            }
        }

        EndGameScene.gameOver = "";
        EndGameScene.gameOver = "Game Over!\n";
        EndGameScene.gameOver += "Game took " + tk.getTotalTime() + " seconds!\n";
        EndGameScene.gameOver += players.get(0).getName() + "\'s score is: " + players.get(0).getScore() + "!\n";
        EndGameScene.gameOver += players.get(1).getName() + "\'s score is: " + players.get(1).getScore() + "!\n";
        EndGameScene.gameOver += players.get(2).getName() + "\'s score is: " + players.get(2).getScore() + "!\n";
        EndGameScene.gameOver += players.get(3).getName() + "\'s score is: " + players.get(3).getScore() + "!\n";

        Platform.runLater(mw::setEndStage);
    }

    public Bag getBag() {
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

    ScrabbleDictionary getScrabbleDictionary() {
        return scrabbleDictionary;
    }

    public void setScrabbleDictionary(ScrabbleDictionary scrabbleDictionary) {
        this.scrabbleDictionary = scrabbleDictionary;
    }

    public TimeKeeper getTimeKeeper() {
        return tk;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
