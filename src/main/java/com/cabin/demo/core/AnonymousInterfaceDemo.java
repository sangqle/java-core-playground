package com.cabin.demo.core;

public class AnonymousInterfaceDemo {
    public static void main(String[] args) {
        MyEventConsumer consumer = new MyEventConsumer() {
            private int eventCount = 0;
            public void consume(Object event) {
                eventCount++;
                System.out.println("Event consumed: " + event + ", Total events consumed: " + eventCount);
            }
        };

        MyEventConsumer consumer1 = (event) -> {
            int eventCount = 0;
            eventCount++;
            System.out.println("Event consumed: " + event + ", Total events consumed: " + eventCount);
        };

        consumer.consume("Hello");
        consumer1.consume("World");
    }
}
