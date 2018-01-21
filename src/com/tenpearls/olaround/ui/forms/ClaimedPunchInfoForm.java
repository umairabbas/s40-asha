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
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class ClaimedPunchInfoForm extends BaseForm {

	private Container contentContainer;
	private BaseForm currentForm;

	
	public ClaimedPunchInfoForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}
	
	public ClaimedPunchInfoForm(String title, OlaroundMidlet mainMidlet, BaseForm current, String venueName) {
		super(title, mainMidlet);
		this.currentForm = current;
		createLargeSizeLabel(venueName, 20, 32, 0, 0); 
		this.addComponent(contentContainer);
		contentContainer = null;
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
		contentContainer.getStyle().setMargin(100, 0, 10, 10);

		createLargeSizeLabel(ApplicationConstants.LABEL_TEXT_CONGRATULATION, 2, 1, 0, 0);
		createSmallSizeLabel(ApplicationConstants.LABEL_TEXT_JUST_CLAIM_PUNCH, 4, 0, 0, 0);

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
		label.getStyle().setAlignment(CENTER);
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
		label.getStyle().setAlignment(CENTER);
		label.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		contentContainer.addComponent(label);
		label = null;
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
		mainMidlet.showCurrentForm(ClaimedPunchInfoForm.this, currentForm, true);
	}
}