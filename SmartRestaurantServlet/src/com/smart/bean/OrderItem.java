package com.smart.bean;

public class OrderItem {

	private int order_id;
	private int food_id;
	private int food_num;
	private int food_state;
	public final int getOrder_id() {
		return order_id;
	}
	public final void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public final int getFood_id() {
		return food_id;
	}
	public final void setFood_id(int food_id) {
		this.food_id = food_id;
	}
	public final int getFood_num() {
		return food_num;
	}
	public final void setFood_num(int food_num) {
		this.food_num = food_num;
	}
	public final int getFood_state() {
		return food_state;
	}
	public final void setFood_state(int food_state) {
		this.food_state = food_state;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[order_id:"+order_id+" food_id:"+food_id+" food_num"+food_num+" food_state:"+food_state+"]";
	}
}
