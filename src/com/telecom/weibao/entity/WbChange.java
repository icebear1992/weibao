package com.telecom.weibao.entity;

import java.util.Date;

public class WbChange {
	private long changeId;
	private long wbId;
	private long changerId;
	private String changeContent;
	private Date changeTime;
	private Manufacturer changer;
	public long getChangeId() {
		return changeId;
	}
	public void setChangeId(long changeId) {
		this.changeId = changeId;
	}
	public long getWbId() {
		return wbId;
	}
	public void setWbId(long wbId) {
		this.wbId = wbId;
	}
	public long getChangerId() {
		return changerId;
	}
	public void setChangerId(long changerId) {
		this.changerId = changerId;
	}
	public String getChangeContent() {
		return changeContent;
	}
	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public Manufacturer getChanger() {
		return changer;
	}
	public void setChanger(Manufacturer changer) {
		this.changer = changer;
	}
	@Override
	public String toString() {
		return "WbChange [changeId=" + changeId + ", wbId=" + wbId + ", changerId=" + changerId + ", changeContent="
				+ changeContent + ", changeTime=" + changeTime + ", changer=" + changer + "]";
	}
	
	
}
