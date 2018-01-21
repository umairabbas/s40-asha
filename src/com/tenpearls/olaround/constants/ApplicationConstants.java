package com.tenpearls.olaround.constants;

public class ApplicationConstants {
	
	public static final String THEME_FILE = "/olaround.res";
	
	public static final String DEFAULT_IMAGE = "img_not_found.png";
	public static final String DEFAULT_THUMB_IMAGE = "thumb_img_not_found.png";
	
	public static final String[] MAIN_CATEGORY_ICON_COMMAND_NAMES = { "ol venues", "ol deals", "ol scan", "ol friends" , "ol me"};
	public static final String[] MAIN_CATEGORY_ICON_IMAGE_NAMES = { "venue", "deals", "scan", "friends", "me"};
	
	public static final String[] VENUE_CATEGORY_ICON_COMMAND_NAMES = { "info", "deals", "kicks", "menu" };
	public static final String[] VENUE_CATEGORY_ICON_IMAGE_NAMES = { "info_icon", "sub_deal_icon", "kicks_icon", "menu_icon"};
	
	public static String[] DISTANCE_CHOICES_TEXT = new String[] { "nearby", "popular 01 - 10km", "popular 10 - 20km", "popular 20 - 30km"};
	public static String[] DISTANCE_CHOICES_VALUES = new String[] { "nearby", "1000", "2000", "3000"};
	public static String[] CATEGORY_CHOICES_TEXT = new String[] { "all", "shop & retail", "service", "arts and entertainment"};
	public static String[] CATEGORY_CHOICES_VALUES = new String[] { "all", "48", "50", "88"};
	
	/*------------COMMAND NAMES------------*/
	public static final String BACK_COMMAND = "back";
	public static final String ABOUT_COMMAND = "about";
	public static final String HELP_COMMAND = "help"; 
	public static final String FILTER_COMMAND = "filters";
	public static final String SEARCH_COMMAND = "search";
	public static final String SELECT_PHOTO_COMMAND = "select_photo";
	public static final String SELECT_FILTER_COMMAND = "select filter";
	public static final String FEEDBACK_COMMAND = "feedback";
	public static final String LOGOUT_COMMAND = "sign out";
	public static final String SHARE_COMMAND = "share";
	public static final String TERMS_COMMAND = "terms & conditions";
	public static final String ADD_COMMAND = "select add";
	
	/*------------FORMS TITLE------------*/
	public static final String APP_TITLE = "Olaround";
	public static final String TITLE_LOGIN = "login";
	public static final String TITLE_FORGOT_PASSWORD = "forgot password";
	public static final String TITLE_SiGNUP = "create account";
	public static final String TITLE_SELECT_PHOTO = "select photo";
	public static final String TITLE_OL_VENUES = "ol venues";
	public static final String TITLE_OL_VENUE = "ol venue";
	public static final String TITLE_OL_ADDRESS = "ol address";
	public static final String TITLE_OL_FILTERS = "ol filters";
	public static final String TITLE_OL_SEARCH_FORM = "ol search";
	public static final String TITLE_OL_DEALS = "ol deals";
	public static final String TITLE_OL_DEALS_DETAIL = "ol deal";
	public static final String TITLE_QR_SCAN_FORM = "ol scan";
	public static final String TITLE_CLAIMED_DEAL = "claimed deal";
	public static final String TITLE_OL_KICKS = "ol kicks";
	public static final String TITLE_OL_MENU = "ol menu";
	public static final String TITLE_OL_FRIENDS = "ol friends";
	public static final String TITLE_OL_FEEDBACK = "feedback";
	public static final String TITLE_OL_ME = "ol me";
	public static final String TITLE_OL_CHECKIN = "ol check-in";
	public static final String TITLE_CLAIMED_CHECKIN = "check-in";
	public static final String TITLE_PUNCH = "punch";
	public static final String TITLE_OL_INVITE = "invite friends";
	

