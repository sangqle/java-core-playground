package com.cabin.demo.core.lambda;

@FunctionalInterface
public interface MyEventConsumer {
    public void consume(Object event);
}
