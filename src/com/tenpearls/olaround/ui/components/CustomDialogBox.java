package com.tenpearls.olaround.ui.components;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

import com.sun.lwuit.Button;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Font;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.plaf.Border;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class CustomDialogBox {
	
	public static void showInfoAlert(String title, String message) {
	     Alert alert = new Alert(title, message, null, AlertType.INFO);
	     alert.setString(message);
	     Display.getDisplay(OlaroundMidlet.getInstance()).setCurrent(alert);
	     alert = null; 
	}
	
	public static void showDialog(String title, String text, final BaseForm currentForm){

		Alert alert = new Alert(title, text, null, null);
		alert.addCommand(new Command (ApplicationConstants.DIALOG_BUTTON_OK, Command.OK, 1));
		alert.setTimeout(Alert.FOREVER);
		Display.getDisplay(OlaroundMidlet.getInstance()).setCurrent(alert,new Form(""));

		alert.setCommandListener(new CommandListener() {

			public void commandAction(javax.microedition.lcdui.Command command, Displayable displayable) {
				OlaroundMidlet.getInstance().showCurrentForm(currentForm);
			}
		});
		alert = null;
	}
	
    public void showConfirmationAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(title, message, null, type);
        alert.setString(message);
        
        alert.setCommandListener(new CommandListener() {
			
			public void commandAction(Command arg0, Displayable arg1) {
				
			}
		});
        
        Display.getDisplay(OlaroundMidlet.getInstance()).setCurrent(alert);
        
    }
	
	public static void showDialogForClaimDeal(String text, final BaseForm currentForm){

		Alert alert = new Alert(ApplicationConstants.DIALOG_TITLE_FOR_CLAIM_DEAL, text, null, null);
		alert.addCommand(new Command (ApplicationConstants.DIALOG_BUTTON_CLAIM_LATER, Command.OK, 1));
		alert.addCommand(new Command (ApplicationConstants.DIALOG_BUTTON_CLAIM_NOW, Command.OK, 0));

		alert.setTimeout(Alert.FOREVER);
		javax.microedition.lcdui.Display.getDisplay(OlaroundMidlet.getInstance()).setCurrent(alert);

		alert.setCommandListener(new CommandListener() {

			public void commandAction(javax.microedition.lcdui.Command command, Displayable displayable) {
				if(command.getLabel().equals(ApplicationConstants.DIALOG_BUTTON_CLAIM_NOW)) {
					System.out.println(ApplicationConstants.DIALOG_BUTTON_CLAIM_NOW);
					OlaroundMidlet.getInstance().showClaimDealInfoForm(currentForm, false);
				} else {
					System.out.println(ApplicationConstants.DIALOG_BUTTON_CLAIM_LATER);
					OlaroundMidlet.getInstance().showCurrentForm(currentForm);

				}
			}
		});
		alert = null;
	}
	
	public static void showDialogForLogout(String text, final BaseForm currentForm){

		Alert alert = new Alert(ApplicationConstants.DIALOG_TITLE_FOR_SIGN_OUT, text, null, null);
		alert.addCommand(new Command (ApplicationConstants.DIALOG_BUTTON_YES, Command.OK, 1));
		alert.addCommand(new Command (ApplicationConstants.DIALOG_BUTTON_NO, Command.OK, 0));
		alert.setTimeout(Alert.FOREVER);
		javax.microedition.lcdui.Display.getDisplay(OlaroundMidlet.getInstance()).setCurrent(alert);
		alert.setCommandListener(new CommandListener() {

			public void commandAction(javax.microedition.lcdui.Command command, Displayable displayable) {
				if(command.getLabel().equals(ApplicationConstants.DIALOG_BUTTON_YES)) {
					DataModel.logoutWithOlaround(currentForm);
				} else {
					System.out.println(ApplicationConstants.DIALOG_BUTTON_NO);
					OlaroundMidlet.getInstance().showCurrentForm(currentForm);
				}
			}
		});
		alert = null;
	}
	
	public static void showDialogForShare(String text, final BaseForm currentForm){

		Alert alert = new Alert(ApplicationConstants.DIALOG_TITLE_FOR_SHARE, text, null, null);
		alert.addCommand(new Command (ApplicationConstants.DIALOG_BUTTON_YES, Command.OK, 1));
		alert.addCommand(new Command (ApplicationConstants.DIALOG_BUTTON_NO, Command.OK, 0));
		alert.setTimeout(Alert.FOREVER);
		javax.microedition.lcdui.Display.getDisplay(OlaroundMidlet.getInstance()).setCurrent(alert);
		alert.setCommandListener(new CommandListener() {

			public void commandAction(javax.microedition.lcdui.Command command, Displayable displayable) {
				if(command.getLabel().equals(ApplicationConstants.DIALOG_BUTTON_YES)) {
					DataModel.shareOnFb(currentForm);
				} else {
					System.out.println(ApplicationConstants.DIALOG_BUTTON_NO);
					OlaroundMidlet.getInstance().showCurrentForm(currentForm);
				}
			}
		});
		alert = null;
	}
	
	
	public static void showDialog(String title, String text, String OkText){
		
		final Dialog dialog = new Dialog(title);
       
        Button okButton = new Button(OkText);
        okButton.setPreferredW(200);
        okButton.getStyle().setMargin(14, 0, 2, 2);
        okButton.getPressedStyle().setMargin(14, 0, 2, 2);
        okButton.getSelectedStyle().setMargin(14, 0, 2, 2);
        okButton.getUnselectedStyle().setMargin(14, 0, 2, 2);
        okButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				dialog.dispose();
			}
		});
      
        dialog.setScrollable(false);
        dialog.addComponent(setMessage(text));
        dialog.addComponent(okButton);
        dialog.removeAllCommands();
        if(OlaroundMidlet.getInstance().getMainCategoryBar().getVisibility()) {
        	dialog.show(50, 50, 10, 10, false);
        } else {
        	dialog.show(70, 70, 10, 10, false);
        }

    }

    private static TextArea setMessage(String text) {
    	
      	TextArea message = new TextArea(text, 18000);
        message.setRows(3);
        message.setScrollVisible(false);
        message.setColumns(30);
        message.setEditable(false);
        message.getPressedStyle().setBgTransparency(0);
        message.getSelectedStyle().setBgTransparency(0);
        message.getUnselectedStyle().setBgTransparency(0);

        message.getStyle().setBgColor(0x3B3B3B);
        message.getPressedStyle().setBgColor(0x3B3B3B);
        message.getSelectedStyle().setBgColor(0x3B3B3B);
        message.getUnselectedStyle().setBgColor(0x3B3B3B);
        
        
        message.getStyle().setMargin(5,5,0,0);
        message.getSelectedStyle().setMargin(5,5,0,0);
        message.getStyle().setFgColor(0xFFFFFF);
        message.getSelectedStyle().setFgColor(0xFFFFFF);
        message.getPressedStyle().setFgColor(0xFFFFFF);
        message.getUnselectedStyle().setFgColor(0xFFFFFF);
        //message.setText(text);
        message.getStyle().setBorder(Border.createEmpty());
        message.getPressedStyle().setBorder(Border.createEmpty());
        message.getSelectedStyle().setBorder(Border.createEmpty());
        message.getUnselectedStyle().setBorder(Border.createEmpty());

        message.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        message.getSelectedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        message.getPressedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        message.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        return message;

   }

	
}


