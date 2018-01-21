package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.entities.Category;
import com.tenpearls.olaround.entities.VenueEntity;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.Map;
import com.tenpearls.olaround.helpers.Utils;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class AddressDetailForm extends BaseForm {

	private VenueEntity entity;
	private Container midContainer;
	private Container botContainer;

	public AddressDetailForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}

	public void onCreate() {
		this.getStyle().setBgColor(0xF3EEE9);
		this.entity = DataModel.getVenueEntity();
		setmidContainer();
		setBotContainer();	
		showMap();
		this.addComponent(midContainer);
		this.addComponent(botContainer);
	}

	protected void onShow() {
		super.onShow();
	}

	private void showMap() {
		this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		javax.microedition.lcdui.Display display = javax.microedition.lcdui.Display
				.getDisplay(OlaroundMidlet.getInstance());
		double longitute = Double.parseDouble(entity.getLocation()
				.getLongitude());
		double lattitude = Double.parseDouble(entity.getLocation()
				.getLattitude());
		Component componentMap = new Map(display, ApplicationConstants.APP_ID,
				ApplicationConstants.APP_TOKEN, longitute, lattitude);
		componentMap.setPreferredH(ApplicationConstants.MAP_HEIGHT);
		componentMap.setPreferredW(getWidth());
		componentMap.getStyle().setBgColor(0xF3EEE9);
		componentMap.getStyle().setMargin(0, 0, 0, 0);
		componentMap.getStyle().setBgTransparency(255);
		componentMap.getSelectedStyle().setBgTransparency(255);
		componentMap.getSelectedStyle().setBgColor(0xF3EEE9);
		componentMap.getSelectedStyle().setMargin(0, 0, 0, 0);
		Dimension d =  new Dimension(getWidth(), ApplicationConstants.MAP_HEIGHT);
		componentMap.setSize(d);
		this.addComponent(componentMap);
		componentMap = null;
		createdivider();
	}

