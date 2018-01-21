package com.tenpearls.olaround.ui.forms;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
import com.tenpearls.olaround.entities.ClaimKickEntity;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.Utils;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class ClaimedKickInfoForm  extends BaseForm {

	private Container contentContainer;
	Label timeLabel;
	Timer timer = new Timer();
	
	public ClaimedKickInfoForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}

	public void onCreate() {
		ClaimKickEntity claimkick = KicksForm.getClaimKickEntity();
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
		if(claimkick != null) {
			createLargeSizeLabel(ApplicationConstants.LABEL_TEXT_CONGRATULATION, 0, 0, 0, 0);
			createSmallSizeLabel(ApplicationConstants.LABEL_TEXT_JUST_CLAIM_KICK, 2, 0, 0, 0);
			createLargeSizeLabel(claimkick.getTitle(), 15, 0, 0, 0);
			createSmallSizeLabel("at " + claimkick.getVenueName(), 15, 0, 0, 0);
			createTimeLabel("", 7, 0, 0, 0) ;
			bottomTextArea();
		}
		this.addComponent(contentContainer);
		contentContainer = null;
	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(false);
	}
	
	private void createLargeSizeLabel(String text, int top, int bottom, int left, int right) {
		CustomTextArea textArea = new CustomTextArea(text, true);
		textArea.setCustomMargin(top, bottom, left, right);
		if(text.equals(ApplicationConstants.LABEL_TEXT_CONGRATULATION)) {
			textArea.setCustomTextColor(ApplicationConstants.COLOR_WHITE);
		} else {
			textArea.setCustomTextColor(ApplicationConstants.COLOR_BLACK);
		}
		textArea.setCustomFont(ApplicationConstants.FONT_LARGE);
		textArea.setAlignment(CENTER);
		contentContainer.addComponent(textArea);
		textArea = null;
	}
	
	private void createSmallSizeLabel(String text, int top, int bottom, int left, int right) {
		Label label = new Label(text);
		label.getStyle().setFgColor(0xFFFFFF);
		label.getStyle().setMargin(top, bottom, left, right);
		label.getStyle().setPadding(0, 0, 0, 0);
		label.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		label.getStyle().setAlignment(CENTER);
		contentContainer.addComponent(label);
		label = null;
	}
	
	private void createTimeLabel(String text, int top, int bottom, int left, int right) {
		timeLabel = new Label(text);
		timeLabel.getStyle().setFgColor(0x000000);
		timeLabel.getStyle().setMargin(top, bottom, left, right);
		timeLabel.getStyle().setPadding(0, 0, 0, 0);
		timeLabel.getStyle().setFont(Utils.getFontFromResources(ApplicationConstants.FONT_SMALL));
		timeLabel.getStyle().setAlignment(CENTER);
		contentContainer.addComponent(timeLabel);
	}

	private void bottomTextArea() {
		CustomTextArea textArea = new CustomTextArea(ApplicationConstants.LABEL_TEXT_TO_GET_KICK, true);
		textArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		textArea.setCustomTextColor(0x660000);
		textArea.setCustomMargin(70, 0, 28, 25);
		textArea.setAlignment(CENTER);
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
		mainMidlet.showKicksForm(this, true);
		timer.cancel();
	}
	
	public void onShowCompleted() {
		super.onShowCompleted();
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}
	
	TimerTask timerTask = new TimerTask() {
		Calendar c = Calendar.getInstance();
			
		public void run() {
			Date date = new Date();
			c.setTime(date);
			String time = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
			int month = c.get(Calendar.MONTH);
			timeLabel.setText("Time: " + time + ", " + ApplicationConstants.MONTHS[month] + " " + c.get(Calendar.DAY_OF_MONTH));
		}
	};

}
