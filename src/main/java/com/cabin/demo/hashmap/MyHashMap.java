package com.cabin.demo.hashmap;

import java.util.Objects;

public class MyHashMap<K, V> {
    private static class Node<K, V> {
        final K key;
        V value;
        final int hash;
        Node<K, V> next;

        Node(K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private Node<K, V>[] table;

    public MyHashMap() {
        table = new Node[DEFAULT_CAPACITY];
    }

    private int hash(K key) {
        int h = key.hashCode();
        return h ^ (h >>> 16); // spreading
    }

    // Calculate the bucket
    private int indexFor(int hash) {
        return hash & (table.length - 1);
    }

    public void put(K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash);

        Node<K, V> head = table[index];
        Node<K, V> current = head;

        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        // insert new node at the beginning
        Node<K, V> newNode = new Node<>(key, value, hash);
        newNode.next = head;
        table[index] = newNode;
    }

    public V get(K key) {
        int hash = hash(key);
        int index = indexFor(hash);

        Node<K, V> current = table[index];
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }
}
