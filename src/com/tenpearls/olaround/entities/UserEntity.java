package com.tenpearls.olaround.entities;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.tenpearls.olaround.constants.JSONConstants;

public class UserEntity {
	
	private int userId;
	
	private String userName;
	private String firstName;
	private String lastName;
	private String profilePictureURL;
	private String email;
	private String tokenType;
	private String accessToken;
	private String refreshToken;
	private long tokenExpiryTime;
	
	
	public void setData(JSONObject jsonObj) {
		try {
			JSONObject userJSON = jsonObj.getJSONObject(JSONConstants.USER_TAG);
			setUserName(userJSON.getString(JSONConstants.USER_NAME_TAG));
			
			if(userJSON.has(JSONConstants.USER_FIRST_NAME_TAG)) {
				setFirstName(userJSON.getString(JSONConstants.USER_FIRST_NAME_TAG));
			}
			
			if(userJSON.has(JSONConstants.USER_LAST_NAME_TAG)) {
				setLastName(userJSON.getString(JSONConstants.USER_LAST_NAME_TAG));
			}
			
			if(userJSON.has(JSONConstants.USER_DISPLAY_PICTURE_TAG)) {
				setProfilePictureURL(userJSON.getString(JSONConstants.USER_DISPLAY_PICTURE_TAG));
			}
			
			if(userJSON.has(JSONConstants.USER_ID_TAG)) {
				setUserId(Integer.parseInt(userJSON.getString(JSONConstants.USER_ID_TAG)));
			} else if(userJSON.has(JSONConstants.ID_TAG)) {
				setUserId(Integer.parseInt(userJSON.getString(JSONConstants.ID_TAG)));
			}
			
			if(userJSON.has(JSONConstants.USER_EMAIL_TAG)) {
				setEmail(userJSON.getString(JSONConstants.USER_EMAIL_TAG));
			}
			
			setTokenType(jsonObj.getString(JSONConstants.USER_TOKEN_TYPE_TAG));
			setAccessToken(jsonObj.getString(JSONConstants.USER_ACCESS_TOKEN_TAG));
			setRefreshToken(jsonObj.getString(JSONConstants.USER_REFRESH_TOKEN_TAG));
			setTokenExpiryTime(Long.parseLong(jsonObj.getString(JSONConstants.USER_TOKEN_EXPIRE_TAG)));
		} catch (JSONException e) {
			System.out.println("Failed: setData for user");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
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
	
	public String getProfilePictureURL() {
		return profilePictureURL;
	}
	
	public void setProfilePictureURL(String profilePictureURL) {
		this.profilePictureURL = profilePictureURL;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public long getTokenExpiryTime() {
		return tokenExpiryTime;
	}
	
	public void setTokenExpiryTime(long tokenExpiryTime) {
		this.tokenExpiryTime = tokenExpiryTime;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
