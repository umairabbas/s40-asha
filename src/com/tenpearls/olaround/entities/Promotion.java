package com.tenpearls.olaround.entities;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.tenpearls.olaround.constants.JSONConstants;

public class Promotion {
	private String promotionsTitle;
	private String promotionsDescription;
	
	public void setData(JSONObject jsonObj) {

		try {
			if(jsonObj.has(JSONConstants.PROMOTION_TITLE_TAG)) {
			setPromotionsTitle(jsonObj.getString(JSONConstants.PROMOTION_TITLE_TAG));
			}
			if(jsonObj.has(JSONConstants.PROMOTION_DESCRIPTION_TAG)) {
				setPromotionsDescription(jsonObj.getString(JSONConstants.PROMOTION_DESCRIPTION_TAG));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getPromotionsTitle() {
		return promotionsTitle;
	}
	
	public void setPromotionsTitle(String promotionsTitle) {
		this.promotionsTitle = promotionsTitle;
	}
	
	public String getPromotionsDescription() {
		return promotionsDescription;
	}
	
	public void setPromotionsDescription(String promotionsDescription) {
		this.promotionsDescription = promotionsDescription;
	}
}
