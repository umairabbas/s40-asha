package com.tenpearls.olaround.entities;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.tenpearls.olaround.constants.JSONConstants;

public class FriendsCheckins {

    private int count;
	private String result;
	private Vector checkins;

	public FriendsCheckins(JSONObject jsonObj){
		try {
			setCheckins(jsonObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Vector getCheckins() {
		return checkins;
	}
	
	public void setCheckins(JSONObject jsonObj) throws JSONException {

		
		checkins = new Vector();
		JSONArray jsonArray = jsonObj.getJSONArray(JSONConstants.CHECKINS_TAG);
		
		for (int i=0 ; i < jsonArray.length() ; i++) {
			
			JSONObject checkinsJSON = jsonArray.getJSONObject(i);
			Checkins checks = new Checkins();
			checks.setFirstName(checkinsJSON.getString(JSONConstants.CHECKINS_FIRSTNAME));
			checks.setLastName(checkinsJSON.getString(JSONConstants.CHECKINS_LASTNAME));
			if(checkinsJSON.has(JSONConstants.CHECKINS_DISPLAYPICURL)) {
				checks.setDisplayPicUrl(checkinsJSON.getString(JSONConstants.CHECKINS_DISPLAYPICURL));
			}
			checkins.addElement(checks);
		}
	}
	
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
		//System.out.println("**************COUNT*****************->"+count);
	}
	
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
		//System.out.println("**************RESULT*****************->"+result);
	}
	

}
