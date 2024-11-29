package bluescope;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVToDatabase {
    private final Employee Employee;

    public CSVToDatabase() {
    	Employee = new Employee();
    }

    public void loadCSVToDatabase(String csvFilePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            while ((line = br.readLine()) != null) {
                // Split the line by commas to get individual columns
                String[] data = line.split(",");
                
                if (data.length < 4) {
                    // Skip lines with insufficient data (e.g., missing fields)
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                try {
                    // Check if the ID and Age fields are valid numbers
                    int id = parseInteger(data[0]); // Parse the ID (first column)
                    String name = data[1]; // Name is a string, so no parsing needed
                    int age = parseInteger(data[2]); // Parse the age (third column)
                    String department = data[3]; // Department is a string

                    // Insert the valid employee data into the database
                    Employee.insertEmployee(id, name, age, department);
                } catch (NumberFormatException e) {
                    // If there's an invalid number format, print the error and skip this line
                    System.out.println("Skipping line with invalid number format: " + line);
                }
            }
            System.out.println("CSV data loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to safely parse integers and handle invalid or empty values
    private int parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            // You can choose to return a default value (e.g., 0) or throw an exception if empty
            System.out.println("Empty or invalid integer value encountered, defaulting to 0");
            return 0;  // Default value when the field is empty or invalid
        }

        try {
            return Integer.parseInt(value);  // Try parsing the integer
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for value: " + value);
            throw e;  // Re-throw exception after logging the error
            
        }
    }
}