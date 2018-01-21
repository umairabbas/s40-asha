package com.tenpearls.olaround.ui.forms;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;

import com.sun.lwuit.Button;
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
import com.tenpearls.olaround.entities.ClaimKickEntity;
import com.tenpearls.olaround.entities.KickEntity;
import com.tenpearls.olaround.entities.VenueEntity;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class KicksForm extends BaseForm {

	private Container contentContainer;
	private int availablePunches;
	private ListModel kickList;
	private static ClaimKickEntity claimKickEntity;
	private int storeId;
	
	public KicksForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}

	public void onCreate() {
		this.setScrollableY(true);
		this.getStyle().setBgColor(0xF0EBE6);
	}
	
	public void loadData(boolean isRefreshed) {
		VenueEntity venueEntity = DataModel.getVenueEntity();
		List list = null;
		storeId = venueEntity.getBrand().getId();
		list = DataModel.fetchKicksList(storeId, this);
		createUI(list);
	}
	
	private void createUI(List list) {
		//setBannerImage(DataModel.getVenueEntity().getBrand().getBackgroundPictureURL());
		contentContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		contentContainer.getStyle().setMargin(0, 2, 3, 3);
		if(list != null) {
			kickList = list.getModel();
			KickEntity kick = (KickEntity) kickList.getItemAt(0);
			availablePunches = getAvailablePunches(kick.getUserPointBalance());
			System.out.println("avialable punches: " + availablePunches);	
		} else {
			availablePunches = 0;
		}
		
			
		createTopLabel();
		createKickArea();
		createHelpArea();
		this.addComponent(contentContainer);
	}
	
	private void createKickArea() {
		if(kickList != null) {
			for (int i = 0; i < kickList.getSize(); i++) {
				KickEntity entity = (KickEntity) kickList.getItemAt(i);
				if(availablePunches >= entity.getCost()) {
					createOffersContainer(i, entity.getTitle(), 0xD5D5D5, false);
				} else {
					createOffersContainer(i, entity.getTitle(), ApplicationConstants.COLOR_WHITE, true);
				}
			}
		}
	}
 
	private int getAvailablePunches(int pointBalance) {
		return pointBalance / 10;
	}
	
	private void createTopLabel() {
		Container topContainer = new Container(new BorderLayout());
		topContainer.getStyle().setMargin(5, 5, 35, 35); 
		topContainer.getStyle().setPadding(0, 0, 0, 0);
		
		Image img = ImageHelper.getImageFromResources(ImageConstants.LABEL_DESIGN);
		Label labelDesign = new Label(img);
		labelDesign.getStyle().setMargin(0, 0, 0, 0);
		labelDesign.getStyle().setPadding(0, 13, 0, 0);
		topContainer.addComponent(BorderLayout.WEST, labelDesign);
		String text = "You have " + availablePunches +  " punches available here";
		CustomTextArea topArea = new CustomTextArea(text, true);
		topArea.setCustomMargin(0, 0, 0, 0);
		topArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		topArea.setAlignment(CENTER);
		topArea.setCustomTextColor(ApplicationConstants.COLOR_FONT_DARK_GREY);
		topContainer.addComponent(BorderLayout.CENTER, topArea);
		
		labelDesign = new Label(img);
		labelDesign.getStyle().setMargin(0, 0, 0, 0);
		labelDesign.getStyle().setPadding(0, 13, 0, 0);
		topContainer.addComponent(BorderLayout.EAST, labelDesign);

		contentContainer.addComponent(topContainer);
		img = null;
		labelDesign = null;
		topArea = null;
	}
	
	private void createOffersContainer(int index, String descriptionText, int containerBgColor, boolean isLocked) {
		Container offersContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		
		offersContainer.getStyle().setBgColor(containerBgColor);
		offersContainer.getStyle().setBgTransparency(255);
		offersContainer.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
		offersContainer.getStyle().setMargin(8, 0, 0, 0);
		
		Image lockImage = null;
		if(isLocked) {
			lockImage = ImageHelper.getImageFromResources(ImageConstants.LOCK_ICON);
		} else {
			lockImage = ImageHelper.getImageFromResources(ImageConstants.UNLOCK_ICON);
		}
		
		Label lockIcon = new Label(lockImage);
		offersContainer.addComponent(lockIcon);
		lockIcon = null;
		
		Button offerButton = new Button(descriptionText);
		offerButton.setName(Integer.toString(index));
		createEmptyBorderButton(offerButton);
		offerButton.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		offerButton.getPressedStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		offerButton.getStyle().setAlignment(LEFT);
		offerButton.getPressedStyle().setAlignment(LEFT);
		offerButton.getStyle().setBgTransparency(0);
		offerButton.setPreferredW(240);
		offerButton.setPreferredH(34);
		offerButton.addActionListener(this);
		offersContainer.addComponent(offerButton);
		contentContainer.addComponent(offersContainer);
		offersContainer = null;
		
	}
	
	private void createHelpArea() {
		CustomTextArea helpArea = new CustomTextArea(ApplicationConstants.MSG_KICK_HELP, true);
		helpArea.setCustomTextColor(ApplicationConstants.COLOR_FONT_LIGHT_GREY);
		helpArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		helpArea.setCustomMargin(15, 0, 3, 2);
		contentContainer.addComponent(helpArea);
		helpArea = null;
	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(true);
	}

	public void onPause() {

	}
	
	public void onButtonClick(Button btn) {
		int index = Integer.parseInt(btn.getName());
		if(kickList != null) {
			KickEntity kickEntity = (KickEntity) kickList.getItemAt(index);
			//if(availablePunches >= kickEntity.getCost()) {
				System.out.println("kick id:" + kickEntity.getId());
				String msg = ApplicationConstants.MSG_CLIAM_KICK + kickEntity.getTitle();
				showDialogForClaimKick(kickEntity.getId(), storeId, msg, KicksForm.this);
		//	} else {
			//	CustomDialogBox.showDialog("", "You have not enough punches.", KicksForm.this);
		//	}
		}
	}
	
	public static void showDialogForClaimKick(final int kickId, final int storeId, String text, final BaseForm currentForm){

		Alert alert = new Alert(ApplicationConstants.DIALOG_TITLE_FOR_CLAIM_KICK, text, null, null);
		alert.addCommand(new Command (ApplicationConstants.DIALOG_BUTTON_CLAIM_LATER, Command.OK, 1));
		alert.addCommand(new Command (ApplicationConstants.DIALOG_BUTTON_CLAIM_NOW, Command.OK, 0));

		alert.setTimeout(Alert.FOREVER);
		javax.microedition.lcdui.Display.getDisplay(OlaroundMidlet.getInstance()).setCurrent(alert);

		alert.setCommandListener(new CommandListener() {

			public void commandAction(javax.microedition.lcdui.Command command, Displayable displayable) {
				if(command.getLabel().equals(ApplicationConstants.DIALOG_BUTTON_CLAIM_NOW)) {
					System.out.println(ApplicationConstants.DIALOG_BUTTON_CLAIM_NOW);
					claimKickEntity = DataModel.cliamKick(storeId, kickId, currentForm);
					if(claimKickEntity!= null && claimKickEntity.getResult().equals("true")) {
						OlaroundMidlet.getInstance().showClaimKickInfoForm(currentForm, false);
					} 
				} else {
					System.out.println(ApplicationConstants.DIALOG_BUTTON_CLAIM_LATER);
					OlaroundMidlet.getInstance().showCurrentForm(currentForm);

				}
			}
		});
		alert = null;
	}

	public void onCommandClick(com.sun.lwuit.Command cmd) {

	}

	public void addCommands() {

	}

	public void onBack() {
		mainMidlet.destoryVenueDetailCategoryForms();
		mainMidlet.showVenuListingForm(KicksForm.this , true, null, "");
	}
	
	public void dealloc() {
		super.dealloc();
		contentContainer = null;
		kickList = null;
		claimKickEntity = null;

	}

	public static ClaimKickEntity getClaimKickEntity() {
		return claimKickEntity;
	}

	public static void setClaimKickEntity(ClaimKickEntity claimKickEntity) {
		KicksForm.claimKickEntity = claimKickEntity;
	}

}
