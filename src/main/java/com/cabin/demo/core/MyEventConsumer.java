package com.cabin.demo.core;

@FunctionalInterface
public interface MyEventConsumer {
    public void consume(Object event);
}
