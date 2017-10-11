package com.telecom.weibao.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Training {
	private long tId;
	private SubRegion subRegion=new SubRegion();
	private Major major=new Major();
	private String trainingName;
	private Date startTime;
	private Date endTime;
	private double totalDuration;
	private String trainingFormat;
	private String trainingContent;
	private String trainingPlace;
	private int traineesnum;
	private long createrId;
	TelecomPersonnel auditor=new TelecomPersonnel();
	ManufacturerPersonnel mp=new ManufacturerPersonnel();
	private Timestamp creatTime;
	private int currentProcess;
	private List<TrainingReview> trainingReviewList=new ArrayList<TrainingReview>();
	public long gettId() {
		return tId;
	}
	public void settId(long tId) {
		this.tId = tId;
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
	public String getTrainingName() {
		return trainingName;
	}
	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
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
	public String getTrainingFormat() {
		return trainingFormat;
	}
	public void setTrainingFormat(String trainingFormat) {
		this.trainingFormat = trainingFormat;
	}
	public String getTrainingContent() {
		return trainingContent;
	}
	public void setTrainingContent(String trainingContent) {
		this.trainingContent = trainingContent;
	}
	public String getTrainingPlace() {
		return trainingPlace;
	}
	public void setTrainingPlace(String trainingPlace) {
		this.trainingPlace = trainingPlace;
	}
	public int getTraineesnum() {
		return traineesnum;
	}
	public void setTraineesnum(int traineesnum) {
		this.traineesnum = traineesnum;
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
	public Timestamp getCreatTime() {
		return creatTime;
	}
	public List<TrainingReview> getTrainingReviewList() {
		return trainingReviewList;
	}
	public void setTrainingReviewList(List<TrainingReview> trainingReviewList) {
		this.trainingReviewList = trainingReviewList;
	}
	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}
	public int getCurrentProcess() {
		return currentProcess;
	}
	public void setCurrentProcess(int currentProcess) {
		this.currentProcess = currentProcess;
	}	

}
