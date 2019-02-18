package lab08.server;

public class TimeKeeper implements Runnable {
    private GuessingGame guessingGame;
    private Integer timeout = 10;

    TimeKeeper(GuessingGame guessingGame) {
        this.guessingGame = guessingGame;
        this.resetTimeout();
    }

    @Override
    public void run() {
        System.out.println("Timekeeper run!");
        synchronized (timeout) {
            while(timeout > 0) {
                System.out.println("Decrease countdown!");
                timeout--;
                sleep(1000);
            }
            System.out.println("Time's up!");
            guessingGame.setAttempts(0);
        }
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    synchronized void resetTimeout() {
        synchronized (timeout) {
          timeout = 10;
        }
        System.out.println("Timeout reset!");
    }
}
