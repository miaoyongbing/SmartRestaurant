package com.smart.bean;

public class User {
	private int user_id;
	private String password;
	private int job;
	private String user_name;
	
	public final String getPassword() {
		return password;
	}
	public final void setPassword(String password) {
		this.password = password;
	}
	public final int getJob() {
		return job;
	}
	public final void setJob(int job) {
		this.job = job;
	}	
	public final int getUser_id() {
		return user_id;
	}
	public final void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public final String getUser_name() {
		return user_name;
	}
	public final void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", password=" + password + ", job=" + job + ", user_name=" + user_name + "]";
	}
	
}
