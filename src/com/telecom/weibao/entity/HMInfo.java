package com.telecom.weibao.entity;

public class HMInfo {
	private long hmInfoId;
	private HM hm=new HM();
	private NEModel neModel=new NEModel();
	private String serialNumber;
	private int totalNumber;
	private int repairNumber;
	public long getHmInfoId() {
		return hmInfoId;
	}
	public void setHmInfoId(long hmInfoId) {
		this.hmInfoId = hmInfoId;
	}
	public HM getHm() {
		return hm;
	}
	public void setHm(HM hm) {
		this.hm = hm;
	}
	public NEModel getNeModel() {
		return neModel;
	}
	public void setNeModel(NEModel neModel) {
		this.neModel = neModel;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getRepairNumber() {
		return repairNumber;
	}
	public void setRepairNumber(int repairNumber) {
		this.repairNumber = repairNumber;
	}
	

}
