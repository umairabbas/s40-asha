package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.StringUtils;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class FeedbackForm extends BaseForm {

	BaseForm current;
	public FeedbackForm(String title, OlaroundMidlet mainMidlet, BaseForm current) {
		super(title, mainMidlet);
		this.current = current;
	}

	private TextField userName;
	private TextField email;
	private TextField subject;
	private TextArea body;

	public void onCreate() {

		this.setScrollable(false);
		this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		Image bgImage = ImageHelper.getImageFromResources(ImageConstants.BACKGROUND);
		if(bgImage != null){
			this.getStyle().setBgImage(bgImage);
			this.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			bgImage = null;
		}
		creatInputArea();
		this.addComponent(createSendButton(ImageConstants.BUTTON_BLUE_NORMAL,
				ImageConstants.BUTTON_BLUE_SELECTED,
				ApplicationConstants.BUTTON_NAME_SEND_FEEDBACK));
	}

	private void creatInputArea() {
		Container conInput = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		conInput.getStyle().setMargin(0, 0, 3, 3);

		userName = new TextField();
		userName.setMaxSize(18);
		
		email = new TextField();
		email.setMaxSize(32);
		email.setConstraint(TextField.EMAILADDR);
		
		subject = new TextField();
		subject.setMaxSize(64);
		
		body = new TextArea(3, 2);
		body.setMaxSize(300);
		
		this.setTextFieldStyle(userName, 0);
		this.setTextFieldStyle(email, 0);
		this.setTextFieldStyle(subject, 0);
		this.setTextAreaStyle(body, 0);

		Label lblName = new Label(ApplicationConstants.LABEL_TEXT_NAME);
		lblName.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		lblName.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_MEDIUM));
		lblName.getStyle().setMargin(1, 0, 0, 0);
		Label lblEmail = new Label(ApplicationConstants.LABEL_TEXT_EMAIL);
		lblEmail.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		lblEmail.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_MEDIUM));
		lblEmail.getStyle().setMargin(1, 0, 0, 0);
		Label lblSubject = new Label(ApplicationConstants.LABEL_TEXT_SUBJECT);
		lblSubject.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		lblSubject.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_MEDIUM));
		lblSubject.getStyle().setMargin(1, 0, 0, 0);
		Label lblComments = new Label(ApplicationConstants.LABEL_TEXT_MESSAGE);
		lblComments.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		lblComments.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_MEDIUM));
		lblComments.getStyle().setMargin(1, 0, 0, 0);

		conInput.addComponent(lblName);
		conInput.addComponent(userName);
		conInput.addComponent(lblEmail);
		conInput.addComponent(email);
		conInput.addComponent(lblSubject);
		conInput.addComponent(subject);
		conInput.addComponent(lblComments);
		conInput.addComponent(body);

		this.addComponent(conInput);
	}

	private Container createSendButton(String imageName,
			String selectedImagename, String buttonName) {

		Container buttonContainer = new Container(new BoxLayout(
				BoxLayout.X_AXIS));
		CustomImageButton button = new CustomImageButton(imageName,
				selectedImagename, buttonName);
		button.setText(buttonName);
		button.setPreferredH(35);
		button.setPreferredW(170);
		button.addActionListener(this);
		button.getStyle().setFgColor(0xFFFFFF);
		button.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_SMALL));
		button.getPressedStyle().setFgColor(0xFFFFFF);
		button.getPressedStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_SMALL));
		buttonContainer.getStyle().setMargin(7, 0, 34, 34);
		buttonContainer.addComponent(button);
		button = null;
		return buttonContainer;
	}

	boolean isSubmit = false;
	public void onButtonClick(Button btn) {
		if (!isSubmit) {
			if (userName.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_EMPTY_NAME);
			} else if (email.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_EMPTY_EMAIl);
			} else if( !(StringUtils.isContains(email.getText(), "@") && StringUtils.isContains(email.getText(), ".")) ) {
				validationPopUp(ApplicationConstants.MSG_VALIDATION_EMAIL);
			}else if (subject.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_EMPTY_SUBJECT);
			} else if (body.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_EMPTY);
			} else {
				String msgBody = new String();
				msgBody += "name=";
				msgBody += userName.getText().trim();
				msgBody += "&email=";
				msgBody += email.getText().trim();
				msgBody += "&subject=";
				msgBody += subject.getText().trim();
				msgBody += "&message=";
				msgBody += body.getText().trim();
				isSubmit = true;
				if(DataModel.getFeedback(msgBody, FeedbackForm.this, current)) {
					mainMidlet.destoryFeedbackForm();
				}
				isSubmit = false;
			}
		}

	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(false);
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
		mainMidlet.showOlMeForm(FeedbackForm.this, true);

	}
}
