package lab08.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Random;

public class GuessingGame {
    private int attempts;
    private String player;
    private int random;
    private int max = 100;
    private String localFile;
    private String serverPath = "/ghita/GuessingGame/";
    private TimeKeeper tk = null;
    private ClientThread ct = null;

    GuessingGame(String player, ClientThread ct)
    {
        System.out.println("New GuessingGame created!");
        this.attempts = 3;
        this.random = new Random().nextInt(max);
        this.player = player;
        this.localFile =
                "stats_" +
                player +
                "_" +
                Long.toString(Instant.now().getEpochSecond()) +
                ".html";

        System.out.println(localFile);

        this.ct = ct;

        tk = new TimeKeeper(this);
        Thread tkThread = new Thread(tk);
        tkThread.start();
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
            tk.resetTimeout();

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
        return "Welcome to the guessing game " + this.player + "!";
    }

    void writeFile() {
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html>\n<html>\n<body>\n");

        sb.append("<h1>You ").append((attempts > 0) ? "won" : "lost").append("!</h1>");

        sb.append("<h2>PlayerHuman name: ").append(player).append("</h2>");

        sb.append("<h2>Attempts left: ").append(Integer.toString(attempts)).append("</h2>");

        sb.append("<h2>Number was: ").append(Integer.toString(random)).append("</h2>");

        sb.append("</body></html>");

        File file = new File(localFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(sb.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    void deleteFile() {
        File file = new File(localFile);
        if(file.delete()) {
            System.out.println("Successfully deleted " + localFile + " file!");
        }
        else {
            System.out.println("Failed to delete " + localFile + " file!");
        }
    }

    void uploadFile() {
        new MyFTPClient().uploadFile(localFile, serverPath + localFile);
    }

    public ClientThread getCt() {
        return ct;
    }

    public void setCt(ClientThread ct) {
        this.ct = ct;
    }
}
