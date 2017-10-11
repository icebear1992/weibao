package com.telecom.weibao.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HM {
	private long hmId;
	private SubRegion subRegion=new SubRegion();
	private Major major=new Major();
	private Date launchTime;
	private Date completionTime;
	private long createrId;
	private ManufacturerPersonnel mp=new ManufacturerPersonnel();
	private TelecomPersonnel auditor=new TelecomPersonnel();
	private Timestamp creatTime;	
	private List<HMInfo> hmInfoList=new ArrayList<HMInfo>();
	private List<HMReview> hmReviewList=new ArrayList<HMReview>();
	private int currentProcess;
	private String title;
	
	public long getHmId() {
		return hmId;
	}
	public void setHmId(long hmId) {
		this.hmId = hmId;
	}
	public SubRegion getSubRegion() {
		return subRegion;
	}
	public void setSubRegion(SubRegion subRegion) {
		this.subRegion = subRegion;
	}
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	public Date getLaunchTime() {
		return launchTime;
	}
	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}
	public Date getCompletionTime() {
		return completionTime;
	}
	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
	}
	
	public TelecomPersonnel getAuditor() {
		return auditor;
	}
	public void setAuditor(TelecomPersonnel auditor) {
		this.auditor = auditor;
	}
	public Timestamp getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}
	public List<HMInfo> getHmInfoList() {
		return hmInfoList;
	}
	public void setHmInfoList(List<HMInfo> hmInfoList) {
		this.hmInfoList = hmInfoList;
	}
	public List<HMReview> getHmReviewList() {
		return hmReviewList;
	}
	public void setHmReviewList(List<HMReview> hmReviewList) {
		this.hmReviewList = hmReviewList;
	}
	public ManufacturerPersonnel getMp() {
		return mp;
	}
	public void setMp(ManufacturerPersonnel mp) {
		this.mp = mp;
	}
	public int getCurrentProcess() {
		return currentProcess;
	}
	public void setCurrentProcess(int currentProcess) {
		this.currentProcess = currentProcess;
	}
	public long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "HM [hmId=" + hmId + ", subRegion=" + subRegion + ", major=" + major + ", launchTime=" + launchTime
				+ ", completionTime=" + completionTime + ", createrId=" + createrId + ", mp=" + mp + ", auditor="
				+ auditor + ", creatTime=" + creatTime + ", hmInfoList=" + hmInfoList + ", hmReviewList=" + hmReviewList
				+ ", currentProcess=" + currentProcess + ", title=" + title + "]";
	}
	
}
