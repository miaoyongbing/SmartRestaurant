package com.smart.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NTCredentials;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.smart.bean.OrderItem;
import com.smart.bean.Orders;
import com.sun.org.apache.regexp.internal.recompile;

import sun.dc.pr.PRError;

public class OrderDAO extends BaseDAO{
	/*
	 * 添加订单
	 */
	public int addOrder(Orders orders) throws SQLException{
		//我们Order对象的Order_time是String类型
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp oTimestamp = Timestamp.valueOf(orders.getOrder_time());
		PreparedStatement pStatement=(PreparedStatement)buildSql("insert into orders(order_time,order_state,total_price,table_id)values(?,?,?,?)", oTimestamp,orders.getOrder_state(),orders.getTotal_price(),orders.getTable_id());
		int num=pStatement.executeUpdate();
		ResultSet rSet=pStatement.getGeneratedKeys();
		int order_id=-1;
		while(rSet.next()){
			order_id=rSet.getInt(1);
		}
		return order_id;
	}

	/*
	 * 我们要求Android端传送过来的是Json格式，于是我们一个Order后紧跟着多个OrderItem
	 */
	public int addOrderItem(List<OrderItem>list) throws SQLException{

		PreparedStatement pStatement=null;
		int num=-1;
		for(int i=0;i<list.size();i++){
			pStatement=(PreparedStatement)buildSql("insert into order_item(order_id,food_id,food_num,food_state)values(?,?,?,?)",list.get(i).getOrder_id(),list.get(i).getFood_id(),list.get(i).getFood_num(),list.get(i).getFood_state());
			num=pStatement.executeUpdate();
			if (num<0) {
				break;
			}
		}
		return num;
	}

	/*
	 * 给定table_id获取该table点的所有订单以及订单详情(针对管理者)
	 */
	public ResultSet getAllOrderAndOrderItemForTableId(int table_id) throws SQLException{

		String sql="select orders.table_id, orders.order_id,orders.order_time,orders.order_state,orders.total_price,order_item.food_id ,order_item.food_num,order_item.food_state  from orders join order_item where orders.order_id=order_item.order_id and table_id=?";
		PreparedStatement preparedStatement=(PreparedStatement) buildSql(sql,table_id);
		ResultSet rSet=preparedStatement.executeQuery();
		return rSet;
	}
	/*
	 * 管理者获取所有的订单包括支付和未支付的
	 */
	public ResultSet getAllOrderAndOrderItem() throws SQLException{

		String sql="select orders.table_id, orders.order_id,orders.order_time,orders.order_state,orders.total_price,order_item.food_id ,order_item.food_num,order_item.food_state  from orders join order_item where orders.order_id=order_item.order_id";
		PreparedStatement preparedStatement=(PreparedStatement) buildSql(sql);
		ResultSet rSet=preparedStatement.executeQuery();
		return rSet;
	}

	/*
	 * 我们通过table_id选取当前所有属于这个顾客的订单，我们这里存在一个前提就是我们必须在用户付完钱的时候我们改变这个用户的所有Order的状态
	 */
	public ResultSet getAllTableOrderAndOrderItemNowEating(int table_id,int order_state) throws SQLException{
		String sql="select * FROM orders join order_item join menu where orders.order_id=order_item.order_id  and menu.food_id=order_item.food_id and table_id=? and order_state=?";
		PreparedStatement preparedStatement=(PreparedStatement)buildSql(sql, table_id,order_state);
		ResultSet resultSet=preparedStatement.executeQuery();
		return resultSet;
	}


	/*
	 * 厨师长获取所有当前的订单
	 */

	public ResultSet getAllOrderAndOrderItemNowEating(int order_state) throws SQLException{
		String sql="select * FROM orders join order_item join menu where orders.order_id=order_item.order_id  and menu.food_id=order_item.food_id and order_state=? order by order_time";
		PreparedStatement preparedStatement=(PreparedStatement)buildSql(sql,order_state);
		ResultSet resultSet=preparedStatement.executeQuery();
		return resultSet;
	}

