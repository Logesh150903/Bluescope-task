package bluescope;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryToFile {
    public void executeQueryAndWriteToFile(String query, String outputFilePath) {
        try (Connection conn = ConnectionsJdbc.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             FileWriter writer = new FileWriter(outputFilePath)) {

            int columnCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    row.append(rs.getString(i)).append(",");
                }
                writer.write(row.substring(0, row.length() - 1) + "\n");
            }
            System.out.println("Query results saved to " + outputFilePath);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}