package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
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

public class ClaimedDealInfoForm extends BaseForm {

	private Container contentContainer;

	public ClaimedDealInfoForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}

	public void onCreate() {
		mainMidlet.showMainCategoryBar(false);
		this.setScrollable(false);
		Image bgImage = ImageHelper.getImageFromResources(ImageConstants.BACKGROUND);
		if(bgImage != null){
			this.getStyle().setBgImage(bgImage);
			this.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			bgImage = null;
		}
		contentContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		contentContainer.getStyle().setMargin(10, 0, 10, 10);

		createLargeSizeLabel(ApplicationConstants.LABEL_TEXT_CONGRATULATION, 0, 0, 12, 0);
		createSmallSizeLabel(ApplicationConstants.LABEL_TEXT_JUST_CLAIM_DEAL, 2, 0, 25, 0);
		createLargeSizeLabel("20% Off", 15, 0, 55, 0);
		createSmallSizeLabel("at Masoom's Pancake Lounge", 15, 0, 20, 0);
		createMediumSizeLabel("Time 12:00:00, March 14", 7, 0, 25, 0);
		createSendButton(ImageConstants.BUTTON_GREY_NORMAL, ImageConstants.BUTTON_GREY_SELECTED, ApplicationConstants.BUTTON_NAME_SCAN_QR);
		createSmallSizeLabel(ApplicationConstants.LABEL_TEXT_TO_COLLECT_PUNCHES, 5, 0, 50, 0);
		bottomTextArea();
		this.addComponent(contentContainer);
		contentContainer = null;
	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(false);
	}
	
	private void createLargeSizeLabel(String text, int top, int bottom, int left, int right) {
		Label label = new Label(text);
		label.getStyle().setFont(Utils.getFontFromResources(ApplicationConstants.FONT_LARGE));
		if(text.equals(ApplicationConstants.LABEL_TEXT_CONGRATULATION)) {
			label.getStyle().setFgColor(0xFFFFFF);
		} else {
			label.getStyle().setFgColor(0x000000);
		}
		
		label.getStyle().setMargin(top, bottom, left, right);
		label.getStyle().setPadding(0, 0, 0, 0);
		contentContainer.addComponent(label);
		label = null;
	}
	
	private void createSmallSizeLabel(String text, int top, int bottom, int left, int right) {
		Label label = new Label(text);
		label.getStyle().setFgColor(0xFFFFFF);
		label.getStyle().setMargin(top, bottom, left, right);
		label.getStyle().setPadding(0, 0, 0, 0);
		label.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		contentContainer.addComponent(label);
		label = null;
	}
	
	private void createMediumSizeLabel(String text, int top, int bottom, int left, int right) {
		Label label = new Label(text);
		label.getStyle().setFgColor(0x000000);
		label.getStyle().setMargin(top, bottom, left, right);
		label.getStyle().setPadding(0, 0, 0, 0);
		label.getStyle().setFont(Utils.getFontFromResources(ApplicationConstants.FONT_SMALL));
		contentContainer.addComponent(label);
		label = null;
	}
	
	private void createSendButton(String imageName, String selectedImagename, String buttonName) {

		Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		CustomImageButton button = new CustomImageButton(imageName, selectedImagename, buttonName);
		button.setText(buttonName);
		button.setPreferredH(40);
		button.setPreferredW(240); 
		button.addActionListener(this);
		button.getStyle().setFgColor(0xFFFFFF);
		button.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		button.getPressedStyle().setFgColor(0xFFFFFF);
		button.getPressedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));

		buttonContainer.getStyle().setMargin(15, 0, 5, 5);
		buttonContainer.addComponent(button);
		contentContainer.addComponent(buttonContainer);
		button = null;
		buttonContainer = null;
	}

	private void bottomTextArea() {
		CustomTextArea textArea = new CustomTextArea(ApplicationConstants.LABEL_TEXT_TO_GET_DEAL, true);
		textArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		textArea.setCustomTextColor(0x660000);
		textArea.setCustomMargin(25, 0, 28, 25);
		contentContainer.addComponent(textArea);
		textArea = null;
	}
	
	public void onPause() {

	}

	public void onButtonClick(Button btn) {

	}

	public void onCommandClick(Command cmd) {

	}

	public void loadData(boolean isRefreshed) {

	}

	public void addCommands() {

	}

	public void onBack() {
		mainMidlet.showDealsDetailForm(this, true);
	}

}
