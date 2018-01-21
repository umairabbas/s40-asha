package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Font;
import com.sun.lwuit.layouts.BoxLayout;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.HelpFormConstants;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class HelpForm extends BaseForm {
	
	private String navigateFrom = "";
	public HelpForm(OlaroundMidlet mainMidlet,String navigateFrom) {
		super("ol help", mainMidlet);
		this.navigateFrom = navigateFrom;
	}

	public void onCreate() {
		this.setScrollable(false);
		this.getStyle().setBgColor(ApplicationConstants.COLOR_BG_FORM);
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		createHeading(HelpFormConstants.HELP_QS0);
		createDetailTextArea(HelpFormConstants.HELP_A0);
		createHeading(HelpFormConstants.HELP_QS1);
		createDetailTextArea(HelpFormConstants.HELP_A1);
		createHeading(HelpFormConstants.HELP_QS2);
		createDetailTextArea(HelpFormConstants.HELP_A2);
		createHeading(HelpFormConstants.HELP_QS3);
		createDetailTextArea(HelpFormConstants.HELP_A3);
		createHeading(HelpFormConstants.HELP_QS4);
		createDetailTextArea(HelpFormConstants.HELP_A4);
		createHeading(HelpFormConstants.HELP_QS5);
		createDetailTextArea(HelpFormConstants.HELP_A5);
		createHeading(HelpFormConstants.HELP_QS6);
		createDetailTextArea(HelpFormConstants.HELP_A6);
		createHeading(HelpFormConstants.HELP_QS7);
		createDetailTextArea(HelpFormConstants.HELP_A7);
		createHeading(HelpFormConstants.HELP_QS8);
		createDetailTextArea(HelpFormConstants.HELP_A8);
		createHeading(HelpFormConstants.HELP_QS9);
		createDetailTextArea(HelpFormConstants.HELP_A9);
		createHeading(HelpFormConstants.HELP_QS10);
		createDetailTextArea(HelpFormConstants.HELP_A10);
		createHeading(HelpFormConstants.HELP_QS11);
		createDetailTextArea(HelpFormConstants.HELP_A11);
		createHeading(HelpFormConstants.HELP_QS12);
		createDetailTextArea(HelpFormConstants.HELP_A12);
		createHeading(HelpFormConstants.HELP_QS13);
		createDetailTextArea(HelpFormConstants.HELP_A13);
		createHeading(HelpFormConstants.HELP_QS14);
		createDetailTextArea(HelpFormConstants.HELP_A14);
		createHeading(HelpFormConstants.HELP_QS15);
		createDetailTextArea(HelpFormConstants.HELP_A15);
		createHeading(HelpFormConstants.HELP_QS16);
		createDetailTextArea(HelpFormConstants.HELP_A16);
		createHeading(HelpFormConstants.HELP_QS17);
		createDetailTextArea(HelpFormConstants.HELP_A17);
		createHeading(HelpFormConstants.HELP_QS18);
		createDetailTextArea(HelpFormConstants.HELP_A18);
		createHeading(HelpFormConstants.HELP_QS19);
		createDetailTextArea(HelpFormConstants.HELP_A19);
		createHeading(HelpFormConstants.HELP_QS20);
		createDetailTextArea(HelpFormConstants.HELP_A20);
		createHeading(HelpFormConstants.HELP_QS21);
		createDetailTextArea(HelpFormConstants.HELP_A21);
		createHeading(HelpFormConstants.HELP_QS22);
		createDetailTextArea(HelpFormConstants.HELP_A22);		
		createHeading(HelpFormConstants.HELP_QS23);
		createDetailTextArea(HelpFormConstants.HELP_A23);
		
	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(false);
	}
	
	private void createDetailTextArea(String Name) {
		CustomTextArea detailTextArea = new CustomTextArea(Name, true);
		detailTextArea.setCustomTextColor(ApplicationConstants.COLOR_BLACK);
		detailTextArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		detailTextArea.setCustomMargin(3, 0, 10, 3);
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
