package com.tenpearls.olaround.ui.components;

import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.ui.components.base.BaseCustomButton;

public class CustomImageButton extends BaseCustomButton {	
	
	public CustomImageButton(String btnName) {
		super();
		setName(btnName);
		setCustomTransparency(45);
		setCustomBorder();
		setCustomPaddingAndMargin();
	}
	
	public CustomImageButton(String imageName,String btnName) {
		this(btnName);
		setCustomBgImage(imageName);
	}
	
	public CustomImageButton(String normalImageName, String selectedImageName, String btnName) {
		this(btnName);
		setCustomBgImage(normalImageName, selectedImageName);
	}


	//I have used the word custom for methods so as to avoid confusion with the original methods of button
	
	public void setCustomBgImage(Image image) {
		if(image != null) {
			getStyle().setBgImage(image);
			getSelectedStyle().setBgImage(image);
			getPressedStyle().setBgImage(image);

			image = null;
		}	
	}
	
	protected void setCustomBgImage(String bgImageName) {
		Image normalImage = ImageHelper.getImageFromResources(bgImageName);
		if(normalImage != null) {
			getStyle().setBgImage(normalImage);
			getSelectedStyle().setBgImage(normalImage);
			getPressedStyle().setBgImage(normalImage);
			getUnselectedStyle().setBgImage(normalImage);
			getDisabledStyle().setBgImage(normalImage);
			
			getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			getSelectedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			getPressedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			getUnselectedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			getDisabledStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			
			normalImage = null;
		}	
	}
	
	public void setCustomDisabledBgImage(String imageName) {
		Image image = ImageHelper.getImageFromResources(imageName);
		if(image != null) {
			getDisabledStyle().setBgImage(image);
			image = null;
		}
	}
	
	protected void setCustomBorder(){
		getStyle().setBorder(Border.createEmpty(), true);
		getSelectedStyle().setBorder(Border.createEmpty(), true);
		getPressedStyle().setBorder(Border.createEmpty(), true);
		getDisabledStyle().setBorder(Border.createEmpty(), true);
	}
	
	public void setCustomMargin(int top, int bottom, int left, int right) {
		getStyle().setMargin(top, bottom, left, right);
		getSelectedStyle().setMargin(top, bottom, left, right);
		getUnselectedStyle().setMargin(top, bottom, left, right);
		getPressedStyle().setMargin(top, bottom, left, right);
	}
	
	public void setCustomTextColor(int color) {
		getStyle().setFgColor(color);
		getSelectedStyle().setFgColor(color);
		getUnselectedStyle().setFgColor(color);
		getPressedStyle().setFgColor(color);
	}
	
	public void setCustomFont(int fontFace, int fontStyle, int fontSize) {
		getStyle().setFont(Font.createSystemFont(fontFace, fontStyle, fontSize));
		getSelectedStyle().setFont(Font.createSystemFont(fontFace, fontStyle, fontSize));
		getUnselectedStyle().setFont(Font.createSystemFont(fontFace, fontStyle, fontSize));
		getPressedStyle().setFont(Font.createSystemFont(fontFace, fontStyle, fontSize));
	}

	protected void setCustomBgImage(String normalImageName, String selectedImageName) {
		Image selectedImage = ImageHelper.getImageFromResources(selectedImageName);
		Image normalImage = ImageHelper.getImageFromResources(normalImageName);
		
		if(normalImage != null) {
			getStyle().setBgImage(normalImage);
			getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			getSelectedStyle().setBgImage(selectedImage);
			getSelectedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			getPressedStyle().setBgImage(selectedImage);
			getPressedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			normalImage = null;
		} 
		
	}

}