	/*------------BUTTONS NAMES------------*/
	public static final String BUTTON_FORGOT_PWD_TEXT = "Forgot Password?";
	public static final String BUTTON_NAME_SIGN_UP = "Sign Up";
	public static final String BUTTON_NAME_LOGIN = "SIGN IN";
	public static final String BUTTON_NAME_SIGN_IN_FB = "SIGNIN WITH FACEBOOK";
	public static final String BUTTON_NAME_TERMS_AND_CONDTION = "Terms & Conditions";
	public static final String BUTTON_NAME_PROFILE_PICTURE = "profile pic";
	public static final String BUTTON_NAME_LOAD_MORE = "load more";
	public static final String BUTTON_NAME_CONTINUE = "CONTINUE";
	public static final String BUTTON_NAME_UPLOAD = "UPLOAD";
	public static final String BUTTON_NAME_SUBMIT = "SUBMIT";
	public static final String BUTTON_NAME_SEND = "SEND";
	public static final String BUTTON_NAME_DONE = "DONE";
	public static final String BUTTON_NAME_SCAN_QR = "SCAN THE QR CODE";
	public static final String RADIO_BUTTON_NAME_MALE = "male";
	public static final String RADIO_BUTTON_NAME_FEMALE = "female";
	public static final String BUTTON_NAME_ADDRESS = "ol address";
	public static final String BUTTON_NAME_CALL = "call";
	public static final String BUTTON_NAME_CLAIM_DEAL = "CLAIM DEAL";
	public static final String BUTTON_NAME_CHECKIN = "CHECK-IN";
	public static final String BUTTON_NAME_PUNCH = "PUNCH";
	public static final String BUTTON_NAME_SEND_FEEDBACK = "Submit";
	public static final String BUTTON_NAME_ACTIVITY = "me_activity";
	public static final String BUTTON_NAME_FRIENDS = "me_friends";
	public static final String BUTTON_NAME_KICKS = "me_kicks";
	public static final String BUTTON_NAME_PUNCHES = "me_punches";
	public static final String BUTTON_NAME_SHARE_ON_FB = "share_on_fb";
	public static final String BUTTON_CAPTURE = "capture";
	
	/*------------LABELS------------*/
	public static final String LABEL_TEXT_THANK_YOU = "Thank you!";
	public static final String LABEL_TEXT_CHECK_YOUR_EMAIL = "Please check your email for password reset.";
	public static final String LABEL_TEXT_AGREE_WITH_TREMS = "I agree with the";
	public static final String LABEL_TEXT_NEAR = "Near: Current Location";
	public static final String LABEL_TEXT_USERNAME = "username";
	public static final String LABEL_TEXT_PASSWORD = "password";
	public static final String LABEL_TEXT_FIRSTNAME = "first name";
	public static final String LABEL_TEXT_LASTNAME = "last name";
	public static final String LABEL_TEXT_EMAIL = "email";
	public static final String LABEL_TEXT_PHONE = "phone";
	public static final String LABEL_TEXT_GENDER = "gender";
	public static final String LABEL_TEXT_NAME = "name";
	public static final String LABEL_TEXT_SUBJECT = "subject";
	public static final String LABEL_TEXT_MESSAGE = "message";
	
