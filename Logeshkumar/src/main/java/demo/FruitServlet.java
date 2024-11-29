package demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

//@WebServlet("/fruit")
public class FruitServlet extends HttpServlet {

	private final FruitService fruitService = new FruitService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String result = "";

        if ("add".equals(action)) {
            
                String name = request.getParameter("name");
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                Date date = Date.valueOf(request.getParameter("date"));
			try {
				result = fruitService.add(name, price, quantity, date);
				result = fruitService.view();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
                else if ("update".equals(action)) {
           
                int Id = Integer.parseInt(request.getParameter("id"));
                String Name = request.getParameter("name");
                double Price = Double.parseDouble(request.getParameter("price"));
                int Quantity = Integer.parseInt(request.getParameter("quantity"));
                Date date1 = Date.valueOf(request.getParameter("date"));
			try {
				result = fruitService.update(Id, Name, Price, Quantity, date1);
				result = fruitService.view();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                }
             
                else if ("delete".equals(action)) {
           
                int id = Integer.parseInt(request.getParameter("id"));
			try {
				result = fruitService.delete(id);
				result = fruitService.view();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                }
 

                else {
                result = "Invalid action.";
        }


        response.setContentType("text/html");
        response.getWriter().write(result);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String result = "";

		if ("view".equals(action)) {
			try {
				result = fruitService.view();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("search".equals(action)) {
			String select = request.getParameter("select");
			String where = request.getParameter("where");
			try {
				result = fruitService.viewBywhere(select, where);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			result = "Invalid action.";
		}

		response.setContentType("text/html");
		response.getWriter().write(result);
	}
}
