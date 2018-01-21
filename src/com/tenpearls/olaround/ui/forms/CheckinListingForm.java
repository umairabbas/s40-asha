package com.tenpearls.olaround.ui.forms;


import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.entities.VenueEntity;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomDialogBox;
import com.tenpearls.olaround.ui.forms.base.BaseListingForm;

public class CheckinListingForm extends BaseListingForm {
	
	CheckinListingForm listingForm = null;
	String query ;
	int pageIndex = 1;
	
	public CheckinListingForm(String title, OlaroundMidlet midlet) {
		super(title, midlet);
		listingForm = this;
//		this.query = query;
	}

	private void setTopText() {
		Label topLabel = new Label("Please select the venue to check-in");
		topLabel.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_SMALL));
		topLabel.getStyle().setFgColor(ApplicationConstants.COLOR_FONT_LIGHT_GREY);
		topLabel.getStyle().setAlignment(CENTER);
		topLabel.getStyle().setMargin(10, 15, 0, 0);
		this.addComponent(topLabel);
	}
	
	public void onCreate() {
		this.getStyle().setBgColor(ApplicationConstants.COLOR_BG_FORM);
		setTopText();
		try {
			createList();
			addComponent(itemsList);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("An exception is raised: " + e.getMessage());
			CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ALERT, ApplicationConstants.DIALOG_ERROR_MESSAGE, ApplicationConstants.DIALOG_BUTTON_OK);
		}
	}
	
	public void loadData(boolean isRefreshed) {
		itemsList = DataModel.fetchVenue("", this);
	}

	public void onButtonClick(Button btn) {
		showLoadingDialog();
		pageIndex++;
		query += "&page=" +  pageIndex;
		itemsList = DataModel.fetchVenue(query, this);
		if(itemsList != null) {
			createList();
			listingForm.addComponent(pageIndex-1, itemsList); 
			if(itemsList.size() < ApplicationConstants.PAGE_SIZE) {
				listingForm.removeComponent(loadMoreButton);
				loadMoreButton = null;
			}
		}
		dismissLoadingDialog();
		mainMidlet.showCurrentForm(CheckinListingForm.this);
		itemsList = null;
	}
	
	public void onListItemClick(Object obj) {
		System.out.println("venue clicked");
		List list = (List) obj;
		Object object = list.getSelectedItem();
		if (object != null) {
			VenueEntity entity = (VenueEntity) object;
			DataModel.setVenueEntity(entity);
			System.out.println("Item clicked:" + entity.getName());
			
///			DataModel.fetchVenueById(entity.getId(), CheckinListingForm.this);
			DataModel.getCheckin(this);
//			mainMidlet.showVenueDetailForm(this, false);
//			mainMidlet.showClaimedCheckinInfoForm(this, false);
		}
	}

	public void dealloc() {
		super.dealloc();
	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(false);
	}

	public void onPause() {

	}

	public void onCommandClick(Command cmd) {

	}

	public void addCommands() {

	}

	public void onBack() {
		mainMidlet.showQRScanWithCheckInForm(CheckinListingForm.this, true);
	}

}
