package com.tenpearls.olaround.ui.forms;

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
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class ForgotPasswordForm extends BaseForm {

	final String FIELD_HINT_FOR_EMAIL = "username or email";
	static TextField emailField  = null;
	
	public ForgotPasswordForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
		
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
		fieldContainer.getStyle().setMargin(100, 0, 10, 10);
		fieldContainer.getStyle().setBgTransparency(0);
		fieldContainer.addComponent(createLabel(ApplicationConstants.LABEL_TEXT_USERNAME));
		fieldContainer.addComponent(createEmailField());
		this.addComponent(fieldContainer);
		this.addComponent(createSendButton(ImageConstants.BUTTON_GREY_NORMAL, ImageConstants.BUTTON_GREY_SELECTED, ApplicationConstants.BUTTON_NAME_SEND));
	}
	
	private Label createLabel(String text) {
		Label label = new Label(text);
		label.getStyle().setFgColor(0xFFFFFF);
		label.getStyle().setMargin(0, 3, 0, 0);
		return label;
	}
	
	private TextField createEmailField() {
		emailField = new TextField();
		setTextFieldStyle(emailField, 0);
		return emailField;
	}
	
	private Container createSendButton(String imageName, String selectedImagename, String buttonName) {

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

		buttonContainer.getStyle().setMargin(20, 0, 14, 10);
		buttonContainer.addComponent(button);
		button = null;
		return buttonContainer;
	}

	public void onResume() {
		

	}

	public void onPause() {
		

	}

	public void onButtonClick(Button btn) {
		if(emailField.getText().equals("")) { 
			//CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ALERT, ApplicationConstants.MSG_VALIDATION_EMAIL_OR_USERNAME, ApplicationConstants.DIALOG_OK_BUTTON);
			validationPopUp(ApplicationConstants.MSG_VALIDATION_EMAIL_OR_USERNAME);
		} else {
			
			boolean isSucces = DataModel.forgotPasswrord(emailField.getText().trim().toLowerCase(), ForgotPasswordForm.this);
			if(isSucces == true) {
				mainMidlet.showThankYouForm(ForgotPasswordForm.this, true);
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
		mainMidlet.showLoginForm(ForgotPasswordForm.this, true);

	}

}
