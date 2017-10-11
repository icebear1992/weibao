package com.telecom.weibao.entity;

import java.util.ArrayList;
import java.util.List;

public class NE {
	private long neId;
	private String neName;
	private Network network=new Network();
	private List<NEModel> neModelList=new ArrayList<NEModel>();
	public long getNeId() {
		return neId;
	}
	public void setNeId(long neId) {
		this.neId = neId;
	}
	public String getNeName() {
		return neName;
	}
	public void setNeName(String neName) {
		this.neName = neName;
	}
	public Network getNetwork() {
		return network;
	}
	public void setNetwork(Network network) {
		this.network = network;
	}
	public List<NEModel> getNeModelList() {
		return neModelList;
	}
	public void setNeModelList(List<NEModel> neModelList) {
		this.neModelList = neModelList;
	}
}
