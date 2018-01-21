package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListModel;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.entities.MenuEntity;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.Utils;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CenterLayout;
import com.tenpearls.olaround.ui.forms.base.BaseListingForm;

public class OlMenuListingForm extends BaseListingForm {

	
	public OlMenuListingForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}

	public void onCreate() {
		this.setScrollableY(true);
		this.getStyle().setBgColor(ApplicationConstants.COLOR_BG_FORM);
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));

	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(true);
	}
	
	private void createMenuHeading(String text) {
		
		Container mainContainer = new Container(new CenterLayout());
		Container innerContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));//new BoxLayout(BoxLayout.X_AXIS)

		Image img = ImageHelper.getImageFromResources(ImageConstants.LABEL_DESIGN);
		Label labelDesign = new Label(img);
		labelDesign.getStyle().setAlignment(Component.RIGHT);
		labelDesign.setPreferredH(16);
		labelDesign.getStyle().setMargin(0, 0, 0, 0);
		labelDesign.getStyle().setPadding(1, 0, 0, 1);
		innerContainer.addComponent(labelDesign);

		Label topLabel = new Label(text);
		topLabel.getStyle().setAlignment(CENTER);
		topLabel.getStyle().setFgColor(ApplicationConstants.COLOR_FONT_LIGHT_GREY);
		topLabel.getStyle().setFont(Utils.getFontFromResources(ApplicationConstants.FONT_SMALL));
		topLabel.getStyle().setMargin(0, 0, 0, 0);
		topLabel.getStyle().setPadding(0, 0, 0, 1);
		innerContainer.addComponent(topLabel);
		
		labelDesign = new Label(img);
		labelDesign.getStyle().setAlignment(Component.LEFT);
		labelDesign.setPreferredH(16);
		labelDesign.getStyle().setMargin(0, 0, 0, 0);
		labelDesign.getStyle().setPadding(1, 0, 1, 0);
		innerContainer.addComponent(labelDesign);

		mainContainer.getStyle().setMargin(5, 5, 0, 0);
		mainContainer.addComponent(innerContainer);
		addComponent(mainContainer);

		topLabel = null;
		img = null;
		labelDesign = null;
		mainContainer = null;
	}
	

	public void onPause() {
		
	}

	public void onButtonClick(Button btn) {
		
	}

	public void onCommandClick(Command cmd) {
		
	}

	public void loadData(boolean isRefreshed) {
		showLoadingDialog();
		setBannerImage(DataModel.getVenueEntity().getBrand().getBackgroundPictureURL());
		ListModel list = DataModel.getMenuList(DataModel.getVenueEntity().getBrand().getId(), OlMenuListingForm.this).getModel();
		for (int i = 0; i < list.getSize(); i++) {
			MenuEntity menu = (MenuEntity) list.getItemAt(i);
			createMenuHeading(menu.getName());
			itemsList = new List(menu.getMenuItem());
			createList();
			addComponent(itemsList);
			itemsList = null;
			menu = null;
			
		}
		list = null;
	}

	public void addCommands() {
	
	}

	public void onBack() {
		mainMidlet.destoryVenueDetailCategoryForms();
		mainMidlet.showVenuListingForm(OlMenuListingForm.this , true, null, "");
	}
	
	public void dealloc() {
		super.dealloc();
	}

	public void onListItemClick(Object obj) {
		
	}
}
