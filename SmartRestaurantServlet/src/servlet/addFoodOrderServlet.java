package servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.DAO.OrderDAO;
import com.smart.bean.OrderItem;
import com.smart.bean.Orders;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class addFoodOrderServlet
 */
public class addFoodOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addFoodOrderServlet() {
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
		
		//http://localhost:8088/SmartRestaurantServlet/foodOrderAddServlet?order=[{"order_time":"2017-04-15 14:26:25","order_state":"1","total_price":"100","table_id":"2"}]&order_item=[{"food_id":"1","food_num":"4","food_state":"0"},{"food_id":"2","food_num":"4","food_state":"1"}]
		response.setCharacterEncoding("utf8");
		String order=request.getParameter("order");	
		String order_item=request.getParameter("order_item");
		System.out.println(order);
		System.out.println(order_item==null?" 11 ":order_item);
		JSONArray jsonObject=JSONArray.fromObject(order);
		JSONArray jsonArray=JSONArray.fromObject(order_item);
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<OrderItem>list=(List<OrderItem>) JSONArray.toList(jsonArray, OrderItem.class);
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<Orders>list_order=(List<Orders>) JSONArray.toList(jsonObject, Orders.class);
		Orders orders=list_order.get(0);
		OrderDAO orderDAO=new OrderDAO();
		int order_id = -1;
		PrintStream out=new PrintStream(response.getOutputStream());
		try {
			order_id = orderDAO.addOrder(orders);
			System.out.println(order_id);
			for (OrderItem orderitem : list) {
				orderitem.setOrder_id(order_id);
			}
			orderDAO.addOrderItem(list);
			out.println(order_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
