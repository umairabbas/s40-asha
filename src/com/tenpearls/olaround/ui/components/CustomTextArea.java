package com.tenpearls.olaround.ui.components;

import com.sun.lwuit.Font;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.plaf.Border;
import com.tenpearls.olaround.helpers.Utils;

public class CustomTextArea extends TextArea{

	public CustomTextArea(String text,boolean growByContent) {
		
		super(text);
		setEditable(false);
		
		if(growByContent) {
			setRows(0);
			setColumns(0);
		} else {
			setRows(2);
		}
		//setRows(2);

		setGrowByContent(growByContent);
		setCustomBgTransparency(0);
		setCustomBorder();
		setCustomPadding();
		
	}
	
	public void setCustomBgTransparency(int transparency) {
		getStyle().setBgTransparency(transparency);
		getUnselectedStyle().setBgTransparency(transparency);
		getSelectedStyle().setBgTransparency(transparency);
		getPressedStyle().setBgTransparency(transparency);
	}
	
	public void setCustomBgColor(int color) {
		getStyle().setBgColor(color);
		getUnselectedStyle().setBgColor(color);
		getSelectedStyle().setBgColor(color);
		getPressedStyle().setBgColor(color);
	}
	
	private void setCustomBorder() {
		getStyle().setBorder(Border.createEmpty(), true);
		getUnselectedStyle().setBorder(Border.createEmpty(),true);
		getSelectedStyle().setBorder(Border.createEmpty(),true);
		getPressedStyle().setBorder(Border.createEmpty(),true);
	}
	
	private void setCustomPadding() {
		getStyle().setPadding(0,0,0,0);
		getSelectedStyle().setPadding(0,0,0,0);
		getUnselectedStyle().setPadding(0,0,0,0);
		getPressedStyle().setPadding(0,0,0,0);
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
	
	public void setCustomFont(String fontName) {
		getStyle().setFont(
				Utils.getFontFromResources(fontName));
		getSelectedStyle().setFont(
				Utils.getFontFromResources(fontName));
		getUnselectedStyle().setFont(
				Utils.getFontFromResources(fontName));
		getPressedStyle().setFont(
				Utils.getFontFromResources(fontName));
	}
	
	public void setCustomFont(int fontFace, int fontStyle, int fontSize) {
		getStyle().setFont(Font.createSystemFont(fontFace, fontStyle, fontSize));
		getSelectedStyle().setFont(Font.createSystemFont(fontFace, fontStyle, fontSize));
		getUnselectedStyle().setFont(Font.createSystemFont(fontFace, fontStyle, fontSize));
		getPressedStyle().setFont(Font.createSystemFont(fontFace, fontStyle, fontSize));
	}
	
	public void setAlignment(int alignment) {
		getStyle().setAlignment(alignment);
		getSelectedStyle().setAlignment(alignment);
		getUnselectedStyle().setAlignment(alignment);
		getPressedStyle().setAlignment(alignment);
	}
	
	public void setCustomText(String text) {				
		try
		{
			super.setText(text);
	
			setRows(2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void setGrowableText(String text, boolean growByContent)
	{
		try
		{
			super.setText(text);
			if(growByContent) 
			{
				setRows(0);
				setColumns(0);
			} 
			else 
			{
				setRows(2);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
