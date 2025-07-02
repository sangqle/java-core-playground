package com.cabin.demo.serialize;

import java.io.*;

public class SerializationExample {
    public static void main(String[] args) {

        System.out.println("=== serialVersionUID Collision Demonstrations ===\n");
        // Demo 1: Same package, different classes, same serialVersionUID
        demo1_SamePackageCollision();

        // Demo 2: Class evolution with same serialVersionUID
        demo2_ClassEvolutionProblem();

        // Demo 3: Cross-deserialization attempt
        demo3_CrossDeserializationAttempt();

    }

    /**
     * Demo 1: Your Person and Employee both have serialVersionUID = 1L
     * This is generally safe because they have different class names
     */
    public static void demo1_SamePackageCollision() {
        System.out.println("--- Demo 1: Same Package, Different Classes, Same serialVersionUID ---");

        // Serialize Person
        Person person = new Person("Alice", 30, "secret123");
        serializeObject(person, "person_demo1.ser");

        // Serialize Employee
        Employee employee = new Employee("Bob", 25);
        serializeObject(employee, "employee_demo1.ser");

        // Deserialize normally - this works fine
        Person deserializedPerson = (Person) deserializeObject("person_demo1.ser");
        Employee deserializedEmployee = (Employee) deserializeObject("employee_demo1.ser");

        System.out.println("✅ Normal deserialization works:");
        System.out.println("Person: " + deserializedPerson.getName() + ", " + deserializedPerson.getAge());
        System.out.println("Employee: " + deserializedEmployee.getName() + ", " + deserializedEmployee.getAge());
        System.out.println();
    }

    /**
     * Demo 2: Simulate class evolution problem
     * Same class name, same serialVersionUID, but different structure
     */
    public static void demo2_ClassEvolutionProblem() {
        System.out.println("--- Demo 2: Class Evolution Problem ---");

        // First, serialize current Person
        Person person = new Person("Charlie", 35, "password123");
        serializeObject(person, "person_evolution.ser");

        System.out.println("✅ Serialized Person v1: " + person.getName() + ", " + person.getAge());

        // Now imagine Person class structure changed but kept same serialVersionUID
        // We'll simulate this by trying to deserialize into a modified structure
        // (In real scenario, you'd change the class and recompile)

        System.out.println("💡 In real scenario, if Person class structure changed but kept serialVersionUID = 1L:");
        System.out.println("   - Fields might get default values");
        System.out.println("   - Data could be silently lost");
        System.out.println("   - No InvalidClassException thrown");
        System.out.println();
    }

    /**
     * Demo 3: Try to deserialize Person data as Employee
     * This should fail with exception since they're different classes
     */
    public static void demo3_CrossDeserializationAttempt() {
        System.out.println("--- Demo 3: Cross-Deserialization Attempt ---");

        // Serialize Person
        Person person = new Person("David", 40, "mypassword");
        serializeObject(person, "person_cross.ser");

        try {
            // Try to deserialize Person data as Employee - this should fail
            System.out.println("Attempting to deserialize Person data as Employee...");
            Employee fakeEmployee = (Employee) deserializeObject("person_cross.ser");
            System.out.println("❌ This shouldn't succeed!");
        } catch (Exception e) {
            System.out.println("✅ Expected failure: " + e.getClass().getSimpleName());
            System.out.println("   Reason: Class mismatch detected despite same serialVersionUID");
        }
        System.out.println();
    }



    // Helper methods
    private static void serializeObject(Object obj, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.err.println("Serialization failed: " + e.getMessage());
        }
    }

    private static Object deserializeObject(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Deserialization failed", e);
        }
    }
}
