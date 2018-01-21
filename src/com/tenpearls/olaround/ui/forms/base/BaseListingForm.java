package com.tenpearls.olaround.ui.forms.base;

import com.sun.lwuit.Button;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.ui.components.CustomDialogBox;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.forms.CheckinListingForm;
import com.tenpearls.olaround.ui.forms.DealsListingForm;
import com.tenpearls.olaround.ui.forms.FriendsActivityListingForm;
import com.tenpearls.olaround.ui.forms.OlMeForm;
import com.tenpearls.olaround.ui.forms.OlMenuListingForm;
import com.tenpearls.olaround.ui.forms.SearchResultListingForm;
import com.tenpearls.olaround.ui.forms.VenuesListingForm;
import com.tenpearls.olaround.ui.listcellrenderers.DealsListCellRenderer;
import com.tenpearls.olaround.ui.listcellrenderers.ActivityListCellRenderer;
import com.tenpearls.olaround.ui.listcellrenderers.FriendListCellRenderer;
import com.tenpearls.olaround.ui.listcellrenderers.MenuListCellRenderer;
import com.tenpearls.olaround.ui.listcellrenderers.SearchResultListCellRenderer;
import com.tenpearls.olaround.ui.listcellrenderers.VenueListCellRenderer;

public abstract class BaseListingForm extends BaseForm implements IListItemClickListener{

	protected static  List itemsList;	
	protected CustomImageButton loadMoreButton ;
	protected boolean is_Friends;

	public BaseListingForm(String title, OlaroundMidlet midlet) {
		super(title, midlet);
	}	
	
	public BaseListingForm(String title, OlaroundMidlet midlet, List list) {
		super(title, midlet);
		itemsList = list;
		createList();
		this.addComponent(itemsList);
		itemsList = null;
		getLoadMoreButton();
	}	
	
	protected void createList() {
		try {
			System.out.println("In createList() , class baseListingForm");
			if(itemsList != null && itemsList.size() > 0 ) {
					
				if(this instanceof VenuesListingForm) {
					itemsList.setRenderer(new VenueListCellRenderer()); 
				} else if(this instanceof SearchResultListingForm) {
					itemsList.setRenderer(new SearchResultListCellRenderer()); 
				} else if(this instanceof DealsListingForm) {
					itemsList.setRenderer(new DealsListCellRenderer()); 
				} else if(this instanceof OlMenuListingForm) {
					itemsList.setRenderer(new MenuListCellRenderer()); 
				} else if(this instanceof FriendsActivityListingForm) {
					itemsList.setRenderer(new ActivityListCellRenderer()); 
				} else if(this instanceof OlMeForm) {
					if(is_Friends != true){
					itemsList.setRenderer(new ActivityListCellRenderer()); 
					} else {
					itemsList.setRenderer(new FriendListCellRenderer()); 
					}
				} else if(this instanceof CheckinListingForm) {
					itemsList.setRenderer(new VenueListCellRenderer());
				}
				
				itemsList.addActionListener(this);
				itemsList.setIgnoreFocusComponentWhenUnfocused(true);
				itemsList.setMinElementHeight(itemsList.size());
				itemsList.getStyle().setPadding(0, 0, 0, 0);
				itemsList.getStyle().setMargin(0, 0, 0, 0);
				itemsList.getSelectedStyle().setPadding(0, 0, 0, 0);
				itemsList.getSelectedStyle().setMargin(0, 0, 0, 0);
				//this.addComponent(itemsList);
				return;
			} else {
			if (this instanceof OlMeForm) {
				} else {
					System.out.println("No item found");
					createNoDataLabel();
				}
				
			}
		} catch (Exception e) {
			CustomDialogBox.showInfoAlert("alert","Error:" + e.getMessage());
		}
		
	}
	
	protected void createNoDataLabel() {
		Label noDataLabel = new Label("No Data Available");
		noDataLabel.getStyle().setMargin(126, 0, 52, 0);
		noDataLabel.getStyle().setBgTransparency(0);
		noDataLabel.getStyle().setFgColor(0x000000);
		this.addComponent(noDataLabel);
		noDataLabel = null;
	}
	
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);		
		if(!event.isConsumed() && event.getSource() instanceof List) {
			onListItemClick(event.getSource());
		} else if(!event.isConsumed() && event.getSource() instanceof Button){
			onButtonClick((Button) event.getSource());
		}
	}	
	
	public void onRefresh() {
		mainMidlet.refreshCurrentForm(this);
	}
	
	public void dealloc() {		
		super.dealloc();
		itemsList = null;
	}
	
	protected void getLoadMoreButton() {
		loadMoreButton = new CustomImageButton(ImageConstants.BUTTON_ORANGE_NORMAL, ImageConstants.BUTTON_ORANGE_SELECTED, ApplicationConstants.BUTTON_NAME_LOAD_MORE);
		loadMoreButton.setText(ApplicationConstants.BUTTON_NAME_LOAD_MORE);
		loadMoreButton.setPreferredH(40);
		loadMoreButton.setPreferredW(208); 
		loadMoreButton.addActionListener(this);
		loadMoreButton.setCustomTextColor(ApplicationConstants.COLOR_WHITE);
		loadMoreButton.setCustomMargin(2, 2, 10, 10);
		loadMoreButton.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		loadMoreButton.addActionListener(this);
		this.addComponent(loadMoreButton);
		//loadMoreButton = null;
	}
	
	protected void getLoadMoreButton(Container container) {
		System.out.println("-> getLoadMoreButton()");
		loadMoreButton = new CustomImageButton(ImageConstants.BUTTON_ORANGE_NORMAL, ImageConstants.BUTTON_ORANGE_SELECTED, ApplicationConstants.BUTTON_NAME_LOAD_MORE);
		loadMoreButton.setText(ApplicationConstants.BUTTON_NAME_LOAD_MORE);
		loadMoreButton.setPreferredH(40);
		loadMoreButton.setPreferredW(208); 
		loadMoreButton.addActionListener(this);
		loadMoreButton.setCustomTextColor(ApplicationConstants.COLOR_WHITE);
		loadMoreButton.setCustomMargin(2, 2, 10, 10);
		loadMoreButton.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		loadMoreButton.addActionListener(this);
		container.addComponent(loadMoreButton);
		//loadMoreButton = null;
	}

}
