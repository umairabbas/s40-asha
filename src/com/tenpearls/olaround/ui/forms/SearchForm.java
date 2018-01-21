package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class SearchForm extends BaseForm {

	static String SEARCH_TEXT = "search";
	TextField searchField;
	public SearchForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}

	public void onCreate() {
		this.getStyle().setBgColor(0xF3EEE9);
		this.setScrollable(false);
		Container mainContainer = new Container();
		mainContainer.getStyle().setBgImage(ImageHelper.getImageFromResources(ImageConstants.BG_SEARCH));
		mainContainer.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
		mainContainer.addComponent(createLabel(SEARCH_TEXT, 5));
		mainContainer.addComponent(getSearchField());
		mainContainer.addComponent(getNearLabel());
		mainContainer.setPreferredW(240);
		mainContainer.setPreferredH(120);
		addComponent(mainContainer);
		mainContainer = null;
	}
	
	private Label createLabel(String text, int topMargin) {
		Label label = new Label(text);
		label.getStyle().setFgColor(ApplicationConstants.COLOR_WHITE);
		label.getStyle().setMargin(topMargin, 0, 10, 0);
		return label;
	}
	
	private Container getSearchField() { 
		Container searchContainer = new Container();
		searchField = new TextField();
		setTextFieldStyle(searchField, 0);
		searchContainer.getStyle().setMargin(5, 5, 10, 10);
		searchContainer.addComponent(searchField);
		return searchContainer;
	}
	
	private Label getNearLabel() {
		Label nearLabel = new Label(ApplicationConstants.LABEL_TEXT_NEAR);
		nearLabel.getStyle().setFgColor(0xFFFFFFF);
		nearLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		nearLabel.getStyle().setMargin(15, 0, 40, 0);
		return nearLabel;
	}
	
	public void loadData(boolean isRefreshed) {
		DataModel.fetchSearchVenues(this);
	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(false);
		if(searchField != null) {
			searchField.setText("");
		}
	}

	public void onPause() {	

	}

	public void onButtonClick(Button btn) {	

	}

	public void onCommandClick(Command cmd) {
		String venueName = searchField.getText();
		if(!venueName.equals("")) {
			DataModel.searchVenueByName(venueName);
			mainMidlet.destorySearchResultListingForm();
			mainMidlet.showSearchResultListingForm(SearchForm.this, false);
		} else {
			validationPopUp(ApplicationConstants.MSG_VALIDATION_SEARCH_FIELD);
		}
	}

	public void addCommands() {
		Command selectSearchCmd = new Command(ApplicationConstants.SELECT_FILTER_COMMAND) ;
		addCommand(selectSearchCmd);
		setDefaultCommand(selectSearchCmd);
	}

	public void onBack() {
		mainMidlet.showVenuListingForm(SearchForm.this, false, null, "");
	}
	
	public void dealloc() {
		super.dealloc();
		searchField = null;
	}
	
}
