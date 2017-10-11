package com.telecom.weibao.entity;

import java.sql.Timestamp;

public class TrainingReview {
	private long reviewId;
	private Training training;
	private TelecomPersonnel reviewer = new TelecomPersonnel();
	private int satisfaction;
	private String reviewContent;
	private Timestamp reviewTime;
	public long getReviewId() {
		return reviewId;
	}
	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	public Training getTraining() {
		return training;
	}
	public void setTraining(Training training) {
		this.training = training;
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
