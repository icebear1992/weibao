package com.telecom.weibao.entity;

public class DemandCategory {
	private long demandId;
	private String demandName;
	private int inputtimeLimit;
	public long getDemandId() {
		return demandId;
	}
	public void setDemandId(long demandId) {
		this.demandId = demandId;
	}
	public String getDemandName() {
		return demandName;
	}
	public void setDemandName(String demandName) {
		this.demandName = demandName;
	}
	public int getInputtimeLimit() {
		return inputtimeLimit;
	}
	public void setInputtimeLimit(int inputtimeLimit) {
		this.inputtimeLimit = inputtimeLimit;
	}

}
