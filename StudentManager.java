import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the in-memory list of students and keeps it synced
 * with a plain text file (students.txt) for persistence between runs.
 */
public class StudentManager {
    private static final String FILE_NAME = "students.txt";

    private List<Student> students;
    private int nextId;

    public StudentManager() {
        students = new ArrayList<>();
        loadFromFile();
        nextId = computeNextId();
    }

    private int computeNextId() {
        int max = 0;
        for (Student s : students) {
            if (s.getId() > max) {
                max = s.getId();
            }
        }
        return max + 1;
    }

    public Student addStudent(String name, int age, String course, double gpa) {
        Student s = new Student(nextId, name, age, course, gpa);
        nextId++;
        students.add(s);
        saveToFile();
        return s;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student findById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public List<Student> findByName(String name) {
        List<Student> results = new ArrayList<>();
        String search = name.toLowerCase();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(search)) {
                results.add(s);
            }
        }
        return results;
    }

    public boolean updateStudent(int id, String name, int age, String course, double gpa) {
        Student s = findById(id);
        if (s == null) {
            return false;
        }
        s.setName(name);
        s.setAge(age);
        s.setCourse(course);
        s.setGpa(gpa);
        saveToFile();
        return true;
    }

    public boolean deleteStudent(int id) {
        Student s = findById(id);
        if (s == null) {
            return false;
        }
        students.remove(s);
        saveToFile();
        return true;
    }

    public int getStudentCount() {
        return students.size();
    }

    public double getAverageGpa() {
        if (students.isEmpty()) {
            return 0.0;
        }
        double total = 0;
        for (Student s : students) {
            total += s.getGpa();
        }
        return total / students.size();
    }

    /** Writes the full current list of students to disk, overwriting the file. */
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.println(s.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    /** Loads students from disk on startup. Silently does nothing if the file doesn't exist yet. */
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    students.add(Student.fromFileString(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}
