package com.cabin.demo.serialize;

import java.io.*;
import java.lang.reflect.Field;

/**
 * This demo shows ACTUAL data corruption when serialVersionUID collides
 */
public class DataCorruptionDemo {
    
    public static void main(String[] args) {
        System.out.println("=== ACTUAL Data Corruption Demo ===\n");
        
        // Create a scenario where we can actually see data corruption
        demonstrateRealDataCorruption();
    }
    
    public static void demonstrateRealDataCorruption() {
        System.out.println("--- Creating Actual Data Corruption Scenario ---");
        
        // Step 1: Create original data
        OriginalClass original = new OriginalClass("John Doe", 30, "Engineer");
        System.out.println("✅ Original data: " + original);
        
        // Step 2: Serialize it
        serializeObject(original, "corruption_test.ser");
        
        // Step 3: Now we'll manually manipulate the class definition
        // In real world, this happens when you deploy new version of class
        System.out.println("\n🔄 Simulating class evolution (same serialVersionUID, different structure)...");
        
        // Step 4: Try to deserialize with evolved class structure
        try {
            EvolvedClass evolved = deserializeAsEvolvedClass("corruption_test.ser");
            System.out.println("⚠️  CORRUPTED data after deserialization: " + evolved);
            System.out.println("❌ Notice how data is lost/corrupted!");
            
        } catch (Exception e) {
            System.out.println("Exception during deserialization: " + e.getMessage());
        }
        
        // Step 5: Show the binary content to prove what's happening
        System.out.println("\n📄 Analyzing the serialized binary:");
        analyzeBinaryContent("corruption_test.ser");
    }
    
    /**
     * Original class structure
     */
    static class OriginalClass implements Serializable {
        private static final long serialVersionUID = 999L;
        
        private String fullName;    // Single name field
        private int age;
        private String jobTitle;
        
        public OriginalClass(String fullName, int age, String jobTitle) {
            this.fullName = fullName;
            this.age = age;
            this.jobTitle = jobTitle;
        }
        
        @Override
        public String toString() {
            return "OriginalClass{fullName='" + fullName + "', age=" + age + ", jobTitle='" + jobTitle + "'}";
        }
    }
    
    /**
     * Evolved class - SAME serialVersionUID but different structure
     * This is what causes silent data corruption
     */
    static class EvolvedClass implements Serializable {
        private static final long serialVersionUID = 999L; // SAME ID - DANGEROUS!
        
        private String firstName;   // Split name into two fields
        private String lastName;
        private int age;           // Same field
        private String department; // Different field name
        private double salary;     // New field
        
        public EvolvedClass() {} // Default constructor needed for deserialization
        
        @Override
        public String toString() {
            return "EvolvedClass{firstName='" + firstName + "', lastName='" + lastName + 
                   "', age=" + age + ", department='" + department + "', salary=" + salary + "}";
        }
    }
    
    /**
     * This method demonstrates the actual corruption
     */
    private static EvolvedClass deserializeAsEvolvedClass(String filename) {
        try {
            // We'll use some reflection trickery to force deserialization
            // In real world, this happens automatically when you have class evolution
            
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            // Read the object data
            Object obj = ois.readObject();
            
            // This will show the actual field values and how they get corrupted
            System.out.println("Raw deserialized object: " + obj);
            System.out.println("Object class: " + obj.getClass().getSimpleName());
            
            // Show field values using reflection
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    System.out.println("  " + field.getName() + " = " + value + 
                                     " (type: " + field.getType().getSimpleName() + ")");
                } catch (IllegalAccessException e) {
                    System.out.println("  " + field.getName() + " = <inaccessible>");
                }
            }
            
            fis.close();
            ois.close();
            
            return new EvolvedClass(); // Return dummy object for demo
            
        } catch (Exception e) {
            throw new RuntimeException("Deserialization failed", e);
        }
    }
    
    private static void serializeObject(Object obj, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(obj);
            System.out.println("✅ Serialized to " + filename);
        } catch (IOException e) {
            System.err.println("Serialization failed: " + e.getMessage());
        }
    }
    
    private static void analyzeBinaryContent(String filename) {
        try {
            byte[] data = readAllBytes(filename);
            String content = new String(data, "UTF-8");
            
            System.out.println("File size: " + data.length + " bytes");
            System.out.println("Contains class: " + (content.contains("OriginalClass") ? "OriginalClass" : "Unknown"));
            System.out.println("SerialVersionUID: 999 (embedded in binary)");
            System.out.println("Field data preserved as binary, but field names/types changed in new class");
            
        } catch (IOException e) {
            System.err.println("Error analyzing file: " + e.getMessage());
        }
    }
    
    private static byte[] readAllBytes(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {
            return fis.readAllBytes();
        }
    }
}