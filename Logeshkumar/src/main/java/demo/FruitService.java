package demo;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FruitService {
    
    public String add(String name, double price, int quantity, Date date) throws SQLException {
    	Connection conn = DBConnection.getConnection();
    	PreparedStatement ps=null;
        try {
            String sql = "INSERT INTO fruits (name, price, quantity, date) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);
            ps.setDate(4, date);
            int rows = ps.executeUpdate();
            return rows > 0 ? "Fruit added successfully!" : "Failed to add fruit.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        finally {
			ps.close();
			conn.close();
		}
    }

    public String update(int id, String name, double price, int quantity, Date date) throws SQLException {
    	Connection conn = DBConnection.getConnection();
    	PreparedStatement ps=null;
        try  {
            String sql = "UPDATE fruits SET name = ?, price = ?, quantity=?, date=? WHERE id = ?";
             ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);
            ps.setDate(4, date);
            ps.setInt(5, id);
            int rows = ps.executeUpdate();
            return rows > 0 ? "Fruit updated successfully!" : "Failed to update fruit.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        finally {
			ps.close();
			conn.close();
		}
    }

    public String view() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM fruits";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            StringBuilder r = new StringBuilder();
            r.append("<table border='1' style='width:100%; text-align:center;'>")
             .append("<tr>")
             .append("<th>ID</th>")
             .append("<th>Name</th>")
             .append("<th>Price</th>")
             .append("<th>Quantity</th>")
             .append("<th>Date</th>")
             .append("<th>Actions</th>")
             .append("</tr>");
            while (rs.next()) {
                int id = rs.getInt("id");
                r.append("<tr>")
                 .append("<td>").append(id).append("</td>")
                 .append("<td>").append(rs.getString("name")).append("</td>")
                 .append("<td>").append(rs.getDouble("price")).append("</td>")
                 .append("<td>").append(rs.getInt("quantity")).append("</td>")
                 .append("<td>").append(rs.getDate("date")).append("</td>")
                 .append("<td>")
                 .append("<form action='Update.html'  style='display:inline;'>")
                 .append("<button type='submit'>Update</button>")
                 .append("</form>")
                 .append(" ")
                 .append("<form action='Delete' method='post' style='display:inline;'>")
                 .append("<input type='hidden' name='action' value='delete'>")
                 .append("<input type='hidden' name='id' value='").append(id).append("'>")
                 .append("<button type='submit'>Delete</button>")
                 .append("</form>")
                 .append("</td>")
                 .append("</tr>");
                
            }
            r.append("</table>")
            .append("<form action='Fruit.html' style='display:inline;'>")
            .append("<button type='submit'>Home</button>")
            .append("</form>")
            .append("<br> ")
            .append("<form action='Add.html' style='display:inline;'>")
            .append("<button type='submit'>Add</button>")
            .append("</form>");
            return r.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        } finally {
         	rs.close();
         	ps.close();
 			conn.close();
        }
    }

    public String viewBywhere(String select, String where) throws SQLException {
    	Connection conn=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	 try  {
    		 System.out.println(select);
    		 System.out.println(where);
             String sql = "SELECT * FROM fruits WHERE ?=?";
//             String sql = "SELECT * FROM fruits WHERE "+select+"='"+where+"'";
             ps= conn.prepareStatement(sql);
             ps.setString(1, select);
             ps.setString(2, where);
             System.out.println(sql);
             rs = ps.executeQuery();
             System.out.println(sql);
    //        System.out.println(rs.next());
             StringBuilder r = new StringBuilder();
             while (rs.next()){
            	 System.out.println(rs.getInt("id"));
            	 System.out.println(rs.getString("name"));
            	 System.out.println(rs.getDouble("price"));
            	 System.out.println(rs.getInt("quantity"));
            	 System.out.println(rs.getDate("date"));
                 r.append("ID: ").append(rs.getInt("id"))
                       .append(", Name: ").append(rs.getString("name"))
                       .append(", Price: ").append(rs.getDouble("price"))
                       .append(", quantity: ").append(rs.getInt("quantity"))
                       .append(", date: ").append(rs.getDate("date"))
                       .append("<br>");
             } 
             return r.toString();
         } catch (SQLException e) {
             e.printStackTrace();
             return "Error: " + e.getMessage();
         }
    	 finally {
         	rs.close();
         	ps.close();
 			conn.close();
 		}
    }
    public String delete(int id) throws SQLException {
    	Connection conn = DBConnection.getConnection();
    	PreparedStatement ps=null;
        try  {
            String sql = "DELETE FROM fruits WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0 ? "Fruit deleted successfully!" : "Failed to delete fruit.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        finally {
			ps.close();
			conn.close();
		}
    }
}
