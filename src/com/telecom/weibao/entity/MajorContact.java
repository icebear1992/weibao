package com.telecom.weibao.entity;

public class MajorContact {
	private SubRegion subRegion=new SubRegion();
	private Major major=new Major();
	private TelecomPersonnel tp=new TelecomPersonnel();
	public SubRegion getSubRegion() {
		return subRegion;
	}
	public void setSubRegion(SubRegion subRegion) {
		this.subRegion = subRegion;
	}
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	public TelecomPersonnel getTp() {
		return tp;
	}
	public void setTp(TelecomPersonnel tp) {
		this.tp = tp;
	}
}
