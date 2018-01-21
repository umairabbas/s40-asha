package com.tenpearls.olaround.entities;

public class Popularity {
  	  
	private int checkinCount;
	private int punchCount;
	private int score;
	private int normalizedScore;
	private int displayScore;
	
	public int getCheckinCount() {
		return checkinCount;
	}
	
	public void setCheckinCount(int checkinCount) {
		this.checkinCount = checkinCount;
	}
	
	public int getPunchCount() {
		return punchCount;
	}
	
	public void setPunchCount(int punchCount) {
		this.punchCount = punchCount;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getNormalizedScore() {
		return normalizedScore;
	}
	
	public void setNormalizedScore(int normalizedScore) {
		this.normalizedScore = normalizedScore;
	}
	
	public int getDisplayScore() {
		return displayScore;
	}
	
	public void setDisplayScore(int displayScore) {
		this.displayScore = displayScore;
	}

}
