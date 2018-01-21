package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.entities.Category;
import com.tenpearls.olaround.entities.Checkins;
import com.tenpearls.olaround.entities.VenueEntity;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class VenueDetailForm extends BaseForm {

	VenueEntity entity;

	public VenueDetailForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}

	private Container topContainer;
	private Container midContainer;
	private Container bottomContainer;

	public void onCreate() {

		this.setScrollable(false);
		this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		this.getStyle().setBgColor(0xF3EEE9);
		this.entity = DataModel.getVenueEntity();
		System.out.println(entity.getName());
		//setBannerImage(entity.getBrand().getBackgroundPictureURL());
		setTopContainer();
		this.addComponent(topContainer);
		setMidContainer();
		this.addComponent(midContainer);
		setBottomContainer();
		this.addComponent(bottomContainer);

	}

	private void setTopContainer() {
		topContainer = new Container(new BorderLayout());
		topContainer.getStyle().setMargin(0, 0, 3, 3);
		topContainer.getStyle().setPadding(5, 8, 5, 5);
		topContainer.getStyle().setBgTransparency(255);
		Image imgContainer = ImageHelper
				.getImageFromResources(ImageConstants.BG_LIST);
		if (imgContainer != null) {
			topContainer.getStyle().setBgImage(imgContainer);
			topContainer.getStyle().setBackgroundType(
					Style.BACKGROUND_IMAGE_SCALED);
		}
		creatTopIcons();
		creatTopText();
		imgContainer = null;
	}

	private void creatTopIcons() {
		CustomImageButton btnImgLocation;
		Label lblimgBrand = new Label();
		lblimgBrand.getStyle().setMargin(0, 0, 0, 0);
		Image imgBrand = null;
		try {
			imgBrand = ImageHelper.getImageFromWeb(entity.getBrand()
					.getPictureURL());
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (imgBrand == null) {
			imgBrand = ImageHelper
					.getImageFromResources(ImageConstants.DEFAULT_BRAND_IMAGE);
		}
		Image imgBrandScaled = imgBrand.scaledSmallerRatio(38, 38);
		lblimgBrand.setIcon(imgBrandScaled);
		btnImgLocation = new CustomImageButton(ImageConstants.LOCATION_ICON,
				ApplicationConstants.BUTTON_NAME_ADDRESS);
		btnImgLocation.setPreferredW(43);
		btnImgLocation.setPreferredH(43);
		btnImgLocation.addActionListener(this);
		topContainer.addComponent(BorderLayout.EAST, btnImgLocation);
		topContainer.addComponent(BorderLayout.WEST, lblimgBrand);
	}

	private void creatTopText() {

		Container topCon1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		topCon1.getStyle().setBgTransparency(0);
		Label lblBrand = new Label(entity.getName());
		Category cat_name = (Category) entity.getCategories().elementAt(0);
		String Cate_name = cat_name.getName();
		Label lblLocation = new Label(Cate_name + " "
				+ entity.getDistance().substring(0, 4) + "km");
		lblBrand.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_LARGE));
		lblBrand.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		lblBrand.getStyle().setMargin(0, 0, 7, 0);
		lblBrand.getStyle().setPadding(0, 0, 0, 0);
		lblLocation.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_SMALL));
		lblLocation.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		lblLocation.getStyle().setMargin(3, 0, 7, 0);
		lblLocation.getStyle().setPadding(0, 0, 0, 0);
		topCon1.addComponent(lblBrand);
		topCon1.addComponent(lblLocation);
		topContainer.addComponent(BorderLayout.CENTER, topCon1);
	}

	private void setMidContainer() {
		midContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		midContainer.getStyle().setMargin(9, 0, 13, 13);
		midContainer.getStyle().setPadding(0, 0, 0, 0);
		
		CustomImageButton chekInButton = new CustomImageButton(ImageConstants.BUTTON_GREY_LEFT, ApplicationConstants.BUTTON_NAME_CHECKIN);
		chekInButton.setText(ApplicationConstants.BUTTON_NAME_CHECKIN);
		chekInButton.setPreferredH(40);
		chekInButton.setPreferredW(107);
		chekInButton.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		chekInButton.setCustomTextColor(ApplicationConstants.COLOR_WHITE);
		chekInButton.setCustomMargin(0, 0, 0, 1);
		
		CustomImageButton punchButton = new CustomImageButton(ImageConstants.BUTTON_GREY_RIGHT, ApplicationConstants.BUTTON_NAME_PUNCH);
		punchButton.setText(ApplicationConstants.BUTTON_NAME_PUNCH + " " +"QR");
		punchButton.setPreferredH(40);
		punchButton.setPreferredW(107);
		punchButton.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		punchButton.setCustomTextColor(ApplicationConstants.COLOR_WHITE);

		chekInButton.addActionListener(this);
		punchButton.addActionListener(this);
		midContainer.addComponent(chekInButton);
		midContainer.addComponent(punchButton);
	}

	private void setBottomContainer() {
		bottomContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		bottomContainer.getStyle().setMargin(10, 0, 3, 3);
		bottomContainer.getStyle().setPadding(0, 0, 3, 0);
		bottomContainer.getStyle().setBgTransparency(255);
		bottomContainer.getStyle().setBgColor(ApplicationConstants.COLOR_WHITE);
		bottomContainer.getStyle().setBorder(Border.createRoundBorder(10, 4));
		bottomContainer.getStyle().setBackgroundType(
				Style.BACKGROUND_IMAGE_SCALED);
		creatBottomIcons();
		creatBottomText();
	}

	private void creatBottomIcons() {
		Container bottomCon1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
		bottomCon1.getStyle().setMargin(0, 0, 0, 0);
		bottomCon1.getStyle().setBgTransparency(0);
		bottomCon1.getStyle().setPadding(0, 0, 4, 0);
		Label lblimgpic1 = new Label();
		Label lblimgpic2 = new Label();
		Label lblimgpic3 = new Label();
		lblimgpic1.getStyle().setMargin(6, 0, 0, 0);
		lblimgpic2.getStyle().setMargin(6, 0, 7, 0);
		lblimgpic3.getStyle().setMargin(6, 0, 7, 0);
		Image imgpic1 = null;
		Image imgpic2 = null;
		Image imgpic3 = null;
		try {
			Checkins checkin1 = (Checkins) entity.getFriendscheckins()
					.getCheckins().elementAt(0);
			imgpic1 = ImageHelper.getImageFromWeb(checkin1.getDisplayPicUrl());
			Checkins checkin2 = (Checkins) entity.getFriendscheckins()
					.getCheckins().elementAt(1);
			imgpic2 = ImageHelper.getImageFromWeb(checkin2.getDisplayPicUrl());
			Checkins checkin3 = (Checkins) entity.getFriendscheckins()
					.getCheckins().elementAt(2);
			imgpic3 = ImageHelper.getImageFromWeb(checkin3.getDisplayPicUrl());

			// Hardcode
			// String umair =
			// "http://m3.licdn.com/mpr/pub/image-RzhcjmH9-uKLnsEJ_wI2OUJfSdQ_tH0opiD3PmHySNUrbJxMRzh3Bvg9SCZrb-ponTlO/umair-abbas-hayat-k102131.jpg";
			// imgpic1 = ImageHelper.getImageFromWeb(umair);
			// imgpic2 = ImageHelper.getImageFromWeb(umair);
			// imgpic3 = ImageHelper.getImageFromWeb(umair);

		} catch (Exception e) {
			// e.printStackTrace();
		}

		if (imgpic1 == null) {
			imgpic1 = ImageHelper
					.getImageFromResources(ImageConstants.DEFAULT_FREINDS_IMAGE);
		}
		if (imgpic2 == null) {
			imgpic2 = ImageHelper
					.getImageFromResources(ImageConstants.DEFAULT_FREINDS_IMAGE);
		}
		if (imgpic3 == null) {
			imgpic3 = ImageHelper
					.getImageFromResources(ImageConstants.DEFAULT_FREINDS_IMAGE);
		}
		Image imgpic1Scaled = imgpic1.scaledSmallerRatio(38, 38);
		lblimgpic1.setIcon(imgpic1Scaled);
		Image imgpic2Scaled = imgpic2.scaledSmallerRatio(38, 38);
		lblimgpic2.setIcon(imgpic2Scaled);
		Image imgpic3Scaled = imgpic3.scaledSmallerRatio(38, 38);
		lblimgpic3.setIcon(imgpic3Scaled);
		bottomCon1.addComponent(lblimgpic1);
		bottomCon1.addComponent(lblimgpic2);
		bottomCon1.addComponent(lblimgpic3);
		bottomContainer.addComponent(bottomCon1);
		imgpic1 = null;
		imgpic2 = null;
		imgpic3 = null;

	}

	private void creatBottomText() {
		Container bottomCon2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		bottomCon2.getStyle().setMargin(0, 5, 7, 0);
		bottomCon2.getStyle().setBgTransparency(0);
		String strFriend = new String("None of your");
		String strFriend1 = "";
		String strFriend2 = "";
		String strFriend3 = "";
		String strFriendFinal = "";
		Checkins checkinName1 = null;
		Checkins checkinName2 = null;
		Checkins checkinName3 = null;
		int count = 0;
		
		try {
			checkinName1 = (Checkins) entity.getFriendscheckins().getCheckins().elementAt(0);
			strFriend1 = new String(checkinName1.getFirstName());
			count++;
			checkinName2 = (Checkins) entity.getFriendscheckins().getCheckins().elementAt(1);
			strFriend2 = (", " + checkinName2.getFirstName());
			count++;
			checkinName3 = (Checkins) entity.getFriendscheckins().getCheckins().elementAt(2);
			strFriend3 = (", " + checkinName3.getFirstName());
			count++;
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (count>0){
			int Num = (entity.getFriendscheckins().getCount() - count);
			strFriendFinal = (" & " + Integer.toString(Num) + ApplicationConstants.LABEL_OTHER_FRIENDS);
			strFriend = (strFriend1+strFriend2+strFriend3 + strFriendFinal);
		}
		else{
			strFriend = (ApplicationConstants.LABEL_NONE_OF_YOUR);
		}

		CustomTextArea lblFriend = new CustomTextArea(strFriend, true);
		
		lblFriend.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		lblFriend.setCustomTextColor(0x474747);
		lblFriend.setCustomMargin(6, 0, 0, 0);
		
		
		Container checiknsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		
		Image checkInImage = ImageHelper.getImageFromResources(ImageConstants.CHECK_IN_ICON);
		Label checkInImageLabel = new Label();
		checkInImageLabel.setIcon(checkInImage);
		checkInImageLabel.getStyle().setMargin(2, 0, 0, 0);
		checkInImageLabel.getStyle().setPadding(0, 0, 0, 0);

		Label lblCheckin = new Label(entity.getPopularity().getCheckinCount() + " check-ins here");
		lblCheckin.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		lblCheckin.getStyle().setFgColor(0x527799);
		lblCheckin.getStyle().setMargin(2, 0, 3, 0);
		lblCheckin.getStyle().setPadding(0, 0, 0, 0);
		
		checiknsContainer.addComponent(checkInImageLabel);
		checiknsContainer.addComponent(lblCheckin);
	
		bottomCon2.addComponent(lblFriend);
		bottomCon2.addComponent(checiknsContainer);
		bottomContainer.addComponent(bottomCon2);
		bottomCon2 = null;
		checkInImage = null;
		checkInImageLabel = null;
	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(true);
		mainMidlet.getVenueCategoryBar().setSelectedIndex(0);
	}

	public void onPause() {
	}

	public void onButtonClick(Button btn) {
		if (btn.getName().equals(ApplicationConstants.BUTTON_NAME_ADDRESS)) {
			mainMidlet.showAddressDetailForm(VenueDetailForm.this, false);
		}
		if (btn.getName().equals(ApplicationConstants.BUTTON_NAME_PUNCH)) {
			mainMidlet.showQRScanForm(VenueDetailForm.this, false);
		}
		if (btn.getName().equals(ApplicationConstants.BUTTON_NAME_CHECKIN)) {
			DataModel.getCheckin(this);
			//mainMidlet.showCheckinListingForm(VenueDetailForm.this, false);
		}
	}

	public void onCommandClick(Command cmd) {
	}

	public void loadData(boolean isRefreshed) {
	}

	public void addCommands() {
	}

	public void onBack() {
		mainMidlet.destoryVenueDetailCategoryForms();
		mainMidlet.showVenuListingForm(VenueDetailForm.this, true, null, "");
	}
	
	public void dealloc() {
		super.dealloc();
		topContainer = null;
		midContainer = null;
		bottomContainer = null;
	}

}
