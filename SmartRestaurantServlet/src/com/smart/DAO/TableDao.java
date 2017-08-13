package com.smart.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class TableDao extends BaseDAO{
	private static List<String>list=new ArrayList<>();
	static{
		list.add("table_id");
		list.add("table_number");
		list.add("table_type");
	}
	public static void main(String[] args) throws SQLException {
		TableDao tableDao=new TableDao();
		ResultSet rSet=tableDao.getAlltable();
		int num=rSet.getMetaData().getColumnCount();
		while(rSet.next()){
			for(int i=0;i<num;i++){			
				System.out.print(list.get(i)+"->>>"+rSet.getObject(i+1)+":::");
			}
			System.out.println();
		}

		boolean change=tableDao.changeTableState(1, 1);
		if (change) {
			System.out.println("改变成功");
		}else{
			System.out.println("改变失败");
		}

		boolean changeAll=tableDao.changeAllTablesState(1);
		if (changeAll) {
			System.out.println("改变所有Table状态成功");
		}else{
			System.out.println("改变所有Table状态失败");
		}
	}

	/*
	 * 选取所有Table，包括当前这个桌子的状态，桌号和桌子可以容纳的人数
	 */
	public ResultSet getAlltable() throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement) buildSql("select * from tables");
		ResultSet rSet=preparedStatement.executeQuery();
		return rSet;
	}

	/*
	 * 当用户选定桌子后我们对桌子的状态进行改变，但是我们需要判断能不能改变
	 */

	public boolean changeTableState(int table_id,int table_typd) throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement)buildSql("select table_type from tables where table_id=?", table_id);
		ResultSet rSet=preparedStatement.executeQuery();
		boolean change=true;
		while(rSet.next()){
			if (rSet.getInt(1)==table_typd) {
				change=false;
				break;
			}
		}
		if (change) {
			preparedStatement=(PreparedStatement) buildSql("update tables set table_type=? where table_id=?",table_typd,table_id );
			int num=preparedStatement.executeUpdate();
			System.out.println("change Table "+table_id+" type to "+table_typd);
		}
		return change;
	}

	/*
	 * 删除一个桌子
	 */
	public boolean deleteTable(int table_id) throws SQLException{

		PreparedStatement preparedStatement=(PreparedStatement)buildSql("delete form tables where table_id=?", table_id);
		int num=preparedStatement.executeUpdate();
		return num>0?true:false;

	}
	/*
	 * 删除所有桌子
	 */
	public boolean deleteAllTables() throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement)buildSql("delete from tables");
		int num=preparedStatement.executeUpdate();
		return num>0?true:false;
	}
	/*
	 * 一下子改变所有Table状态
	 */
	public boolean changeAllTablesState(int table_state) throws SQLException{
		PreparedStatement preparedStatemen=(PreparedStatement)buildSql("update tables set table_type=?", table_state);
		int num=preparedStatemen.executeUpdate();
		return num>0?true:false;
	}
}

