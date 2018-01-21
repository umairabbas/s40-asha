package com.tenpearls.olaround.helpers;

import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;


public class URLHelper {
	public static final String WEBSERVICE_URL = "https://api.olaround.me/v2"; 
	private final static String LOGIN_KEYWORD = "/oauth/login";
	private final static String ACCOUNTS_KEYWORD = "/accounts";
	private final static String REGISTER_KEYWORD = "/register";
	private final static String FORGOT_PASWORD_KEYWORD = "/forgot_password";
	private final static String VENUES_KEYWORD = "/venues";
	public final static String SEARCH_KEYWORD = "/search/get_search_data";
	public final static String BRANDS_KEYWORD = "/brands";
	public final static String MENU_KEYWORD = "/menu";
	public final static String ACTIVITIES_KEYWORD = "/activities";
	public final static String LOGOUT_KEYWORD = "/oauth/logout"; 
	public final static String USERS_KEYWORD = "/users";
	public final static String PUCNHES_KEYWORD = "/punches";
	public final static String CHECKIN_KEYWORD = "/checkins";
	public final static String PROMOTIONS_KEYWORD ="/promotions";
	public final static String FRIENDS_KEYWORD ="/friends";
	public final static String INVITE_KEYWORD ="/invite";
	public final static String REWARDS_KEYWORD ="/rewards";
	public final static String LIST_TYPE_ALL ="?list=all";
	public final static String LIST_TYPE_NEARBY ="&list=nearby";
	public final static String SUPPORT_KEYWORD ="/support";
	public final static String FEEDBACK_KEYWORD ="/feedback";
	public final static String CLAIM_KICK = "/claim_reward";
	
	public static String getLogin() {
		String url = WEBSERVICE_URL + LOGIN_KEYWORD;
		return url;
	}
	
	public static String getLogout() {
		String url = WEBSERVICE_URL + LOGOUT_KEYWORD;
		return url;
	}
	
	public static String getNearbyDetail(String lattitude, String longitude) {
		String url = WEBSERVICE_URL + VENUES_KEYWORD + "?latitude=" + lattitude + "&longitude=" + longitude + LIST_TYPE_NEARBY;
		return url;
	}
	
	public static String getRegister() {
		String url = WEBSERVICE_URL + ACCOUNTS_KEYWORD + REGISTER_KEYWORD;
		return url;
	}
	
	public static String getForgotPassword() {
		String url = WEBSERVICE_URL + ACCOUNTS_KEYWORD + FORGOT_PASWORD_KEYWORD;
		return url;
	}
	
	public static String getVenue(String lattitude, String longitude) {
		String url = WEBSERVICE_URL + VENUES_KEYWORD + "?latitude=" + lattitude + "&longitude=" + longitude;
		return url;
	}
	
	public static String getVenueItem(int venueId, String lattitude, String longitude) {
		String url = WEBSERVICE_URL + VENUES_KEYWORD + "/"+ venueId + "?latitude=" + lattitude + "&longitude=" + longitude;
		return url;
	}
	
	public static String getSearchVenues() {
		String url = WEBSERVICE_URL + SEARCH_KEYWORD ;
		return url;
	}
	
	public static String getMenuList(int brandId) {
		String url = WEBSERVICE_URL + BRANDS_KEYWORD + "/" + brandId + MENU_KEYWORD;
		return url;
	}
	
	public static String getFriendActivityList() {
		String url = WEBSERVICE_URL + ACTIVITIES_KEYWORD + "?type=all";
		return url;
	}
	
	public static String getActivityList(int user_id, String type){
		String url = WEBSERVICE_URL + USERS_KEYWORD + "/" + user_id + ACTIVITIES_KEYWORD  + "?type=" + type;
		return url;
	}
	
	public static String getPunch() {
		String url = WEBSERVICE_URL + PUCNHES_KEYWORD;
		return url;
	}
	
	public static String getCheckins(){
		String url = WEBSERVICE_URL + CHECKIN_KEYWORD;
		return url;
	}
	
	public static String getUserProfile(int user_id) {
		String url = WEBSERVICE_URL + USERS_KEYWORD + "/" + user_id;
		return url;
	}
	
	public static String getDealsList(String lattitude, String longitude) {
		String url = WEBSERVICE_URL + PROMOTIONS_KEYWORD + "?latitude=" + lattitude + "&longitude=" + longitude 
				+ "&partner_code=" + OlaroundMidlet.getNetworkCode() + ApplicationConstants.LIST_PAGE_SIZE ;
		return url;
	}
	
	public static String getDealsVenueList(int venueId){
		String url = WEBSERVICE_URL + VENUES_KEYWORD + "/" +venueId + PROMOTIONS_KEYWORD;
		return url;
	}
	
	public static String getInvites(){
		String url = WEBSERVICE_URL + FRIENDS_KEYWORD + INVITE_KEYWORD;
		return url;
	}

	public static String getFriendList(int userId){
		String url = WEBSERVICE_URL + USERS_KEYWORD + "/" + userId + FRIENDS_KEYWORD + LIST_TYPE_ALL;
		return url;
	}
	
	public static String getKickList(int brandId) {
		String url = WEBSERVICE_URL + BRANDS_KEYWORD + "/" + brandId + REWARDS_KEYWORD;
		return url;
	}
	
	public static String getFeedback(){
		String url = WEBSERVICE_URL + SUPPORT_KEYWORD + FEEDBACK_KEYWORD;
		return url;
	}
	
	public static String getShareOnFb(){
		String url = "https://graph.facebook.com/" + "me" + "/feed";
		return url;
	}
	
	public static String getClaimkick(int rewardId) {
		String url = WEBSERVICE_URL + CLAIM_KICK + "/" + rewardId; 
		return url;
	}
	
	
}
