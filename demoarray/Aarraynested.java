package demoarray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

class Mark {
    private List<Integer> marks;

    public Mark(List<Integer> marks) {
        this.marks = marks;
    }

    public List<Integer> getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < marks.size(); i++) {
            sb.append("Mark ").append(i + 1).append(" = ").append(marks.get(i));
            if (i < marks.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }
}

class Semester {
    private Map<String, Mark> semesterMarks;

    public Semester() {
        this.semesterMarks = new LinkedHashMap<>();
    }

    public void addSemester(String semesterName, Mark marks) {
        semesterMarks.put(semesterName, marks);
    }

    public Map<String, Mark> getSemesterMarks() {
        return semesterMarks;
    }

    @Override
    public String toString() {
        return semesterMarks.toString();
    }
}

class Student {
    private String name;
    private String gender;
    private Semester semesterDetails;

    public Student(String name, String gender) {
        this.name = name;
        this.gender = gender;
        this.semesterDetails = new Semester();
    }

    public void addSemesterMarks(String semesterName, Mark marks) {
        this.semesterDetails.addSemester(semesterName, marks);
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Semester getSemesterDetails() {
        return semesterDetails;
    }

    @Override
    public String toString() {
        return "{ Name: " + name + ", Gender: " + gender + ", Semesters: " + semesterDetails + " }";
    }
}

public class Aarraynested {
    private static final String JSON_FILE = "students.json";

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a new student");
            System.out.println("2. Display all student details");
            System.out.println("3. Sort students by name");
            System.out.println("4. Filter and display a student by name");
            System.out.println("5. View marks semester-wise for a student");
            System.out.println("6. Remove a student by name");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = 0;
            try {
            choice = Integer.parseInt(s.nextLine());
            }catch (NumberFormatException e) {
                System.err.println("Error: Options must be numeric. Please try again.");
            }

            List<Student> students = loadDataFromJsonFile();

            switch (choice) {
                case 1:
                    addStudent(s, students);
                    writeDataToJsonFile(students);
                    break;
                case 2:
                    System.out.println("\nAll Student Details:");
                    students.forEach(System.out::println);
                    break;
                case 3:
                    students.sort(Comparator.comparing(Student::getName));
                    System.out.println("\nSorted Students by Name:");
                    students.forEach(System.out::println);
                    break;
                case 4:
                    System.out.print("\nEnter the student name to filter details: ");
                    String studentName = s.nextLine();
                    students.stream()
                            .filter(student -> student.getName().equalsIgnoreCase(studentName))
                            .forEach(System.out::println);
                    break;
                case 5:
                    System.out.print("\nEnter the student name to view marks semester-wise: ");
                    String marksStudentName = s.nextLine();
                    students.stream()
                            .filter(student -> student.getName().equalsIgnoreCase(marksStudentName))
                            .forEach(student -> {
                                System.out.println("Marks for " + student.getName() + ":");
                                student.getSemesterDetails().getSemesterMarks()
                                        .forEach((sem, marks) -> System.out.println(sem + ": " + marks));
                            });
                    break;
                case 6:
                    removeStudent(s, students);
                    break;
                case 7:
                    System.out.println("Exiting the program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addStudent(Scanner s, List<Student> students) {
        System.out.println("Enter details for the new student:");
        System.out.print("Name: ");
        String name = s.nextLine();
        System.out.print("Gender: ");
        String gender = s.nextLine();

        Student student = new Student(name, gender);

        System.out.print("Enter number of semesters for " + name + ": ");
        int numSemesters = 0;
        try {
        numSemesters = Integer.parseInt(s.nextLine());
        }catch (NumberFormatException e) {
            System.err.println("Error: numSemesters must be numeric. Please try again.");
        }
        for (int sem = 1; sem <= numSemesters; sem++) {
            while (true) {
                System.out.print("Enter marks for 5 subjects for Semester " + sem + " (space-separated): ");
                String[] marksInput = s.nextLine().split(" ");
                if (marksInput.length != 5) {
                    System.err.println("Error: You must enter exactly 5 marks.");
                    continue;
                }
                try {
                    List<Integer> marks = new ArrayList<>();
                    for (String mark : marksInput) {
                        marks.add(Integer.parseInt(mark));
                    }
                    student.addSemesterMarks("Semester " + sem, new Mark(marks));
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Marks must be numeric. Please try again.");
                }
            }
        }
        students.add(student);
    }

    private static void removeStudent(Scanner s, List<Student> students) {
        System.out.print("Enter the name of the student to remove: ");
        String name = s.nextLine();
        List<Student> matchingStudents = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getName().equalsIgnoreCase(name)) {
                matchingStudents.add(students.get(i));
            }
        }
        if (matchingStudents.isEmpty()) {
            System.out.println("No students found with the name " + name);
            return;
        }
        for (int i = 0; i < matchingStudents.size(); i++) {
            System.out.println("Student " + (i + 1) + ": " + matchingStudents.get(i));
        }
        int studentIndex=0;
        while(true) {
        System.out.print("Enter the number of the student to remove: ");
        try {
        studentIndex = Integer.parseInt(s.nextLine()) - 1;
        }catch (NumberFormatException e) {
            System.out.println("Error: Marks must be numeric. Please try again.");
            continue;
        }
        if (studentIndex < 0 || studentIndex >= matchingStudents.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        students.remove(matchingStudents.get(studentIndex));
        writeDataToJsonFile(students);
        System.out.println("Student removed successfully.");
        break;
        }
    }

    private static List<Student> loadDataFromJsonFile() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(JSON_FILE)) {
            Type studentListType = new TypeToken<List<Student>>() {}.getType();
            return gson.fromJson(reader, studentListType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static void writeDataToJsonFile(List<Student> students) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(students, writer);
            System.out.println("\nStudent data has been successfully written to '" + JSON_FILE + "'");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}