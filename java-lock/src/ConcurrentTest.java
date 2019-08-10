import java.util.concurrent.locks.Lock;

public class ConcurrentTest {

    static int m = 0;
    static Lock lock = new SkyLock();

    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[100];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    lock.lock();
                    for (int j = 0; j < 1000; j++) m++;
                } finally {
                    lock.unlock();
                }

            }, "thread-" + i);
        }

        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();

        System.out.println(m);
    }
}
