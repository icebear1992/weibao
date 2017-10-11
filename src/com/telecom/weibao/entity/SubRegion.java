package com.telecom.weibao.entity;

public class SubRegion {
	private long subRegionId;
	private String subRegionName;
	private Region region=new Region();
	
	public long getSubRegionId() {
		return subRegionId;
	}
	public void setSubRegionId(long subRegionId) {
		this.subRegionId = subRegionId;
	}
	public String getSubRegionName() {
		return subRegionName;
	}
	public void setSubRegionName(String subRegionName) {
		this.subRegionName = subRegionName;
	}
	
	
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}

}
