package com.tenpearls.olaround.entities;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.tenpearls.olaround.constants.JSONConstants;

public class DealsEntity {
	
	private int storeId;
	private double distance;
	private String storeName;
	private String storeCategoriesName;
	private Promotion promotions;
	private String brandPictureUrl;
	private String brandBackGroundPictureUrl;
	
	public void setDealsData(JSONObject jsonObj) {
		
		try {
			if(jsonObj.has(JSONConstants.DEAL_STORE_ID_TAG)) {
				setStoreId(Integer.parseInt(jsonObj.getString(JSONConstants.DEAL_STORE_ID_TAG)));
			}

			if(jsonObj.has(JSONConstants.DEAL_STORE_NAME_TAG)) {
				setStoreName(jsonObj.getString(JSONConstants.DEAL_STORE_NAME_TAG));
			}
			
			if(jsonObj.has(JSONConstants.DEAL_DISTANCE_TAG)) {
				setDistance(Double.parseDouble(jsonObj.getString(JSONConstants.DEAL_DISTANCE_TAG)));
			}
			
			if(jsonObj.has(JSONConstants.DEAL_STORE_CATEGORY_NAME_TAG)) {
				setStoreCategoriesName(jsonObj.getString(JSONConstants.DEAL_STORE_CATEGORY_NAME_TAG));
			}
			
			if(jsonObj.has(JSONConstants.DEAL_BRAND_BG_PICTURE_TAG)) {
				setBrandBackGroundPictureUrl(jsonObj.getString(JSONConstants.DEAL_BRAND_BG_PICTURE_TAG));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setPromotionData(JSONObject jsonObj) throws JSONException {
		promotions = new Promotion();
		promotions.setData(jsonObj);
	}
	
	public int getStoreId() {
		return storeId;
	}
	
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public String getStoreName() {
		return storeName;
	}
	
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public String getStoreCategoriesName() {
		return storeCategoriesName;
	}
	
	public void setStoreCategoriesName(String storeCategoriesName) {
		this.storeCategoriesName = storeCategoriesName;
	}

	public Promotion getPromotions() {
		return promotions;
	}

	public void setPromotions(Promotion promotions) {
		this.promotions = promotions;
	}

	public String getBrandPictureUrl() {
		return brandPictureUrl;
	}

	public void setBrandPictureUrl(String brandPictureUrl) {
		this.brandPictureUrl = brandPictureUrl;
	}

	public String getBrandBackGroundPictureUrl() {
		return brandBackGroundPictureUrl;
	}

	public void setBrandBackGroundPictureUrl(String brandBackGroundPictureUrl) {
		this.brandBackGroundPictureUrl = brandBackGroundPictureUrl;
	}
}
