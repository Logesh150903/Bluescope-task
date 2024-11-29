package bluescope;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee {
	private int id;
	private String name;
	private int age;
	private String department;

	public Employee(int id, String name, int age, String department) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.department = department;
	}

	public Employee(String name, int age, String department) {
		this.name = name;
		this.age = age;
		this.department = department;
	}

	public Employee() {
		// TODO Auto-generated constructor stub
	}

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

	// Method to add a new employee to the database
	public void addEmployee() {
		String sql = "INSERT INTO employee (name, age, department) VALUES (?, ?, ?)";
		try (Connection conn = ConnectionsJdbc.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, name);
			stmt.setInt(2, age);
			stmt.setString(3, department);
			stmt.executeUpdate();
			System.out.println("Employee added successfully.");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method to retrieve details of all employee
	public static String getAllEmployees() {
		String sql = "{CALL GetAllEmployees()}"; // Call to stored procedure
		try (Connection conn = ConnectionsJdbc.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.print("Employee [id= ");
				System.out.print(rs.getInt(1));
				System.out.print(", name= ");
				System.out.print(rs.getString(2));
				System.out.print(", age= ");
				System.out.print(rs.getInt(3));
				System.out.print(", department= ");
				System.out.print(rs.getString(4));
				System.out.print("]");
				System.out.println();
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "All Data Retrive";
	}

	// Method to retrieve employee details by ID
	public static Employee getEmployeeById(int id) {
		String sql = "SELECT * FROM employee WHERE id = ?";
		try (Connection conn = ConnectionsJdbc.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			System.out.println("");
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new Employee(rs.getInt("id"), rs.getString("name"), rs.getInt("age"),
						rs.getString("department"));
			} else {
				System.out.println("Employee ID Mismatch");
			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Method to update employee details
	public void updateEmployee() {
		String sql = "UPDATE employee SET name = ?, age = ?, department = ? WHERE id = ?";
		try (Connection conn = ConnectionsJdbc.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, name);
			stmt.setInt(2, age);
			stmt.setString(3, department);
			stmt.setInt(4, id);
			stmt.executeUpdate();
			System.out.println("Employee updated successfully.");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method to delete an employee
	public static void deleteEmployee(int id) {
		String sql = "DELETE FROM employee WHERE id = ?";
		try (Connection conn = ConnectionsJdbc.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			System.out.println("Employee deleted successfully.");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Getters for the employee details
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getDepartment() {
		return department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", department=" + department + "]";
	}
}