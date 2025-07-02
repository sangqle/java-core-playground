package com.cabin.demo.serialize;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private transient String password; // transient fields are not serialized
    public static final String DEFAULT_PASSWORD = "<PASSWORD>";

    public Person(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getPassword() {
        return password;
    }
}
