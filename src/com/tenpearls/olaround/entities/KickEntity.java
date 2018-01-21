package com.tenpearls.olaround.entities;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.tenpearls.olaround.constants.JSONConstants;
import com.tenpearls.olaround.helpers.Utils;

public class KickEntity {
	private int id;
	private String title;
	private int cost;
	private boolean isActive;
	private int userPointBalance;
	private static int POINT_NOT_AVAILABLE = 0;
	
	public void setData(JSONObject kick) {
		try {
			if(kick.has(JSONConstants.KICK_ID_TAG)) {
				setId(Integer.parseInt(kick.getString(JSONConstants.KICK_ID_TAG)));
			}
			
			if(kick.has(JSONConstants.KICK_TITLE_TAG)) {
				setTitle(kick.getString(JSONConstants.KICK_TITLE_TAG));		
			}
			
			if(kick.has(JSONConstants.KICK_IS_ACTIVE_TAG)) {
				setActive(Utils.toBoolean(kick.getString(JSONConstants.KICK_IS_ACTIVE_TAG)));
			}
			
			if(kick.has(JSONConstants.KICK_COST_TAG)) {
				setCost(Integer.parseInt(kick.getString(JSONConstants.KICK_COST_TAG)));
			}
			
			if(kick.has(JSONConstants.USER_BRAND_POINT_TAG)) {
				JSONObject user = kick.getJSONObject(JSONConstants.USER_BRAND_POINT_TAG);
				if(user.has(JSONConstants.USER_POINT_BALANCE_TAG)) {
					setUserPointBalance(Integer.parseInt(user.getString(JSONConstants.USER_POINT_BALANCE_TAG)));
				}
		
			} else {
				setUserPointBalance(POINT_NOT_AVAILABLE);
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

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getUserPointBalance() {
		return userPointBalance;
	}

	public void setUserPointBalance(int userPointBalance) {
		this.userPointBalance = userPointBalance;
	}
}
