package com.panpages.bow.model;

import java.util.Date;

public class Report {
	private String email;
	private String userName;
	private Date timeAccess;
	private Date timeReceived;
	private String type;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getTimeAccess() {
		return timeAccess;
	}
	public void setTimeAccess(Date timeAccess) {
		this.timeAccess = timeAccess;
	}
	public Date getTimeReceived() {
		return timeReceived;
	}
	public void setTimeReceived(Date timeReceived) {
		this.timeReceived = timeReceived;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
