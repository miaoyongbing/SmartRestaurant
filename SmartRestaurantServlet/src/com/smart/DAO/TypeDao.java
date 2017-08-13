package com.smart.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class TypeDao extends BaseDAO {
	public int addType(String type_name) throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement) buildSql("select type_name from types");
		ResultSet rSet=preparedStatement.executeQuery();
		while(rSet.next()){
			if (rSet.getObject(1).toString().equals(type_name)) {
				return -1;
			}
		}
		preparedStatement=(PreparedStatement) buildSql("insert into types(type_name)values(?)", type_name);
		int num=preparedStatement.executeUpdate();
		return num;
	}
	
	public int deleteType(String type_name) throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement) buildSql("select type_name from types");
		ResultSet rSet=preparedStatement.executeQuery();
		int num=-1;
		while(rSet.next()){
			if (rSet.getObject(1).toString().equals(type_name)) {
				preparedStatement=(PreparedStatement)buildSql("select type_id from types where type_name=?", type_name);
				ResultSet rSet2=preparedStatement.executeQuery();
				int type_id=-1;
				while(rSet2.next()){
					type_id=rSet2.getInt(1);
				}
				System.out.println(type_id);
				if (type_id!=-1) {
					preparedStatement=(PreparedStatement)buildSql("select food_id from menu_type where type_id=?", type_id);
					ResultSet rSet3=preparedStatement.executeQuery();
					List<Integer>list=new ArrayList<>();
					while(rSet3.next()){
						list.add(rSet3.getInt(1));
						System.out.println(rSet3.getInt(1));
					}
					
					num=list.size();
					int count=0;
					for (Integer integer : list) {
						OrderDAO orderDAO=new OrderDAO();
						orderDAO.deleteOrderAndOrderItemForFood_id(integer);
						preparedStatement=(PreparedStatement)buildSql("delete from menu_type where food_id=?", integer);
						preparedStatement.executeUpdate();
						preparedStatement=(PreparedStatement)buildSql("delete from menu where food_id=?",integer);
						count+=preparedStatement.executeUpdate();
					}
					
					preparedStatement=(PreparedStatement)buildSql("delete from types where type_id=?", type_id);
					num=preparedStatement.executeUpdate();
				}
			}
		}
		return num;
		
	}
	public static void main(String[] args) throws SQLException {
		TypeDao typeDao =new TypeDao();
//		int num=typeDao.addType("婴儿食物");
//		System.out.println(num);
//		if (num==-1) {
//			System.out.println("已经存在该TYPE");
//		}
		
		int num=typeDao.deleteType("精品糖水");
		System.out.println(num);
		if (num==-1) {
			System.out.println("不存在该TYPE");
		}
	}
}