/*0*/

	private void setmidContainer() {
		midContainer = new Container(new BorderLayout());
		midContainer.getStyle().setMargin(3, 0, 3, 3);
		midContainer.getStyle().setPadding(5, 7, 5, 5);
		midContainer.getStyle().setBgTransparency(255);
		Image imgContainer = null;
		imgContainer = ImageHelper
				.getImageFromResources(ImageConstants.INFO_CONTAINER_LARGE);
		if (imgContainer != null) {
			midContainer.getStyle().setBgImage(imgContainer);
			midContainer.getStyle().setBackgroundType(
					Style.BACKGROUND_IMAGE_SCALED);
		}
		creatMidIcons();
		creatMidText();
		creatBottomText();
		imgContainer = null;
	}

	private void creatMidIcons() {
		Label lblimgBrand = new Label();
		lblimgBrand.getStyle().setMargin(0, 0, 0, 0);
		CustomImageButton btnImgCall;
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
		btnImgCall = new CustomImageButton(ImageConstants.CALL_ICON,
				ApplicationConstants.BUTTON_NAME_CALL);
		btnImgCall.setPreferredW(43);
		btnImgCall.setPreferredH(43);
		btnImgCall.addActionListener(this);
		midContainer.addComponent(BorderLayout.EAST, btnImgCall);
		midContainer.addComponent(BorderLayout.WEST, lblimgBrand);
	}

	private void creatMidText() {
		Container MidCon1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		MidCon1.getStyle().setBgTransparency(0);
		MidCon1.getStyle().setBgColor(ApplicationConstants.COLOR_WHITE);
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
		lblLocation.getStyle().setMargin(3, 0, 7, 0);
		lblLocation.getStyle().setPadding(0, 0, 0, 0);
		lblLocation.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		MidCon1.addComponent(lblBrand);
		MidCon1.addComponent(lblLocation);
		midContainer.addComponent(BorderLayout.CENTER, MidCon1);
	}

	private void creatBottomText() {
		Container BotCon = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Font extra_small_font = Utils
				.getFontFromResources(ApplicationConstants.FONT_EXTRA_SMALL);
		String strAddress = new String(entity.getContact().getAddress() + ", "
				+ entity.getContact().getCity());
		CustomTextArea taAddress = new CustomTextArea(strAddress, true);
		taAddress.getStyle().setFgColor(ApplicationConstants.COLOR_FONT_LIGHT_GREY);
		taAddress.getStyle().setFont(extra_small_font);
		taAddress.getStyle().setMargin(0, 0, 52, 0);
		taAddress.getStyle().setPadding(2, 2, 0, 0);
		BotCon.addComponent(taAddress);
		midContainer.addComponent(BorderLayout.SOUTH, BotCon);
	}

	private void setBotContainer() {

		botContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		botContainer.getStyle().setBgColor(ApplicationConstants.COLOR_WHITE);
		botContainer.getStyle().setBorder(Border.createRoundBorder(10, 4));
		botContainer.getStyle()
				.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
		botContainer.getStyle().setMargin(8, 0, 3, 3);
		botContainer.getStyle().setPadding(0, 0, 0, 0);
		botContainer.getStyle().setBgTransparency(255);
		Container botCon1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
		botCon1.getStyle().setBgTransparency(0);
		botCon1.getStyle().setBorder(Border.createInsetBorder(1));
		botCon1.getStyle().setMargin(0, 0, 0, 0);
		botCon1.getStyle().setPadding(3, 3, 5, 0);
		Container botCon2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
		botCon2.getStyle().setBorder(Border.createInsetBorder(1));
		botCon2.getStyle().setMargin(0, 0, 0, 0);
		botCon2.getStyle().setPadding(3, 3, 5, 0);
		botCon2.getStyle().setBgTransparency(0);
		Label lblOperating = new Label(entity.getOperatingHours());
		Label lblFacility = null;
		Label lblOperating_name = new Label("Timings:     ");
		lblOperating_name.getStyle().setFgColor(
				ApplicationConstants.COLOR_BLACK);
		lblOperating_name.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_SMALL));
		botCon1.addComponent(lblOperating_name);
		Label lblFacility_name = new Label("Facilities:    ");
		lblFacility_name.getStyle()
				.setFgColor(ApplicationConstants.COLOR_BLACK);
		lblFacility_name.getStyle().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
						Font.SIZE_SMALL));
		botCon2.addComponent(lblFacility_name);
		int dine = entity.getDineIn();
		int drive = entity.getDriveThrough();
		int wifi = entity.getWifi();
		int home = entity.getHomeDelivery();
		int smoke = entity.getSmokingArea();
		String strDine = "";
		String strDrive = "";
		String strWifi = "";
		String strHome = "";
		String strSmoke = "";
		if (dine == 1) {
			strDine = ApplicationConstants.MSG_DINE_IN;
		}
		if (drive == 1) {
			strDrive = ApplicationConstants.MSG_DRIVE_THRU;
		}
		if (wifi == 1) {
			strWifi = ApplicationConstants.MSG_WIFI;
		}
		if (home == 1) {
			strHome = ApplicationConstants.MSG_HOME_DEL;
		}
		if (smoke == 1) {
			strSmoke = ApplicationConstants.MSG_SMOKING;
		}
		Font extra_small_font = Utils
				.getFontFromResources(ApplicationConstants.FONT_EXTRA_SMALL);
		lblOperating.getStyle().setFgColor(0x8A8A8A);
		lblOperating.getStyle().setFont(extra_small_font);
		botCon1.addComponent(lblOperating);
		String strSub = new String(strDine + strDrive + strWifi + strHome
				+ strSmoke);
		String strFacility = "";
		if (strSub.length() > 2) {
			strFacility = strSub.substring(0, strSub.length() - 2);
		}
		lblFacility = new Label(strFacility);
		lblFacility.getStyle().setFont(extra_small_font);
		lblFacility.getStyle().setFgColor(0x8A8A8A);
		botCon2.addComponent(lblFacility);
		botContainer.addComponent(botCon1);
		botContainer.addComponent(botCon2);
	}

	public void onResume() {
		mainMidlet.showMainCategoryBar(false);
		mainMidlet.showVenueDetailCategoryBar(false);
	}

	public void onPause() {
	}

	public void onButtonClick(Button btn) {
		if (btn.getName().equals(ApplicationConstants.BUTTON_NAME_CALL)) {
			Display display = Display.getInstance();
			try {
				String telNo = entity.getContact().getPhone();
				if (telNo == null) {
					validationPopUp(ApplicationConstants.DIALOG_NUMBER_NOT_AVALIABLE);
				}
				else{
				display.execute("tel:" + telNo);
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	public void onCommandClick(Command cmd) {
	}

	public void loadData(boolean isRefreshed) {
	}

	public void addCommands() {
	}

	public void onBack() {
		mainMidlet.showVenueDetailForm(AddressDetailForm.this, true);
	}
}
