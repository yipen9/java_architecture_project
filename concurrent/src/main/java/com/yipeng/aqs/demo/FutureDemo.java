package com.yipeng.aqs.demo;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }

    private static void test1() throws ExecutionException, InterruptedException {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1000);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 1000, TimeUnit.SECONDS, queue);


        Future<String> future = poolExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                return "hello";
            }
        });

        String res = future.get();  /** {@link FutureTask#get()}*/
        System.out.println(res);
    }

 /**{@link FutureTask#get()} {@link FutureTask#awaitDone(boolean, long)} ()}

    private int awaitDone(boolean timed, long nanos) throws InterruptedException {
        FutureTask.WaitNode q = null;
        boolean queued = false;
        for (; ; ) {    轮询
            int s = state;
            if (s > COMPLETING) {   如果状态是完成，返回。
                if (q != null)
                    q.thread = null;
                return s;
            } else if (s == COMPLETING)
                Thread.yield();
            else if (Thread.interrupted()) {
                removeWaiter(q);
                throw new InterruptedException();
            } else if (q == null) {
                if (timed && nanos <= 0L)
                    return s;
                q = new FutureTask.WaitNode();
            } else if (!queued)
                queued = WAITERS.weakCompareAndSet(this, q.next = waiters, q);
            else if (timed) {
                final long parkNanos;
                if (startTime == 0L) { // first time
                    startTime = System.nanoTime();
                    if (startTime == 0L)
                        startTime = 1L;
                    parkNanos = nanos;
                } else {
                    long elapsed = System.nanoTime() - startTime;
                    if (elapsed >= nanos) {
                        removeWaiter(q);
                        return state;
                    }
                    parkNanos = nanos - elapsed;
                }
                // nanoTime may be slow; recheck before parking
                if (state < COMPLETING)
                    LockSupport.parkNanos(this, parkNanos);
            } else
                LockSupport.park(this);
        }
    }
  */
}
