package Task_5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MoveController {
    private static int CARS_COUNT;
    public static CyclicBarrier startBarrier;
    public static CountDownLatch finishBarrier;
    public static Semaphore tunnelControl;
    private static boolean isWin = true;

    public MoveController(int CARS_COUNT) {
        this.CARS_COUNT = CARS_COUNT;
        startBarrier = new CyclicBarrier(CARS_COUNT+1);
        finishBarrier = new CountDownLatch(CARS_COUNT);
        tunnelControl = new Semaphore(CARS_COUNT/2);
    }

    public static synchronized void finish(Car car) {
        System.out.print(car.getName() + " закончил гонку");
        if(isWin){
            System.out.println(" ПОБЕДИТЕЛЕМ! поздравим его бурными овациями!");
            isWin = false;
        }else {
            System.out.printf(", и пришёл %dм.%n", (CARS_COUNT+1-finishBarrier.getCount()));
        }
    }

}
