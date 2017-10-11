package com.telecom.weibao.entity;

import java.sql.Timestamp;

public class HMReview {
	private long rewiewId;
	private HM hm=new HM();
	private TelecomPersonnel reviewer=new TelecomPersonnel();
	private Manufacturer changer = new Manufacturer();
	private int timeliness;
	private int satisfaction;
	private String reviewContent;
	private Timestamp reviewTime;
	private int reviewType;
	public long getRewiewId() {
		return rewiewId;
	}
	public void setRewiewId(long rewiewId) {
		this.rewiewId = rewiewId;
	}
	public HM getHm() {
		return hm;
	}
	public void setHm(HM hm) {
		this.hm = hm;
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
	public TelecomPersonnel getReviewer() {
		return reviewer;
	}
	public void setReviewer(TelecomPersonnel reviewer) {
		this.reviewer = reviewer;
	}
	public Manufacturer getChanger() {
		return changer;
	}
	public void setChanger(Manufacturer changer) {
		this.changer = changer;
	}
	public int getReviewType() {
		return reviewType;
	}
	public void setReviewType(int reviewType) {
		this.reviewType = reviewType;
	}
	@Override
	public String toString() {
		return "HMReview [rewiewId=" + rewiewId + ", hm=" + hm + ", reviewer=" + reviewer + ", changer=" + changer
				+ ", timeliness=" + timeliness + ", satisfaction=" + satisfaction + ", reviewContent=" + reviewContent
				+ ", reviewTime=" + reviewTime + ", reviewType=" + reviewType + "]";
	}	
	
}
