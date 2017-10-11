package com.telecom.weibao.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SER {
	private long serId;
	private SubRegion subRegion=new SubRegion();
	private NEModel neModel=new NEModel();
	private Timestamp startTime;
	private Timestamp endTime;
	private double totalDuration;
	private String majorHandler;
	private String supportMethod;
	private String contactinfo;
	private double telnetServDuration;
	private double fieldServDuration;
	private DemandCategory category=new DemandCategory();
	private String demandInfo;
	private String solution;
	private int demandDegree;
	private String remarksInfo;
	private ManufacturerPersonnel mp=new ManufacturerPersonnel();
	private long createrId;
	private TelecomPersonnel auditor=new TelecomPersonnel();
	private Timestamp creatTime;
	private List<SERReview> serReviewList=new ArrayList<SERReview>();
	private int currentProcess;
	private String title;
	public long getSerId() {
		return serId;
	}
	public void setSerId(long serId) {
		this.serId = serId;
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
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public double getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(double totalDuration) {
		this.totalDuration = totalDuration;
	}
	public String getSupportMethod() {
		return supportMethod;
	}
	public void setSupportMethod(String supportMethod) {
		this.supportMethod = supportMethod;
	}
	public String getContactinfo() {
		return contactinfo;
	}
	public void setContactinfo(String contactinfo) {
		this.contactinfo = contactinfo;
	}
	public String getMajorHandler() {
		return majorHandler;
	}
	public void setMajorHandler(String majorHandler) {
		this.majorHandler = majorHandler;
	}
	public double getTelnetServDuration() {
		return telnetServDuration;
	}
	public void setTelnetServDuration(double telnetServDuration) {
		this.telnetServDuration = telnetServDuration;
	}
	public double getFieldServDuration() {
		return fieldServDuration;
	}
	public void setFieldServDuration(double fieldServDuration) {
		this.fieldServDuration = fieldServDuration;
	}
	public DemandCategory getCategory() {
		return category;
	}
	public void setCategory(DemandCategory category) {
		this.category = category;
	}
	public String getDemandInfo() {
		return demandInfo;
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
	public int getCurrentProcess() {
		return currentProcess;
	}
	public void setCurrentProcess(int currentProcess) {
		this.currentProcess = currentProcess;
	}
	public void setDemandInfo(String demandInfo) {
		this.demandInfo = demandInfo;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public int getDemandDegree() {
		return demandDegree;
	}
	public void setDemandDegree(int demandDegree) {
		this.demandDegree = demandDegree;
	}
	public String getRemarksInfo() {
		return remarksInfo;
	}
	public void setRemarksInfo(String remarksInfo) {
		this.remarksInfo = remarksInfo;
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
	public List<SERReview> getSerReviewList() {
		return serReviewList;
	}
	public void setSerReviewList(List<SERReview> serReviewList) {
		this.serReviewList = serReviewList;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "SER [serId=" + serId + ", subRegion=" + subRegion + ", neModel=" + neModel + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", totalDuration=" + totalDuration + ", majorHandler=" + majorHandler
				+ ", supportMethod=" + supportMethod + ", contactinfo=" + contactinfo + ", telnetServDuration="
				+ telnetServDuration + ", fieldServDuration=" + fieldServDuration + ", category=" + category
				+ ", demandInfo=" + demandInfo + ", solution=" + solution + ", demandDegree=" + demandDegree
				+ ", remarksInfo=" + remarksInfo + ", mp=" + mp + ", createrId=" + createrId + ", auditor=" + auditor
				+ ", creatTime=" + creatTime + ", serReviewList=" + serReviewList + ", currentProcess=" + currentProcess
				+ "]";
	}	
	
}
