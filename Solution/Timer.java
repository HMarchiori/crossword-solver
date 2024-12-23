package Solution;

public class Timer {
    private long startTime;
    private long endTime;

    public Timer() {
        startTime = 0;
        endTime = 0;
    }

    public void reset() {
        startTime = 0;
        endTime = 0;
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        endTime = System.currentTimeMillis();
    }

    public long getElapsedTime() {
        return endTime - startTime;
    }

}
