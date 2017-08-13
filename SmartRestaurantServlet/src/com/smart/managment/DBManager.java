package com.smart.managment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
	private static Connection mysqlConnection = null;
	
	private static String mysqlDriver = "com.mysql.jdbc.Driver";
	private static String mysqlUrl = "jdbc:mysql://localhost:3306/zzjjx?characterEncoding=utf8";
	private static String mysqlUser = "root";
	private static String mysqlPw = "345345";
	
	
	private static void mysqlConnect() {
		try {
			 Class.forName(mysqlDriver);
			 mysqlConnection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPw);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	}
	
	public static Connection getMysqlConnection() {
		try {
			if (mysqlConnection == null || mysqlConnection.isClosed()){
				mysqlConnect();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mysqlConnection;
	}
	
	public static void closeMysqlConnection() {
		try {
			if (mysqlConnection != null && !mysqlConnection.isClosed()) {
				mysqlConnection.close();
				mysqlConnection = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
