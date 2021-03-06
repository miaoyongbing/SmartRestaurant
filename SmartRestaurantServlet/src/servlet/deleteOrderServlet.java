package servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.DAO.OrderDAO;

/**
 * Servlet implementation class deleteOrderServlet
 */
public class deleteOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		//http://localhost:8088/SmartRestaurantServlet/deleteOrderServlet?order_id=22
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charSet=utf-8");
		PrintStream out=new PrintStream(response.getOutputStream());
		String order_idString = request.getParameter("order_id");
		int order_id = Integer.parseInt(order_idString);
		
		OrderDAO orderDAO = new OrderDAO();
		try {
			orderDAO.deleteOrderItem(order_id);
			if(orderDAO.deleteOrderByOrder_id(order_id)>0)
				out.println("true");
			else 
				out.println("false");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
