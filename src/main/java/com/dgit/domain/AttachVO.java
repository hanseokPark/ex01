package com.dgit.domain;

import java.util.Date;

public class AttachVO {
	private String fullName;
	private int bno;
	private Date regdate;
	
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "AttachVO [fullName=" + fullName + ", bno=" + bno + ", regdate=" + regdate + "]";
	}
	
}
