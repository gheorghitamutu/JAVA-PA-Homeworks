package lab07.model;

import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class TimeKeeper implements Runnable {
    private final Map<Player, Integer> countDown = new HashMap<>();
    private List<Thread> playerThreads = new ArrayList<>();

    private int countDownTime = 10;
    private int seconds = 1;

    private long totalTime = 0;


    private String gameTimeKeeperTextMessage = "Game takes {} seconds! <> has [] seconds to choose!";
    private Text gtkt = null;
    private Text utt = null;

    @Override
    public void run() {
        boolean isAnyThreadAlive = true;
        while (isAnyThreadAlive) {
            synchronized (countDown) {
                countDown.forEach((key, value) -> {
                    if (value == 0) {
                        key.pass();
                        resetTimeKeeperCountDown(key);
                    } else {
                        if (key.isItsTurn()) {
                            if(key.getLetters().size() == 7)
                                utt.setText(key.getLetters().toString());
                            key.decreaseCountDown();
                        }
                    }
                });

                isAnyThreadAlive = false;
                for (Thread player : playerThreads) {
                    if (player.isAlive()) {

                        isAnyThreadAlive = true;
                        break;
                    }
                }
            }

            totalTime += 1;

            if(gtkt != null) {
                String message = gameTimeKeeperTextMessage.replace("{}", Long.toString(totalTime));
                synchronized (countDown) {
                    for (Map.Entry<Player, Integer> entry : countDown.entrySet()) {
                        if (entry.getKey().isItsTurn()) {
                            message = message.replace("<>", entry.getKey().toString());
                            message = message.replace("[]", Integer.toString(entry.getValue()));
                            break;
                        }
                    }

                }
                if(!message.contains("[]")) gtkt.setText(message);
            }

            try {
                sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String message = gameTimeKeeperTextMessage.replace("{}", Long.toString(totalTime));
        message = message.replace("takes", "took");
        message = message.replace(
                "<> has [] seconds to choose!",
                "Game over!");
        gtkt.setText(message);

        synchronized (System.out) {
            System.out.println("Game took " + totalTime + " seconds!");
        }
    }

    synchronized void resetTimeKeeperCountDown(Player player) {
        synchronized (System.out) {
            System.out.println(player.toString() + " countdown reset!");
        }
        if(player.isAlive()) countDown.put(player, 10);
    }

    synchronized void decreaseCountDown(Player player) {
        int value = countDown.get(player);
        countDown.put(player, value - 1);
    }

    void addPlayerThread(Thread thread) {
        playerThreads.add(thread);
    }

    void addPlayer(Player player) {
        countDown.put(player, countDownTime);
    }

    synchronized int getTimeLeft(Player player) {
        return countDown.get(player);
    }

    public void setGameTimeKeeperText(Text gameTimeKeeperText) {
        this.gtkt = gameTimeKeeperText;
    }

    public synchronized void setUserTilesText(Text userTilesText) {
        this.utt = userTilesText;
    }

    String getTotalTime() {
        return Long.toString(totalTime);
    }
}
