package com.tenpearls.olaround.ui.forms;

import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomDialogBox;
import com.tenpearls.olaround.ui.forms.base.BaseListingForm;

public class DealsListingForm extends BaseListingForm {
	
	DealsListingForm listingForm = null;
	String query;
	
	public DealsListingForm(String title, OlaroundMidlet midlet) {
		super(title, midlet);
	}

	public void onCreate() {
		try {
			mainMidlet.showMainCategoryBar(true);
			this.getStyle().setBgColor(ApplicationConstants.COLOR_BG_FORM);
			createList();
			if(itemsList != null ) {
				this.addComponent(itemsList);
				if(itemsList.size() == ApplicationConstants.PAGE_SIZE) { 
					getLoadMoreButton();
				}
				itemsList = null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("An exception is raised: " + e.getMessage());
			CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, ApplicationConstants.DIALOG_ERROR_MESSAGE, this);
		}
	}
	
	public void loadData(boolean isRefreshed) {
		System.out.println("loading data...");
		pageIndex = 1;
		query = "&page=" +  pageIndex;
		itemsList = DataModel.fetchDealsList(query, this);
	}

	public void onResume() {
		mainMidlet.showVenueDetailCategoryBar(false);
		mainMidlet.showMainCategoryBar(true);
	}

	public void onPause() {

	}
	
	public void onListItemClick(Object obj) {

	}

	public void onButtonClick(Button btn) {
		showLoadingDialog();
		pageIndex++;
		query = "&page=" + pageIndex ;
		itemsList = DataModel.fetchDealsList(query, DealsListingForm.this);
		
		if(itemsList != null) {
			createList();
			listingForm.addComponent(pageIndex-1, itemsList); 
			if(itemsList.size() < ApplicationConstants.PAGE_SIZE) {
				listingForm.removeComponent(loadMoreButton);
				loadMoreButton = null;
			}
		} 
		mainMidlet.showCurrentForm(DealsListingForm.this);
		itemsList = null;
	}
	
	public void onCommandClick(Command cmd) {

	}

	public void addCommands() {

	}

	public void onBack() {
		try {
			mainMidlet.destroyApp(true);
		} catch (MIDletStateChangeException e) {
			e.printStackTrace();
		}
	}

}
