package com.tenpearls.olaround.ui.forms;

import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Image;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomDialogBox;
import com.tenpearls.olaround.ui.forms.base.BaseListingForm;

public class FriendsActivityListingForm extends BaseListingForm {
	
	FriendsActivityListingForm listingForm = null;
	String query;
	
	public FriendsActivityListingForm(String title, OlaroundMidlet midlet) {
		super(title, midlet);
		listingForm = this;
	}
	
	public void onCreate() {
		try {
			this.getStyle().setBgColor(ApplicationConstants.COLOR_BG_FORM);
			createList();
			if(itemsList != null) {
				this.addComponent(itemsList);
				if(itemsList.size() == ApplicationConstants.PAGE_SIZE) { 
					getLoadMoreButton();
				}
				itemsList = null;
			}

		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("An exception is raised: " + e.getMessage());
			CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ALERT, ApplicationConstants.DIALOG_ERROR_MESSAGE, ApplicationConstants.DIALOG_BUTTON_OK);
		}

	}
	
	public void onListItemClick(Object obj) {

	}
	
	public void onPause() {

	}
	
	public void loadData(boolean isRefreshed) {
		pageIndex = 1;
		query = "&page=" +  pageIndex;
		itemsList = DataModel.getFriendsActivityList(query, FriendsActivityListingForm.this);
	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(true);
		mainMidlet.showVenueDetailCategoryBar(false);
	}


	public void onButtonClick(Button btn) {
		showLoadingDialog();
		pageIndex++;
		query = "&page=" + pageIndex ;
		itemsList = DataModel.getFriendsActivityList(query, FriendsActivityListingForm.this);
		if(itemsList != null) {
			createList();
			listingForm.addComponent(pageIndex-1, itemsList); 
			if(itemsList.size() < ApplicationConstants.PAGE_SIZE) {
				listingForm.removeComponent(loadMoreButton);
				loadMoreButton = null;
			}
		}
		mainMidlet.showCurrentForm(FriendsActivityListingForm.this);
		itemsList = null;
	}

	public void onCommandClick(Command cmd) {

	}

	public void addCommands() {
		Image img = ImageHelper
				.getImageFromResources(ImageConstants.ADD_FRIEND);
		
		Command selectAddCmd = new Command(ApplicationConstants.ADD_COMMAND, img) ;
		addCommand(selectAddCmd);
		setDefaultCommand(selectAddCmd);
	}

	public void dealloc() {
		super.dealloc();
	}
	
	public void onBack() {
		try {
			if(mainMidlet.getMainCategoryBar().getVisibility()) {
				mainMidlet.destroyApp(true);
			}
		} catch (MIDletStateChangeException e) {
			e.printStackTrace();
		}
	}

}
