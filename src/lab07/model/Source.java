package lab07.model;

public class Source {
    public static void main(String args[]) {
        Game game = new Game();
        game.setBag(new Bag());
        game.setBoard(new Board());
        game.addPlayer(new PlayerHuman("Human_1"));
        game.addPlayer(new PlayerAI());
        game.addPlayer(new PlayerAI());
        game.addPlayer(new PlayerAI());
        game.addPlayer(new PlayerAI());
        game.start();
    }
}
