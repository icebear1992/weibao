package com.telecom.weibao.statistics;

public class HMModelStatistics {
	private long neModelId;
	private String neModelName;
	private int total;
	private int bad;
	private int repair;
	public long getNeModelId() {
		return neModelId;
	}
	public void setNeModelId(long neModelId) {
		this.neModelId = neModelId;
	}
	public String getNeModelName() {
		return neModelName;
	}
	public void setNeModelName(String neModelName) {
		this.neModelName = neModelName;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRepair() {
		return repair;
	}
	public void setRepair(int repair) {
		this.repair = repair;
	}
	public int getBad() {
		return bad;
	}
	public void setBad(int bad) {
		this.bad = bad;
	}
}
