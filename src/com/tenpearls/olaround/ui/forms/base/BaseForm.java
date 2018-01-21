package com.tenpearls.olaround.ui.forms.base;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.StringItem;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.WebServiceHelper;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomDialogBox;
import com.tenpearls.olaround.ui.forms.DealsListingForm;
import com.tenpearls.olaround.ui.forms.FeedbackForm;
import com.tenpearls.olaround.ui.forms.ForgotPasswordForm;
import com.tenpearls.olaround.ui.forms.FriendsActivityListingForm;
import com.tenpearls.olaround.ui.forms.LoginForm;
import com.tenpearls.olaround.ui.forms.OlMeForm;
import com.tenpearls.olaround.ui.forms.QRScanForm;
import com.tenpearls.olaround.ui.forms.QRScanWithChekInForm;
import com.tenpearls.olaround.ui.forms.SearchForm;
import com.tenpearls.olaround.ui.forms.SearchResultListingForm;
import com.tenpearls.olaround.ui.forms.SignUpFormStep1;
import com.tenpearls.olaround.ui.forms.SignUpFormStep2;
import com.tenpearls.olaround.ui.forms.SignUpFormStep3;
import com.tenpearls.olaround.ui.forms.ThankyouForm;
import com.tenpearls.olaround.ui.forms.VenuesListingForm;

public abstract class BaseForm extends Form implements ActionListener,INavigationForm, IClickListener, IMemoryManager {

	protected OlaroundMidlet mainMidlet;
	protected javax.microedition.lcdui.Form loadingDialog;
	protected String title;
	protected boolean isFormCancelled;
	protected int pageIndex = 1;
	protected int storeId;
	protected String storePicture;
	
	public BaseForm(String title, OlaroundMidlet mainMidlet) {
		super(title);
		this.mainMidlet = mainMidlet;
		this.title = title;
		this.isFormCancelled = false;
		init();
	}
	
	public BaseForm(String title, OlaroundMidlet mainMidlet, int storeId, String storePicture) {
		super(title);
		this.mainMidlet = mainMidlet;
		this.title = title;
		this.isFormCancelled = false;
		this.storeId = storeId;
		this.storePicture = storePicture;
		init();
	}
	
	private void init() {
		if( !(this instanceof ForgotPasswordForm || this instanceof SearchResultListingForm 
				|| this instanceof SignUpFormStep1 || this instanceof SignUpFormStep2 
				|| this instanceof SignUpFormStep3 || this instanceof FeedbackForm
				|| this instanceof QRScanForm ||  this instanceof QRScanWithChekInForm 
				|| this instanceof LoginForm) ) {
			showLoadingDialog();
		} 
		loadData(false);
		onCreate();
		populateCommandMenu();
		this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		setScrollableY(true);
		setCyclicFocus(false);
	}
	
