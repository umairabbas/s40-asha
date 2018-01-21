package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Font;
import com.sun.lwuit.layouts.BoxLayout;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.TermFormConstants;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class TermsAndCondtionForm extends BaseForm {
	
	private String navigateFrom = "";
	public TermsAndCondtionForm(OlaroundMidlet mainMidlet,String navigateFrom) {
		super("ol terms", mainMidlet);
		this.navigateFrom = navigateFrom;
	}

	public void onCreate() {
		this.setScrollable(false);
		this.getStyle().setBgColor(ApplicationConstants.COLOR_BG_FORM);
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		createHeading(TermFormConstants.TERMS_HEADING0);
		createHeading(TermFormConstants.TERMS_TEXT_Terms);
		createHeading(TermFormConstants.TERMS_HEADING1);
		createDetailTextArea(TermFormConstants.TERMS_BODY1);
		createHeading(TermFormConstants.TERMS_HEADING2);
		createDetailTextArea(TermFormConstants.TERMS_BODY2);
		createHeading(TermFormConstants.TERMS_HEADING3);
		createDetailTextArea(TermFormConstants.TERMS_BODY3);
		createHeading(TermFormConstants.TERMS_HEADING4);
		createDetailTextArea(TermFormConstants.TERMS_BODY4);
		createHeading(TermFormConstants.TERMS_HEADING5);
		createDetailTextArea(TermFormConstants.TERMS_BODY5);
		createHeading(TermFormConstants.TERMS_HEADING6);
		createDetailTextArea(TermFormConstants.TERMS_BODY6);
		createHeading(TermFormConstants.TERMS_HEADING7);
		createDetailTextArea(TermFormConstants.TERMS_BODY7);
		createHeading(TermFormConstants.TERMS_HEADING8);
		createHeading(TermFormConstants.TERMS_DISCLAIMER);
		
	}

	public void onResume() {
		
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(false);
	}
	
	private void createDetailTextArea(String Name) {
		CustomTextArea detailTextArea = new CustomTextArea(Name, true);
		detailTextArea.setCustomTextColor(ApplicationConstants.COLOR_BLACK);
		detailTextArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		detailTextArea.setCustomMargin(10, 0, 15, 3);
		this.addComponent(detailTextArea);
	}
	
	private void createHeading(String Name) {
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
