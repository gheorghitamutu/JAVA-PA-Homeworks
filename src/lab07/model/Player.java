package lab07.model;

public interface Player extends Runnable {
    boolean createSubmitWord() throws InterruptedException;
    void setGame(Game game);
    @Override
    void run();
    @Override
    String toString();
    Action getWord();
    void setWord(Action word);
    void createWord();
    String getPlayerType();
    void pass();
    void extract();

    Game getGame();

    void decreaseCountDown();

    boolean isAlive();

    boolean isItsTurn();
    void setTurn(boolean isItsTurn);

    void extractMany();
}