	/*
	 * 通过table_id获取当前用户的所有订单单号
	 */
	public ResultSet getOrderId(int table_id,int order_state) throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement) buildSql("select order_id from orders where table_id=? and order_state=?", table_id,order_state);
		ResultSet rSet=preparedStatement.executeQuery();
		return rSet;
	}


	/*
	 * 删除order_id对应的所有orderItem
	 */
	public int deleteOrderItem(int order_id) throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement) buildSql("delete from order_item where order_id=?", order_id);
		int num=preparedStatement.executeUpdate();
		System.out.println("删除"+num+"个订单子项");
		return num;
	}

	/*
	 * 顾客和管理员都可以删除Order
	 */
	public int deleteOrderByOrder_id(int order_id) throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement) buildSql("delete from orders where order_id=?", order_id);
		int num=preparedStatement.executeUpdate();
		System.out.println("删除"+num+"个order");
		return num;
	}



	public int deleteOrder(int table_id) throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement) buildSql("delete from orders where table_id=?", table_id);
		int num=preparedStatement.executeUpdate();
		System.out.println("删除"+num+"个order");
		return num;
	}

	/*
	 * 根据table_id和order_type删除曾在这个桌子用餐或者正在用餐的order 和orderItem
	 */
	public void deleteOrderAndOrderItem(int table_id,int order_state) throws NumberFormatException, SQLException{
		ResultSet rSet=getOrderId(table_id,order_state);
		List<Integer> list=new ArrayList<>();
		while(rSet.next()){
			list.add(Integer.parseInt(rSet.getObject(1).toString()));
		}
		for (Integer integer : list) {
			deleteOrderItem(integer);
		}
		deleteOrder(table_id);
	}




	/*
	 * 我们想要删除与Food_id相关的所有Order和OrderItem
	 */
	public void deleteOrderAndOrderItemForFood_id(int food_id) throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement) buildSql("select order_id from order_item where food_id=?", food_id);
		List<Integer>list=new ArrayList<>();
		ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next()){
			list.add(resultSet.getInt(1));
			System.out.println(resultSet.getInt(1));
		}

		preparedStatement=(PreparedStatement)buildSql("delete from order_item where food_id=?", food_id);
		preparedStatement.executeUpdate();
		for (Integer integer : list) {
			preparedStatement=(PreparedStatement)buildSql("select * from order_item where order_id=?", integer);
			ResultSet resultSet2=preparedStatement.executeQuery();
			int num=0;
			while(resultSet2.next()){
				num++;
			}if (num==0) {
				preparedStatement=(PreparedStatement)buildSql("delete from orders where order_id=?", integer);
				preparedStatement.executeUpdate();
			}
		}

	}

	/*
	 * 顾客删除所有订单里的一个菜
	 */
	public int deleteOrderItem(int table_id,int food_id,int order_state) throws SQLException{
		int num=-1;
		PreparedStatement preparedStatement=(PreparedStatement) buildSql("select * from orders join order_item where table_id=? and food_id=? and orders.order_id=order_item.order_id and order_state=?", table_id,food_id,order_state);
		ResultSet rSet=preparedStatement.executeQuery();
		List<Integer>list=new ArrayList<>();
		while(rSet.next()){
			list.add(rSet.getInt("order_id"));
		}
		for (int i = 0; i < list.size(); i++) {
			preparedStatement=(PreparedStatement) buildSql("delete from  order_item where order_id=? and food_id=?", list.get(i),food_id);
			num=preparedStatement.executeUpdate();
		}
		return num;
	}



	/*
	 * 厨师长更改菜的状态
	 */
	public boolean changeOrderItemState(int food_id,int order_id,int state) throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement)buildSql("update order_item set food_state=? where food_id=? and order_id=?",state,food_id,order_id);
		int num=preparedStatement.executeUpdate();
		return num>0?true:false;
	}




	/*
	 * 厨师长根据出来的菜搜索第一个桌子
	 */
	public ResultSet selectTheTableIdByFoodId(int food_id,int food_state) throws SQLException{
		String sql="SELECT table_id from orders join  order_item where orders.order_id=order_item.order_id and food_id=? and food_state=? ORDER BY order_time";
		PreparedStatement preparedStatement=(PreparedStatement)buildSql(sql, food_id,food_state);
		ResultSet resultSet=preparedStatement.executeQuery();
		return resultSet;
	}
	/*
	 * 顾客删除一个订单里面的一道菜
	 */
	public boolean changeOrderItemByOrder_id(int order_id,int food_id) throws SQLException{
		PreparedStatement preparedStatement=(PreparedStatement)buildSql("select order_id from orders");
		ResultSet rSet=preparedStatement.executeQuery();
		boolean has=false;
		while(rSet.next()){
			if (rSet.getInt(1)==order_id) {
				has=true;
			}
		}
		if (has) {
			preparedStatement=(PreparedStatement)buildSql("select food_state from order_item where order_id=? and food_id=?", order_id,food_id);
			ResultSet rSet2=preparedStatement.executeQuery();
			while(rSet2.next()){
				int food_state=rSet2.getInt(1);
				if (food_state==0) {
					preparedStatement=(PreparedStatement)buildSql("delete from order_item where order_id=? and food_id=?", order_id,food_id);
					int num=preparedStatement.executeUpdate();
					return num>0?true:false;
				}
				break;
			}
			return false;
		}else{
			return has;
		}
	}
	public static void main(String[] args) throws SQLException {
		//所有这些我们都没有try_catch这样就代表这我们只会知道出现了错误，但是没办法提醒用户，后期改进
		OrderDAO orderDAO=new OrderDAO();

		//添加ORDERS和对应的ORDER_ITEM
		//		List<OrderItem>list=new ArrayList<>();
		//		Orders orders=new Orders();
		//		orders.setOrder_state(1);
		//		orders.setOrder_time(String.valueOf(System.currentTimeMillis()));
		//		orders.setTable_id(1);
		//		orders.setTotal_price(1000);
		//
		//		int order_id=orderDAO.addOrder(orders);
		//		System.out.println(order_id);
		//		//order={"order_time":"2017-04-15 14:26:25","order_state":"1","total_price":"100","table_id":"2"} & order_item=[{"food_id":"1","food_num":"4","food_state":"0"},{"food_id":"2","food_num":"4","food_state":"0"}]
		//		OrderItem orderItem=new OrderItem();
		//		orderItem.setFood_id(1);
		//		orderItem.setFood_num(3);
		//		orderItem.setFood_state(1);
		//		orderItem.setOrder_id(order_id);
		//
		//
		//		OrderItem orderItem2=new OrderItem();
		//		orderItem2.setFood_id(2);
		//		orderItem2.setFood_num(2);
		//		orderItem2.setFood_state(0);
		//		orderItem2.setOrder_id(order_id);
		//
		//		list.add(orderItem);
		//		list.add(orderItem2);
		//		orderDAO.addOrderItem(list);



		//用于根据TABLE_ID获取这个桌子的所有订单
		//		ResultSet rSet=orderDAO.getAllOrderAndOrderItemForTableId(2);
		//		ResultSetMetaData resultSetMetaDate=(ResultSetMetaData) rSet.getMetaData();
		//		int columNum=resultSetMetaDate.getColumnCount();
		//		System.out.println(columNum);
		//		int count=0;
		//		while(rSet.next()){
		//			for(int i=0;i<columNum;i++){
		//				System.out.print(resultSetMetaDate.getColumnName(i+1)+" : "+ rSet.getObject(i+1)+"   ");
		//			}
		//			count++;
		//			System.out.println();
		//		}
		//		if (count==0) {
		//			System.out.println("不存在这么一个Table_id");
		//		}


		//删除订单，注意先删除order_item再删除order
		//orderDAO.deleteOrderAndOrderItem(2);    

		//通过TABLE_ID和FOOD_ID更改订单
		//		int num=orderDAO.changeOrderItem(2, 2);
		//		if (num==-1) {
		//			System.out.println("change the order_item failed");
		//		}
		//
		//		orderDAO.deleteOrderAndOrderItemForFood_id(1);
		boolean b=orderDAO.changeOrderItemByOrder_id(21, 1);
		if (b) {
			System.out.println("修改成功");
		}
		else{
			System.out.println("修改失败");
		}
	}

}
