package com.telecom.weibao.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Cruising {
	private long cId;
	private SubRegion subRegion;
	private Major major;
	private Date startTime;
	private Date endTime;
	private double totalDuration;
	private long createrId;
	private TelecomPersonnel auditor=new TelecomPersonnel();
	private ManufacturerPersonnel mp=new ManufacturerPersonnel();
	private Timestamp creatTime;
	private int cenginerroomNum;
	private int cequipmentNum;
	private int staffing;
	private int vehicleDeployment;
	private String cObject;
	private String cContent;
	private String cResult;
	private String cReport;
	private String remarksinfo;
	private int currentProcess;
	private List<CruisingReview> cruisingReviewList=new ArrayList<CruisingReview>();
	public long getcId() {
		return cId;
	}
	public void setcId(long cId) {
		this.cId = cId;
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public double getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(double totalDuration) {
		this.totalDuration = totalDuration;
	}
	public long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(long createrId) {
		this.createrId = createrId;
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
	public int getCenginerroomNum() {
		return cenginerroomNum;
	}
	public Timestamp getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}
	public void setCenginerroomNum(int cenginerroomNum) {
		this.cenginerroomNum = cenginerroomNum;
	}
	public int getCequipmentNum() {
		return cequipmentNum;
	}
	public void setCequipmentNum(int cequipmentNum) {
		this.cequipmentNum = cequipmentNum;
	}
	public int getStaffing() {
		return staffing;
	}
	public void setStaffing(int staffing) {
		this.staffing = staffing;
	}
	public int getVehicleDeployment() {
		return vehicleDeployment;
	}
	public void setVehicleDeployment(int vehicleDeployment) {
		this.vehicleDeployment = vehicleDeployment;
	}
	public String getcObject() {
		return cObject;
	}
	public void setcObject(String cObject) {
		this.cObject = cObject;
	}
	public String getcContent() {
		return cContent;
	}
	public void setcContent(String cContent) {
		this.cContent = cContent;
	}
	public String getcResult() {
		return cResult;
	}
	public void setcResult(String cResult) {
		this.cResult = cResult;
	}
	public String getcReport() {
		return cReport;
	}
	public void setcReport(String cReport) {
		this.cReport = cReport;
	}
	public String getRemarksinfo() {
		return remarksinfo;
	}
	public void setRemarksinfo(String remarksinfo) {
		this.remarksinfo = remarksinfo;
	}
	public int getCurrentProcess() {
		return currentProcess;
	}
	public void setCurrentProcess(int currentProcess) {
		this.currentProcess = currentProcess;
	}
	public List<CruisingReview> getCruisingReviewList() {
		return cruisingReviewList;
	}
	public void setCruisingReviewList(List<CruisingReview> cruisingReviewList) {
		this.cruisingReviewList = cruisingReviewList;
	}
	

}
