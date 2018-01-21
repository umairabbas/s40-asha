package com.tenpearls.olaround.entities;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.tenpearls.olaround.constants.JSONConstants;

public class VenueEntity {
	
	private int id;
	private String name;
	private int isPremium;
	private String distance;
	private String locatlity;
	private String pictureURL;
	private Vector categories;
	private Brand brand;
	private Contact contact;
	private Location location;
	private Popularity popularity;
	private String operatingHours;
	private int dineIn;
	private int homeDelivery;
	private int smokingArea;
	private int wifi;
	private int driveThrough;
	private FriendsCheckins friendsCheckins;
	
	public void setData(JSONObject jsonObj) {
		try {
			if(jsonObj.has(JSONConstants.VENUE_ID_TAG)) {
				setId(Integer.parseInt(jsonObj.getString(JSONConstants.VENUE_ID_TAG)));
			}
			
			if(jsonObj.has(JSONConstants.VENUE_NAME_TAG)) {
				setName(jsonObj.getString(JSONConstants.VENUE_NAME_TAG)); 
			}
			
			if(jsonObj.has(JSONConstants.OPERATING_HOURS)) {
				setOperatingHours((jsonObj.getString(JSONConstants.OPERATING_HOURS)));
			}
			if(jsonObj.has(JSONConstants.DINE_IN)) {
				setDineIn(Integer.parseInt(jsonObj.getString(JSONConstants.DINE_IN)));
			}
			if(jsonObj.has(JSONConstants.HOME_DELIVERY)) {
				setHomeDelivery(Integer.parseInt(jsonObj.getString(JSONConstants.HOME_DELIVERY)));
			}
			if(jsonObj.has(JSONConstants.SMOKING_AREA)) {
				setSmokingArea(Integer.parseInt(jsonObj.getString(JSONConstants.SMOKING_AREA)));
			}
			if(jsonObj.has(JSONConstants.WIFI)) {
				setWifi(Integer.parseInt(jsonObj.getString(JSONConstants.WIFI)));
			}
			if(jsonObj.has(JSONConstants.DRIVE_THROUGH)) {
				setDriveThrough(Integer.parseInt(jsonObj.getString(JSONConstants.DRIVE_THROUGH)));
			}
			
			if(jsonObj.has(JSONConstants.VENUE_IS_PREMIUM_TAG)) {
				setIsPremium(Integer.parseInt(jsonObj.getString(JSONConstants.VENUE_IS_PREMIUM_TAG)));
			}
			
			if(jsonObj.has(JSONConstants.VENUE_DISTANCE)) {
				setDistance(jsonObj.getString(JSONConstants.VENUE_DISTANCE));
			}
			
			if(jsonObj.has(JSONConstants.VENUE_LOCALITY)) {
				setLocatlity(jsonObj.getString(JSONConstants.VENUE_LOCALITY));
			}
			
			if(jsonObj.has(JSONConstants.VENUE_PICTURE)) {
				setPictureURL(jsonObj.getString(JSONConstants.VENUE_PICTURE));
			}

			setCategoryData(jsonObj);
			setBrandData(jsonObj);
			setContactData(jsonObj);
			setLocationData(jsonObj);
			setPopularityData(jsonObj);
			setFriendscheckins(jsonObj);
			

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void setCategoryData(JSONObject jsonObj) throws JSONException {
		
		if(jsonObj.has(JSONConstants.CATEGORIES_TAG)) {
			JSONArray jsonArray = jsonObj.getJSONArray(JSONConstants.CATEGORIES_TAG);
			categories = new Vector();
			for (int i=0 ; i < jsonArray.length() ; i++) {
				JSONObject categoryJSON = jsonArray.getJSONObject(i);
				Category category = new Category();
				if(categoryJSON.has(JSONConstants.CATEGORIES_ID_TAG)) {
					category.setId(Integer.parseInt(categoryJSON.getString(JSONConstants.CATEGORIES_ID_TAG)));
				}
				if(categoryJSON.has(JSONConstants.CATEGORIES_NAME_TAG)) {
					category.setName(categoryJSON.getString(JSONConstants.CATEGORIES_NAME_TAG));
				}
				if(categoryJSON.has(JSONConstants.CATEGORIES_PICTURE_ID_TAG)) {
					category.setPictureId(Integer.parseInt(categoryJSON.getString(JSONConstants.CATEGORIES_PICTURE_ID_TAG)));
				}
				if(categoryJSON.has(JSONConstants.CATEGORIES_PICTURE_URL_TAG)) {
					category.setPictureURL(categoryJSON.getString(JSONConstants.CATEGORIES_PICTURE_URL_TAG)); 
				}
				categories.addElement(category);
			}
		}
		
	}
	
	private void setBrandData(JSONObject jsonObj) throws JSONException {
		if(jsonObj.has(JSONConstants.BRAND_TAG)) {
			JSONObject brandJSON = jsonObj.getJSONObject(JSONConstants.BRAND_TAG);
			brand = new Brand();
			brand.setId(Integer.parseInt(brandJSON.getString(JSONConstants.BRAND_ID_TAG)));
			brand.setName(brandJSON.getString(JSONConstants.BRAND_NAME_TAG));
			//brand.setBrandSlug(brandJSON.getString(JSONConstants.BRAND_SLUG_TAG));
			brand.setPictureURL(brandJSON.getString(JSONConstants.BRAND_PICTURE_URL_TAG));
			brand.setBackgroundPictureURL(brandJSON.getString(JSONConstants.BRAND_BG_PICTURE_URL_TAG));
			
			//System.out.println("-> Brand Name: " + brand.getName());
		}	
	}
	
	private void setContactData(JSONObject jsonObj) throws JSONException {
		if(jsonObj.has(JSONConstants.CONTACT_TAG)) {
			JSONObject contactJSON = jsonObj.getJSONObject(JSONConstants.CONTACT_TAG);
			contact = new Contact();
			if(contactJSON.has(JSONConstants.CONTACT_ADDRESS_TAG)) {
				contact.setAddress(contactJSON.getString(JSONConstants.CONTACT_ADDRESS_TAG));
			}
			if(contactJSON.has(JSONConstants.CONTACT_CITY_TAG)) {
				contact.setCity(contactJSON.getString(JSONConstants.CONTACT_CITY_TAG));
			}
			if(contactJSON.has(JSONConstants.CONTACT_PHONE_TAG)) {
				contact.setPhone(contactJSON.getString(JSONConstants.CONTACT_PHONE_TAG));
			}
		}
	}
	
	private void setLocationData(JSONObject jsonObj) throws JSONException {
		if(jsonObj.has(JSONConstants.LOCATION_TAG)) {
			JSONObject locationJSON = jsonObj.getJSONObject(JSONConstants.LOCATION_TAG);
			location = new Location();
			location.setLattitude(locationJSON.getString(JSONConstants.LOCATION_LATTITUDE_TAG));
			location.setLongitude(locationJSON.getString(JSONConstants.LOCATION_LONGITUDE_TAG));
		}
	}
	
	private void setPopularityData(JSONObject jsonObj) throws JSONException {
		if(jsonObj.has(JSONConstants.POPULARTIY_TAG)) {
			JSONObject popularityJSON = jsonObj.getJSONObject(JSONConstants.POPULARTIY_TAG);
			popularity = new Popularity();
			popularity.setCheckinCount(Integer.parseInt(popularityJSON.getString(JSONConstants.POPULARTIY_CHECKIN_COUNT_TAG)));
			popularity.setPunchCount(Integer.parseInt(popularityJSON.getString(JSONConstants.POPULARTIY_PUNCH_COUNT_TAG)));
			popularity.setScore(Integer.parseInt(popularityJSON.getString(JSONConstants.POPULARTIY_SCORE_TAG)));
			popularity.setNormalizedScore(Integer.parseInt(popularityJSON.getString(JSONConstants.POPULARTIY_NORMALIZED_SCORE_TAG)));
			popularity.setDisplayScore(Integer.parseInt(popularityJSON.getString(JSONConstants.POPULARTIY_DISPLAY_SCORE_TAG)));
		}
	}
	private void setFriendscheckins(JSONObject jsonObj) throws JSONException {
		if(jsonObj.has(JSONConstants.FRIENDSCHECKINS_TAG)) {
			System.out.println("setFriendscheckins -> has.FRIENDSCHECKINS_TAG ");
			JSONObject friendJSON = jsonObj.getJSONObject(JSONConstants.FRIENDSCHECKINS_TAG);
			if(friendJSON.has(JSONConstants.CHECKINS_TAG)){
				System.out.println("has checkins");
			friendsCheckins = new FriendsCheckins(friendJSON);	
			}
			if(friendJSON.has(JSONConstants.FRIENDSCHECKINS_COUNT)){
				System.out.println("has count");
			JSONObject countJSON = friendJSON.getJSONObject(JSONConstants.FRIENDSCHECKINS_COUNT);
			friendsCheckins.setCount(Integer.parseInt(countJSON.getString(JSONConstants.FRIENDSCHECKINS_COUNT)));
			friendsCheckins.setResult(countJSON.getString(JSONConstants.FRIENDSCHECKINS_RESULT));	
			}
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getIsPremium() {
		return isPremium;
	}

	public void setIsPremium(int isPremium) {
		this.isPremium = isPremium;
	}

	public Vector getCategories() {
		return categories;
	}

	public void setCategories(Vector categories) {
		this.categories = categories;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Popularity getPopularity() {
		return popularity;
	}

	public void setPopularity(Popularity popularity) {
		this.popularity = popularity;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getLocatlity() {
		return locatlity;
	}

	public void setLocatlity(String locatlity) {
		this.locatlity = locatlity;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public FriendsCheckins getFriendscheckins() {
		return friendsCheckins;
	}


	public String getOperatingHours() {
		return operatingHours;
	}

	public void setOperatingHours(String operatingHours) {
		this.operatingHours = operatingHours;
	}

	public int getDineIn() {
		return dineIn;
	}

	public void setDineIn(int dineIn) {
		this.dineIn = dineIn;
	}

	public int getHomeDelivery() {
		return homeDelivery;
	}

	public void setHomeDelivery(int homeDelivery) {
		this.homeDelivery = homeDelivery;
	}

	public int getSmokingArea() {
		return smokingArea;
	}

	public void setSmokingArea(int smokingArea) {
		this.smokingArea = smokingArea;
	}

	public int getWifi() {
		return wifi;
	}

	public void setWifi(int wifi) {
		this.wifi = wifi;
	}

	public int getDriveThrough() {
		return driveThrough;
	}

	public void setDriveThrough(int driveThrough) {
		this.driveThrough = driveThrough;
	}
}



