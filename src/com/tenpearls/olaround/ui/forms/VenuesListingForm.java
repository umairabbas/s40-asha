package com.tenpearls.olaround.ui.forms;

import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.List;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.entities.VenueEntity;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomDialogBox;
import com.tenpearls.olaround.ui.forms.base.BaseListingForm;

public class VenuesListingForm extends BaseListingForm {
	
	VenuesListingForm listingForm = null;
	String query ;
	
	public VenuesListingForm(String title, OlaroundMidlet midlet, List venueList, String query) {
		super(title, midlet, venueList);
		listingForm = this;
		this.query = query;
	}

	public void onCreate() {
		try {
			this.getStyle().setBgColor(ApplicationConstants.COLOR_BG_FORM);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("An exception is raised: " + e.getMessage());
			CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ALERT, ApplicationConstants.DIALOG_ERROR_MESSAGE, ApplicationConstants.DIALOG_BUTTON_OK);
		}
	}
	
	public void loadData(boolean isRefreshed) {
		
	}

	public void onButtonClick(Button btn) {
		showLoadingDialog();
		pageIndex++;
		query = "&page=" +  pageIndex;
		itemsList = DataModel.fetchVenue(query, this);
		if(itemsList != null) {
			createList();
			listingForm.addComponent(pageIndex-1, itemsList); 
			if(itemsList.size() < ApplicationConstants.PAGE_SIZE) {
				listingForm.removeComponent(loadMoreButton);
				loadMoreButton = null;
			}
		}
		mainMidlet.showCurrentForm(VenuesListingForm.this);
		itemsList = null;
	}
	
	public void onListItemClick(Object obj) {
		List list = (List) obj;
		Object object = list.getSelectedItem();
		if (object != null) {
			VenueEntity entity = (VenueEntity) object;
			DataModel.setVenueEntity(entity);
			DataModel.fetchVenueById(entity.getId(), VenuesListingForm.this);
			mainMidlet.showVenueDetailForm(this, false);
		}
	}

	public void dealloc() {
		super.dealloc();
	}

	public void onResume() {
		mainMidlet.showVenueDetailCategoryBar(false);
		mainMidlet.showMainCategoryBar(true);
		mainMidlet.getMainCategoryBar().setSelectedIndex(0);
	}

	public void onPause() {

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
