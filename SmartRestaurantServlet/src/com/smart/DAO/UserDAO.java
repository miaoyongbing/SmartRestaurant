package com.smart.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.smart.bean.User;

public class UserDAO extends BaseDAO {

	//添加新用户
	public boolean Add(User user){
		try {
			String sql = "SELECT * FROM users WHERE user_name = ?;";
			ResultSet rs = buildSql(sql,""+user.getUser_name()).executeQuery();
			if(rs.next()){
				System.out.println("用户名已经存在！！！");
				return false;
			} else {
				sql = "INSERT INTO users(user_name,password,job) VALUES(?,?,?);";
				PreparedStatement ps = buildSql(sql,user.getUser_name(),user.getPassword(),""+user.getJob());
				if(!(ps.executeUpdate()>0)){
					System.out.println("数据库未知错误!!");
					return false;
				} else {
					//测试用
					System.out.println("new User:"+user.toString()+"add success!!");
					ResultSet rSet=ps.getGeneratedKeys();
					while(rSet.next()){
						System.out.println(rSet.getInt(1));
					}
					return true;
				}			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	//验证登录 失败返回null 成功返回对象
	public static ResultSet verify(String user_name,String password){		
		try {
			String sql = "SELECT * FROM users WHERE user_name = ? AND password = ?";
			ResultSet rs = buildSql(sql, user_name,password).executeQuery();
			return rs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}

	//查找所有用户	返回ArrayList
	public ResultSet UserAll(){

		try {
			String sql = "select * from users";
			ResultSet rs = buildSql(sql).executeQuery();
			return rs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int deleteUser(String username) throws SQLException{
		PreparedStatement preparedStatement=buildSql("delete from users where user_name=?", username);
		int num=preparedStatement.executeUpdate();
		return num;
	}

	public boolean changeUser(User user) throws SQLException
	{
		PreparedStatement preparedStatement=(PreparedStatement)buildSql("update users set user_name=? , password=? ,job=? ,", user.getUser_name(),user.getPassword(),user.getJob());
		int num=preparedStatement.executeUpdate();
		return num>0?true:false;

	}

	public static void main(String[] args) throws SQLException {
		UserDAO userDAO=new UserDAO();
		ResultSet resultSet=userDAO.UserAll();
		while(resultSet.next()){
			System.out.println(resultSet.getObject("user_id"));		
		}
		//		User user=new User();
		//		user.setPassword("1112");
		//		user.setJob(111);
		//		user.setUser_name("牛聪聪");
		//		userDAO.Add(user);

		//        ResultSet rSet=verify("111", "111");
		//        while(rSet.next()){
		//        	System.out.println(rSet.getString("user_id"));
		//        }

		//我们涉及不到删除用户 如果有这里输出1正确输出0错误进行判断再进一步处理
		int num=userDAO.deleteUser("1111");
		System.out.println(num);

		resultSet=userDAO.UserAll();
		while(resultSet.next()){
			System.out.println(resultSet.getObject("user_id"));		
		}
	}

}
