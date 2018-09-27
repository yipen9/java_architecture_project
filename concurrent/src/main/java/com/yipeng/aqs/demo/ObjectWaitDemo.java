package com.yipeng.aqs.demo;

public class ObjectWaitDemo {
    /**
     * 1.测试{@link Object#wait}
     */
    public static void main(String[] args) {
        test2();
    }

    //wait和notify/notifyAll方法只能在同步代码块里用,否则抛java.lang.IllegalMonitorStateException 异常
    private static void test1() {
        final Object obj = new Object();
        Thread threadA = new Thread() {
            @Override
            public void run() {
                System.out.println("begin to wait ....");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end to wait ....");
            }
        };

        threadA.start();

        System.out.println(Thread.currentThread() + " begin to signal .....");

        obj.notify();

        System.out.println("main end ....");
    }

    private static void test2() {
        final Object obj = new Object();
        Thread threadA = new Thread() {
            @Override
            public void run() {
                System.out.println("begin to wait ....");
                try {
                    synchronized (obj) {
                        obj.wait();     //但其进入wait状态时，会释放掉obj的锁
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end to wait ....");
            }
        };

        threadA.start();

        System.out.println(Thread.currentThread() + " begin to signal .....");

        //可能主线程会先进入此方法，从而导致threadA一直在wait
        synchronized (obj) {
            obj.notify();
        }

        System.out.println("main end ....");
    }


}