	public static final String LABEL_TEXT_CONGRATULATION = "Congratulations";
	public static final String LABEL_TEXT_JUST_CLAIM_DEAL = "You've just claimed a Deal";
	public static final String LABEL_TEXT_JUST_CLAIM_KICK = "You've just claimed a Kick";
	public static final String LABEL_TEXT_JUST_CLAIM_CHECKIN = "You've checked-in at";
	public static final String LABEL_TEXT_TO_COLLECT_PUNCHES = "(To collect punches)";
	public static final String LABEL_TEXT_TO_GET_DEAL = "(Show this screen to the staff to get your Deal)";
	public static final String LABEL_TEXT_TO_GET_KICK = "(Show this screen to the staff to get your Kick)";
	public static final String LABEL_TEXT_SHARE_ON_FB = "Share on facebook";
	public static final String LABEL_TEXT_JUST_CLAIM_PUNCH = "You've punched at";
	public static final String LABEL_OTHER_FRIENDS = " other friends have been to this place.";
	public static final String LABEL_NONE_OF_YOUR ="None of your friends have been to this place.";
	public static final String LABEL_FACEBOOK_SHARE = "\nAre you sure you want to share on facebook?";
	public static final String LABEL_LOGOUT_TEXT = "\n\n\t\t Are you sure?";
	
		
	/*------------DIALOG------------*/
		public static final String DIALOG_TYPE_INFO = "Info";
	public static final String DIALOG_TYPE_ALERT = "Alert";
	public static final String DIALOG_TYPE_ERROR = "Error";
	public static final String DIALOG_BUTTON_OK = "OK";
	public static final String DIALOG_BUTTON_CLAIM_LATER = "CLAIM LATER";
	public static final String DIALOG_BUTTON_CLAIM_NOW = "CLAIM NOW";
	public static final String DIALOG_ERROR_MESSAGE = "Error occured while loading the data. Please check your internet connection and try again.";
	public static final String DIALOG_TITLE_FOR_CLAIM_DEAL = "description";
	public static final String DIALOG_TITLE_FOR_CLAIM_KICK = "description";
	public static final String DIALOG_NUMBER_NOT_AVALIABLE = "Sorry number not avaliable!";
	public static final String DIALOG_TITLE_FOR_SIGN_OUT = "sign out";
	public static final String DIALOG_TITLE_FOR_SHARE = "share";
	public static final String DIALOG_BUTTON_YES = "yes";
	public static final String DIALOG_BUTTON_NO = "no";
		
	/*------------MESSAGES------------*/
	public static final String MSG_EMPTY_ID_OR_PWD_FIELD = "Please enter correct email and password.";
	public static final String MSG_NO_INTERNET_CONNECTION = "No internet connection found.";
	public static final String MSG_VALIDATION_EMAIL_OR_USERNAME = "Please provide valid email or username.";
	public static final String MSG_VALIDATION_PASSWORD = "Please provide valid password.";
	public static final String MSG_VALIDATION_USERNAME = "Please provide valid username.";
	public static final String MSG_VALIDATION_EMAIL = "Please provide valid email address.";
	public static final String MSG_VALIDATION_FIRSTNAME = "Please provide valid first name.";
	public static final String MSG_VALIDATION_LASTNAME = "Please provide valid last name.";
	public static final String MSG_VALIDATION_SAME_FNAME_LNAME = "first name and last name cannot be same.";
	public static final String MSG_VALIDATION_SEARCH_FIELD = "Please provide venue name.";
	public static final String MSG_VALIDATION_PHONE_FIELD = "Please provide valid phone number.";
	public static final String MSG_NO_MORE_DATA = "No more data available.";
	public static final String MSG_ACCOUNT_CREATE= "Account created succesfully.";
	public static final String MSG_DEAL_HELP = "HELP: Deals are instant, just tap the deal to show it to the venue staff and claim the deal. Some deals may require you to Punch the QR code at the venue.";
	public static final String MSG_KICK_HELP = "HELP: Kicks are rewards offered by the partner brands and can be claimed against available Punches. Scan the Olaround QR code at the venue to collect Punches.";
	public static final String MSG_OL_SCAN_HELP = "Please scan the Olaround QR code to receive. Can't find it ask the staff";
	public static final String MSG_GOT_KICK = "got a kick at";
	public static final String MSG_CHECK_IN = "check-in at";
	public static final String MSG_JOINED_OLAROUND = "joined olaround";
	public static final String MSG_PUNCHED = "punched";
	public static final String MSG_INVALID_QR_CODE = "Invalid QR code, Please try again.";
	public static final String MSG_NO_DEALS = "No Deals Avaliable for this venue!";
	public static final String MSG_LOADING_DIALOG = "Please wait...";
	public static final String MSG_EMAIL_HEADER = "Enter email addresses seperated by colon(;)";
	public static final String MSG_QR_ERROR = "Error: Could not capture image.";
	public static final String MSG_SERVER_NOT_RESPONDING = "There was a problem while connecting to server please try later.";
	public static final String MSG_FACEBOOK_ACCESS = "Kindly login with facebook account to share.";
	public static final String MSG_DINE_IN = "Dine in, ";
	public static final String MSG_DRIVE_THRU = "Drive Through, ";
	public static final String MSG_WIFI = "Wifi, ";
	public static final String MSG_HOME_DEL = "Home Delivery, ";
	public static final String MSG_SMOKING = "Smoking Area, ";
	public static final String MSG_EMPTY_NAME = "please provide valid name.";
	public static final String MSG_EMPTY = "please provide valid message.";
	public static final String MSG_EMPTY_SUBJECT = "please provide valid subject.";
	public static final String MSG_EMPTY_EMAIl = "please provide valid email.";
	public static final String MSG_EMAIL_SEND = "Email has been sent successfully";
	public final static String MSG_CLIAM_KICK = "You are about to claim kick \n" ;

