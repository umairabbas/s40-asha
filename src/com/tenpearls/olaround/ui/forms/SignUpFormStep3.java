package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.RadioButton;
import com.sun.lwuit.TextField;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.entities.Account;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.Utils;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class SignUpFormStep3 extends BaseForm {

	private Container contentContainer;
	private static TextField phoneField = null;
	private static ButtonGroup group = null;
	private Account account;
	
	public SignUpFormStep3(String title, OlaroundMidlet mainMidlet, Account account) {
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
		getSteps(ImageConstants.STEP_3);
		createLabel(ApplicationConstants.LABEL_TEXT_GENDER, 0);
		getGenderRadioButtons();
		createLabel(ApplicationConstants.LABEL_TEXT_PHONE, 10);
		getPhoneField();
		addComponent(contentContainer);
		getAgreeWithTerms();
		getSubmitButton();
		
		contentContainer = null;
	}
	
	private void getGenderRadioButtons() {
		Container radioContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		RadioButton radiobutton;
		group = new ButtonGroup();
		
		//male radio button
		radiobutton = new RadioButton(ApplicationConstants.RADIO_BUTTON_NAME_MALE);
		radiobutton.getStyle().setFgColor(0xFFFFFF);
		radiobutton.getPressedStyle().setFgColor(0xFFFFFF);
		radiobutton.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		radiobutton.getPressedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		group.add(radiobutton);
		group.setSelected(0);
		radioContainer.addComponent(radiobutton);
		
		//female radio buttons
		radiobutton = new RadioButton(ApplicationConstants.RADIO_BUTTON_NAME_FEMALE);
		radiobutton.getStyle().setFgColor(0xFFFFFF);
		radiobutton.getPressedStyle().setFgColor(0xFFFFFF);
		radiobutton.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		radiobutton.getPressedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		group.add(radiobutton);
		radioContainer.getStyle().setMargin(0, 0, 0, 0);
		radioContainer.getStyle().setPadding(0, 0, 0, 0);
		radioContainer.addComponent(radiobutton);
		radioContainer.getStyle().setMargin(Component.TOP, 10);
		contentContainer.addComponent(radioContainer);
		radiobutton = null;
		radioContainer = null;
	}

	private void getPhoneField() {
		phoneField = new TextField("");
		phoneField.setMaxSize(14);
		phoneField.setConstraint(TextField.PHONENUMBER);
		setTextFieldStyle(phoneField, 0);
		contentContainer.addComponent(phoneField);
	}
	
	private void getAgreeWithTerms() {
		Container horizontalContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		Label agreeWithTerms = new Label(ApplicationConstants.LABEL_TEXT_AGREE_WITH_TREMS);
		Font font = Utils.getFontFromResources(ApplicationConstants.FONT_EXTRA_SMALL);
		//agreeWithTerms.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		agreeWithTerms.getStyle().setFont(font);
		agreeWithTerms.getStyle().setFgColor(0xFFFFFF);
		agreeWithTerms.getStyle().setMargin(Component.RIGHT, 0);
		horizontalContainer.addComponent(agreeWithTerms);
		horizontalContainer.addComponent(createTermsAndConditonButton());
		horizontalContainer.getStyle().setMargin(10, 0, 15, 0);
		this.addComponent(horizontalContainer);
		agreeWithTerms = null;
	}
	
	private Container createTermsAndConditonButton() {
		Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		Button termsButton = new Button(ApplicationConstants.BUTTON_NAME_TERMS_AND_CONDTION);
		termsButton.setName(ApplicationConstants.BUTTON_NAME_TERMS_AND_CONDTION);
		createEmptyBorderButton(termsButton);
		
		Font font = Utils.getFontFromResources(ApplicationConstants.FONT_EXTRA_SMALL);
		termsButton.getStyle().setFont(font);
		termsButton.getPressedStyle().setFont(font);
		termsButton.getSelectedStyle().setFont(font);
		termsButton.getUnselectedStyle().setFont(font);
		termsButton.getDisabledStyle().setFont(font);
		
		buttonContainer.getStyle().setMargin(0, 0, 0, 0);
		buttonContainer.getStyle().setPadding(0, 0, 0, 0);
		buttonContainer.addComponent(termsButton);
		termsButton.addActionListener(this);
		termsButton = null;
		return buttonContainer;
	}
	
	private void getSubmitButton() {
		Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		CustomImageButton button = new CustomImageButton(ImageConstants.BUTTON_GREEN_NORMAL, ImageConstants.BUTTON_GREEN_SELECTED, ApplicationConstants.BUTTON_NAME_SUBMIT);
		button.setText(ApplicationConstants.BUTTON_NAME_SUBMIT);
		button.setPreferredH(40);
		button.setPreferredW(208); 
		button.addActionListener(this);
		button.getStyle().setFgColor(0xFFFFFF);
		button.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		button.getPressedStyle().setFgColor(0xFFFFFF);
		button.getPressedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		buttonContainer.getStyle().setMargin(10, 0, 10, 0);
		buttonContainer.addComponent(button);
		this.addComponent(buttonContainer);
		button = null;
		buttonContainer = null;
	}
	
	private void createLabel(String text, int topMargin) {
		Label label = new Label(text);
		label.getStyle().setFgColor(0xFFFFFF);
		label.getStyle().setMargin(topMargin, 3, 0, 0);
		contentContainer.addComponent(label);
	}

	public void onResume() {
		
	}

	public void onPause() {

	}

	public void onButtonClick(Button button) {
		if(button.getName().equals(ApplicationConstants.BUTTON_NAME_SUBMIT)) {
			if(phoneField.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_VALIDATION_PHONE_FIELD);
			} else {
				RadioButton gender = group.getRadioButton(group.getSelectedIndex());
				System.out.println(gender.getText());
				account.setGender(gender.getText());
				account.setPhone(phoneField.getText().trim());
				DataModel.createAccount(account, SignUpFormStep3.this);
			}
		}
		else if (button.getName().equals(ApplicationConstants.BUTTON_NAME_TERMS_AND_CONDTION)){
			String navigateFrom = this.getClass().getName();
			mainMidlet.showTermsForm(SignUpFormStep3.this, false, navigateFrom);
		}
	}

	public void onCommandClick(Command cmd) {

	}

	public void loadData(boolean isRefreshed) {

	}

	public void addCommands() {

	}

	public void onBack() {
		mainMidlet.showSignUpStep2Form(this, false, account);
	}
	
	public void dealloc() {
		super.dealloc();
		phoneField = null;
		group = null;
	}

}