	public void showLoadingDialog() {

		if(loadingDialog==null) {
			try {
				mainMidlet.showMainCategoryBar(false);
				mainMidlet.showVenueDetailCategoryBar(false);
				System.out.println("in showLaoadDialog. loading...");
				loadingDialog = new javax.microedition.lcdui.Form("loading...");
				Gauge gau = new Gauge(ApplicationConstants.MSG_LOADING_DIALOG, false, Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING);
				gau.setPreferredSize(240, 80);
				StringItem cancelButton = new StringItem("", "CANCEL", Item.BUTTON);
                
				cancelButton.setDefaultCommand(new javax.microedition.lcdui.Command(
						"CANCEL", javax.microedition.lcdui.Command.CANCEL, 1));
				
				ItemCommandListener cancelListener = new ItemCommandListener() {

					public void commandAction(
							javax.microedition.lcdui.Command arg0, Item arg1) {
						System.out.println("*Cancel pressed");
						isFormCancelled = true;
						dismissLoadingDialog();
						navigateBack();
						try {
							WebServiceHelper.setConnectionInProgress(false);
							WebServiceHelper.closeInputStream(); 
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				};
				cancelButton.setItemCommandListener(cancelListener);
				loadingDialog.append(gau);	
				loadingDialog.append(cancelButton);
				Display.getDisplay(mainMidlet).setCurrent(loadingDialog);
			
			} catch (Exception e) {
				e.printStackTrace();
				System.gc();
			}
		}		
	}
	
	private void navigateBack() {
		String navigateFrom = this.getClass().getName();
		mainMidlet.navigateToForm(this, navigateFrom, true);
	}
	
	public void actionPerformed(ActionEvent event) {
		Object obj = event.getSource();
		if (obj instanceof Button) {
			event.consume();
			onButtonClick((Button) obj);
			return;
		}
		if (obj instanceof Command) {
			event.consume();
			Command cmd = (Command) obj;
			String commandName = cmd.getCommandName();

			if (commandName.equals(ApplicationConstants.BACK_COMMAND)) {
				onBack();
				return;
			} else if (commandName.equals(ApplicationConstants.ABOUT_COMMAND)) {
				onAbout();
				return;
			} else if (commandName.equals(ApplicationConstants.HELP_COMMAND)) {
				onHelp();
				return;
			} else if (commandName.equals(ApplicationConstants.FILTER_COMMAND)) {
				onFiler();
				return;
			} else if (commandName.equals(ApplicationConstants.SEARCH_COMMAND)) {
				onSearch();
				return;
			} else if (commandName.equals(ApplicationConstants.FEEDBACK_COMMAND)) {
				onFeedback();
				return;
			} else if (commandName.equals(ApplicationConstants.LOGOUT_COMMAND)) {
				onLogout();
				return;
			} else if (commandName.equals(ApplicationConstants.SHARE_COMMAND)) {
				onShare();
				return;
			} else if (commandName.equals(ApplicationConstants.TERMS_COMMAND)) {
				onTerms();
				return;
			} else if (commandName.equals(ApplicationConstants.ADD_COMMAND)) {
				onAdd();
				return;
			}

			onCommandClick(cmd);
			return;
		}
	}
	
	private void onAdd() {
		mainMidlet.showInviteFriendsForm(this, false);
	}

	private void onTerms() {
		String navigateFrom = this.getClass().getName();
		if(!navigateFrom.equals("")){
			mainMidlet.showTermsForm(this, false, navigateFrom);
		}
	}

	private void onShare() {
		if(OlaroundMidlet.isLoginWithFB()) {
			String msg = ApplicationConstants.LABEL_FACEBOOK_SHARE;
			CustomDialogBox.showDialogForShare(msg, this);
		}
		else {
			String errorMsg = ApplicationConstants.MSG_FACEBOOK_ACCESS;
			CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, errorMsg, this);
		}
	}

	private void onLogout() {
		String msg = ApplicationConstants.LABEL_LOGOUT_TEXT;
		CustomDialogBox.showDialogForLogout(msg, this);
	}

	private void onFeedback() {
		mainMidlet.showFeedbackForm(this, false);
	}

	public void onAbout() {
		String navigateFrom = this.getClass().getName();
		if(!navigateFrom.equals("")){
			mainMidlet.showAboutForm(this, false, navigateFrom);
		}
	}
	
	public void onHelp() {
		String navigateFrom = this.getClass().getName();
		if(!navigateFrom.equals("")){
			mainMidlet.showHelpForm(this, false, navigateFrom);
		}		
	}

	public void onFiler() {
		String navigateFrom = this.getClass().getName();
		if(!navigateFrom.equals("")){
			mainMidlet.showFilterOptionForm(this, false);
		}		
	}
	
	public void onSearch() {
		//String navigateFrom = this.getClass().getName();
		//if(!navigateFrom.equals("")){
			mainMidlet.showSearchForm(this, false);
		//}		
	}

	public void populateCommandMenu() {

		Command aboutCmd = new Command(ApplicationConstants.ABOUT_COMMAND);
		Command helpCmd = new Command(ApplicationConstants.HELP_COMMAND);

		addCommands(); 

		if(this instanceof LoginForm || this instanceof ForgotPasswordForm || this instanceof ThankyouForm) {
			addCommand(helpCmd);
			addCommand(aboutCmd);
		} else if(this instanceof VenuesListingForm) {
			Command filterCommand = new Command(ApplicationConstants.FILTER_COMMAND);
			Command searchCommand = new Command(ApplicationConstants.SEARCH_COMMAND);
			addCommand(null);
			addCommand(helpCmd);
			addCommand(aboutCmd);
			addCommand(searchCommand);
			addCommand(filterCommand);
			filterCommand = null;
			searchCommand = null;
		} else if(this instanceof OlMeForm) {
			Command feedbackCmd = new Command(ApplicationConstants.FEEDBACK_COMMAND);
			Command logoutCmd = new Command(ApplicationConstants.LOGOUT_COMMAND);
			Command shareCmd = new Command(ApplicationConstants.SHARE_COMMAND);
			Command termsCmd = new Command(ApplicationConstants.TERMS_COMMAND);		
			addCommand(null);	
			addCommand(helpCmd);			
			addCommand(logoutCmd);
			addCommand(aboutCmd);
			addCommand(feedbackCmd);
			addCommand(termsCmd);
			addCommand(shareCmd);
			feedbackCmd = null;
			logoutCmd = null;
			shareCmd = null;
			termsCmd = null;
		}

		addBackCommand();
		this.addCommandListener(this);
		aboutCmd = null;
		helpCmd = null;
		
		
	}

	public void dismissLoadingDialog() {
		System.out.println("Destroying loading dialog");
		loadingDialog = null;
	}
	
	private void addBackCommand() {
		Command backCmd = new Command(ApplicationConstants.BACK_COMMAND);
		setBackCommand(backCmd);
		backCmd = null;
	}
	
	public void dealloc() {
		removeAll();
		removeAllCommands();
	}

	public void onShowCompleted() {
		System.out.println("**On Show Completed");
		if(isFormCancelled) {
			isFormCancelled = false;
			if(this instanceof SearchForm) {
				DataModel.emptySearchData();
				mainMidlet.destorySearchForm();
				System.out.println("**Going Back");
				onBack();
			}
			if(this instanceof VenuesListingForm || this instanceof FriendsActivityListingForm 
					|| this instanceof DealsListingForm ) {
				--pageIndex;
			}
		}
	}
	
	protected void createEmptyBorderButton(Button button) {
		button.getStyle().setFgColor(0xFFFFFF);
		button.getPressedStyle().setFgColor(0xFFFFFF);
		
		if(button.getName() != null) {
			if(button.getName().equals(ApplicationConstants.BUTTON_NAME_SIGN_UP) ||
					button.getName().equals(ApplicationConstants.BUTTON_NAME_TERMS_AND_CONDTION)) {
				button.getStyle().setUnderline(true);
				button.getPressedStyle().setUnderline(true);
			}
		}
		
		button.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		button.getSelectedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		button.getPressedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		button.getDisabledStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		
		button.getStyle().setBorder(Border.createEmpty(), true);
		button.getSelectedStyle().setBorder(Border.createEmpty(), true);
		button.getPressedStyle().setBorder(Border.createEmpty(), true);
		button.getDisabledStyle().setBorder(Border.createEmpty(), true);

		button.getStyle().setPadding(0, 0, 0, 8);
		button.getSelectedStyle().setPadding(0, 0, 0, 8);
		button.getPressedStyle().setPadding(0, 0, 0, 8);
		button.getDisabledStyle().setPadding(0, 0, 0, 8);
	}
	
	protected void setTextFieldStyle(TextField textField, int bottomMargin) {
		int topMargin = 0;
		int leftMargin = 0;
		int rightMargin = 0;
		textField.getStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);
		textField.getSelectedStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);
		textField.getPressedStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);
		textField.getUnselectedStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);
		textField.getStyle().setBorder(Border.createRoundBorder(25, 30, ApplicationConstants.COLOR_WHITE));
		textField.getPressedStyle().setBorder(Border.createRoundBorder(25, 30, ApplicationConstants.COLOR_WHITE));
		textField.getSelectedStyle().setBorder(Border.createRoundBorder(25, 30, ApplicationConstants.COLOR_WHITE));
		textField.getUnselectedStyle().setBorder(Border.createRoundBorder(25, 30, ApplicationConstants.COLOR_WHITE));
		textField.getStyle().setBgColor(0xFFFFFF);
		textField.getPressedStyle().setBgColor(0xFFFFFF);
		textField.getSelectedStyle().setBgColor(0xFFFFFF);
		textField.getUnselectedStyle().setBgColor(0xFFFFFF);
		textField.setScrollVisible(false);
	}
	
	protected void setTextAreaStyle(TextArea textArea, int bottomMargin) {
		int topMargin = 0;
		int leftMargin = 0;
		int rightMargin = 0;
		textArea.getStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);
		textArea.getSelectedStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);
		textArea.getPressedStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);
		textArea.getUnselectedStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);
		textArea.getStyle().setBorder(Border.createRoundBorder(25, 30, ApplicationConstants.COLOR_WHITE));
		textArea.getPressedStyle().setBorder(Border.createRoundBorder(25, 30, ApplicationConstants.COLOR_WHITE));
		textArea.getSelectedStyle().setBorder(Border.createRoundBorder(25, 30, ApplicationConstants.COLOR_WHITE));
		textArea.getUnselectedStyle().setBorder(Border.createRoundBorder(25, 30, ApplicationConstants.COLOR_WHITE));
		textArea.getStyle().setBgColor(0xFFFFFF);
		textArea.getPressedStyle().setBgColor(0xFFFFFF);
		textArea.getSelectedStyle().setBgColor(0xFFFFFF);
		textArea.getUnselectedStyle().setBgColor(0xFFFFFF);
		textArea.setScrollVisible(false);
	}
	
	protected void validationPopUp(String message) {
		CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, message, this);
	}
	
	protected void getSteps(String stepImageName) {
		Label stepLabel = new Label();
		stepLabel.getStyle().setBgTransparency(0);
		stepLabel.getStyle().setPadding(15, 5, 5, 5);
		stepLabel.getStyle().setAlignment(Component.CENTER);
		Image stepImage = ImageHelper.getImageFromResources(stepImageName);
		if(stepImage != null) {
			stepLabel.setIcon(stepImage);
		}
		this.addComponent(stepLabel);
		stepImage = null;
		stepLabel = null;
	}
	
	public void createdivider() {
		Label lblImgShadow = new Label();
		lblImgShadow.getStyle().setPadding(0, 0, 0, 0);
		lblImgShadow.getStyle().setMargin(0, 0, 0, 0);
		Image imgShadow = ImageHelper
				.getImageFromResources(ImageConstants.DIVIDER);
		if (imgShadow != null) {
			lblImgShadow.setIcon(imgShadow);
		}
		this.addComponent(lblImgShadow);
		imgShadow = null;
	}
	
	public void setBannerImage(String url) {
		Image imgBack = null;
		Label lblBack = new Label();
		try {
			 imgBack = ImageHelper.getImageFromWeb(url);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (imgBack != null) {
			int width = this.getWidth();
			
			lblBack.setPreferredH(50);
			lblBack.setPreferredW(width);
			lblBack.getStyle().setBgImage(imgBack);
			lblBack.getStyle().setMargin(0, 0, 0, 0);
			lblBack.getStyle().setPadding(0, 0, 0, 0);
			lblBack.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			this.addComponent(lblBack);
			createdivider();
		}

	}
	
	public void showScanResult(String result) {
	}

	public abstract void loadData(boolean isRefreshed);

	public abstract void addCommands();

	public abstract void onBack();

}
