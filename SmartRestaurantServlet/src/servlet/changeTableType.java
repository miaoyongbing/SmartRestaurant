package servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.DAO.TableDao;

/**
 * Servlet implementation class changeTableType
 */
public class changeTableType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changeTableType() {
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
		//http://localhost:8088/SmartRestaurantServlet/changeTableType?table_id=1&table_type=2
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charSet=utf-8");
		String table_idString = request.getParameter("table_id");
		String table_typeString = request.getParameter("table_type");
		int table_id = Integer.parseInt(table_idString);
		int table_type = Integer.parseInt(table_typeString);
		TableDao tableDao = new TableDao();
		PrintStream out=new PrintStream(response.getOutputStream());
		try {	
			if(tableDao.changeTableState(table_id, table_type))
				out.println("true");
			else
				out.println("false");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
