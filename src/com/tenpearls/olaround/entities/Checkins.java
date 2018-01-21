package com.tenpearls.olaround.entities;

public class Checkins {

    private int userId;
    private int displayPicId;
    private int fbUserId;
    private String firstName;
    private String lastName;
    private String displayPicUrl;
    
    public Checkins(){
    	
    }
    

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getDisplayPicId() {
		return displayPicId;
	}
	
	public void setDisplayPicId(int displayPicId) {
		this.displayPicId = displayPicId;
	}
	
	public int getFbUserId() {
		return fbUserId;
	}
	
	public void setFbUserId(int fbUserId) {
		this.fbUserId = fbUserId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getDisplayPicUrl() {
		return displayPicUrl;
	}
	
	public void setDisplayPicUrl(String displayPicUrl) {
		this.displayPicUrl = displayPicUrl;
	}


}
