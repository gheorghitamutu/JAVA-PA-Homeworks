package lab08.server;

public class TimeKeeper implements Runnable {
    private GuessingGame gg = null;
    private Integer timeout = 4;

    TimeKeeper(GuessingGame gg) {
        this.gg = gg;
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
            gg.setAttempts(0);
        }
    }

    private void sleep(int i) {
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
