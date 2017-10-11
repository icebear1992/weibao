package com.telecom.weibao.entity;

import java.util.ArrayList;
import java.util.List;

public class Region {
	private long regionId;
	private String regionName;
	private List<SubRegion> subRegionList=new ArrayList<SubRegion>();
	
	public long getRegionId() {
		return regionId;
	}
	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public List<SubRegion> getSubRegionList() {
		return subRegionList;
	}
	public void setSubRegionList(List<SubRegion> subRegionList) {
		this.subRegionList = subRegionList;
	}
}
