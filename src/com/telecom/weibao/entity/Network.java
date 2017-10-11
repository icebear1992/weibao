package com.telecom.weibao.entity;

import java.util.ArrayList;
import java.util.List;

public class Network {
	private long networkId;
	private String networkName;
	private Major major=new Major();
	private List<NE> neList=new ArrayList<NE>();
	public long getNetworkId() {
		return networkId;
	}
	public void setNetworkId(long networkId) {
		this.networkId = networkId;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	public List<NE> getNeList() {
		return neList;
	}
	public void setNeList(List<NE> neList) {
		this.neList = neList;
	}

}
