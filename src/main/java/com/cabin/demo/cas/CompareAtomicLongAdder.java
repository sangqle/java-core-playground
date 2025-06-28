package com.cabin.demo.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class CompareAtomicLongAdder {
    static AtomicInteger atomicCounter = new AtomicInteger(0);
    static LongAdder longAdderCounter = new LongAdder();

    public static void main(String[] args) {
        int threads = 1000;
        int iterations = 10000;

        long startAtomic = System.nanoTime();
        runTestAtomic(threads, iterations);
        long endAtomic = System.nanoTime();

        long startLongAdder = System.nanoTime();
        runTestLongAdder(threads, iterations);
        long endLongAdder = System.nanoTime();

        System.err.println("AtomicInteger time : " + (endAtomic - startAtomic) / 1_000_000+ "ms");
        System.err.println("LongAdder time : " + (endLongAdder - startLongAdder) / 1_000_000 + "ms");
    }

    private static void runTestAtomic(int threads, int iterations) {
        Thread[] threadArray = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            threadArray[i] = new Thread(() -> {
                for (int j = 0; j < iterations; j++) {
                    atomicCounter.incrementAndGet();
                }
            });
            threadArray[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threadArray) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.err.println("Final AtomicInteger value: " + atomicCounter.get());
    }

    private static void runTestLongAdder(int threads, int iterations) {
        Thread[] threadArray = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            threadArray[i] = new Thread(() -> {
                for (int j = 0; j < iterations; j++) {
                    longAdderCounter.increment();
                }
            });
            threadArray[i].start();
        }
        // Wait for all threads to finish
        for (Thread thread : threadArray) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.err.println("Final LongAdder value: " + longAdderCounter.sum());
    }
}
