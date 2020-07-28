package Task_4_part_1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Object mon = new Object();
    private static volatile char currentLetter = 'A';

    public static void main(String[] args) {
        doTask();
    }

    private static void doTask() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(() -> printX('A'));
        executor.execute(() -> printX('B'));
        executor.execute(() -> printX('C'));

        executor.shutdown();
    }

    private static void printX(final char x) {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != x) mon.wait();
                    System.out.println(x);
                    currentLetter++;
                    if (currentLetter == 'D') currentLetter = 'A';
                    mon.notifyAll();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

