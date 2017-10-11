package com.telecom.weibao.entity;

public class NENumber {
	private NEModel neModel=new NEModel();
	private SubRegion subRegion=new SubRegion();
	private int number;
	public NEModel getNeModel() {
		return neModel;
	}
	public void setNeModel(NEModel neModel) {
		this.neModel = neModel;
	}
	public SubRegion getSubRegion() {
		return subRegion;
	}
	public void setSubRegion(SubRegion subRegion) {
		this.subRegion = subRegion;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
