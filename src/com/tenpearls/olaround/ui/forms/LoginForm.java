package com.tenpearls.olaround.ui.forms;

import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.helpers.FacebookHelper;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class LoginForm extends BaseForm {

	static TextField emailField  = null;
	static TextField passwordField = null; 
	private LoginForm loginForm;
		
	public LoginForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
		loginForm = this;
	}

	public void onCreate() {
		this.setScrollable(false);
		Image bgImage = ImageHelper.getImageFromResources(ImageConstants.BACKGROUND);
		if(bgImage != null){
			this.getStyle().setBgImage(bgImage);
			this.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			bgImage = null;
		}
	
		Container fieldContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		fieldContainer.getStyle().setMargin(15, 0, 10, 10);
		fieldContainer.getStyle().setBgTransparency(0);
		fieldContainer.addComponent(createLabel(ApplicationConstants.LABEL_TEXT_USERNAME, 0));
		fieldContainer.addComponent(createEmailField());
		fieldContainer.addComponent(createLabel(ApplicationConstants.LABEL_TEXT_PASSWORD, 10));
		fieldContainer.addComponent(createPasswordField());
		
		this.addComponent(createLoginButton(ImageConstants.BUTTON_BLUE_NORMAL, ImageConstants.BUTTON_BLUE_SELECTED, ApplicationConstants.BUTTON_NAME_SIGN_IN_FB));
		this.addComponent(fieldContainer);
		this.addComponent(createLoginButton(ImageConstants.BUTTON_GREEN_NORMAL, ImageConstants.BUTTON_GREEN_SELECTED, ApplicationConstants.BUTTON_NAME_LOGIN));
		this.addComponent(linkButtonConatiner());
		
		fieldContainer = null;
	}
	
	private TextField createEmailField() {
		emailField = new TextField(ApplicationConstants.USERNAME); 
		emailField.setMaxSize(32);
		emailField.setConstraint(TextField.EMAILADDR);
		setTextFieldStyle(emailField, 0);
		return emailField;
	}
	
	private TextField createPasswordField() {
		passwordField = new TextField(ApplicationConstants.PSWD); 
		passwordField.setConstraint(TextField.PASSWORD);
		passwordField.setMaxSize(18);
		setTextFieldStyle(passwordField, 0);
		return passwordField;
	}

	private Label createLabel(String text, int topMargin) {
		Label label = new Label(text);
		label.getStyle().setFgColor(0xFFFFFF);
		label.getStyle().setMargin(topMargin, 3, 0, 0);
		return label;
	}
	
	private Container createLoginButton(String imageName, String selectedImagename, String buttonName) {
		Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		CustomImageButton button = new CustomImageButton(imageName, selectedImagename, buttonName);
		button.setText(buttonName);
		button.setPreferredH(40);
		button.setPreferredW(208); 
		button.addActionListener(this);
		button.getStyle().setFgColor(0xFFFFFF);
		button.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		button.getPressedStyle().setFgColor(0xFFFFFF);
		button.getPressedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));

		buttonContainer.getStyle().setMargin(23, 0, 14, 10);
		buttonContainer.addComponent(button);
		button = null;
		return buttonContainer;
	}
	
	private Container linkButtonConatiner() {
		Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		
		Button signUpButton = new Button(ApplicationConstants.BUTTON_NAME_SIGN_UP);
		signUpButton.setName(ApplicationConstants.BUTTON_NAME_SIGN_UP);
		createEmptyBorderButton(signUpButton);
		signUpButton.addActionListener(this);
		
		Button forgotButton = new Button(ApplicationConstants.BUTTON_FORGOT_PWD_TEXT);
		forgotButton.setName(ApplicationConstants.BUTTON_FORGOT_PWD_TEXT);
		forgotButton.addActionListener(this);
		createEmptyBorderButton(forgotButton);
		
		buttonContainer.getStyle().setMargin(16, 0, 15, 10);
		buttonContainer.addComponent(signUpButton);
		buttonContainer.addComponent(forgotButton);
		
		signUpButton = null;
		forgotButton = null;
		return buttonContainer;
	}

	public void onButtonClick(Button btn) {
		String buttonName = btn.getName();
		if(buttonName.equals(ApplicationConstants.BUTTON_NAME_LOGIN)) {
			if(emailField.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_VALIDATION_EMAIL_OR_USERNAME);
			} else if(passwordField.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_VALIDATION_PASSWORD);
			} else {
				String userId = emailField.getText().trim().toLowerCase();
				String password = passwordField.getText().trim().toLowerCase();
				System.out.println("Email: " + userId + "\nPassword: " + password);
				showLoadingDialog();
				DataModel.loginWithOlaround(userId, password, loginForm);
			}
		} else if(buttonName.equals(ApplicationConstants.BUTTON_NAME_SIGN_IN_FB)) {
			FacebookHelper.connectWithFacebook(loginForm);
		} else if(buttonName.equals(ApplicationConstants.BUTTON_FORGOT_PWD_TEXT)) {
			mainMidlet.showForgotPwdForm(LoginForm.this, false);
		} else if(buttonName.equals(ApplicationConstants.BUTTON_NAME_SIGN_UP)) {
			mainMidlet.showSignUpStep1Form(LoginForm.this, false);
		}
		
	}
	
	public void onResume() {
		mainMidlet.showVenueDetailCategoryBar(false);
		mainMidlet.showMainCategoryBar(false);
	}

	public void onPause() {

	}

	public void onCommandClick(Command cmd) {
		
	}

	public void loadData(boolean isRefreshed) {
	
	}

	public void addCommands() {
		
	}

	public void onBack() {
		try {
			mainMidlet.destroyApp(true);
		} catch (MIDletStateChangeException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void dealloc() {
		super.dealloc();
		emailField = null;
		passwordField = null;
		loginForm = null;
	}
}
