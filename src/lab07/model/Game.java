package lab07.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

class Game {
    private Bag bag;
    private Board board;
    private ScrabbleDictionary scrabbleDictionary = new ScrabbleDictionary();
    private final List<Player> players = new ArrayList<>();
    private List<Thread> playerThreads = new ArrayList<>();

    private final List<Action> words = new ArrayList<>();

    private TimeKeeper tk = new TimeKeeper();

    void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    void start()
    {
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

        synchronized (words)
        {
            while(threadsAlive) {
                players.forEach(player -> {
                    player.setTurn(true);

                    if(!player.getPlayerType().equals("AI")) {
                        boolean isInputGood = false;
                        while(!isInputGood) {
                            synchronized (System.out) {
                                System.out.println("What's your next action " + player.toString() + " ?");
                            }
                            ConsoleInput con = new ConsoleInput(
                                    1,
                                    player.getGame().getTimeKeeper().getTimeLeft(player),
                                    TimeUnit.SECONDS
                            );

                            String input = null;
                            try {
                                input = con.readLine();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if(input == null) {
                                isInputGood = true;
                                synchronized (System.out) {
                                    System.out.println("Time passed for " + player + "!");
                                }
                                player.pass();
                                continue;
                            }
                            if(!input.equals("pass") &&
                                    !input.equals("extract") &&
                                    !input.equals("create")) continue;

                            isInputGood = true;
                            switch (input) {
                                case "pass":
                                    player.pass();
                                    break;
                                case "extract":
                                    player.extractMany();
                                    break;
                                case "create":
                                    player.createWord();
                                    break;
                            }
                        }
                    }
                    else {
                        player.createWord();
                    }

                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        player.setTurn(false);
                    }
                });

                threadsAlive = false;
                for(Thread player : playerThreads)
                {
                    if(player.isAlive()) threadsAlive = true;
                }
            }
        }
    }

    Bag getBag() {
        return bag;
    }

    void setBag(Bag bag) {
        this.bag = bag;
    }

    Board getBoard() {
        return board;
    }

    void setBoard(Board board) {
        this.board = board;
    }

    ScrabbleDictionary getScrabbleDictionary() {
        return scrabbleDictionary;
    }

    public void setScrabbleDictionary(ScrabbleDictionary scrabbleDictionary) {
        this.scrabbleDictionary = scrabbleDictionary;
    }

    TimeKeeper getTimeKeeper() {
        return tk;
    }
}
