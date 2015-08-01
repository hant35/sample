package com.panpages.bow.model;

import java.util.Date;

public class Report {
	private String email;
	private String userName;
	private Date timeAccess;
	private Date timeReceived;
	private String type;
	private Date fromDate;
	private Date toDate;
	private String sFromDate;
	private String sToDate;
	private String action;
	private String result;
	private String mailTo;
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
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getsFromDate() {
		return sFromDate;
	}
	public void setsFromDate(String sFromDate) {
		this.sFromDate = sFromDate;
	}
	public String getsToDate() {
		return sToDate;
	}
	public void setsToDate(String sToDate) {
		this.sToDate = sToDate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	
	
}
