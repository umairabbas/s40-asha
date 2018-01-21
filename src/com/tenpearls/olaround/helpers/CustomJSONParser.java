package com.tenpearls.olaround.helpers;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.sun.lwuit.List;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.JSONConstants;
import com.tenpearls.olaround.entities.ActivityEntity;
import com.tenpearls.olaround.entities.ClaimKickEntity;
import com.tenpearls.olaround.entities.DealsEntity;
import com.tenpearls.olaround.entities.FriendsEntity;
import com.tenpearls.olaround.entities.KickEntity;
import com.tenpearls.olaround.entities.MenuEntity;
import com.tenpearls.olaround.entities.UserEntity;
import com.tenpearls.olaround.entities.VenueEntity;

public class CustomJSONParser {

	
	public static String parseForNoCredentials(String streamData) throws JSONException {

		String errorMsg = "";
		JSONObject jsonObject = null;
		if (streamData != "") {
			jsonObject = new JSONObject(streamData);
			errorMsg = jsonObject.getString(JSONConstants.ERROR_DESCRIPTION_TAG);
			jsonObject = null;
		}
		return errorMsg;
	}
	
	public static String parseForError(String streamData) throws JSONException {
		String errorMsg = "";
		try {
			
			JSONObject jsonObject = null;
			if (streamData != "") {
				jsonObject = new JSONObject(streamData);
				errorMsg = jsonObject.getString(JSONConstants.ERROR_DESCRIPTION_TAG);
				jsonObject = null;
			}
		} catch(JSONException e) {
			errorMsg = ApplicationConstants.MSG_SERVER_NOT_RESPONDING;
		}
			
		return errorMsg;
	}
	
	public static List parseVenuJSON(String streamData) {
		List list = null;
		try {
			JSONArray jsonArray = new JSONArray(streamData);

			if (jsonArray != null) {
				list = new List();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					VenueEntity entity = new VenueEntity();
					entity.setData(obj);
					list.addItem(entity);
					entity = null;
					obj = null;
				}
				jsonArray = null;
			} 
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static VenueEntity parseVenuItemJSON(String streamData) {
		VenueEntity entity = new VenueEntity();
		try {
			JSONObject jsonObject = new JSONObject(streamData);
			if (jsonObject !=null) {
				entity.setData(jsonObject);
				jsonObject = null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return entity;
	}
	
	public static UserEntity parseUserJSON(String streamData) {
		UserEntity entity = null;
		try {
			
			JSONObject jsonObj = new JSONObject(streamData);
			if (jsonObj != null) {
				entity = new UserEntity();
				entity.setData(jsonObj);
				jsonObj = null;
			}
		} catch (JSONException e) {
			System.out.println("Parsing error");
			e.printStackTrace();
		}
		return entity;
	}
	
	public static List parseMenuJSON(String streamData) {
		List list =  null;
		try {	
			JSONArray jsonArray = new JSONArray(streamData);

			if (jsonArray != null) {
				list = new List();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					MenuEntity entity = new MenuEntity();
					entity.setData(obj);
					list.addItem(entity);
					entity = null;
					obj = null;
				}
				jsonArray = null;
			} 
			
		} catch (JSONException e) {
			System.out.println("Parsing error");
			e.printStackTrace();
		}
		return list;
	}
	
	public static List parseFriendActivityJSON(String streamData) {
		List list =  null;
		try {	
			JSONArray jsonArray = new JSONArray(streamData);

			if (jsonArray != null) {
				list = new List();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					ActivityEntity entity = new ActivityEntity();
					entity.setData(obj);
					list.addItem(entity);
					entity = null;
					obj = null;
				}
				jsonArray = null;
			} 
			
		} catch (JSONException e) {
			System.out.println("Parsing error");
			e.printStackTrace();
		}
		return list;
	}
	
	public static String parsePunchedJSON(String streamData) {
	
		try {	
			JSONObject jsonObj = new JSONObject(streamData);
			if(jsonObj.has(JSONConstants.VENUE)) {
				return jsonObj.getString(JSONConstants.VENUE); 
			}

		} catch (JSONException e) {
			System.out.println("Parsing error");
			e.printStackTrace();
		}
		return null;
	}
	
	public static String parseUserIdJSON(String streamData) {

		try {
			JSONObject jsonObj = new JSONObject(streamData);
			String City = null;
			String Country = null;
			String Location = null;
			if (jsonObj.has(JSONConstants.USER_CITY)
					&& (jsonObj.has(JSONConstants.USER_COUNTRY))) {
				City = jsonObj.getString(JSONConstants.USER_CITY);
				Country = jsonObj.getString(JSONConstants.USER_COUNTRY);
				Location = City + " " + Country;
				return Location;
			} else {
				return null;
			}
		} catch (JSONException e) {
			System.out.println("Parsing error");
			e.printStackTrace();
		}
		return null;
	}
	
	public static List parseDealsJSON(String streamData) {
		List list = null;
		try {	
			JSONArray jsonArray = new JSONArray(streamData);

			if (jsonArray != null) {
				list = new List();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					
					JSONArray promotionArray = jsonObj.getJSONArray(JSONConstants.PROMOTION_TAG);
					
					for (int j = 0; j < promotionArray.length(); j++) {
						DealsEntity entity = new DealsEntity();
						entity.setDealsData(jsonObj);
						JSONObject promotionObj = promotionArray.getJSONObject(j);
						entity.setPromotionData(promotionObj);
						list.addItem(entity);
						entity = null;
					}
					jsonObj = null;
				}
				
				jsonArray = null;
			} 
			

		} catch (JSONException e) {
			System.out.println("Parsing error");
			e.printStackTrace();
		}
		return list;
	}
	
