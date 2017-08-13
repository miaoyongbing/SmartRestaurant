package servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.DAO.OrderDAO;
import com.smart.bean.OrderItem;
import com.smart.bean.Orders;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class getAllOrderServlet
 */
public class getAllOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getAllOrderServlet() {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charSet=utf-8");
		PrintStream out=new PrintStream(response.getOutputStream());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

		String table_idString = request.getParameter("table_id");
		System.out.println(table_idString);
		int table_id = Integer.parseInt(table_idString);
		System.out.println(table_id);
		OrderDAO orderDAO = new OrderDAO();
		List<OrderItem> orderItem_list = new ArrayList<>();
		try {
			ResultSet rSet = orderDAO.getAllOrderAndOrderItemForTableId(table_id);

			Orders order = new Orders();
			while(rSet.next()){

				order.setOrder_id(rSet.getInt("order_id"));
				order.setOrder_state(rSet.getInt("order_state"));
				order.setOrder_time(sdf.format(rSet.getDate("order_time")));
				order.setTable_id(rSet.getInt("table_id"));
				order.setTotal_price(rSet.getInt("total_price"));

				OrderItem orderItem = new OrderItem();				
				orderItem.setFood_id(rSet.getInt("food_id"));
				orderItem.setFood_num(rSet.getInt("food_num"));
				orderItem.setFood_state(rSet.getInt("food_state"));
				orderItem.setOrder_id(rSet.getInt("order_id"));
				orderItem_list.add(orderItem);
			}
			JSONArray orderJson = JSONArray.fromObject(order);
			JSONArray orderItemJson = JSONArray.fromObject(orderItem_list);
			Map<String, String> map=new HashMap<>();	
			map.put("order", orderJson.toString());
			map.put("orderItem", orderItemJson.toString());
			JSONObject jsonObject = JSONObject.fromObject(map);
			out.println(jsonObject.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
