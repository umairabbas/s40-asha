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
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class SignUpFormStep1 extends BaseForm {

	private Container contentContainer;
	static TextField firstNameField  = null;
	static TextField lastNameField = null; 
	static CustomImageButton profilePicButton = null;

	
	public SignUpFormStep1(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}
	

	public  void setProfileImage(Image profileImage){
		profilePicButton.setCustomBgImage(profileImage);
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
		contentContainer.getStyle().setBgTransparency(0);
		getSteps(ImageConstants.STEP_1);
		getProfileContainer();
		getContinueButton();
		addComponent(contentContainer);
	}
	
	private void getProfileContainer() {
		Container horizContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		Container verticalContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		horizContainer.addComponent(getProfilePicture());
		horizContainer.addComponent(getUploadButton());
		verticalContainer.getStyle().setMargin(5, 10, 10, 10);
		verticalContainer.addComponent(horizContainer);
		verticalContainer.addComponent(createLabel(ApplicationConstants.LABEL_TEXT_FIRSTNAME, 10));
		verticalContainer.addComponent(getFirstNameField());
		verticalContainer.addComponent(createLabel(ApplicationConstants.LABEL_TEXT_LASTNAME, 0));
		verticalContainer.addComponent(getLastNameField());
		verticalContainer.getStyle().setBgTransparency(0);
		contentContainer.addComponent(verticalContainer);
		horizContainer = null;
		verticalContainer = null;
	}
	
	private Label createLabel(String text, int topMargin) {
		Label label = new Label(text);
		label.getStyle().setFgColor(0xFFFFFF);
		label.getStyle().setMargin(topMargin, 3, 0, 0);
		return label;
	}
	
	private Container getUploadButton() {
		Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		CustomImageButton uploadButton = new CustomImageButton(ImageConstants.BUTTON_GREEN_NORMAL, ImageConstants.BUTTON_GREEN_SELECTED, ApplicationConstants.BUTTON_NAME_UPLOAD);
		uploadButton.setText(ApplicationConstants.BUTTON_NAME_UPLOAD);
		uploadButton.setPreferredH(20);
		uploadButton.setPreferredW(240); 
		uploadButton.addActionListener(this);
		uploadButton.getStyle().setFgColor(0xFFFFFF);
		uploadButton.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		uploadButton.getPressedStyle().setFgColor(0xFFFFFF);
		uploadButton.getPressedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		buttonContainer.addComponent(uploadButton);
		buttonContainer.getStyle().setMargin(15, 15, 5, 0);
		uploadButton = null;
		return buttonContainer;
	}
	
	private TextField getFirstNameField() {
		firstNameField = new TextField();
		firstNameField.setMaxSize(18);
		setTextFieldStyle(firstNameField, 5);
		return firstNameField;
	}
	
	private TextField getLastNameField() {
		lastNameField = new TextField();
		lastNameField.setMaxSize(18);
		setTextFieldStyle(lastNameField, 0);
		return lastNameField;
	}
	
	private CustomImageButton getProfilePicture() {
		profilePicButton = new CustomImageButton(ImageConstants.PROFILE_PICTURE, ApplicationConstants.BUTTON_NAME_PROFILE_PICTURE);
		profilePicButton.setPreferredH(73);
		profilePicButton.setPreferredW(70); 
		return profilePicButton;
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
		buttonContainer.getStyle().setMargin(10, 0, 10, 0);
		buttonContainer.addComponent(button);
		contentContainer.addComponent(buttonContainer);
		button = null;
		buttonContainer = null;
	}

	public void onButtonClick(Button btn) {
		if(btn.getName().equals(ApplicationConstants.BUTTON_NAME_CONTINUE)) {
			if(firstNameField.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_VALIDATION_FIRSTNAME);
			} else if(lastNameField.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_VALIDATION_LASTNAME);
			} else if(firstNameField.getText().equals(lastNameField.getText())) {
				validationPopUp(ApplicationConstants.MSG_VALIDATION_SAME_FNAME_LNAME);
			} else {
				Account account = new Account();
				setAccountData(account);
				mainMidlet.showSignUpStep2Form(SignUpFormStep1.this, false, account);
			}
		} else if(btn.getName().equals(ApplicationConstants.BUTTON_NAME_UPLOAD)) {
			mainMidlet.showGalleryForm(SignUpFormStep1.this, false);
		}
	}
	
	private void setAccountData(Account account) {
		account.setFirstName(firstNameField.getText().trim());
		account.setLastName(lastNameField.getText().trim());
	}
	
	public void onResume() {

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
		mainMidlet.showLoginForm(SignUpFormStep1.this, true);
	}
	
	public void dealloc() {
		super.dealloc();
		firstNameField  = null;
		lastNameField = null; 
		profilePicButton = null;
		
	}

}
