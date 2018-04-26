package lab08.server;

import java.util.Random;

public class GuessingGame {
    private int attempts;
    private String player = "";
    private int random;
    private int max = 100;

    GuessingGame()
    {
        System.out.println("New GuessingGame created!");
        this.attempts = 3;
        this.random = new Random().nextInt(max);
    }

    int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    String guess(int number) {
        String message;

        if (attempts > 0) {

            if (number < random)
                message = "Too small!";
            else if (number == random)
                message = "You got it!";
            else
                message = "Too big!";

            this.attempts -= 1;
        }
        else {
            message = "You lost! In order to play, you need to create another game!";
        }

        return message;
    }

    String quit() {
        return "Loser, the number was " + this.random + "!";
    }

    String getWelcomeMessage() {
        return "Welcome to the guessing game!";
    }
}
