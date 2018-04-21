package lab07;

import java.util.ArrayList;
import java.util.List;

class Game {
    private Bag bag;
    private Board board;
    private ScrabbleDictionary scrabbleDictionary = new ScrabbleDictionary();
    private final List<Player> players = new ArrayList<>();

    void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    void start()
    {
        players.forEach(player -> new Thread(player).start());
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
}
