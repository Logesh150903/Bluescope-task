package biuescope;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

public class DBOperation {
	
	public int add(String name, float price) throws SQLException{
		Connection conn = ConnectionsJdbc.getConnection();
		String sql="insert into item(name,price)values(?,?)";
		PreparedStatement p = conn.prepareStatement(sql);
		p.setString(1,name);
		p.setFloat(2, price);
		int r = p.executeUpdate();
		conn.close();
		return r;
		
	}
	
	public Stack<Item> view() throws SQLException{
		Connection conn = ConnectionsJdbc.getConnection();
		Stack<Item> st = new Stack<Item>();
		String sql="select * from item";
		PreparedStatement p = conn.prepareStatement(sql);
		ResultSet rs = p.executeQuery();
		while(rs.next()){
			int id = rs.getInt(1);
			String name = rs.getString(2);
			float price = rs.getFloat(3);
			Item item= new Item(id, name, price);
			st.push(item);
		}
		conn.close();
		return st;
	}
}
