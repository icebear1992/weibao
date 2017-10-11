package com.telecom.weibao.entity;

import java.util.ArrayList;
import java.util.List;

public class Major {
	private long majorId;
	private String majorName;
	private List<Network> networkList=new ArrayList<Network>();	
	public long getMajorId() {
		return majorId;
	}
	public void setMajorId(long majorId) {
		this.majorId = majorId;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public List<Network> getNetworkList() {
		return networkList;
	}
	public void setNetworkList(List<Network> networkList) {
		this.networkList = networkList;
	}

}
