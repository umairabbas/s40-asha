package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListModel;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.entities.DealsEntity;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.Utils;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomDialogBox;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class DealsDetailForm extends BaseForm {
	
	private Container contentContainer;
	private String navigateFrom = "";

	
	public DealsDetailForm(String title, OlaroundMidlet mainMidlet, int storeId, String storePicture, String navigateFrom) {
		super(title, mainMidlet, storeId, storePicture);
		this.navigateFrom = navigateFrom;
	}

	public void onCreate() {
		this.setScrollableY(true);
		this.getStyle().setBgColor(0xF0EBE6);
	//	setBannerImage(storePicture);
		this.addComponent(contentContainer);
	}

	public void onResume() {
		if(navigateFrom.equals(VenuesListingForm.class.getName())) {
			mainMidlet.showMainCategoryBar(false);
			mainMidlet.showVenueDetailCategoryBar(true); 
		} else {
			mainMidlet.showMainCategoryBar(false);
			mainMidlet.showVenueDetailCategoryBar(false); 
		}

	}
	
	public void loadData(boolean isRefreshed) {
		System.out.println("loading data...");
		contentContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		
		List dealsList = DataModel.fetchDealItem(this, storeId);
			if (dealsList != null) {
				ListModel itemsList = dealsList.getModel(); 
				int size = itemsList.getSize();
				contentContainer.getStyle().setMargin(0, 2, 3, 3);
				createTopLabel(size);
				for (int i = 0; i < size; i++) {
					DealsEntity Dl = (DealsEntity) itemsList.getItemAt(i);
					createDealContainer(Dl.getPromotions().getPromotionsDescription());
				}
			} else {
				createTopLabel(0);
			}
			createHelpArea();

	}
	
	private void createTopLabel(int size) {
		Container topContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		Image img = ImageHelper.getImageFromResources(ImageConstants.LABEL_DESIGN);
		Label labelDesign = new Label(img);
		labelDesign.setPreferredH(16);
		labelDesign.getStyle().setMargin(0, 0, 0, 0);
		labelDesign.getStyle().setPadding(1, 0, 0, 1);
		topContainer.addComponent(labelDesign);

		String text  =  "You have "+size+" available here";
		Label topLabel = new Label(text);
		topLabel.getStyle().setFgColor(ApplicationConstants.COLOR_FONT_LIGHT_GREY);
		topLabel.getStyle().setFont(Utils.getFontFromResources(ApplicationConstants.FONT_SMALL));
		topLabel.getStyle().setMargin(0, 0, 0, 0);
		topLabel.getStyle().setPadding(0, 0, 0, 0);
		topContainer.addComponent(topLabel);
		
		labelDesign = new Label(img);
		labelDesign.setPreferredH(6);
		labelDesign.setPreferredH(16);
		labelDesign.getStyle().setMargin(0, 0, 0, 0);
		labelDesign.getStyle().setPadding(1, 0, 3, 0);
		topContainer.addComponent(labelDesign);
		topContainer.getStyle().setMargin(2, 0, 8, 0);
		
		contentContainer.addComponent(topContainer);
		topLabel = null;
		img = null;
		labelDesign = null;
	}
	
	private void createDealContainer(String promotionMsg) {
		Container dealContainer = new Container(new BorderLayout());
		dealContainer.getStyle().setBgTransparency(0);
		dealContainer.getStyle().setBgImage(ImageHelper.getImageFromResources(ImageConstants.BG_CONTAINER));
		dealContainer.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
		
		CustomTextArea dealDetailArea = new CustomTextArea(promotionMsg, true);
		dealDetailArea.setCustomTextColor(ApplicationConstants.COLOR_FONT_LIGHT_GREY);
		dealDetailArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		dealDetailArea.setCustomMargin(8, 0, 25, 0);
		
		Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		CustomImageButton button = new CustomImageButton(ImageConstants.BUTTON_ORANGE_NORMAL, ImageConstants.BUTTON_ORANGE_SELECTED, ApplicationConstants.BUTTON_NAME_CLAIM_DEAL);
		button.setText(ApplicationConstants.BUTTON_NAME_CLAIM_DEAL);
		button.setPreferredH(40);
		button.setPreferredW(240);
		button.addActionListener(this);
		button.getStyle().setFgColor(ApplicationConstants.COLOR_WHITE);
		button.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		button.getPressedStyle().setFgColor(ApplicationConstants.COLOR_WHITE);
		button.getPressedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));

		buttonContainer.getStyle().setMargin(10, 15, 8, 8); 
		buttonContainer.getStyle().setPadding(0, 0, 0, 0);
		buttonContainer.addComponent(button);
		button = null;
	
		dealContainer.addComponent(BorderLayout.SOUTH, buttonContainer);
		dealContainer.addComponent(BorderLayout.CENTER, dealDetailArea);
		dealContainer.getStyle().setMargin(5, 0, 0, 0);
		dealContainer.getStyle().setPadding(0, 0, 0, 0);
		contentContainer.addComponent(dealContainer);
		dealContainer = null;
		buttonContainer = null;
	}

	private void createHelpArea() {
		CustomTextArea helpArea = new CustomTextArea(ApplicationConstants.MSG_DEAL_HELP, true);
		helpArea.setCustomTextColor(ApplicationConstants.COLOR_FONT_LIGHT_GREY);
		helpArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		helpArea.setCustomMargin(15, 0, 0, 0);
		contentContainer.addComponent(helpArea);
		helpArea = null;
	}
	
	public void onPause() {

	}

	public void onButtonClick(Button button) {
		if(button.getName().equals(ApplicationConstants.BUTTON_NAME_CLAIM_DEAL)) {
			String msg ="You are about to claim deal \n\n\t\t\t20% off \n\n Description of the deal & any other info that comes in";
			CustomDialogBox.showDialogForClaimDeal(msg, DealsDetailForm.this);
		}
	}

	public void onCommandClick(Command cmd) {

	}

	public void addCommands() {

	}

	public void onBack() {
		mainMidlet.destoryVenueDetailCategoryForms();
		mainMidlet.navigateToForm(this, navigateFrom, true);
	}
	
	public void dealloc() {
		super.dealloc();
		contentContainer = null;
	}

}
