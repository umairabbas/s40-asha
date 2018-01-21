package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.Utils;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class ThankyouForm extends BaseForm {

	public ThankyouForm(String title, OlaroundMidlet mainMidlet) {
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
		
		this.addComponent(createThankYouLabel());
		this.addComponent(createCheckEmailLabel());
		this.addComponent(createDoneButton(ImageConstants.BUTTON_GREY_NORMAL, ImageConstants.BUTTON_GREY_SELECTED, ApplicationConstants.BUTTON_NAME_DONE));

	}
	
	private Label createThankYouLabel() {
		Label thankYouLabel = new Label(ApplicationConstants.LABEL_TEXT_THANK_YOU);
		thankYouLabel.getStyle().setMargin(80, 0, 45, 0);
		thankYouLabel.getStyle().setFgColor(0xFFFFFF);
		//thankYouLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE));
		thankYouLabel.getStyle().setFont(Utils.getFontFromResources(ApplicationConstants.FONT_LARGE));
		
		return thankYouLabel;
	}
	
	private Container createCheckEmailLabel() {
		Container checkEmailContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		CustomTextArea checkEmailTextArea = new CustomTextArea(ApplicationConstants.LABEL_TEXT_CHECK_YOUR_EMAIL, true);
		checkEmailTextArea.setRows(3);
		checkEmailTextArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
		//checkEmailTextArea.getStyle().setFont(Utils.getFontFromResources(ApplicationConstants.FONT_SMALL));
		
		checkEmailTextArea.getStyle().setAlignment(TextArea.CENTER);
		checkEmailTextArea.getPressedStyle().setAlignment(TextArea.CENTER);
		checkEmailTextArea.getSelectedStyle().setAlignment(TextArea.CENTER);
		
		checkEmailTextArea.getStyle().setFgColor(0xFFFFFF);
		checkEmailTextArea.getPressedStyle().setFgColor(0xFFFFFF);
		checkEmailTextArea.getSelectedStyle().setFgColor(0xFFFFFF);

		checkEmailContainer.getStyle().setMargin(8, 0, 2, 2);
		checkEmailContainer.addComponent(checkEmailTextArea);
		checkEmailTextArea = null;
		return checkEmailContainer;
	}
	
	private Container createDoneButton(String imageName, String selectedImagename, String buttonName) {

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

		buttonContainer.getStyle().setMargin(10, 0, 14, 10);
		buttonContainer.addComponent(button);
		button = null;
		return buttonContainer;
	}

	public void onResume() {


	}

	public void onPause() {


	}

	public void onButtonClick(Button btn) {
		mainMidlet.showLoginForm(ThankyouForm.this, true);

	}

	public void onCommandClick(Command cmd) {


	}

	public void loadData(boolean isRefreshed) {


	}

	public void addCommands() {


	}

	public void onBack() {
		mainMidlet.showLoginForm(ThankyouForm.this, true);

	}

}
