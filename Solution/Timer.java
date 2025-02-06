package Solution;

public class Timer {
    private long startTime = 0;
    private long endTime = 0;
    private boolean running = false;

    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stop() {
        if (running) {
            endTime = System.currentTimeMillis();
            running = false;
        }
    }

    public void reset() {
        startTime = 0;
        endTime = 0;
        running = false;
    }

    public long getElapsedTime() {
        return (running ? System.currentTimeMillis() : endTime) - startTime;
    }

    public void printElapsedTime() {
        System.out.println("Elapsed time: " + getElapsedTime() + "ms");
    }
}