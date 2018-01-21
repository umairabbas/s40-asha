package com.tenpearls.olaround.ui.forms;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

import com.sun.lwuit.Button;
import com.sun.lwuit.List;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class FilterOptionForm extends BaseForm implements CommandListener {
	
	ChoiceGroup distanceGroup;
	ChoiceGroup categoryGroup;
	Command cmdSelect;
	Command cmdBack;
	protected javax.microedition.lcdui.Form filterForm;
	
	public FilterOptionForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}

	public void onCreate() {
		mainMidlet.showMainCategoryBar(false);
		filterForm = new Form(title);
		this.getStyle().setBgColor(0xF3EEE9);
		getSearchByDistance();
		getSearchByCategory();
		cmdSelect = new Command("Select", Command.OK, 0);
		cmdBack = new Command("Back", Command.BACK, 0);
		filterForm.setCommandListener(this); 
		filterForm.addCommand(cmdSelect);
		filterForm.addCommand(cmdBack);
	}
	
	private void getSearchByDistance() {
		distanceGroup = new ChoiceGroup(ApplicationConstants.SEARCH_BY_DISTANCE, Choice.POPUP, ApplicationConstants.DISTANCE_CHOICES_TEXT, null);
		filterForm.append(distanceGroup);
	}
	
	private void getSearchByCategory() {
		categoryGroup = new ChoiceGroup(ApplicationConstants.SEARCH_BY_CATEGORY, Choice.POPUP ,ApplicationConstants.CATEGORY_CHOICES_TEXT, null);
		filterForm.append(categoryGroup);
	}

	public void onBack() {
		mainMidlet.showVenuListingForm(FilterOptionForm.this, true, null, "");
	}
	
	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(false);
	}

	public void onPause() {

	}

	public void onButtonClick(Button btn) {
		
	}

	public void loadData(boolean isRefreshed) {

	}

	public void addCommands() {

	}
	
	public void dealloc() {
		super.dealloc();
		categoryGroup = null;
		distanceGroup = null;
	}

	public void commandAction(javax.microedition.lcdui.Command command, Displayable displayable) {
		
		  if(command == cmdSelect) {
			  filterRequest();
	      }
	      if(command == cmdBack) {
	        	onBack();
	      }
		
	}
	
	private void filterRequest() {
        String query = "";
		int distanceIndex = distanceGroup.getSelectedIndex();
		String distance = ApplicationConstants.DISTANCE_CHOICES_VALUES[distanceIndex];
		int categoryInderx = categoryGroup.getSelectedIndex();
		String categoryId = categoryGroup.getString(categoryInderx) ;
		if(distanceIndex == 0) {
			query = "&list=" + distance;
		} else {
			query +=  "&range=" + distance;
		}
		
		if(!categoryId.equals(ApplicationConstants.CATEGORY_CHOICES_VALUES[0])) {
			int selectedIndex = categoryGroup.getSelectedIndex();
			query += "&category_id=" + ApplicationConstants.CATEGORY_CHOICES_VALUES[selectedIndex];
		}
		
		List filterList = DataModel.fetchVenue(query, this);
		if(filterList != null) {
			mainMidlet.destoryVenueListingForm();
			mainMidlet.showVenuListingForm(FilterOptionForm.this, true, filterList , query);
		}
	}
	
	public void onShowCompleted() {
		super.onShowCompleted();
		Display.getDisplay(mainMidlet).setCurrent(filterForm);
	}

	public void onCommandClick(com.sun.lwuit.Command cmd) {
		
	}

}
