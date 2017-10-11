package com.telecom.weibao.entity;

import java.sql.Timestamp;

public class SSReview {
	private long ssReviewId;
	private SS ss=new SS();
	private TelecomPersonnel reviewer=new TelecomPersonnel();
	private int satisfaction;
	private String reviewContent;
	private Timestamp reviewTime;
	private int CurrentProcess;
	public long getSsReviewId() {
		return ssReviewId;
	}
	public void setSsReviewId(long ssReviewId) {
		this.ssReviewId = ssReviewId;
	}
	public SS getSs() {
		return ss;
	}
	public void setSs(SS ss) {
		this.ss = ss;
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
	public int getCurrentProcess() {
		return CurrentProcess;
	}
	public void setCurrentProcess(int currentProcess) {
		CurrentProcess = currentProcess;
	}
}
