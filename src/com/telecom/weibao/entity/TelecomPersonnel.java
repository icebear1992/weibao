package com.telecom.weibao.entity;

public class TelecomPersonnel {
	private long tpId;
	private String tpPhone;
	private String tpPassword;
	private String tpName;
	private int tpRole;
	private SubRegion subRegion=new SubRegion();
	private TelecomPersonnel TpManager;
	
	public TelecomPersonnel getTpManager() {
		return TpManager;
	}
	public void setTpManager(TelecomPersonnel tpManager) {
		TpManager = tpManager;
	}
	
	public long getTpId() {
		return tpId;
	}
	public void setTpId(long tpId) {
		this.tpId = tpId;
	}
	public String getTpPhone() {
		return tpPhone;
	}
	public void setTpPhone(String tpPhone) {
		this.tpPhone = tpPhone;
	}
	public String getTpPassword() {
		return tpPassword;
	}
	public void setTpPassword(String tpPassword) {
		this.tpPassword = tpPassword;
	}
	public String getTpName() {
		return tpName;
	}
	public void setTpName(String tpName) {
		this.tpName = tpName;
	}
	public int getTpRole() {
		return tpRole;
	}
	public void setTpRole(int tpRole) {
		this.tpRole = tpRole;
	}
	public SubRegion getSubRegion() {
		return subRegion;
	}
	public void setSubRegion(SubRegion subRegion) {
		this.subRegion = subRegion;
	}
}
