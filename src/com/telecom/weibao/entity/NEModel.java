package com.telecom.weibao.entity;

public class NEModel {
	private long neModelId;
	private String neModelName;
	private Manufacturer manufacturer=new Manufacturer();
	private NE ne=new NE();
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
	public Manufacturer getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
	public NE getNe() {
		return ne;
	}
	public void setNe(NE ne) {
		this.ne = ne;
	}
}
