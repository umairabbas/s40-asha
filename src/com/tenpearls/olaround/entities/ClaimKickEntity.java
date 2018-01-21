package com.tenpearls.olaround.entities;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.tenpearls.olaround.constants.JSONConstants;
import com.tenpearls.olaround.helpers.Utils;

public class ClaimKickEntity {
	private int id;
	private String title;
	private String result;
	private String venueName;

	
	public void setData(JSONObject kick) {
		try {
			if(kick.has(JSONConstants.CLAIM_KICK_ID)) {
				setId(Integer.parseInt(kick.getString(JSONConstants.CLAIM_KICK_ID)));
			}
			
			if(kick.has(JSONConstants.CLAIM_KICK_VENUE_NAME)) {
				setVenueName(kick.getString(JSONConstants.CLAIM_KICK_VENUE_NAME));		
			}
			if(kick.has(JSONConstants.KICK_REWARD_TAG)) {
				
				JSONObject reward = kick.getJSONObject(JSONConstants.KICK_REWARD_TAG);
			
				if(reward.has(JSONConstants.CLAIM_KICK_TITLE)) {
					setTitle(reward.getString(JSONConstants.CLAIM_KICK_TITLE));		
				}
				if(reward.has(JSONConstants.CLAIM_KICK_RESULT)) {
					setResult(reward.getString(JSONConstants.CLAIM_KICK_RESULT));		
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getVenueName() {
		return venueName;
	}
	
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	
}
