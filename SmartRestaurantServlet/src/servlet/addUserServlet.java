package servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.DAO.UserDAO;
import com.smart.bean.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class addUserServlet
 */
public class addUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addUserServlet() {
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
		//http://localhost:8088/SmartRestaurantServlet/addUserServlet?user={"user_name":"admin","password":"admin","job":"11"}
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charSet=utf-8");
		String userString = request.getParameter("user");
		System.out.println(userString);
		JSONObject jsonObject=JSONObject.fromObject(userString);
		User user=(User) JSONObject.toBean(jsonObject, User.class);
		@SuppressWarnings({ "deprecation", "unchecked" })
		UserDAO userDAO = new UserDAO();
		PrintStream out=new PrintStream(response.getOutputStream());
		out.println(userDAO.Add(user));
	}

}
