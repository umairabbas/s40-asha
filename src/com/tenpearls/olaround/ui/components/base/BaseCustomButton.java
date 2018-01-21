package com.tenpearls.olaround.ui.components.base;

import com.sun.lwuit.Button;
import com.sun.lwuit.Image;
import com.sun.lwuit.events.ActionListener;
import com.tenpearls.olaround.helpers.ImageHelper;

public abstract class BaseCustomButton extends Button {

	
	public BaseCustomButton() {
		super();
	}
	
	public void setActionListener(ActionListener listener) {
		addActionListener(listener);
	}
	
	//I have used the word custom for methods so as to avoid confusion with the original methods of button
	
	public void setCustomSize(int width, int height) {
		setPreferredW(width);
		setPreferredH(height);
		setWidth(width);
		setHeight(height);
	}
	
	protected void setCustomIcon(String imageName) {
		Image icon = ImageHelper.getImageFromResources(imageName);
		if(icon != null) {
			setIcon(icon);
		} 
	}
	
	protected void setCustomTransparency(int transparency) {
		getStyle().setBgTransparency(transparency);
		getUnselectedStyle().setBgTransparency(transparency);
		getSelectedStyle().setBgTransparency(transparency);
	}
	
	protected void setCustomPaddingAndMargin() {

		getStyle().setPadding(0,0,0,0);
		getSelectedStyle().setPadding(0,0,0,0);
		getDisabledStyle().setPadding(0,0,0,0);
		getPressedStyle().setPadding(0,0,0,0);
	
		getStyle().setMargin(0,0,0,0);
		getSelectedStyle().setMargin(0,0,0,0);
		getDisabledStyle().setMargin(0,0,0,0);
		getPressedStyle().setMargin(0,0,0,0);
	}
	
	protected abstract void setCustomBorder();
	
	protected abstract void setCustomBgImage(String imageName);
	
	protected abstract void setCustomBgImage(String normalImageName, String selectedImageName);
	
	protected abstract void setCustomBgImage(Image image);
	
	
	
}
