package com.tenpearls.olaround.ui.forms;

import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.helpers.ImageHelper;

public class Splash extends Form {

	public Splash() {
		this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));	
		Image img = ImageHelper.getImageFromResources(ImageConstants.SPLASH);
		if(img != null) {
			this.getStyle().setBgImage(img);
			this.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			img = null;
		}
	}
}
