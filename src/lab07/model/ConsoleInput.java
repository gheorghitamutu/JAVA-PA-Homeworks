package lab07.model;

// https://www.javaspecialists.eu/archive/Issue153.html

import java.util.concurrent.*;

class ConsoleInput {
    private final int tries;
    private final int timeout;
    private final TimeUnit unit;

    ConsoleInput(int tries, int timeout, TimeUnit unit) {
        this.tries = tries;
        this.timeout = timeout;
        this.unit = unit;
    }

    String readLine() throws InterruptedException {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        String input = null;
        try {
            // start working
            for (int i = 0; i < tries; i++) {
                Future<String> result = ex.submit(
                        new ConsoleInputReadTask());
                try {
                    input = result.get(timeout, unit);
                    break;
                } catch (ExecutionException e) {
                    e.getCause().printStackTrace();
                } catch (TimeoutException e) {
                    result.cancel(true);
                }
            }
        } finally {
            ex.shutdownNow();
        }
        return input;
    }
}