package studentmanagementsystem;

import java.io.*;
import java.util.*;

public class StudentManagement {
    private static final String FILE_NAME = "students.dat";
    private Map<String, Student> studentMap = new HashMap<>();

    public StudentManagement() {
        loadFromFile();
    }

    public void addStudent(Student student) {
        if (studentMap.containsKey(student.getRollNumber())) {
            System.out.println("Student with this roll number already exists.");
        } else {
            studentMap.put(student.getRollNumber(), student);
            System.out.println("Student added successfully.");
        }
    }

    public void removeStudent(String rollNumber) {
        if (studentMap.remove(rollNumber) != null) {
            System.out.println("Student removed.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void searchStudent(String rollNumber) {
        Student student = studentMap.get(rollNumber);
        if (student != null) {
            System.out.println("Found: " + student);
        } else {
            System.out.println("Student not found.");
        }
    }

    public void editStudent(String rollNumber, String newName, String newGrade) {
        Student student = studentMap.get(rollNumber);
        if (student != null) {
            student.setName(newName);
            student.setGrade(newGrade);
            System.out.println("Student updated.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void displayAllStudents() {
        if (studentMap.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Student List:");
            for (Student s : studentMap.values()) {
                System.out.println("• " + s);
            }
        }
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(studentMap);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                Map<?, ?> rawMap = (Map<?, ?>) obj;
                Map<String, Student> tempMap = new HashMap<>();
                for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if (key instanceof String && value instanceof Student) {
                        tempMap.put((String) key, (Student) value);
                    }
                }
                studentMap = tempMap;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }
}
