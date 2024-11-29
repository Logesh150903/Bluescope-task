package bluescope;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseOperations {
    public void insertEmployee(int id, String name, int age, String department) {
        String insertQuery = "INSERT INTO Employee (id, name, age, department) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionsJdbc.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.setString(4, department);
            pstmt.executeUpdate();
            System.out.println("Employee inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}