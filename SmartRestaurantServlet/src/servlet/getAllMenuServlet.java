package servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.DAO.MenuDao;
import com.smart.bean.Menu;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class getAllMenuServlet
 */
public class getAllMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getAllMenuServlet() {
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

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charSet=utf-8");
		PrintStream out=new PrintStream(response.getOutputStream());

		MenuDao menuDao = new MenuDao();
		List<Menu> menu_list = new ArrayList<>();
		try {
			ResultSet rSet = menuDao.getAllMenu();
			while(rSet.next()){
				Menu menu = new Menu();
				menu.setFood_id(rSet.getInt("food_id"));
				menu.setFood_name(rSet.getString("food_name"));
				menu.setFood_picture(rSet.getString("food_picture"));
				menu.setFood_price(rSet.getInt("food_price"));
				menu.setIs_available(rSet.getInt("is_available"));
				menu_list.add(menu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jsonArray = JSONArray.fromObject(menu_list);
		out.println(jsonArray);

	}

}
