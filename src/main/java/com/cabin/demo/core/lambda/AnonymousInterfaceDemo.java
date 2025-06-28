package com.cabin.demo.core.lambda;

public class AnonymousInterfaceDemo {
    public static void main(String[] args) {
        MyEventConsumer consumer = new MyEventConsumer() {
            // Using an anonymous class to implement the interface
            private int eventCount = 0;
            public void consume(Object event) {
                eventCount++;
                System.out.println("Event consumed: " + event + ", Total events consumed: " + eventCount);
            }
        };

        // Using a lambda expression for the same interface
        MyEventConsumer consumer1 = (event) -> {
            int eventCount = 0;
            eventCount++;
            System.out.println("Event consumed: " + event + ", Total events consumed: " + eventCount);
        };

        consumer.consume("Hello");
        consumer1.consume("World");
    }
}
