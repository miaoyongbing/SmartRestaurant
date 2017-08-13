package com.smart.bean;

import java.sql.Timestamp;

public class Orders {

	private int order_id;
	private String order_time;
	private int order_state;
	private int total_price;
	private int table_id;
	public final int getOrder_id() {
		return order_id;
	}
	public final void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public final String getOrder_time() {
		return order_time;
	}
	public final void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public final int getOrder_state() {
		return order_state;
	}
	public final void setOrder_state(int order_state) {
		this.order_state = order_state;
	}
	public final int getTotal_price() {
		return total_price;
	}
	public final void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	public final int getTable_id() {
		return table_id;
	}
	public final void setTable_id(int table_id) {
		this.table_id = table_id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " order_id:"+order_id+" order_state:"+order_state+" order_time:"+order_time+" total_price:"+total_price+" table_id:"+table_id;
	}
}