	public static final String SEARCH_BY_DISTANCE = "search by distance";
	public static final String SEARCH_BY_CATEGORY = "search by category";
	
	public static final String TYPE_REWARDS = "rewards";
	public static final String TYPE_CHECK_IN = "checkin";
	public static final String TYPE_JOIN = "join";
	public static final String TYPE_VERIFIED_CHECK_IN = "verified_checkin";
	
	public static final String FONT_LARGE = "FONT_LARGE"; //size 28
	public static final String FONT_MEDIUM = "FONT_MEDIUM"; //size 16
	public static final String FONT_SMALL = "FONT_SMALL"; //size 14
	public static final String FONT_EXTRA_SMALL = "FONT_EXTRA_SMALL"; //size 12
	
	public static final int COLOR_BLACK = 0x000000;
	public static final int COLOR_WHITE = 0xFFFFFF; 
	public static final int COLOR_FONT_LIGHT_GREY = 0x808080;
	public static final int COLOR_FONT_DARK_GREY = 0x6d6d6d;
	public static final int COLOR_BG_FORM = 0xF0EBE6;

	public static final int MAP_ZOOM_LEVEL =12; 
	public static final int MAP_HEIGHT  = 110;
	public static final int PAGE_SIZE = 5;
	public static final String LIST_PAGE_SIZE = "&size=" + PAGE_SIZE;
	public static final String DEVICE_IMEI = "om.nokia.mid.imei";
	
	//App id and token for map
	public static final String APP_ID = "ruKXqFS25Gsjhpj9Sbgq";
	public static final String APP_TOKEN = "kWRp_VpkNsxOKeTVTAG7rw";
	
	public static final String FACEBOOK_APP_ID = "286715611395896";
	public static final String APP_URL = "http://www.olaround.me/";
	public static final String[] FACEBOOK_PERMISSIONS = {"publish_actions", "email", "publish_stream"};
	public static final String QR_CODE_PREFIX = "/v/";
	public static final String USERNAME = "mehtabghani";
	public static final String PSWD = "test1234";

	public static final String ABOUT_DETAIL_TEXT = "Want the best Kicks in town for some big Punches? " +
			"You've come to the right corner! Olaround, the brand new app helps you explore what's nearby " +
			"and reap sweet rewards at your favorite restaurants, cafés and more. \n\nWith social on the rise, " +
			"Olaround is a new way businesses are connecting with customers and for the first time, they are " +
			"ready to give the love back. So hurry now and download this coolest app on your Android and iPhone.\n\n" +
			"Already downloaded, go to your nearest Olaround powered venue & start collecting Punches by scanning the " +
			"venue specific QR codes. Once collected, these punches can be redeemed against exciting rewards at your " +
			"desired outlets, we call them Kicks ! \n\nWe've just kicked off and have already powered over 170+ restaurants " +
			"and cafes in Lahore, Karachi and Islamabad, and much more to come. With Olaround you're never going to " +
			"miss the most interesting things around you.";		
	public static final String ABOUT_DETAIL_TEXT_BOLD = "The app is developed by Bramerz in association with Google, P@SHA, and Ufone.";
	
	public static final String[] MONTHS = {"January","Feburary","March","April","May","June","July","August","September","October","November","December"};
}

