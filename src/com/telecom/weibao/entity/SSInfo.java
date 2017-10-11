package com.telecom.weibao.entity;

public class SSInfo {
	private long ssInfoId;
	private SS ss=new SS();
	private String ssPersonnelName;
	private double effectiveduration;
	private String serviceContent;
	private String servicePlace;
	private String remarksInfo;
	public long getSsInfoId() {
		return ssInfoId;
	}
	public void setSsInfoId(long ssInfoId) {
		this.ssInfoId = ssInfoId;
	}
	public SS getSs() {
		return ss;
	}
	public void setSs(SS ss) {
		this.ss = ss;
	}
	public String getSsPersonnelName() {
		return ssPersonnelName;
	}
	public void setSsPersonnelName(String ssPersonnelName) {
		this.ssPersonnelName = ssPersonnelName;
	}
	public double getEffectiveduration() {
		return effectiveduration;
	}
	public void setEffectiveduration(double effectiveduration) {
		this.effectiveduration = effectiveduration;
	}
	public String getServiceContent() {
		return serviceContent;
	}
	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}
	public String getServicePlace() {
		return servicePlace;
	}
	public void setServicePlace(String servicePlace) {
		this.servicePlace = servicePlace;
	}
	public String getRemarksInfo() {
		return remarksInfo;
	}
	public void setRemarksInfo(String remarksInfo) {
		this.remarksInfo = remarksInfo;
	}
}
