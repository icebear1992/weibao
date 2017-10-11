package com.telecom.weibao.entity;

import java.sql.Timestamp;

public class SPRReview {
	private long reviewId;
	private SPR spr=new SPR();
	private TelecomPersonnel reviewer=new TelecomPersonnel();
	private int timeliness;
	private int satisfaction;
	private String reviewContent;
	private Timestamp reviewTime;
	public long getReviewId() {
		return reviewId;
	}
	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	public SPR getSpr() {
		return spr;
	}
	public void setSpr(SPR spr) {
		this.spr = spr;
	}
	public TelecomPersonnel getReviewer() {
		return reviewer;
	}
	public void setReviewer(TelecomPersonnel reviewer) {
		this.reviewer = reviewer;
	}
	public int getTimeliness() {
		return timeliness;
	}
	public void setTimeliness(int timeliness) {
		this.timeliness = timeliness;
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
