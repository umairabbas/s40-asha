package com.tenpearls.olaround.model;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.sun.lwuit.List;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.HTTPConstatns;
import com.tenpearls.olaround.constants.JSONConstants;
import com.tenpearls.olaround.entities.Account;
import com.tenpearls.olaround.entities.ClaimKickEntity;
import com.tenpearls.olaround.entities.Location;
import com.tenpearls.olaround.entities.UserEntity;
import com.tenpearls.olaround.entities.VenueEntity;
import com.tenpearls.olaround.helpers.Base64;
import com.tenpearls.olaround.helpers.CustomJSONParser;
import com.tenpearls.olaround.helpers.FacebookHelper;
import com.tenpearls.olaround.helpers.URLHelper;
import com.tenpearls.olaround.helpers.WebServiceHelper;
import com.tenpearls.olaround.ui.components.CustomDialogBox;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class DataModel {
	
	static List searchList;
	static UserEntity userEntity;
	static VenueEntity venueEntity;
	static Location location = new Location("31.46373","74.38572");
	//static Location location = LocationHelper.getCurrentlocation();
	static String searchData= null;

	public static void loginWithOlaround(String userId, String passwrord, BaseForm currentForm) {
		try {
			System.out.println("-> loginWithOlaround()");
			String url = URLHelper.getLogin();
			String requestBody = "grant_type=password&username=" + userId 
					+ "&password=" + passwrord 
					+ "&device_uuid=" + OlaroundMidlet.getInstance().getImeiNumber();
			String requestHeader = getBasicHeader();
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_POST, requestHeader, requestBody);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					if(WebServiceHelper.isConnectionInProgress()) {
						userEntity = CustomJSONParser.parseUserJSON(streamData);
						String query = "&page=" + 1;
						List venueList = fetchVenue(query, currentForm);
						if(venueList != null) {
							OlaroundMidlet.getInstance().showVenuListingForm(currentForm, true, venueList, "");
							venueList = null;
							OlaroundMidlet.setLoginWithFB(false);
						}
					}
				} else if(responseCode == HTTPConstatns.STATUS_CODE_400) {
					String errorMsg = CustomJSONParser.parseForNoCredentials(streamData);
					System.out.println(errorMsg);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}
			streamData = null;
		}  catch (Exception e) {
			System.out.println("ERROR: at loginRequest()");
			e.printStackTrace();
		}
	}
	
	public static void loginWithFacebook(String userId, String accessToken, BaseForm currentForm) {
		try {
			String url = URLHelper.getLogin();
			String requestBody ="grant_type=http://olrd.me/oauth/facebook&fb_user_id=" + userId 
					+ "&facebook_access_token=" + accessToken 
					+ "&facebook_token_expiry=1374997904&device_uuid=" + OlaroundMidlet.getInstance().getImeiNumber();
			String requestHeader = getBasicHeader();
			System.out.println(url);
			System.out.println(requestBody);
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_POST, requestHeader, requestBody);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					userEntity = CustomJSONParser.parseUserJSON(streamData);
					String query = "&page=" + 1;
					List venueList = fetchVenue(query, currentForm);
					OlaroundMidlet.getInstance().showVenuListingForm(currentForm, true, venueList, "");
					venueList = null;
					OlaroundMidlet.setLoginWithFB(true);
				} else if(responseCode == HTTPConstatns.STATUS_CODE_400) {
					currentForm.dismissLoadingDialog();
					String errorMsg = CustomJSONParser.parseForNoCredentials(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}
			streamData = null;
		}  catch (Exception e) {
			System.out.println("ERROR: at loginRequest()");
			e.printStackTrace();
		}
	}
	
	public static void createAccount(Account account, BaseForm currentForm) {
		try {
			String url = URLHelper.getRegister();
			String requestBody = "username=" + account.getUserName() +
					"&password=" + account.getPassword() +
					"&email=" + account.getEmail() + 
					"&first_name=" + account.getFirstName() + 
					"&last_name=" + account.getLastName(); 
			
			if(!account.getGender().equals("")) {
					requestBody += "&gender=" + account.getGender();
			} 
			if(!account.getPhone().equals("")) {
				requestBody += "&phone=" + account.getPhone();
			}
			System.out.println(url);
			System.out.println(requestBody);
			String requestHeader = getBasicHeader();
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_POST, requestHeader, requestBody);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					userEntity = CustomJSONParser.parseUserJSON(streamData);
					String query = "&page=" + 1;
					List venueList = fetchVenue(query, currentForm);
					OlaroundMidlet.getInstance().showVenuListingForm(currentForm, true, venueList, "");
				} else if(responseCode == HTTPConstatns.STATUS_CODE_400 || responseCode == HTTPConstatns.STATUS_CODE_500) {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}
			streamData = null;
		}  catch (Exception e) {
			System.out.println("ERROR: at createAccount()");
			e.printStackTrace();
		}
	
	}
	
	public static boolean forgotPasswrord(String userName, BaseForm currentForm) {
		try {
			
			String url = URLHelper.getForgotPassword();
			String requestBody = "username=" + userName;
			String requestHeader = getBasicHeader();
			System.out.println(url);
			
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_POST, requestHeader, requestBody);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					return true;
				} else if(responseCode == HTTPConstatns.STATUS_CODE_400 || responseCode == HTTPConstatns.STATUS_CODE_404) {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}
			streamData = null;

		}  catch (Exception e) {
			System.out.println("ERROR: at forgotPasswrord()");
			e.printStackTrace();
		}
		return false;
	}
	
	public static void logoutWithOlaround(BaseForm currentForm) {
		try {
			System.out.println("-> logoutWithOlaround()");
			String url = URLHelper.getLogout();
			String encodedHeader = "Bearer" + " " + Base64.encode(HTTPConstatns.CLIENT_ID.trim() + ":"
									+ DataModel.getUserEntity().getAccessToken().trim()).trim();
			int responseCode = WebServiceHelper.startHTTPConnector(url,
					HTTPConstatns.REQUEST_TYPE_POST, encodedHeader, null);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if ((responseCode == HTTPConstatns.STATUS_CODE_200 && streamData.equals("true"))
					|| responseCode == HTTPConstatns.STATUS_CODE_401) {
				OlaroundMidlet.getInstance().destoryMianCategoryForms();
				OlaroundMidlet.getInstance().showLoginForm(currentForm, true);
			} else {
				System.out.println("LOG OUT FAILED");
			}
			streamData = null;
		} catch (Exception e) {
			System.out.println("ERROR: at logoutRequest()");
			e.printStackTrace();
		}
	}
	
	public static void shareOnFb(BaseForm currentForm) {
		try {
			System.out.println("-> shareOnFb()");
			String url = URLHelper.getShareOnFb();
			String Header = "";
			String Body = "access_token=" + FacebookHelper.getAccessToken() + "&link=https://www.facebook.com/appcenter/olaround";
			int responseCode = WebServiceHelper.startHTTPConnector(url,
					HTTPConstatns.REQUEST_TYPE_POST, Header, Body);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if (responseCode == HTTPConstatns.STATUS_CODE_200) {
				CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_INFO, "Shared Successfully!", currentForm);
			} else {
				System.out.println("SHARE FAILED");
				String errorMsg = CustomJSONParser.parseForError(streamData);
				CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
			}
			streamData = null;
		} catch (Exception e) {
			System.out.println("ERROR: at shareOnFb()");
			e.printStackTrace();
		}
	}
	
	public static List fetchVenue(String query, BaseForm currentForm) {
		System.out.println("-> fetchVenue()");
		List venueList = null;

		try {
			String url = URLHelper.getVenue(location.getLattitude(), location.getLongitude()) + ApplicationConstants.LIST_PAGE_SIZE + query;
			System.out.println(url);
			String requestHeader = getBasicHeader();
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_GET, requestHeader, null);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					venueList = CustomJSONParser.parseVenuJSON(streamData);
				} else if(responseCode == HTTPConstatns.STATUS_CODE_500) {
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, ApplicationConstants.MSG_SERVER_NOT_RESPONDING, currentForm);
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}
			streamData = null;

		} catch (Exception e) {
			System.out.println("Error fetchVenue()");
			e.printStackTrace();
		}
		
		return venueList;
	}
	
	public static void fetchSearchVenues(BaseForm currentForm) {
		
		if(searchData == null) {
			
			try {
				String url = URLHelper.getSearchVenues();
				System.out.println(url);
				String requestHeader = getBasicHeader();
				int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_GET, requestHeader, null);
				searchData =  WebServiceHelper.getdeCompressSearchData(); 
				System.out.println(searchData);
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
						System.out.println("Search data loaded.");
				} else {
					if(searchData != null && !searchData.equals("")) {
						String errorMsg = CustomJSONParser.parseForError(searchData);
						CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
						System.out.println("Search data not loaded.");
					}
					searchData = null;
				}
	
			} catch (Exception e) {
				System.out.println("Error fetchSearchVenues()");
				e.printStackTrace();
				searchData = null;
			}
			
		}
	}
	
	public static void searchVenueByName(String searchName) {
		System.out.println("-> searchVenueByName()");
		try {
			searchList = new List();
			if(searchData != null) {
				System.out.println("parsing start...");
				JSONArray jsonArray = new JSONArray(searchData);
				if(jsonArray != null) {
					for(int i=0 ; i < jsonArray.length() ; i++) {
						JSONObject jsonObj = jsonArray.getJSONObject(i);
						String venueName = jsonObj.getString(JSONConstants.VENUE_NAME_TAG);
						if(venueName.trim().toLowerCase().startsWith(searchName.trim().toLowerCase())) {
							VenueEntity entity = new VenueEntity();
							entity.setData(jsonObj);
							searchList.addItem(entity);
							entity = null;
						}
						jsonObj =  null;
					}
				}
				jsonArray = null;
			} 
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
	
	public static void fetchVenueById(int venueId, BaseForm currentForm) {
		System.out.println("->fetchVenueById()");
		try {
			String url = URLHelper.getVenueItem(venueId, location.getLattitude(), location.getLongitude());
			System.out.println(url);
			String encodedHeader = "Bearer"+ " " + Base64.encode(HTTPConstatns.CLIENT_ID.trim() + ":" + DataModel.getUserEntity().getAccessToken().trim()).trim();
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_GET, encodedHeader, null);
			String streamData = WebServiceHelper.getStringFromInputStream();
			
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					venueEntity = CustomJSONParser.parseVenuItemJSON(streamData);
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}

		} catch (Exception e) {
			System.out.println("Error fetchVenueById()");
			e.printStackTrace();
		}
	}
	
	public static List getMenuList(int brandId, BaseForm currentForm) {
		System.out.println("->getMenuList()");
		List menuList = null;
		try {
			String url = URLHelper.getMenuList(brandId);
			System.out.println(url);
			String requestHeader = getBasicHeader();
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_GET, requestHeader, null);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					 menuList = CustomJSONParser.parseMenuJSON(streamData);
					
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}

		} catch (Exception e) {
			System.out.println("Error getMenuList()");
			e.printStackTrace();
		}
		return menuList;
	}
	
	public static List getFriendsActivityList(String query, BaseForm currentForm) {
		System.out.println("->getFriendsActivityList()");
		List friendList = null;
		try {
			String url = URLHelper.getFriendActivityList() + ApplicationConstants.LIST_PAGE_SIZE + query;
			System.out.println(url);
			String encodedHeader = "Bearer"+ " " + Base64.encode(HTTPConstatns.CLIENT_ID.trim() + ":" + DataModel.getUserEntity().getAccessToken().trim()).trim();
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_GET, encodedHeader, null);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					 friendList = CustomJSONParser.parseFriendActivityJSON(streamData);
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}

		} catch (Exception e) {
			System.out.println("Error getFriendsActivityList()");
			e.printStackTrace();
		}
		return friendList;
	}
	
	public static List getActivityList(String query, BaseForm currentForm, String type) {
		System.out.println("->fetchVenueById()");
		List friendList = null;
		try {
			int user_id = DataModel.getUserEntity().getUserId(); 
			String url = URLHelper.getActivityList(user_id, type) + ApplicationConstants.LIST_PAGE_SIZE + query;
			System.out.println(url);
			String encodedHeader = "Bearer"+ " " + Base64.encode(HTTPConstatns.CLIENT_ID.trim() + ":" + DataModel.getUserEntity().getAccessToken().trim()).trim();
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_GET, encodedHeader, null);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					 friendList = CustomJSONParser.parseFriendActivityJSON(streamData);
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}

		} catch (Exception e) {
			System.out.println("Error getFriendsActivityList()");
			e.printStackTrace();
		}
		return friendList;
	}

	public static void getCheckin(BaseForm currentForm) {
		System.out.println("->fetchVenueById()");
		try {
			String url = URLHelper.getCheckins();
			System.out.println(url);
			String encodedHeader = "Bearer"+ " " + Base64.encode(HTTPConstatns.CLIENT_ID.trim() + ":" + DataModel.getUserEntity().getAccessToken().trim()).trim();
			String share;
			if(OlaroundMidlet.isLoginWithFB()) {
				share = new Boolean(OlaroundMidlet.isShareOnFb()).toString();
			} else{
				share = "false";
			}
			String requestBody = "store_id=" + venueEntity.getId() + "&longitude=" + location.getLongitude() + "&latitude=" + location.getLattitude() + "&share=" + share;
			System.out.println("REQ BODY========= "+requestBody);
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_POST, encodedHeader, requestBody);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					System.out.println("STATUS 200 SUCCESSS****************"); 
					OlaroundMidlet.getInstance().showClaimedCheckinInfoForm(currentForm, false);
				} else if(responseCode == HTTPConstatns.STATUS_CODE_430) {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
					return;
				} else if(responseCode == HTTPConstatns.STATUS_CODE_400) {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
					return;
				}
			}

		} catch (Exception e) {
			System.out.println("Error getCheckin()");
			e.printStackTrace();
		}
	}

	public static void getPunch(String punchCode, BaseForm currentForm) {
		System.out.println("->punch()");
		try {
			String url = URLHelper.getPunch() ;
			System.out.println(url);
			String encodedHeader = "Bearer"+ " " + Base64.encode(HTTPConstatns.CLIENT_ID.trim() + ":" + DataModel.getUserEntity().getAccessToken().trim()).trim();
			System.out.println("Request header:" + encodedHeader);
			String shareOnFb ="";
			if(OlaroundMidlet.isLoginWithFB()) {
				 shareOnFb = new Boolean(OlaroundMidlet.isShareOnFb()).toString();
			} else {
				shareOnFb = "false";
			}
			String requestBody = "code=" + punchCode + "&longitude=" + location.getLongitude()+ "&latitude=" + location.getLattitude() + "&share=" + shareOnFb;
			System.out.println("REQ BODY: "+requestBody);
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_POST, encodedHeader, requestBody);
			String streamData = WebServiceHelper.getStringFromInputStream();

			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					String venueName = CustomJSONParser.parsePunchedJSON(streamData);
					System.out.println("venue:" + venueName);
					if(venueName != null) {
						OlaroundMidlet.getInstance().showClaimedPunchInfoForm(currentForm, false, venueName);
					}
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}

		} catch (Exception e) {
			System.out.println("Error punch()");
			e.printStackTrace();
		}
	}
	
	public static String getUserProfile() {
		System.out.println("->userprofile()");
		try {
			int user_id = DataModel.getUserEntity().getUserId(); 
			String url = URLHelper.getUserProfile(user_id) ;
			System.out.println(url);
			String requestHeader = getBasicHeader();
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_GET, requestHeader, null);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					String Location = CustomJSONParser.parseUserIdJSON(streamData);
					System.out.println("Location:" + Location);
					if(Location != null) {
						return Location;
					}
				} else {
					return null;
				}
			}

		} catch (Exception e) {
			System.out.println("Error punch()");
			e.printStackTrace();
		}
		return null;
	}
	
	public static List fetchDealsList(String query, BaseForm currentForm) {
		System.out.println("->fetchDealsList()");
		List list = null;
		try {
			String url = URLHelper.getDealsList(location.getLattitude(), location.getLongitude())  + query;
			System.out.println(url);
			String encodedHeader = getBasicHeader();
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_GET, encodedHeader, "");
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					list = CustomJSONParser.parseDealsJSON(streamData);
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}
			return list;

		} catch (Exception e) {
			System.out.println("Error fetchDealsList()");
			e.printStackTrace();
		}
		return null;
	}
	
	public static List fetchDealItem(BaseForm currentForm, int storeId) {
		System.out.println("->fetchDealItem()");
		List list = null;
		try {
			String url = URLHelper.getDealsVenueList(storeId) ;
			System.out.println(url);
			String encodedHeader = getBasicHeader();
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_GET, encodedHeader, "");
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					list = CustomJSONParser.parseDealsVenueJSON(streamData);
					return list;
				} else if(responseCode == HTTPConstatns.STATUS_CODE_404){
				//	String errorMsg = CustomJSONParser.parseForError(streamData);
				//	CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}		
		} catch (Exception e) {
			System.out.println("Error fetchDealItem()");
			e.printStackTrace();
		}
		return list;
	}
	
	public static boolean getInvites(String[] body, BaseForm currentForm, BaseForm prevForm) {
		System.out.println("->getInvites()");
		try {
			String url = URLHelper.getInvites() ;
			System.out.println(url);
			String encodedHeader = "Bearer"+ " " + Base64.encode(HTTPConstatns.CLIENT_ID.trim() + ":" + DataModel.getUserEntity().getAccessToken().trim()).trim();
			System.out.println("Request header:" + encodedHeader);
			String requestBody = getInviteRequestBody(body);
			System.out.println("REQ BODY: "+requestBody);
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_POST, encodedHeader, requestBody);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_INFO, ApplicationConstants.MSG_EMAIL_SEND, currentForm);
					System.out.println("STATUS_CODE_200");
					return true;
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}

		} catch (Exception e) {
			System.out.println("Error Invites()");
			e.printStackTrace();
		}
		return false;
	}
	
	private static String getInviteRequestBody(String[] body) {
		System.out.println("BODY LEN: " +  body.length);
		String Result = "contacts=[";
		for (int count = 0; count < body.length; count++) {
			Result +="{\"name\":\"\",\"email\":\"";
			Result += body[count];
			Result += "\"}";
			if (count < body.length-1) {
				Result += ",";
			}
		}
		Result += "]";
		return Result;
	}
	
	public static boolean getFeedback(String body, BaseForm currentForm, BaseForm prevForm) {
		System.out.println("->getFeedback()");
		try {
			String url = URLHelper.getFeedback() ;
			System.out.println(url);
			String Header = getBasicHeader();
			String requestBody = body;
			System.out.println("REQ BODY: "+requestBody);
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_POST, Header, requestBody);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					System.out.println(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_INFO, ApplicationConstants.MSG_EMAIL_SEND, prevForm);
					return true;
 				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Feedback()");
			e.printStackTrace();
		}
		return false;
	}
	
	public static List fetchFriendList(String query, BaseForm currentForm) {
		System.out.println("->fetchFriendList()");
		List friendList = null;
		try {
			String url = URLHelper.getFriendList(userEntity.getUserId()) + ApplicationConstants.LIST_PAGE_SIZE + query;
			System.out.println(url);
			String encodedHeader = "Bearer"+ " " + Base64.encode(HTTPConstatns.CLIENT_ID.trim() + ":" + DataModel.getUserEntity().getAccessToken().trim()).trim();
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_GET, encodedHeader, null);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					 friendList = CustomJSONParser.parseFriendsJSON(streamData);
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}

		} catch (Exception e) {
			System.out.println("Error fetchFriendList()");
			e.printStackTrace();
		}
		return friendList;
	}
	
	public static List fetchKicksList(int brandId, BaseForm currentForm) {
		System.out.println("->fetchKicksList()");
		List kickList = null;
		try {
			String url = URLHelper.getKickList(brandId) ;
			System.out.println(url);
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_GET, getBasicHeader(), null);
			String streamData = WebServiceHelper.getStringFromInputStream();
			if(streamData != null && !streamData.equals("") && !streamData.equals("null")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					 kickList = CustomJSONParser.parseKickJSON(streamData);
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}

		} catch (Exception e) {
			System.out.println("Error fetchKicksList()");
			e.printStackTrace();
		}
		return kickList;
	}
	
	public static ClaimKickEntity cliamKick(int storeId, int rewardId, BaseForm currentForm) {
		System.out.println("->cliamKick()");
		ClaimKickEntity claimKick = null;
		try {
			String url = URLHelper.getClaimkick(rewardId) ;
			String encodedHeader = "Bearer"+ " " + Base64.encode(HTTPConstatns.CLIENT_ID.trim() + ":" + DataModel.getUserEntity().getAccessToken().trim()).trim();
			String requestBody = "store_id=" + storeId + "&latitude=" + location.getLattitude() + "&longitude=" + location.getLongitude();

			System.out.println(url);
			System.out.println("encodedHeader:"+ encodedHeader);
			System.out.println("req body:" + requestBody);
			
			int responseCode = WebServiceHelper.startHTTPConnector(url, HTTPConstatns.REQUEST_TYPE_POST, encodedHeader, requestBody);
			String streamData = WebServiceHelper.getStringFromInputStream();
			System.out.println(streamData);
			if(streamData != null && !streamData.equals("")) {
				if(responseCode == HTTPConstatns.STATUS_CODE_200) {
					claimKick  = CustomJSONParser.parseClaimkickJSON(streamData);
				} else {
					String errorMsg = CustomJSONParser.parseForError(streamData);
					CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, currentForm);
				}
			}

		} catch (Exception e) {
			System.out.println("Error cliamKick()");
			e.printStackTrace();
		}
		return claimKick;
	}
	

	public static UserEntity getUserEntity() {
		return userEntity;
	}
	
	public static void setUserEntity(UserEntity userEntity) {
		DataModel.userEntity = userEntity;
	}

	public static VenueEntity getVenueEntity() {
		return venueEntity;
	}

	public static void setVenueEntity(VenueEntity venueEntity) {
		DataModel.venueEntity = venueEntity;
	}

	public static List getSearchList() {
		return searchList;
	}

	public static void setSearchList(List searchList) {
		DataModel.searchList = searchList;
	}
	
	public static void emptySearchData() {
		searchData = null;
	}
	
	public static void emptySearchList() {
		searchList = null;
	}

	public static String getBasicHeader() {
		return ("Basic" + " " + HTTPConstatns.ENCODED_CLIENT_ID);
	}

}
