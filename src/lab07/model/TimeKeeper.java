package lab07.model;

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
                        if(key.isItsTurn())
                        key.decreaseCountDown();
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
            try {
                sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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
}
