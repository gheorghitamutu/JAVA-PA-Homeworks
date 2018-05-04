package lab07.model;

import java.util.List;

public interface Player extends Runnable {
    boolean createSubmitWord() throws InterruptedException;
    void setGame(Game game);
    @Override
    void run();
    @Override
    String toString();
    Action getWord();
    void setWord(Action word);
    boolean createWord(String input);
    String getPlayerType();
    String getName();
    int getScore();
    void pass();
    void extract();

    Game getGame();

    void decreaseCountDown();

    boolean isAlive();

    boolean isItsTurn();
    void setTurn(boolean isItsTurn);

    void extractMany(String howMany);

    List<Character> getLetters();
}
