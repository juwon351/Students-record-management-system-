import java.util.List;
import java.util.Scanner;

/**
 * Console entry point for the Student Record Management System.
 * Run with: java Main
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManager manager = new StudentManager();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    viewStatistics();
                    break;
                case 7:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== Student Record Management System =====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. View Statistics");
        System.out.println("7. Exit");
        System.out.println("==============================================");
    }

    private static void addStudent() {
        System.out.println("--- Add New Student ---");
        String name = readString("Enter name: ");
        int age = readInt("Enter age: ");
        String course = readString("Enter course: ");
        double gpa = readDouble("Enter GPA: ");
        Student s = manager.addStudent(name, age, course, gpa);
        System.out.println("Student added successfully with ID: " + s.getId());
    }

    private static void viewAllStudents() {
        System.out.println("--- All Students ---");
        List<Student> all = manager.getAllStudents();
        if (all.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student s : all) {
            System.out.println(s);
        }
    }

    private static void searchStudent() {
        System.out.println("--- Search Student ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        int choice = readInt("Enter your choice: ");
        if (choice == 1) {
            int id = readInt("Enter ID: ");
            Student s = manager.findById(id);
            if (s != null) {
                System.out.println(s);
            } else {
                System.out.println("No student found with that ID.");
            }
        } else if (choice == 2) {
            String name = readString("Enter name (or part of it): ");
            List<Student> results = manager.findByName(name);
            if (results.isEmpty()) {
                System.out.println("No matching students found.");
            } else {
                for (Student s : results) {
                    System.out.println(s);
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void updateStudent() {
        System.out.println("--- Update Student ---");
        int id = readInt("Enter ID of student to update: ");
        Student existing = manager.findById(id);
        if (existing == null) {
            System.out.println("No student found with that ID.");
            return;
        }
        System.out.println("Current details -> " + existing);
        String name = readString("Enter new name: ");
        int age = readInt("Enter new age: ");
        String course = readString("Enter new course: ");
        double gpa = readDouble("Enter new GPA: ");
        boolean success = manager.updateStudent(id, name, age, course, gpa);
        System.out.println(success ? "Student updated successfully." : "Update failed.");
    }

    private static void deleteStudent() {
        System.out.println("--- Delete Student ---");
        int id = readInt("Enter ID of student to delete: ");
        boolean success = manager.deleteStudent(id);
        System.out.println(success ? "Student deleted successfully." : "No student found with that ID.");
    }

    private static void viewStatistics() {
        System.out.println("--- Statistics ---");
        System.out.println("Total students: " + manager.getStudentCount());
        System.out.printf("Average GPA: %.2f%n", manager.getAverageGpa());
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
