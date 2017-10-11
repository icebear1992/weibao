package com.telecom.weibao.entity;

public class ManufacturerPersonnel {
	private long mpId;
	private String mpPhone;
	private String mpPassword;
	private String mpName;
	private int mpRole;
	private Manufacturer manufacturer=new Manufacturer();
	public long getMpId() {
		return mpId;
	}
	public void setMpId(long mpId) {
		this.mpId = mpId;
	}
	public String getMpPhone() {
		return mpPhone;
	}
	public void setMpPhone(String mpPhone) {
		this.mpPhone = mpPhone;
	}
	public String getMpPassword() {
		return mpPassword;
	}
	public void setMpPassword(String mpPassword) {
		this.mpPassword = mpPassword;
	}
	public String getMpName() {
		return mpName;
	}
	public void setMpName(String mpName) {
		this.mpName = mpName;
	}
	public int getMpRole() {
		return mpRole;
	}
	public void setMpRole(int mpRole) {
		this.mpRole = mpRole;
	}
	public Manufacturer getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

}
