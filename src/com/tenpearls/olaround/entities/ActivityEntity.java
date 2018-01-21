package com.tenpearls.olaround.entities;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.tenpearls.olaround.constants.JSONConstants;

public class ActivityEntity {
	
	private int id;
	private String name;
	private String displayPicURL;
	private String venueName;
	private String type;
	
	public void setData(JSONObject jsonObj) { 
		
		try {
			setId(Integer.parseInt(jsonObj.getString(JSONConstants.ID_TAG)));
			if(jsonObj.has(JSONConstants.ACTIVITY_TYPE_TAG)) {
				setType(jsonObj.getString(JSONConstants.ACTIVITY_TYPE_TAG));
			} 
		
			if(jsonObj.has(JSONConstants.ACTIVITY_DATA_TAG)) {
				JSONObject data = jsonObj.getJSONObject(JSONConstants.ACTIVITY_DATA_TAG);
				if(data.has(JSONConstants.DISPLAY_PICTURE_TAG)) {
					setDisplayPicURL(data.getString(JSONConstants.DISPLAY_PICTURE_TAG));
				}
				if(data.has(JSONConstants.ACTIVITY_NAME_TAG)) {
					setName(data.getString(JSONConstants.ACTIVITY_NAME_TAG));
				} else if(data.has("first_name")) {
					setName(data.getString("first_name"));
				}
				
				if(data.has(JSONConstants.ACTIVITY_VENUE_TAG)) {
					setVenueName(data.getString(JSONConstants.ACTIVITY_VENUE_TAG));
				}
				data = null;
			}
			jsonObj = null;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDisplayPicURL() {
		return displayPicURL;
	}
	
	public void setDisplayPicURL(String displayPicURL) {
		this.displayPicURL = displayPicURL;
	}
	
	public String getVenueName() {
		return venueName;
	}
	
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
