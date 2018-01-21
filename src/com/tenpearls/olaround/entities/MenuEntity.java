package com.tenpearls.olaround.entities;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.tenpearls.olaround.constants.JSONConstants;

public class MenuEntity {
	
	private String name;
	private Vector menuItem;
	
	public void setData(JSONObject jsonObj) {
		try {
			if(jsonObj.has(JSONConstants.MENU_NAME_TAG)) {
				setName(jsonObj.getString(JSONConstants.MENU_NAME_TAG));
			}
			if(jsonObj.has(JSONConstants.MENU_ITEMS_TAG)) {
				JSONArray jsonArray = jsonObj.getJSONArray(JSONConstants.MENU_ITEMS_TAG);
				menuItem = new Vector();
				for (int i=0 ; i < jsonArray.length() ; i++) {
					JSONObject jsonItem = jsonArray.getJSONObject(i);
					MenuItem item = new MenuItem();
					if(jsonItem.has(JSONConstants.ITEM_NAME_TAG)) {
						item.setItemName(jsonItem.getString(JSONConstants.ITEM_NAME_TAG));
					}
					if(jsonItem.has(JSONConstants.ITEM_PRICE_TAG)) {
						item.setPrice(Float.parseFloat(jsonItem.getString(JSONConstants.ITEM_PRICE_TAG)));
					}
					menuItem.addElement(item);
					jsonItem = null;
					item = null;
				}
				jsonArray = null;
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
	
	public Vector getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(Vector menuItem) {
		this.menuItem = menuItem;
	}
	
	public class MenuItem {
		private float price;
		private String itemName;
		
		public float getPrice() {
			return price;
		}
		public void setPrice(float price) {
			this.price = price;
		}
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
	}


}
