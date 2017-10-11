package com.telecom.weibao.entity;

import java.sql.Timestamp;

public class ShowThing {
	private String title;
	private Timestamp creatTime;
	private String auditor;
	private String type;
	private String subRegion;
	private long number;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubRegion() {
		return subRegion;
	}
	public void setSubRegion(String subRegion) {
		this.subRegion = subRegion;
	}
	
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "ShowThing [title=" + title + ", creatTime=" + creatTime + ", auditor=" + auditor + ", type=" + type
				+ ", subRegion=" + subRegion + ", number=" + number + "]";
	}
}
