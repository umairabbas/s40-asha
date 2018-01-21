package com.tenpearls.olaround.helpers;

import java.io.IOException;

import com.sun.lwuit.Display;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.tenpearls.app.facebook.api.io.NetworkManager;
import com.tenpearls.app.facebook.api.io.services.facebook.FaceBookAccess;
import com.tenpearls.app.facebook.api.io.services.facebook.User;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.model.DataModel;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class FacebookHelper implements Runnable {

	static FacebookHelper instance;
	static BaseForm currentForm = null;
	
	public FacebookHelper() {
		instance = this;
	}
	
	public static FacebookHelper getInstance(){
		if(instance == null){
			instance = new FacebookHelper();
		}
		return instance;
	}
	

	public static void connectWithFacebook(BaseForm form) {
		currentForm = form;
		NetworkManager.getInstance().start();
	    Display.getInstance().callSerially(FacebookHelper.getInstance()); 
	}
	
    public void run() {
    	final BaseForm form = currentForm;
        try {
			FaceBookAccess.getInstance().authenticate(ApplicationConstants.FACEBOOK_APP_ID, ApplicationConstants.APP_URL, ApplicationConstants.FACEBOOK_PERMISSIONS);
			form.showLoadingDialog();
			final User user = new User();
			FaceBookAccess.getInstance().getUser("me", user, new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                	System.out.println("User id: " + user.getId());
            	    DataModel.loginWithFacebook(user.getId(), getAccessToken(), form); 
                }
            });

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
 
        currentForm = null;
    }
    
   public static String getAccessToken() {
    	return FaceBookAccess.getToken();
   }
}
