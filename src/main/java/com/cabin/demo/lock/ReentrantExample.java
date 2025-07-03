package com.cabin.demo.lock;

public class ReentrantExample {
    public void outer() throws InterruptedException {
        System.err.println("Entered outer()");
        inner();
    }

    public void inner() throws InterruptedException {
        // Await 1000ms
        Thread.sleep(4000);
        System.err.println("Inside inner()");
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantExample re = new ReentrantExample();
        re.outer();
    }
}
