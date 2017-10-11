package com.telecom.weibao.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SS {
	private long ssId;
	private SubRegion subRegion = new SubRegion();
	private Major major = new Major();
	private Date startDate;
	private Date endDate;
	private double totalDuration;
	private TelecomPersonnel auditor = new TelecomPersonnel();
	private Timestamp creatTime;
	private int currentProcess;
	private long createrId;
	private ManufacturerPersonnel mp = new ManufacturerPersonnel();
	private List<SSInfo> ssInfoList=new ArrayList<SSInfo>();
	private List<SSReview> ssReviewList=new ArrayList<SSReview>();
	public long getSsId() {
		return ssId;
	}
	public void setSsId(long ssId) {
		this.ssId = ssId;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public double getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(double totalDuration) {
		this.totalDuration = totalDuration;
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
	public List<SSInfo> getSsInfoList() {
		return ssInfoList;
	}
	public void setSsInfoList(List<SSInfo> ssInfoList) {
		this.ssInfoList = ssInfoList;
	}
	public List<SSReview> getSsReviewList() {
		return ssReviewList;
	}
	public void setSsReviewList(List<SSReview> ssReviewList) {
		this.ssReviewList = ssReviewList;
	}
	public int getCurrentProcess() {
		return currentProcess;
	}
	public void setCurrentProcess(int currentProcess) {
		this.currentProcess = currentProcess;
	}	
	public ManufacturerPersonnel getMp() {
		return mp;
	}
	public void setMp(ManufacturerPersonnel mp) {
		this.mp = mp;
	}
	public long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}
}
