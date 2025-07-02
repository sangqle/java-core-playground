package com.cabin.demo.serialize;

import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 2L;
    private String name;
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter, setters
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
}
