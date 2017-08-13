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

import com.smart.DAO.TableDao;
import com.smart.bean.Tables;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class getAllTableServlet
 */
public class getAllTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getAllTableServlet() {
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
		List<Tables> tables = new ArrayList<>();
		TableDao tableDao = new TableDao();
		try {
			ResultSet rSet = tableDao.getAlltable();
			while(rSet.next()){
				Tables tableItem = new Tables();
				tableItem.setTable_id(rSet.getInt("table_id"));
				tableItem.setTable_num(rSet.getInt("table_number"));
				tableItem.setTable_type(rSet.getInt("table_type"));
				tables.add(tableItem);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jsonArray =JSONArray.fromObject(tables);
		PrintStream out=new PrintStream(response.getOutputStream());
		out.println(jsonArray.toString());
	}

}
