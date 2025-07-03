package com.cabin.demo.lock;


/**
 * Thread-A starts and enters outerMethod, acquiring the intrinsic lock on demo object
 * It then calls innerMethod() (also synchronized), but since it's the same thread, it does not block(reentrancy)
 * Thread-B starts a bit later and tries to entry outerMethod, but it's blocked until Thread-A finishes both methods and releases the lock
 */
public class LockDemo {

    public synchronized void outerMethod() {
        System.out.println(Thread.currentThread().getName() + " acquired lock in outerMethod");
        sleep(1000);  // Simulate work

        // Reentrant call
        innerMethod(); // also synchronized on the same object
    }

    public synchronized void innerMethod() {
        System.out.println(Thread.currentThread().getName() + " entered innerMethod (reentrant)");
        sleep(1000);
    }

    public void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        LockDemo demo = new LockDemo();

        Runnable task = demo::outerMethod;

        Thread threadA = new Thread(task, "Thread-A");
        Thread threadB = new Thread(task, "Thread-B");

        threadA.start();
        sleepStatic(200);
        threadB.start();
    }

    public static void sleepStatic(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
}
