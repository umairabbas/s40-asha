package com.tenpearls.olaround.ui.forms;

import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.Utils;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CenterLayout;
import com.tenpearls.olaround.ui.components.CustomDialogBox;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.forms.base.BaseListingForm;

public class OlMeForm extends BaseListingForm {
	OlMeForm listingForm = null;
	String query;
	private int pageIndex;
	private int pageIndexForUser;
	private int pageIndexForFriends;
	private int pageIndexForPunches;
	private int pageIndexForKicks;
	private Container topContainer;
	private Container bottomContainer;
	private CustomImageButton btnImgActivity;
	private CustomImageButton btnImgFriends;
	private CustomImageButton btnImgPunches;
	private CustomImageButton btnImgKicks;
	private String type = "all";
	private boolean punch;
	private boolean kicks;
	private boolean activity;
	private boolean friends;
	private Label lblName;
	private Label lblLocation;

	public OlMeForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
		listingForm = this;
		is_Friends = false;
	}

	public void onCreate() {
		this.getStyle().setBgColor(ApplicationConstants.COLOR_BG_FORM);
		this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		punch = false;
		kicks = false;
		activity = true;
		friends = false;
		
	}

	private void firstLoad() {
		settopContainer();
		bottomContainer = new Container(new CenterLayout());
		setBtnStyle(activity, btnImgActivity, ImageConstants.BUTTON_ACTIVITY_SELECTED);
		createMenuHeading("Activities");
		setNewContainer(pageIndexForUser);
		this.addComponent(bottomContainer);
	}

	private void settopContainer() {
		topContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		topContainer.getStyle().setMargin(3, 0, 3, 3);
		topContainer.getStyle().setPadding(5, 7, 5, 5);
		topContainer.getStyle().setBgTransparency(255);
		Image imgContainer = null;
		imgContainer = ImageHelper
				.getImageFromResources(ImageConstants.INFO_CONTAINER_LARGE);
		if (imgContainer != null) {
			topContainer.getStyle().setBgImage(imgContainer);
			topContainer.getStyle().setBackgroundType(
					Style.BACKGROUND_IMAGE_SCALED);
		}
		creatTopText();
		creatTopIcons();
		imgContainer = null;
		this.addComponent(topContainer);
	}

	private void creatTopText() {
		String strName = DataModel.getUserEntity().getFirstName() + " "
				+ DataModel.getUserEntity().getLastName();
		lblName = new Label(strName);
		lblName.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_LARGE));
		lblName.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		lblName.getStyle().setMargin(0, 0, 50, 0);
		topContainer.addComponent(lblName);
		String Location = DataModel.getUserProfile();
		if (Location != null) {
			System.out.println("COMES Here");
			lblLocation = new Label(Location);
			lblLocation.getStyle().setFont(
					Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
							Font.SIZE_SMALL));
			lblLocation.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
			lblLocation.getStyle().setMargin(0, 0, 50, 0);
			topContainer.addComponent(lblLocation);
		}
	}

	private void setButton(Button btn) {
		((CustomImageButton) btn).setCustomMargin(0, 0, 6, 0);
		btn.setPreferredW(43);
		btn.setPreferredH(43);
		btn.getStyle().setBorder(Border.createLineBorder(1, 0xB7B7B7));
		btn.getSelectedStyle().setBorder(Border.createLineBorder(1, 0xB7B7B7));
		btn.getPressedStyle().setBorder(Border.createLineBorder(1, 0xB7B7B7));
		btn.addActionListener(this);
	}

	private void creatTopIcons() {
		Container conIcons = new Container(new BoxLayout(BoxLayout.X_AXIS));
		conIcons.getStyle().setMargin(4, 3, 10, 0);
		btnImgActivity = new CustomImageButton(
				ImageConstants.BUTTON_ACTIVITY_NORMAL,
				ImageConstants.BUTTON_ACTIVITY_SELECTED,
				ApplicationConstants.BUTTON_NAME_ACTIVITY);
		btnImgFriends = new CustomImageButton(
				ImageConstants.BUTTON_FRIENDS_NORMAL,
				ImageConstants.BUTTON_FRIENDS_SELECTED,
				ApplicationConstants.BUTTON_NAME_FRIENDS);
		btnImgPunches = new CustomImageButton(
				ImageConstants.BUTTON_PUNCH_NORMAL,
				ImageConstants.BUTTON_PUNCH_SELECTED,
				ApplicationConstants.BUTTON_NAME_PUNCHES);
		btnImgKicks = new CustomImageButton(ImageConstants.BUTTON_KICKS_NORMAL,
				ImageConstants.BUTTON_KICKS_SELECTED,
				ApplicationConstants.BUTTON_NAME_KICKS);
		setButton(btnImgActivity);
		setButton(btnImgFriends);
		setButton(btnImgPunches);
		setButton(btnImgKicks);
		conIcons.addComponent(btnImgActivity);
		conIcons.addComponent(btnImgFriends);
		conIcons.addComponent(btnImgPunches);
		conIcons.addComponent(btnImgKicks);
		topContainer.addComponent(conIcons);
	}

	private void createMenuHeading(String text) {

		Container innerContainer = new Container(
				new BoxLayout(BoxLayout.X_AXIS));
		// Container mainContainer = new Container(new CenterLayout());

		Image img = ImageHelper
				.getImageFromResources(ImageConstants.LABEL_DESIGN);
		Label labelDesign = new Label(img);
		labelDesign.getStyle().setAlignment(Component.RIGHT);
		labelDesign.setPreferredH(16);
		labelDesign.getStyle().setMargin(0, 0, 0, 0);
		labelDesign.getStyle().setPadding(1, 0, 0, 1);
		innerContainer.addComponent(labelDesign);

		Label topLabel = new Label(text);
		topLabel.getStyle().setAlignment(CENTER);
		topLabel.getStyle().setFgColor(
				ApplicationConstants.COLOR_FONT_LIGHT_GREY);
		topLabel.getStyle().setFont(
				Utils.getFontFromResources(ApplicationConstants.FONT_SMALL));
		topLabel.getStyle().setMargin(0, 0, 0, 0);
		topLabel.getStyle().setPadding(0, 0, 0, 1);
		innerContainer.addComponent(topLabel);
		labelDesign = new Label(img);
		labelDesign.getStyle().setAlignment(Component.LEFT);
		labelDesign.setPreferredH(16);
		labelDesign.getStyle().setMargin(0, 0, 0, 0);
		labelDesign.getStyle().setPadding(1, 0, 1, 0);
		innerContainer.addComponent(labelDesign);
		bottomContainer.getStyle().setMargin(0, 0, 0, 0);
		bottomContainer.addComponent(innerContainer);
		// bottomContainer.addComponent(mainContainer);

		topLabel = null;
		img = null;
		labelDesign = null;
		innerContainer = null;
	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(true);
		mainMidlet.showVenueDetailCategoryBar(false);
		mainMidlet.getMainCategoryBar().setSelectedIndex(4);
	}

	void setBtnStyle(boolean var, Button btn, String name) {
		punch = false;
		kicks = false;
		activity = false;
		friends = false;
		btnImgActivity.getStyle().setBgImage(ImageHelper.getImageFromResources(ImageConstants.BUTTON_ACTIVITY_NORMAL));
		btnImgFriends.getStyle().setBgImage(ImageHelper.getImageFromResources(ImageConstants.BUTTON_FRIENDS_NORMAL));
		btnImgPunches.getStyle().setBgImage(ImageHelper.getImageFromResources(ImageConstants.BUTTON_PUNCH_NORMAL));
		btnImgKicks.getStyle().setBgImage(ImageHelper.getImageFromResources(ImageConstants.BUTTON_KICKS_NORMAL));
		btn.getStyle().setBgImage(ImageHelper.getImageFromResources(name));
		var = true;
	}

	private void removeOldContainer() {
		if (this.contains(bottomContainer)) {
			this.removeComponent(bottomContainer);
			bottomContainer.removeAll();
		}
	}

	private void setNewContainer(int Index) {
		is_Friends = false;
		Index = 1;
		pageIndex = Index;
		query = "&page=" + Index;
		itemsList = DataModel.getActivityList(query, OlMeForm.this, type);
		setItemList();
	}

	private void setItemList() {
		try {
			createList();
			if (itemsList == null) {
				Label errorMsg = new Label("No Data Avaliable");
				errorMsg.getStyle().setFont(
						Font.createSystemFont(Font.FACE_SYSTEM,
								Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
				errorMsg.getStyle().setFgColor(
						ApplicationConstants.COLOR_FONT_DARK_GREY);
				errorMsg.getStyle().setAlignment(CENTER);
				bottomContainer.addComponent(errorMsg);
			} else {

				bottomContainer.addComponent(itemsList);
				if (itemsList.getModel().getSize() == ApplicationConstants.PAGE_SIZE) {
					getLoadMoreButton(bottomContainer);
				}
				itemsList = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An exception is raised: " + e.getMessage());
			CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ALERT,
					ApplicationConstants.DIALOG_ERROR_MESSAGE,
					ApplicationConstants.DIALOG_BUTTON_OK);
		}
	}

	private void setFriendContainer(int Index) {
		is_Friends = true;
		Index = 1;
		pageIndex = Index;
		query = "&page=" + Index;
		itemsList = DataModel.fetchFriendList(query, OlMeForm.this);
		setItemList();
	}

	public void onButtonClick(Button btn) {
		if (btn.getName().equals(ApplicationConstants.BUTTON_NAME_ACTIVITY)) {
			if(activity!=true){
			showLoadingDialog();
			type = "all";
			setBtnStyle(activity, btn, ImageConstants.BUTTON_ACTIVITY_SELECTED);
			removeOldContainer();
			createMenuHeading("Activities");
			setNewContainer(pageIndexForUser);
			this.addComponent(bottomContainer);
			mainMidlet.showCurrentForm(this);
			activity = true;
			}

		} else if (btn.getName().equals(
				ApplicationConstants.BUTTON_NAME_FRIENDS)) {
			if(friends!=true){
			showLoadingDialog();
			setBtnStyle(friends, btn, ImageConstants.BUTTON_FRIENDS_SELECTED);
			removeOldContainer();
			createMenuHeading("Friends");
			setFriendContainer(pageIndexForFriends);
			this.addComponent(bottomContainer);
			mainMidlet.showCurrentForm(this);
			friends = true;
			}

		} else if (btn.getName().equals(
				ApplicationConstants.BUTTON_NAME_PUNCHES)) {
			if(punch!=true){
			showLoadingDialog();
			type = "verified_checkin";
			setBtnStyle(punch, btn, ImageConstants.BUTTON_PUNCH_SELECTED);
			removeOldContainer();
			createMenuHeading("Punches");
			setNewContainer(pageIndexForPunches);
			this.addComponent(bottomContainer);
			mainMidlet.showCurrentForm(this);
			punch = true;
			}

		} else if (btn.getName().equals(ApplicationConstants.BUTTON_NAME_KICKS)) {
			if(kicks!=true){
			showLoadingDialog();
			type = "rewards";
			setBtnStyle(kicks, btn, ImageConstants.BUTTON_KICKS_SELECTED);
			removeOldContainer();
			createMenuHeading("Kicks");
			setNewContainer(pageIndexForKicks);
			this.addComponent(bottomContainer);
			mainMidlet.showCurrentForm(this);
			kicks = true;
			}

		} else if (btn.getName().equals(
				ApplicationConstants.BUTTON_NAME_LOAD_MORE)) {
			showLoadingDialog();
			implementLoadMore();
			dismissLoadingDialog();
			mainMidlet.showCurrentForm(this);
			itemsList = null;
		}
	}

	private void implementLoadMore() {
		pageIndex++;
		query = "&page=" + pageIndex;
		if (is_Friends != true) {
			itemsList = DataModel.getActivityList(query, OlMeForm.this, type);
		} else {
			itemsList = DataModel.fetchFriendList(query, OlMeForm.this);
		}

		if (itemsList != null) {
			createList();
			bottomContainer.addComponent(pageIndex, itemsList);
			if (itemsList.size() < ApplicationConstants.PAGE_SIZE) {
				bottomContainer.removeComponent(loadMoreButton);
				loadMoreButton = null;
			}
		} else {
			pageIndex--;
		}
	}

	public void onBack() {
		try {
			if (mainMidlet.getMainCategoryBar().getVisibility()) {
				mainMidlet.destroyApp(true);
			}
		} catch (MIDletStateChangeException e) {
			e.printStackTrace();
		}
	}

	public void dealloc() {
		topContainer = null;
		bottomContainer = null;
		btnImgActivity = null;
		btnImgFriends = null;
		btnImgPunches = null;
		btnImgKicks = null;
		lblName = null;
		lblLocation = null;
		super.dealloc();
	}

	public void onListItemClick(Object obj) {

	}

	public void onPause() {

	}

	public void onCommandClick(Command cmd) {

	}

	public void loadData(boolean isRefreshed) {
		firstLoad();
	}

	public void addCommands() {

	}

}
