package com.telecom.weibao.statistics;

//针对服务响应类别进行数目和时长的统计
public class SERTypeStatistics {
	private long demandId;
	private String demandName;
    private int total;
    private double time;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
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
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	
}
