package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.entities.Account;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.StringUtils;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class SignUpFormStep2 extends BaseForm {

	private Container contentContainer;
	private static TextField userNameField = null; 
	private static TextField passwordField = null; 
	private static TextField emailField  = null;
	private Account account;
	
	public SignUpFormStep2(String title, OlaroundMidlet mainMidlet, Account account) {
		super(title, mainMidlet);
		this.account = account;
	}

	public void onCreate() {
		this.setScrollable(false);
		Image bgImage = ImageHelper.getImageFromResources(ImageConstants.BACKGROUND);
		if(bgImage != null){
			this.getStyle().setBgImage(bgImage);
			this.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			bgImage = null;
		}
		
		contentContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Dimension dimension = new Dimension(50, 50);
		contentContainer.setScrollSize(dimension);
		contentContainer.getStyle().setMargin(0, 0, 10, 10);
		contentContainer.getStyle().setBgTransparency(0);
		getSteps(ImageConstants.STEP_2);
		createLabel(ApplicationConstants.LABEL_TEXT_USERNAME, 0);
		getUserName();
		createLabel(ApplicationConstants.LABEL_TEXT_PASSWORD, 0);
		getPassword();
		createLabel(ApplicationConstants.LABEL_TEXT_EMAIL, 0);
		getEmailField();
		getContinueButton();
		addComponent(contentContainer);
		contentContainer = null;
	}

	private void createLabel(String text, int topMargin) {
		Label label = new Label(text);
		label.getStyle().setFgColor(0xFFFFFF);
		label.getStyle().setMargin(topMargin, 3, 0, 0);
		contentContainer.addComponent(label);
	}
	
	private void getUserName() {
		userNameField = new TextField();
		userNameField.setMaxSize(18);
		setTextFieldStyle(userNameField, 0);
		contentContainer.addComponent(userNameField);
	}
	
	private void getPassword() {
		passwordField = new TextField();
		passwordField.setConstraint(TextField.PASSWORD);
		passwordField.setMaxSize(18);
		setTextFieldStyle(passwordField, 0);
		contentContainer.addComponent(passwordField);
	}
	
	private void getEmailField() {
		emailField = new TextField();
		emailField.setMaxSize(32);
		setTextFieldStyle(emailField, 10);
		emailField.setConstraint(TextField.EMAILADDR);
		contentContainer.addComponent(emailField);
	}
	
	private void getContinueButton() {
		Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		CustomImageButton button = new CustomImageButton(ImageConstants.BUTTON_GREEN_NORMAL, ImageConstants.BUTTON_GREEN_SELECTED, ApplicationConstants.BUTTON_NAME_CONTINUE);
		button.setText(ApplicationConstants.BUTTON_NAME_CONTINUE);
		button.setPreferredH(40);
		button.setPreferredW(208); 
		button.addActionListener(this);
		button.getStyle().setFgColor(0xFFFFFF);
		button.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		button.getPressedStyle().setFgColor(0xFFFFFF);
		button.getPressedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		buttonContainer.getStyle().setMargin(10, 0, 0, 0);
		buttonContainer.addComponent(button);
		contentContainer.addComponent(buttonContainer);
		button = null;
		buttonContainer = null;
	}
	
	public void onResume() {

	}

	public void onPause() {

	}

	public void onButtonClick(Button button) {
		String email = emailField.getText().trim().toLowerCase();
		if(button.getName().equals(ApplicationConstants.BUTTON_NAME_CONTINUE)) {
			if(userNameField.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_VALIDATION_USERNAME);
			} else if(passwordField.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_VALIDATION_PASSWORD);
			} else if(email.equals("") ) {
				validationPopUp(ApplicationConstants.MSG_VALIDATION_EMAIL);
			} else if( !(StringUtils.isContains(email, "@") && StringUtils.isContains(email, ".")) ) {
				validationPopUp(ApplicationConstants.MSG_VALIDATION_EMAIL);
			} else {
				account.setUserName(userNameField.getText().trim().toLowerCase());
				account.setPassword(passwordField.getText().trim().toLowerCase());
				account.setEmail(emailField.getText().trim().toLowerCase());
				mainMidlet.showSignUpStep3Form(SignUpFormStep2.this, false, account);
			}
		}

	}

	public void onCommandClick(Command cmd) {

	}

	public void loadData(boolean isRefreshed) {

	}

	public void addCommands() {

	}

	public void onBack() {
		mainMidlet.showSignUpStep1Form(SignUpFormStep2.this, false);

	}
	
	public void dealloc() {
		super.dealloc();
		userNameField = null;
		passwordField = null;
		emailField = null;	
	}

}
