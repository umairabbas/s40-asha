package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.List;
import com.sun.lwuit.list.ListModel;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.entities.VenueEntity;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.forms.base.BaseListingForm;

public class SearchResultListingForm extends BaseListingForm {
	
	SearchResultListingForm listingForm = null;
	ListModel searchList ;
	static int listCount ;
	static int pageSize = ApplicationConstants.PAGE_SIZE;
	static int startIndex;
	int pageIndex = 1;

	public SearchResultListingForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
		listingForm = this;
	}
	
	public void onCreate() {
		try {
			this.getStyle().setBgColor(ApplicationConstants.COLOR_BG_FORM);
			createList();
			this.addComponent(itemsList);
			if(listCount > startIndex) {
				getLoadMoreButton();
			}
			itemsList = null;
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("An exception is raised: " + e.getMessage());
		}
	}
	
	public void onListItemClick(Object obj) {
		List list = (List) obj;
		Object object = list.getSelectedItem();
		if (object != null) {
			VenueEntity entity = (VenueEntity) object;
			DataModel.setVenueEntity(entity);
			System.out.println("Item clicked:" + entity.getName());
			DataModel.fetchVenueById(entity.getId(), SearchResultListingForm.this);
			mainMidlet.showVenueDetailForm(this, false);
		}
	}
	
	public void loadData(boolean isRefreshed) {
		mainMidlet.showMainCategoryBar(false);
		startIndex = 1;
		if(DataModel.getSearchList() != null) {
			searchList  = DataModel.getSearchList().getModel();
			listCount = searchList.getSize();
			itemsList = paging();
			DataModel.emptySearchList();
		} else {
			itemsList = null;
		}
	}
	
	private List paging() {
		List list = new List();

		if(listCount > startIndex) {
			for (int i = (startIndex-1); i < (pageSize+startIndex) ; i++) {
				if(i == listCount)  {
					System.out.println(i);
					break;
				}
				list.addItem((VenueEntity)searchList.getItemAt(i));
			}
			startIndex += pageSize;
		} else {
			//CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ALERT, ApplicationConstants.MSG_NO_MORE_DATA,ApplicationConstants.DIALOG_OK_BUTTON);
		}
		return list;
	}
	
	public void onButtonClick(Button btn) {
		itemsList = paging();
		if(itemsList != null && itemsList.size() > 0) {
			createList();
			listingForm.addComponent(pageIndex++, itemsList); 
			itemsList = null;
			if(startIndex >= listCount ) {
				listingForm.removeComponent(loadMoreButton);
			}
		}
	}
	
	public void onResume() {
		
	}

	public void onPause() {
		
	}

	public void onCommandClick(Command cmd) {
		
	}

	public void addCommands() {
		
	}

	public void onBack() {
		mainMidlet.destorySearchForm();
		mainMidlet.showVenuListingForm(SearchResultListingForm.this, true, null, "");
	}
	
	public void dealloc() {
		super.dealloc();
		listCount = 0;
		startIndex = 0;
		searchList = null;
		listingForm = null;
		loadMoreButton = null;
	}

}
