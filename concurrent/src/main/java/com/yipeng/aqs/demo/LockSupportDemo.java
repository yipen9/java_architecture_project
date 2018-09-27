package com.yipeng.aqs.demo;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    public static void main(String[] args) {
        test1();
    }

    /**
     * 相对于object的wait和notify来说LockSupport.
     *
     * 总结一下，LockSupport比Object的wait/notify有两大优势：
     * ①LockSupport不需要在同步代码块里 。所以线程间也不需要维护一个共享的同步对象了，实现了线程间的解耦。
     * ②unpark函数可以先于park调用，所以不需要担心线程间的执行的先后顺序。
     *
     *
     * 使用{@link LockSupport#park()}
     *    {@link LockSupport#unpark(Thread)}
     */
    private static void test1() {
        Thread threadA = new Thread() {
            @Override
            public void run() {
                System.out.println("begin run ........");
                LockSupport.park();
                System.out.println("end run ........");
            }
        };

        threadA.start();

        System.out.println("main begin to unlock ... ");
        LockSupport.unpark(threadA);
        System.out.println("main end");
    }





}
