package com.tenpearls.olaround.ui.forms;


import javax.microedition.media.Player;
import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.VideoComponent;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.QRScanHelper;
import com.tenpearls.olaround.helpers.StringUtils;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.components.CustomDialogBox;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class QRScanWithChekInForm extends BaseForm {

	private Container contentContainer;

	public QRScanWithChekInForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}

	public void onCreate() {
		this.setScrollableY(false);
		this.getStyle().setBgColor(0xF0EBE6);
		contentContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		contentContainer.getStyle().setMargin(0, 0, 5, 0);
		createHelpArea();
		getQRImageContainer();
		getButtonsContainer();
		System.out.println("Login with Fb" + OlaroundMidlet.isLoginWithFB());
		System.out.println("Share on FB:" + OlaroundMidlet.isShareOnFb());
		if(OlaroundMidlet.isLoginWithFB()) {
			getBottomShareContainer();
		}
		this.addComponent(contentContainer);
		this.revalidate();
	}
	
	public void onResume() {
		mainMidlet.showMainCategoryBar(true);
	}
	
	private void createHelpArea() {
		CustomTextArea helpArea = new CustomTextArea(ApplicationConstants.MSG_OL_SCAN_HELP, true);
		helpArea.setCustomTextColor(ApplicationConstants.COLOR_FONT_DARK_GREY);
		helpArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		helpArea.setCustomMargin(10, 0, 0, 0);
		helpArea.setAlignment(CENTER);
		contentContainer.addComponent(helpArea);
		helpArea = null;
	}
	
	private void getQRImageContainer() {
		Container qrContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		qrContainer.getStyle().setBgColor(ApplicationConstants.COLOR_WHITE);
		qrContainer.getStyle().setBgTransparency(255);
		qrContainer.getStyle().setMargin(10, 0, 5, 10);
		qrContainer.getStyle().setBorder(Border.createLineBorder(1));
		qrContainer.setPreferredH(117);
		qrContainer.setPreferredW(216);
		
		Image qrImage = ImageHelper.getImageFromResources(ImageConstants.QR_CODE);
		Label qrLabel = new Label(qrImage);
		qrLabel.getStyle().setMargin(10, 20, 20, 20);
		qrLabel.getStyle().setAlignment(CENTER);
		qrContainer.addComponent(qrLabel);
		contentContainer.addComponent(qrContainer);
		qrContainer = null;
	}

	private void getButtonsContainer() {
		Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		buttonContainer.getStyle().setMargin(10, 0, 5, 0);
		CustomImageButton punchButton = new CustomImageButton(ImageConstants.BUTTON_GREY_LEFT, ApplicationConstants.BUTTON_NAME_PUNCH);
		punchButton.setText(ApplicationConstants.BUTTON_NAME_PUNCH);
		punchButton.setPreferredH(40);
		punchButton.setPreferredW(110);
		punchButton.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		punchButton.setCustomTextColor(ApplicationConstants.COLOR_WHITE);
		punchButton.setCustomMargin(0, 0, 0, 1);

		CustomImageButton chekInButton = new CustomImageButton(ImageConstants.BUTTON_GREY_RIGHT, ApplicationConstants.BUTTON_NAME_CHECKIN);
		chekInButton.setText(ApplicationConstants.BUTTON_NAME_CHECKIN);
		chekInButton.setPreferredH(40);
		chekInButton.setPreferredW(110);
		chekInButton.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		chekInButton.setCustomTextColor(ApplicationConstants.COLOR_WHITE);

		chekInButton.addActionListener(this);
		punchButton.addActionListener(this);
		buttonContainer.addComponent(punchButton);
		buttonContainer.addComponent(chekInButton);
		contentContainer.addComponent(buttonContainer);
		buttonContainer = null;
	}
	
	private void getBottomShareContainer() {
		Container shareContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		shareContainer.getStyle().setMargin(25, 0, 30, 0);
		CustomImageButton shareButton = null;
		if(OlaroundMidlet.isShareOnFb()) {
			shareButton = new CustomImageButton(ImageConstants.BUTTON_SHARE_ON_FB_CHECKED, ApplicationConstants.BUTTON_NAME_SHARE_ON_FB);
		} else {
			shareButton = new CustomImageButton(ImageConstants.BUTTON_SHARE_ON_FB_NORMAL, ApplicationConstants.BUTTON_NAME_SHARE_ON_FB);
		}
		shareButton.setPreferredH(43);
		shareButton.setPreferredW(45);
		shareButton.setCustomMargin(0, 0, 0, 0);
		shareButton.setActionListener(this);
		
		Label shareLabel = new Label(ApplicationConstants.LABEL_TEXT_SHARE_ON_FB);
		shareLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		shareLabel.getStyle().setFgColor(0x474747);
		
		shareContainer.addComponent(shareButton);
		shareContainer.addComponent(shareLabel);
		contentContainer.addComponent(shareContainer);
		shareContainer = null;
		shareButton = null;
		shareLabel = null;
	}

	public void onButtonClick(Button button) {
		if(button.getName().equals(ApplicationConstants.BUTTON_NAME_PUNCH)) {
			openCamera();
		} else if(button.getName().equals(ApplicationConstants.BUTTON_NAME_CHECKIN)) {
			mainMidlet.showCheckinListingForm(QRScanWithChekInForm.this, false);
		} else if(button.getName().equals(ApplicationConstants.BUTTON_NAME_SHARE_ON_FB)) {
			if(OlaroundMidlet.isShareOnFb() == false) {
				OlaroundMidlet.setShareOnFb(true);
				mainMidlet.refreshCurrentForm(QRScanWithChekInForm.this);
			} else {
				OlaroundMidlet.setShareOnFb(false);
				mainMidlet.refreshCurrentForm(QRScanWithChekInForm.this);
			}
		}
	}

	public void openCamera() {
		mainMidlet.showMainCategoryBar(false);
		Form cameraForm = new Form(ApplicationConstants.TITLE_QR_SCAN_FORM);
		cameraForm.getStyle().setBgColor(0xF0EBE6);
		
		final QRScanHelper qrHelper = new QRScanHelper(QRScanWithChekInForm.this);
		final VideoComponent videoComponent = qrHelper.createVideoComponent();
		videoComponent.setPreferredW(240);
    	videoComponent.setPreferredH(300);
		
		CustomImageButton captureButton = new CustomImageButton(ImageConstants.BUTTON_ORANGE_NORMAL,
				ImageConstants.BUTTON_ORANGE_SELECTED, ApplicationConstants.BUTTON_CAPTURE);
		captureButton.setText(ApplicationConstants.BUTTON_CAPTURE);
		captureButton.setPreferredH(40);
		captureButton.setPreferredW(240);
		
		Command bckCmd = new Command("Back") {

            public void actionPerformed(ActionEvent evt) {
            	mainMidlet.showCurrentForm(QRScanWithChekInForm.this);
            	Player player = (Player) videoComponent.getNativePeer();
            	player.close();
            	player = null;
            }
   
        };
				
		cameraForm.setScrollable(false);
		cameraForm.addComponent(videoComponent);
		cameraForm.addComponent(captureButton);
		cameraForm.setBackCommand(bckCmd);
		cameraForm.addCommand(bckCmd);
		cameraForm.show();
		
    	captureButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				qrHelper.capture();
				showLoadingDialog();
			}
		});
	}
	
	public void showScanResult(String qrCode) {
		if(StringUtils.isContains(qrCode, ApplicationConstants.QR_CODE_PREFIX)) {
			qrCode = qrCode.substring(qrCode.lastIndexOf('/')+1);
			DataModel.getPunch(qrCode, QRScanWithChekInForm.this);
		} else {
			if(qrCode.equals(ApplicationConstants.MSG_QR_ERROR)) {
				this.show();
			} else {
				CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, ApplicationConstants.MSG_INVALID_QR_CODE, QRScanWithChekInForm.this);
			}
		}
	}

	public void onCommandClick(Command cmd) {

	}
	
	public void onPause() {

	}

	public void loadData(boolean isRefreshed) {
	
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
	
	public void dealloc() {
		super.dealloc();
		contentContainer = null;
	}

}
