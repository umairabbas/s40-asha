package com.tenpearls.olaround.entities;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.tenpearls.olaround.constants.JSONConstants;

public class FriendsEntity {

	private String firstName;
	private String lastName;
	private String displayPictureUrl;
	
	public void setData(JSONObject friendJSON) {
		try {
			
			if(friendJSON.has(JSONConstants.FRIEND_FIRST_NAME_TAG)) {
				setFirstName(friendJSON.getString(JSONConstants.FRIEND_FIRST_NAME_TAG));
			}
			
			if(friendJSON.has(JSONConstants.FRIEND_LAST_NAME_TAG)) {
				setLastName(friendJSON.getString(JSONConstants.FRIEND_LAST_NAME_TAG));
			}
			
			if(friendJSON.has(JSONConstants.FRIEND_FIRST_NAME_TAG)) {
				setDisplayPictureUrl(friendJSON.getString(JSONConstants.FRIEND_DISPLAY_PICTURE_TAG));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
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
	
	public String getDisplayPictureUrl() {
		return displayPictureUrl;
	}
	
	public void setDisplayPictureUrl(String displayPictureUrl) {
		this.displayPictureUrl = displayPictureUrl;
	}
	
}
