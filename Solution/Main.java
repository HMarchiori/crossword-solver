package Solution;

class Main {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.start();
        System.out.println("Hello World");
        timer.stop();
        System.out.println("Elapsed time: " + timer.getElapsedTime() + "ms");
    }

}