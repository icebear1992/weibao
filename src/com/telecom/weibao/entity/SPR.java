package com.telecom.weibao.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SPR {	
	private long sprId;
	private Timestamp malfunctionTime;
	private Timestamp replacementTime;
	private SubRegion subRegion = new SubRegion();
	private NEModel neModel;
	private String oldSerialNum;
	private String newSerialNum;
	private long createrId;
	private Timestamp creatTime;
	private TelecomPersonnel auditor = new TelecomPersonnel();
	private ManufacturerPersonnel mp = new ManufacturerPersonnel();
	private int currentProcess;
	private SubRegion sprOccurArea = new SubRegion();
	private String sprCondition;
	private List<SPRReview> sprReviewList=new ArrayList<SPRReview>();
	public long getSprId() {
		return sprId;
	}
	public void setSprId(long sprId) {
		this.sprId = sprId;
	}
	public Timestamp getMalfunctionTime() {
		return malfunctionTime;
	}
	public void setMalfunctionTime(Timestamp malfunctionTime) {
		this.malfunctionTime = malfunctionTime;
	}
	public Timestamp getReplacementTime() {
		return replacementTime;
	}
	public void setReplacementTime(Timestamp replacementTime) {
		this.replacementTime = replacementTime;
	}
	public SubRegion getSubRegion() {
		return subRegion;
	}
	public void setSubRegion(SubRegion subRegion) {
		this.subRegion = subRegion;
	}
	public NEModel getNeModel() {
		return neModel;
	}
	public void setNeModel(NEModel neModel) {
		this.neModel = neModel;
	}
	public String getOldSerialNum() {
		return oldSerialNum;
	}
	public void setOldSerialNum(String oldSerialNum) {
		this.oldSerialNum = oldSerialNum;
	}
	public String getNewSerialNum() {
		return newSerialNum;
	}
	public void setNewSerialNum(String newSerialNum) {
		this.newSerialNum = newSerialNum;
	}
	public long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}
	public Timestamp getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}
	public TelecomPersonnel getAuditor() {
		return auditor;
	}
	public void setAuditor(TelecomPersonnel auditor) {
		this.auditor = auditor;
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
	public SubRegion getSprOccurArea() {
		return sprOccurArea;
	}
	public void setSprOccurArea(SubRegion sprOccurArea) {
		this.sprOccurArea = sprOccurArea;
	}
	public String getSprCondition() {
		return sprCondition;
	}
	public void setSprCondition(String sprCondition) {
		this.sprCondition = sprCondition;
	}
	public List<SPRReview> getSprReviewList() {
		return sprReviewList;
	}
	public void setSprReviewList(List<SPRReview> sprReviewList) {
		this.sprReviewList = sprReviewList;
	}
	
}
