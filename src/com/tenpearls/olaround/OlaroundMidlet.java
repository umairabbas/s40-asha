package com.tenpearls.olaround;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.nokia.lwuit.CategoryBarProvider;
import com.sun.lwuit.Command;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.sun.lwuit.List;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.entities.Account;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.LocationHelper;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.forms.AboutForm;
import com.tenpearls.olaround.ui.forms.AddressDetailForm;
import com.tenpearls.olaround.ui.forms.CheckinListingForm;
import com.tenpearls.olaround.ui.forms.ClaimedCheckinInfoForm;
import com.tenpearls.olaround.ui.forms.ClaimedDealInfoForm;
import com.tenpearls.olaround.ui.forms.ClaimedKickInfoForm;
import com.tenpearls.olaround.ui.forms.ClaimedPunchInfoForm;
import com.tenpearls.olaround.ui.forms.DealsDetailForm;
import com.tenpearls.olaround.ui.forms.DealsListingForm;
import com.tenpearls.olaround.ui.forms.FeedbackForm;
import com.tenpearls.olaround.ui.forms.FilterOptionForm;
import com.tenpearls.olaround.ui.forms.ForgotPasswordForm;
import com.tenpearls.olaround.ui.forms.FriendsActivityListingForm;
import com.tenpearls.olaround.ui.forms.GalleryForm;
import com.tenpearls.olaround.ui.forms.HelpForm;
import com.tenpearls.olaround.ui.forms.InviteFriendsForm;
import com.tenpearls.olaround.ui.forms.KicksForm;
import com.tenpearls.olaround.ui.forms.LoginForm;
import com.tenpearls.olaround.ui.forms.OlMeForm;
import com.tenpearls.olaround.ui.forms.OlMenuListingForm;
import com.tenpearls.olaround.ui.forms.QRScanForm;
import com.tenpearls.olaround.ui.forms.QRScanWithChekInForm;
import com.tenpearls.olaround.ui.forms.SearchForm;
import com.tenpearls.olaround.ui.forms.SearchResultListingForm;
import com.tenpearls.olaround.ui.forms.SignUpFormStep1;
import com.tenpearls.olaround.ui.forms.SignUpFormStep2;
import com.tenpearls.olaround.ui.forms.SignUpFormStep3;
import com.tenpearls.olaround.ui.forms.Splash;
import com.tenpearls.olaround.ui.forms.TermsAndCondtionForm;
import com.tenpearls.olaround.ui.forms.ThankyouForm;
import com.tenpearls.olaround.ui.forms.VenueDetailForm;
import com.tenpearls.olaround.ui.forms.VenuesListingForm;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class OlaroundMidlet extends MIDlet implements CategoryBarProvider.ElementListener { 
	
	Splash splash;
	Timer splashTimer;
	LoginForm loginForm;
	ForgotPasswordForm forgotPasswordForm;
	ThankyouForm thankyouForm;
	SignUpFormStep1 signUpFormStep1;
	SignUpFormStep2 signUpFormStep2;
	SignUpFormStep3 signUpFormStep3;
	QRScanWithChekInForm qrScanWithCheckInForm;
	GalleryForm galleryForm;
	FilterOptionForm filterOptionForm;
	SearchForm searchForm;
	VenuesListingForm venuesListingForm;
	SearchResultListingForm searchResultListingForm;
	DealsListingForm dealsListingForm;
	VenueDetailForm venueDetailForm;
	AddressDetailForm addressDetailForm;
	DealsDetailForm dealsDetailForm;
	ClaimedDealInfoForm claimedDealInfoForm;
	KicksForm kicksForm;
	ClaimedKickInfoForm claimedKickInfoForm;
	OlMenuListingForm olMenuListingForm;
	FriendsActivityListingForm activityListingForm;
	FeedbackForm feedbackForm;
	OlMeForm olMeForm;
	CheckinListingForm checkinListingForm;
	QRScanForm qrScanForm;
	TermsAndCondtionForm termsForm;
	ClaimedCheckinInfoForm claimedCheckinInfoForm;
	ClaimedPunchInfoForm claimedPunchInfoForm;
	
	HelpForm helpForm;
	AboutForm aboutForm;
	InviteFriendsForm inviteFriendsForm;
	
	CategoryBarProvider mainCategoryBar;
	CategoryBarProvider venueCategoryBar;
	private String imeiNumber;
	public static Resources res;
	static OlaroundMidlet instance;
	static boolean isLoginWithFB;
	static boolean shareOnFb;
	
	public OlaroundMidlet() {
		instance = this;
	}

	public static OlaroundMidlet getInstance(){
		if(instance == null){
			instance = new OlaroundMidlet();
		}
		return instance;
	}
		
	public void destroyApp(boolean unconditional)
			throws MIDletStateChangeException {
		this.notifyDestroyed();
	}

	protected void pauseApp() {

	}

	protected void startApp() throws MIDletStateChangeException {
		Display.init(this);
		Display.getInstance().setForceFullScreen(true);
		Display.getInstance().setPureTouch(true);
		showSplash();
		setImeiNumber(System.getProperty(ApplicationConstants.DEVICE_IMEI));
		shareOnFb = true; //default set
	}
	
	protected void showSplash() {
		splashTimer = new Timer();
		splashTimer.schedule(new SplashTimeOutClass(), 0000);
		splash = new Splash();
		splash.show();
	}
	
	class SplashTimeOutClass extends TimerTask {
		public void run() {
			//loadTheme();
			String[] iconCommandsNames = ApplicationConstants.MAIN_CATEGORY_ICON_COMMAND_NAMES;
			mainCategoryBar = createCategoryBar(iconCommandsNames, ApplicationConstants.MAIN_CATEGORY_ICON_IMAGE_NAMES);
			iconCommandsNames = ApplicationConstants.VENUE_CATEGORY_ICON_COMMAND_NAMES;
			venueCategoryBar = createCategoryBar(iconCommandsNames, ApplicationConstants.VENUE_CATEGORY_ICON_IMAGE_NAMES);
			showLoginForm(null, false);
			LocationHelper.getLocation();
			splash = null;
		}
	}
	
	private CategoryBarProvider createCategoryBar(String[] iconCommands, String[] iconNames) {
		CategoryBarProvider categoryBar; 
		int length = iconCommands.length; 
		Command[] cmds = new Command[length];
		for (int i = 0; i < length; i++) {
			Command iconCmd = createIconCommand(iconCommands[i], iconNames[i]);//createIconCommand(ApplicationConstants.MAIN_CATEGORY_ICON_COMMAND_NAMES[i]);
			if (iconCmd != null) {
				cmds[i] = iconCmd;
				iconCmd = null;
			}
		}
		categoryBar = CategoryBarProvider.getCategoryBarProvider(cmds, true);
		categoryBar.setElementListener(this);
		return categoryBar;
	}

	//event handler for main and venue detail category bar
	public void notifyElementSelected(CategoryBarProvider bar, int i) {
		switch (i) {
			case 0:
				if(mainCategoryBar.getVisibility()) {
					System.out.println("Venue Category selected");
					showVenuListingForm(null, false, null, "");
				} else if(venueCategoryBar.getVisibility()) {
					System.out.println("venu info category selected");
					if(venueDetailForm != null) {
						showVenueDetailForm(null, false);
					}
				}
				break;
			case 1:
				if(mainCategoryBar.getVisibility()) {
					System.out.println("Deals Category selected");
					showDealsListingForm(null, false);
				} else if(venueCategoryBar.getVisibility()) {
					System.out.println("Deals sub category selected");
					showDealsDetailForm(null, false, DataModel.getVenueEntity().getId(),
							DataModel.getVenueEntity().getBrand().getBackgroundPictureURL(), VenuesListingForm.class.getName());
				}
				break;
			case 2:
				if(mainCategoryBar.getVisibility()) {
					System.out.println("QR scan Category selected");
					showQRScanWithCheckInForm(qrScanWithCheckInForm, true);
				} else if(venueCategoryBar.getVisibility()) {
					System.out.println("Kicks sub category selected");
					showKicksForm(null, false);
				}
				break;
			case 3:
				if(mainCategoryBar.getVisibility()) {
					System.out.println("Friends Category selected");
					showfriendsActivityListingForm(null, false);
				} else if(venueCategoryBar.getVisibility()) {
					System.out.println("menu sub category selected");
					showOlMenuListingForm(null, false);
				}
				break;
			case 4:
				if(mainCategoryBar.getVisibility()) {
					System.out.println("Ol me selected");
					showOlMeForm(null, false);
				} 
				break;
		}
	}
	
	private  Command createIconCommand(String commandName, String iconName) {
		Command iconCmd = null;
		String normalImageName = iconName.toLowerCase() + ".png";
		com.sun.lwuit.Image normalImage = ImageHelper.getImageFromResources(normalImageName);

		if (normalImage != null) {       
			   iconCmd = new com.sun.lwuit.Command(commandName, normalImage, 1);
		}
		return iconCmd;
	}
	
	public void showMainCategoryBar(boolean isVisible) {
		if(mainCategoryBar != null) {
			mainCategoryBar.setVisibility(isVisible);
		} 
	}
	
	public void showVenueDetailCategoryBar(boolean isVisible) {
		if(venueCategoryBar != null) {
			venueCategoryBar.setVisibility(isVisible);
		} 
	}
	
	public void showLoginForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (loginForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);
					loginForm = new LoginForm(ApplicationConstants.TITLE_LOGIN, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(loginForm);
	}
	
	public void showForgotPwdForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (forgotPasswordForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);
					forgotPasswordForm = new ForgotPasswordForm(ApplicationConstants.TITLE_FORGOT_PASSWORD, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(forgotPasswordForm);
	}
	
	public void showThankYouForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (thankyouForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);
					thankyouForm = new ThankyouForm(ApplicationConstants.TITLE_FORGOT_PASSWORD, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(thankyouForm);
	}
	
	public void showSignUpStep1Form(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (signUpFormStep1 == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);
					signUpFormStep1 = new SignUpFormStep1(ApplicationConstants.TITLE_SiGNUP, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(signUpFormStep1);
	}
	
	public void showSignUpStep1Form(BaseForm currentForm, boolean destroy , Image profileImage) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		signUpFormStep1.setProfileImage(profileImage);
		showCurrentForm(signUpFormStep1);
	}
	
	public void showSignUpStep2Form(BaseForm currentForm, boolean destroy, final Account account) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (signUpFormStep2 == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);
					signUpFormStep2 = new SignUpFormStep2(ApplicationConstants.TITLE_SiGNUP, OlaroundMidlet.this, account);
				}
			});
		}
		showCurrentForm(signUpFormStep2);
	}
	
	public void showSignUpStep3Form(BaseForm currentForm, boolean destroy, final Account account) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (signUpFormStep3 == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);
					signUpFormStep3 = new SignUpFormStep3(ApplicationConstants.TITLE_SiGNUP, OlaroundMidlet.this, account);
				}
			});
		}
		showCurrentForm(signUpFormStep3);
	}
	
	public void showSignUpStep3Form(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}

		showCurrentForm(signUpFormStep3);
	}
	
	public void showQRScanWithCheckInForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (qrScanWithCheckInForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);
					qrScanWithCheckInForm = new QRScanWithChekInForm(ApplicationConstants.TITLE_QR_SCAN_FORM, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(qrScanWithCheckInForm);
	}
	
	public void showGalleryForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (galleryForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);
					galleryForm = new GalleryForm(ApplicationConstants.TITLE_SELECT_PHOTO, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(galleryForm);
	}
	
	public void showVenuListingForm(BaseForm currentForm, boolean destroy, final List venueList, final String query) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (venuesListingForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);
					if(venueList != null) {
						venuesListingForm = new VenuesListingForm(ApplicationConstants.TITLE_OL_VENUES, OlaroundMidlet.this, venueList, query);
					} 
				}
			});
		}
		showCurrentForm(venuesListingForm);
	}

	public void showFilterOptionForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (filterOptionForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);

					filterOptionForm = new FilterOptionForm(ApplicationConstants.TITLE_OL_FILTERS, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(filterOptionForm);
	}
	
	public void showSearchForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (searchForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {

				public void run() {
					Display.getInstance().setForceFullScreen(false);
					searchForm = new SearchForm(ApplicationConstants.TITLE_OL_SEARCH_FORM, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(searchForm);
	}

	public void showVenueDetailForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (venueDetailForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {

				public void run() {
					Display.getInstance().setForceFullScreen(false);

					venueDetailForm = new VenueDetailForm(ApplicationConstants.TITLE_OL_VENUES, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(venueDetailForm);
	}
	
	public void showAddressDetailForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (addressDetailForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {

				public void run() {
					Display.getInstance().setForceFullScreen(false);

					addressDetailForm = new AddressDetailForm(ApplicationConstants.TITLE_OL_ADDRESS, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(addressDetailForm);
	}
	
	public void showSearchResultListingForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (searchResultListingForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {

				public void run() {
					searchResultListingForm = new SearchResultListingForm(ApplicationConstants.TITLE_OL_VENUES, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(searchResultListingForm);
	}
	
	public void showDealsListingForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (dealsListingForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {

				public void run() {
					dealsListingForm = new DealsListingForm(ApplicationConstants.TITLE_OL_DEALS, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(dealsListingForm);
	}
	
	public void showDealsDetailForm(BaseForm currentForm, boolean destroy, final int storeId, final String storePicture, final String navigateForm) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (dealsDetailForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					dealsDetailForm = new DealsDetailForm(ApplicationConstants.TITLE_OL_DEALS_DETAIL, OlaroundMidlet.this,
							storeId, storePicture, navigateForm);
				}
			});
		}
		showCurrentForm(dealsDetailForm);
	}
	
	public void showDealsDetailForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		showCurrentForm(dealsDetailForm);
	}
	
	public void showClaimDealInfoForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (claimedDealInfoForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					claimedDealInfoForm = new ClaimedDealInfoForm(ApplicationConstants.TITLE_CLAIMED_DEAL, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(claimedDealInfoForm);
	}
	
	public void showKicksForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (kicksForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					kicksForm = new KicksForm(ApplicationConstants.TITLE_OL_KICKS, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(kicksForm);
	}
	
	public void showClaimKickInfoForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (claimedKickInfoForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					claimedKickInfoForm = new ClaimedKickInfoForm(ApplicationConstants.TITLE_CLAIMED_DEAL, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(claimedKickInfoForm);
	}
	
	public void showOlMenuListingForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (olMenuListingForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					olMenuListingForm = new OlMenuListingForm(ApplicationConstants.TITLE_OL_MENU, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(olMenuListingForm);
	}
	
	public void showfriendsActivityListingForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (activityListingForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					activityListingForm = new FriendsActivityListingForm(ApplicationConstants.TITLE_OL_FRIENDS, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(activityListingForm);
	}
	
	public void showFeedbackForm(final BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (feedbackForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {

				public void run() {
					feedbackForm = new FeedbackForm(ApplicationConstants.TITLE_OL_FEEDBACK, OlaroundMidlet.this, currentForm);
				}
			});
		}
		showCurrentForm(feedbackForm);
	}
	
	public void showInviteFriendsForm(final BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (inviteFriendsForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {

				public void run() {
					inviteFriendsForm = new InviteFriendsForm(ApplicationConstants.TITLE_OL_INVITE, OlaroundMidlet.this, currentForm);
				}
			});
		}
		showCurrentForm(inviteFriendsForm);
	}
	
	public void showOlMeForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (olMeForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {

				public void run() {
					olMeForm = new OlMeForm(ApplicationConstants.TITLE_OL_ME, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(olMeForm);
	}
	
	public void showCheckinListingForm(BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (checkinListingForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);
					checkinListingForm = new CheckinListingForm(ApplicationConstants.TITLE_OL_CHECKIN, OlaroundMidlet.this);
				}
			});
		}
		showCurrentForm(checkinListingForm);
	}
	
	public void showQRScanForm(final BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (qrScanForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					Display.getInstance().setForceFullScreen(false);
					qrScanForm = new QRScanForm(ApplicationConstants.TITLE_QR_SCAN_FORM, OlaroundMidlet.this, currentForm);
				}
			});
		}
		showCurrentForm(qrScanForm);
	}
	
	public void showTermsForm(BaseForm currentForm, boolean destroy, final String navigateFrom) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (termsForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					termsForm = new TermsAndCondtionForm(OlaroundMidlet.this, navigateFrom);
				}
			});
		}
		showCurrentForm(termsForm);
	}

	public void showHelpForm(BaseForm currentForm, boolean destroy, final String navigateFrom) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (helpForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					helpForm = new HelpForm(OlaroundMidlet.this, navigateFrom);
				}
			});
		}
		showCurrentForm(helpForm);
	}
	
	public void showAboutForm(BaseForm currentForm, boolean destroy, final String navigateFrom) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (aboutForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					aboutForm = new AboutForm(OlaroundMidlet.this, navigateFrom);
				}
			});
		}
		showCurrentForm(aboutForm);
	}
	
	public void showClaimedCheckinInfoForm(final BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(currentForm);
		if (destroy) {
			deallocateCurrentForm(currentForm);
		}
		if (claimedKickInfoForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					claimedCheckinInfoForm = new ClaimedCheckinInfoForm(ApplicationConstants.TITLE_CLAIMED_CHECKIN, OlaroundMidlet.this, currentForm);
				}
			});
		}
		showCurrentForm(claimedCheckinInfoForm);
	}
	
	public void showClaimedPunchInfoForm(final BaseForm currentForm, boolean destroy, final String venueName) {
		pauseCurrentForm(currentForm);
		if (claimedPunchInfoForm == null) {
			Display.getInstance().invokeAndBlock(new Runnable() {
				public void run() {
					claimedPunchInfoForm = new ClaimedPunchInfoForm(ApplicationConstants.TITLE_PUNCH, OlaroundMidlet.this, currentForm, venueName);
				}
			});
		}
		showCurrentForm(claimedPunchInfoForm);
	}

	private void pauseCurrentForm(BaseForm currentForm) {
		if (currentForm != null) {
			currentForm.onPause();
		}
	}
	
	public void showCurrentForm(BaseForm currentForm) {
		if (currentForm != null) {
			currentForm.onResume();
			currentForm.dismissLoadingDialog();
			currentForm.show();
		}
	}
	
	public void showCurrentForm(BaseForm oldForm, BaseForm currentForm, boolean destroy) {
		pauseCurrentForm(oldForm);
		if (destroy) {
			deallocateCurrentForm(oldForm);
		}
		if (currentForm != null) {
			currentForm.onResume();
			currentForm.dismissLoadingDialog();
			currentForm.show();
		}
	}
	
	public void refreshCurrentForm(BaseForm currentForm) {
		pauseCurrentForm(currentForm);
		System.out.println("Refresh:"+ currentForm.getClass().getName());
		deallocateCurrentForm(currentForm);
		//currentForm.showLoadingDialog();
		currentForm.loadData(true);
		currentForm.onCreate();
		currentForm.populateCommandMenu();
		showCurrentForm(currentForm);
	}
	
	public void deallocateCurrentForm(BaseForm currentForm) {
	
		if (currentForm != null) {
			currentForm.dealloc();
			if (currentForm instanceof LoginForm) {
				loginForm = null;
			} else if(currentForm instanceof ForgotPasswordForm) {
				forgotPasswordForm = null;
			} else if (currentForm instanceof ThankyouForm) {
				thankyouForm = null;
			} else if(currentForm instanceof SignUpFormStep1) {
				signUpFormStep1 = null;
			} else if(currentForm instanceof SignUpFormStep2) {
				signUpFormStep2 = null;
			} else if(currentForm instanceof SignUpFormStep3) {
				signUpFormStep3 = null;
			}else if(currentForm instanceof GalleryForm) {
				galleryForm = null;
			} else if (currentForm instanceof FilterOptionForm) {
				filterOptionForm = null;
			} else if(currentForm instanceof VenuesListingForm) {
				venuesListingForm = null;
			} else if(currentForm instanceof SearchForm) {
				searchForm = null;
			} else if(currentForm instanceof SearchResultListingForm) {
				searchResultListingForm = null;
			} else if(currentForm instanceof DealsListingForm) {
				dealsListingForm = null;
			} else if(currentForm instanceof VenueDetailForm) {
				venueDetailForm = null;
			} else if(currentForm instanceof AddressDetailForm) {
				addressDetailForm = null;
			} else if(currentForm instanceof DealsDetailForm) {
				dealsDetailForm = null;
			} else if(currentForm instanceof ClaimedDealInfoForm) {
				claimedDealInfoForm = null;
			} else if(currentForm instanceof KicksForm) {
				kicksForm = null;
			} else if(currentForm instanceof ClaimedKickInfoForm) {
				claimedKickInfoForm = null;
			} else if(currentForm instanceof OlMenuListingForm) {
				olMenuListingForm = null;
			} else if(currentForm instanceof FriendsActivityListingForm) {
				activityListingForm = null;
			} else if (currentForm instanceof FeedbackForm) {
				feedbackForm = null;
			} else if (currentForm instanceof OlMeForm) {
				olMeForm = null;
			} else if (currentForm instanceof QRScanWithChekInForm) {
				qrScanWithCheckInForm = null;
			} else if (currentForm instanceof QRScanForm) {
				qrScanForm = null;
			} else if(currentForm instanceof TermsAndCondtionForm) {
				termsForm = null;
			} else if (currentForm instanceof CheckinListingForm) {
				checkinListingForm = null;
			} else if (currentForm instanceof ClaimedCheckinInfoForm) {
				claimedCheckinInfoForm = null;
			} else if(currentForm instanceof ClaimedPunchInfoForm) {
				claimedPunchInfoForm = null;
			}else if(currentForm instanceof HelpForm) {
				helpForm = null;
			} else if(currentForm instanceof AboutForm) {
				aboutForm = null;
			} else if(currentForm instanceof InviteFriendsForm){
				inviteFriendsForm = null;
			}
		}
	}
	
	public void destoryVenueListingForm() {
		if(venuesListingForm != null) {
			venuesListingForm.dealloc();
			venuesListingForm = null;
		}
	}
	
	public void destoryFeedbackForm() {
		if(feedbackForm != null) {
			feedbackForm.dealloc();
			feedbackForm = null;
		}
	}
	
	public void destoryinviteFriendsForm() {
		  if(inviteFriendsForm != null) {
		   inviteFriendsForm.dealloc();
		   inviteFriendsForm = null;
		  }
		  showfriendsActivityListingForm(null, false);
	}
	
	public void destoryGalleryForm() {
		if(galleryForm != null) {
			galleryForm.dealloc();
			galleryForm = null;
		}
	}
	
	public void destorySearchForm() {
		if(searchForm != null) {
			System.out.println("destroying search form");
			searchForm.dealloc();
			searchForm = null;
		}
	}
	
	public void destorySearchResultListingForm() {
		if(searchResultListingForm != null) {
			searchResultListingForm.dealloc();
			searchResultListingForm = null;
		}
	}
	
	public void destoryMianCategoryForms() {

		deallocateCurrentForm(venuesListingForm);
		deallocateCurrentForm(qrScanWithCheckInForm);
		deallocateCurrentForm(olMeForm);
		deallocateCurrentForm(activityListingForm);
		deallocateCurrentForm(dealsListingForm);
		
	}
	
	public void destoryVenueDetailCategoryForms() {
		System.out.println("Destroying venue detail category forms");
		deallocateCurrentForm(venueDetailForm);
		deallocateCurrentForm(dealsDetailForm);
		deallocateCurrentForm(kicksForm);
		deallocateCurrentForm(olMenuListingForm);
	}

	public void navigateToForm(BaseForm currentForm, String formName, boolean destroy) {
		if(!formName.equals("")) {
			if(formName.equals(SearchForm.class.getName())) {
				showVenuListingForm(currentForm, destroy, null, "");
			} else if(formName.equals(VenuesListingForm.class.getName())) {
				helpForm = null;
				aboutForm = null;
				showVenuListingForm(currentForm, false, null, "");
			} else if(formName.equals(LoginForm.class.getName())) {
				if(currentForm instanceof LoginForm) {
					destroy = false;
				}
				showLoginForm(currentForm, destroy);
			} else if(formName.equals(OlMeForm.class.getName())) {
				showOlMeForm(currentForm, destroy);
			} else if(formName.equals(SignUpFormStep3.class.getName())) {
				showSignUpStep3Form(currentForm, destroy);
			}  else if(formName.equals(DealsListingForm.class.getName())) {
				showDealsListingForm(currentForm, destroy);
			} 
		}
	}

	public void loadTheme() {
		try {
			res = com.sun.lwuit.util.Resources
					.open(ApplicationConstants.THEME_FILE);
			UIManager.getInstance().setThemeProps(
					res.getTheme(res.getThemeResourceNames()[0]));
		} catch (IOException ioe) {
			System.out.println("Error");
		}
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public CategoryBarProvider getMainCategoryBar() {
		return mainCategoryBar;
	}
	
	public CategoryBarProvider getVenueCategoryBar() {
		return venueCategoryBar;
	}

	public static boolean isLoginWithFB() {
		return isLoginWithFB;
	}

	public static void setLoginWithFB(boolean isLoginWithFB) {
		OlaroundMidlet.isLoginWithFB = isLoginWithFB;
	}

	public static boolean isShareOnFb() {
		return shareOnFb;
	}

	public static void setShareOnFb(boolean shareOnFb) {
		OlaroundMidlet.shareOnFb = shareOnFb;
	}
	
	public static String getNetworkCode() {
		String code   =  new String (System.getProperty("com.nokia.mid.mnc"));
		return code;
	}

}
