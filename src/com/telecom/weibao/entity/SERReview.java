package com.telecom.weibao.entity;

import java.sql.Timestamp;

public class SERReview {
	private long serReviewId;
	private SER ser = new SER();
	private TelecomPersonnel reviewer = new TelecomPersonnel();
	private Manufacturer changer = new Manufacturer();
	private int serviceSatisfaction;
	private int resultSatisfaction;
	private String reviewContent;
	private Timestamp reviewTime;
	private int reviewType;
	public long getSerReviewId() {
		return serReviewId;
	}
	public void setSerReviewId(long serReviewId) {
		this.serReviewId = serReviewId;
	}
	public SER getSer() {
		return ser;
	}
	public void setSer(SER ser) {
		this.ser = ser;
	}
	public TelecomPersonnel getReviewer() {
		return reviewer;
	}
	public void setReviewer(TelecomPersonnel reviewer) {
		this.reviewer = reviewer;
	}
	public int getServiceSatisfaction() {
		return serviceSatisfaction;
	}
	public void setServiceSatisfaction(int serviceSatisfaction) {
		this.serviceSatisfaction = serviceSatisfaction;
	}
	public int getResultSatisfaction() {
		return resultSatisfaction;
	}
	public void setResultSatisfaction(int resultSatisfaction) {
		this.resultSatisfaction = resultSatisfaction;
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
	public int getReviewType() {
		return reviewType;
	}
	public void setReviewType(int reviewType) {
		this.reviewType = reviewType;
	}
	
	public Manufacturer getChanger() {
		return changer;
	}
	public void setChanger(Manufacturer changer) {
		this.changer = changer;
	}
	@Override
	public String toString() {
		return "SERReview [serReviewId=" + serReviewId + ", ser=" + ser + ", reviewer=" + reviewer
				+ ", serviceSatisfaction=" + serviceSatisfaction + ", resultSatisfaction=" + resultSatisfaction
				+ ", reviewContent=" + reviewContent + ", reviewTime=" + reviewTime + ", reviewType=" + reviewType
				+ "]";
	}
}
