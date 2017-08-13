package com.smart.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class MenuDao extends BaseDAO{

  
	private static List<String>list=new ArrayList<>();
	static{
		list.add("food_id");
		list.add("food_price");
		list.add("food_picture");
		list.add("is_available");
		list.add("food_name");
	}

	public ResultSet getAllMenu() throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement) buildSql("select * from menu");
		ResultSet rSet=preparedStatement.executeQuery();
		return rSet;
	}
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
            MenuDao menuDao=new MenuDao();
            ResultSet rSet=menuDao.getAllMenu();
            int count=rSet.getMetaData().getColumnCount();
            while(rSet.next()){
            	for(int i=0;i<count;i++){
            		System.out.print(list.get(i)+"对应："+rSet.getObject(i+1)+ "  ");
            	}
            	System.out.println();
            }
	}

}
