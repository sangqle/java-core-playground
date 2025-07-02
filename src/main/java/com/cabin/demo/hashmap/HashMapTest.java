package com.cabin.demo.hashmap;

public class HashMapTest {
    public static void main(String[] args) {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("Alice", "123");
        map.put("Bob", "456");
        map.put("Charlie", "789");
        map.put("David", "012");

        System.err.println("Alice: " + map.get("Alice"));
        System.err.println("Bob: " + map.get("Bob"));
        System.err.println("Charlie: " + map.get("Charlie"));
        System.err.println("David: " + map.get("David"));
    }
}
