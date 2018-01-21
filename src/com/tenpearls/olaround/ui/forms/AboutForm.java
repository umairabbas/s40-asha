package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Font;
import com.sun.lwuit.layouts.BoxLayout;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class AboutForm extends BaseForm {
	
	private String navigateFrom = "";
	public AboutForm(OlaroundMidlet mainMidlet, String navigateFrom) {
		super("ol about", mainMidlet);
		this.navigateFrom = navigateFrom;
	}

	public void onCreate() {
		this.setScrollable(false);
		this.getStyle().setBgColor(ApplicationConstants.COLOR_BG_FORM);
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		createDetailTextArea(ApplicationConstants.ABOUT_DETAIL_TEXT);
		createDetailTextAreaBold(ApplicationConstants.ABOUT_DETAIL_TEXT_BOLD);
	}

	public void onResume() {
		
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(false);
	}
	
	private void createDetailTextArea(String Name) {
		CustomTextArea detailTextArea = new CustomTextArea(Name, true);
		detailTextArea.setCustomTextColor(ApplicationConstants.COLOR_BLACK);
		detailTextArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		detailTextArea.setCustomMargin(10, 0, 3, 3);
		this.addComponent(detailTextArea);
	}
	
	private void createDetailTextAreaBold(String Name) {
		CustomTextArea detailTextArea = new CustomTextArea(Name, true);
		detailTextArea.setCustomTextColor(ApplicationConstants.COLOR_BLACK);
		detailTextArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
		detailTextArea.setCustomMargin(10, 0, 3, 3);
		this.addComponent(detailTextArea);
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
		System.out.println("navigateFrom: "+navigateFrom);
		mainMidlet.navigateToForm(this, navigateFrom, true);
	}

}
