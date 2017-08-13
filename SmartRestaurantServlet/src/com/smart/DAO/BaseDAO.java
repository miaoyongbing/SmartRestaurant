package com.smart.DAO;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.smart.managment.DBManager;

public class BaseDAO {
	
	private static List<Class<?>> validTypeList = Arrays.asList(
			int.class, Integer.class,
			double.class, Double.class,
			BigDecimal.class,
			String.class,
			Timestamp.class,
			Blob.class
		);

	protected static PreparedStatement buildSql(String sql, Object... args) throws SQLException{
		PreparedStatement ps = null;
		ps = DBManager.getMysqlConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		for(int i=0;i<args.length;i++){
			ps.setObject(i+1, args[i]);
		}
		return ps;		
	}
	
	protected static <T> T toBeanObject(Class<T> classOfT, ResultSet rs) throws Exception {
		if (!rs.next())
			return null;
		
		Method[] methods = classOfT.getDeclaredMethods();
		T obj = classOfT.newInstance();
		
		if (null != methods) {
			for (Method m : methods) {
				String methodName = m.getName();
				if (methodName.startsWith("set")) {
					methodName = methodName.substring(3);
					// 获取属性名称
					methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
					
					if (!methodName.equalsIgnoreCase("class") && validTypeList.contains(m.getParameterTypes()[0])) {
						try {
							m.invoke(obj, rs.getObject(methodName));
						} catch (IllegalArgumentException e) {
							System.out.println("warning: " + methodName + " " + rs.getObject(methodName).getClass().getName() 
									+ " to " + m.getParameterTypes()[0].getName());
							// 待完善
							if (rs.getObject(methodName) instanceof Long)
								m.invoke(obj, rs.getInt(methodName));
							if (rs.getObject(methodName) instanceof byte[])
								m.invoke(obj, rs.getBlob(methodName));
						}
					}
				}
			}
		}
		
		return obj;
	}
	
	protected static <T> ArrayList<T> toBeanList(Class<T> classOfT, ResultSet rs) throws Exception {
		ArrayList<T> listOfT = new ArrayList<>();
		T obj = toBeanObject(classOfT, rs);
		while (obj != null) {
			listOfT.add(obj);
			obj = toBeanObject(classOfT, rs);
		}		
		return listOfT;
	}
	
}
