package com.telecom.weibao.entity;

import java.sql.Timestamp;

public class CruisingReview {
	private long rewiewId;
	private Cruising cruising=new Cruising();
	private TelecomPersonnel reviewer=new TelecomPersonnel();
	private int satisfaction;
	private String reviewContent;
	private Timestamp reviewTime;
	public long getRewiewId() {
		return rewiewId;
	}
	public void setRewiewId(long rewiewId) {
		this.rewiewId = rewiewId;
	}
	public Cruising getCruising() {
		return cruising;
	}
	public void setCruising(Cruising cruising) {
		this.cruising = cruising;
	}
	public TelecomPersonnel getReviewer() {
		return reviewer;
	}
	public void setReviewer(TelecomPersonnel reviewer) {
		this.reviewer = reviewer;
	}
	public int getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	public Timestamp getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}
	
}