	public static List parseDealsVenueJSON(String streamData) {
		List list = null;
		try {	
			JSONObject jsonObj = new JSONObject(streamData);

			if (jsonObj != null) {
	
					JSONArray promotionArray = jsonObj.getJSONArray(JSONConstants.PROMOTION_TAG);
					list = new List();			
					
					for (int j = 0; j < promotionArray.length(); j++) {
						DealsEntity entity = new DealsEntity();
						entity.setDealsData(jsonObj);
						JSONObject promotionObj = promotionArray.getJSONObject(j);
						entity.setPromotionData(promotionObj);
						list.addItem(entity);
						entity = null;
					}
					jsonObj = null;
				}			

		} catch (JSONException e) {
			System.out.println("Parsing error");
			e.printStackTrace();
		}
		return list;
	}
	
	public static List parseFriendsJSON(String streamData) {
		List list = null;
		try {	
			JSONArray friendsArray = new JSONArray(streamData);

			if (friendsArray != null) {

				list = new List();			
				for (int j = 0; j < friendsArray.length(); j++) {
					FriendsEntity entity = new FriendsEntity();
					JSONObject friendObj = friendsArray.getJSONObject(j);
					entity.setData(friendObj);
					list.addItem(entity);
					entity = null;
					friendObj = null;
				}
				friendsArray = null;	
			}			

		} catch (JSONException e) {
			System.out.println("Parsing error");
			e.printStackTrace();
		}
		return list;
	}
	

	public static List parseKickJSON(String streamData) {
		List list = null;
		try {	
			JSONArray kickArray = new JSONArray(streamData);

			if (kickArray != null) {

				list = new List();			
				for (int i = 0; i < kickArray.length(); i++) {
					KickEntity entity = new KickEntity();
					JSONObject kickObj = kickArray.getJSONObject(i);
					entity.setData(kickObj);
					list.addItem(entity);
					entity = null;
					kickObj = null;
				}
				kickArray = null;	
			}			

		} catch (JSONException e) {
			System.out.println("Parsing error");
			e.printStackTrace();
		}
		return list;
	}
	
	public static ClaimKickEntity parseClaimkickJSON(String streamData) {
		ClaimKickEntity kick = null;
		try {
			JSONObject jsonObj = new JSONObject(streamData);

			if (jsonObj != null) {
				kick = new ClaimKickEntity();
				kick.setData(jsonObj);
				jsonObj = null;
			} 
		} catch (JSONException e) {
			System.out.println("Parsing error");
			e.printStackTrace();
		}
		return kick;
	}
}
