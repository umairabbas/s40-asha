package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.StringUtils;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class InviteFriendsForm extends BaseForm {

	BaseForm CurrentForm;
	public InviteFriendsForm(String title, OlaroundMidlet mainMidlet, BaseForm CurrentForm) {
		super(title, mainMidlet);
		this.CurrentForm= CurrentForm;
	}

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
		this.addComponent(createSendButton(ImageConstants.BUTTON_BLUE_NORMAL,ImageConstants.BUTTON_BLUE_SELECTED,
				ApplicationConstants.BUTTON_NAME_SEND));

	}

	private void creatInputArea() {
		Container conInput = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		conInput.getStyle().setMargin(10, 0, 3, 3);
		body = new TextArea(8, 2);
		this.setTextAreaStyle(body, 0);

		CustomTextArea txtBody = new CustomTextArea(ApplicationConstants.MSG_EMAIL_HEADER, true);
		txtBody.setCustomTextColor(ApplicationConstants.COLOR_BLACK);
		txtBody.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
		txtBody.setAlignment(CENTER);
		txtBody.setCustomMargin(0, 10, 10, 10);

		conInput.addComponent(txtBody);
		conInput.addComponent(body);
		this.addComponent(conInput);
	}

	private Container createSendButton(String imageName, String selectedImagename, String buttonName) {

		Container buttonContainer = new Container(new BoxLayout(
				BoxLayout.X_AXIS));
		CustomImageButton button = new CustomImageButton(imageName, selectedImagename, buttonName);
		button.setText(buttonName);
		button.setPreferredH(40);
		button.setPreferredW(208);
		button.addActionListener(this);
		button.getStyle().setFgColor(0xFFFFFF);
		button.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_SMALL));
		button.getPressedStyle().setFgColor(0xFFFFFF);
		button.getPressedStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_SMALL));

		buttonContainer.getStyle().setMargin(12, 0, 15, 15);
		buttonContainer.addComponent(button);
		button = null;
		return buttonContainer;
	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(false);
	}

	public void onPause() {

	}

	public void onButtonClick(Button btn) {
		if (btn.getName().equals(ApplicationConstants.BUTTON_NAME_SEND)) {
			if (body.getText().equals("")) {
				validationPopUp(ApplicationConstants.MSG_EMPTY_EMAIl);
			} else {
				String email = body.getText();
				String[] result = StringUtils.split(email.trim(), ";");
				boolean count = true;
				for (int i = 0; i < result.length; i++) {
					result[i] = result[i].trim();
					if (!(StringUtils.isContains(result[i], "@") && StringUtils
							.isContains(result[i], "."))) {
						validationPopUp(ApplicationConstants.MSG_VALIDATION_EMAIL);
						count = false;
					}
				}
				if (count != false) {
					if(DataModel.getInvites(result, InviteFriendsForm.this, CurrentForm)){
						mainMidlet.destoryinviteFriendsForm();
						//onBack();
					}
				}
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
		mainMidlet.showfriendsActivityListingForm(InviteFriendsForm.this, true);
	}
}
