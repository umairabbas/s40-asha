package com.tenpearls.olaround.ui.listcellrenderers;

import java.util.Hashtable;

import com.sun.lwuit.Button;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.Layout;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.entities.DealsEntity;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.ui.components.CustomImageButton;
import com.tenpearls.olaround.ui.components.CustomTextArea;
import com.tenpearls.olaround.ui.forms.DealsListingForm;
import com.tenpearls.olaround.ui.forms.base.IMemoryManager;

public class DealsListCellRenderer implements ListCellRenderer, IMemoryManager {

	Hashtable hashTable = new Hashtable();
	DealsEntity entity ;

	public DealsListCellRenderer() {
	}
	
	public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
		this.entity = (DealsEntity) value;

		if (hashTable.containsKey(new String(entity.getStoreId() + "" ))) {
			return (Component) hashTable.get(new String(entity.getStoreId() + ""));
		}
		Container mainContainer = getContainer(new BorderLayout());
		mainContainer.setCellRenderer(true);
		mainContainer.getStyle().setBgImage(getListBg());
		mainContainer.getStyle().setBgColor(ApplicationConstants.COLOR_WHITE);
		mainContainer.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
		mainContainer.getStyle().setBgTransparency(100);
		mainContainer.getStyle().setBorder(Border.createInsetBorder(1));
		
		mainContainer.addComponent(BorderLayout.WEST, getBrandImage());
		mainContainer.addComponent(BorderLayout.CENTER, getBrandNameAndLocation());
		mainContainer.addComponent(BorderLayout.EAST,getDealImageButton());
		mainContainer.addComponent(BorderLayout.SOUTH, getDealsDetailArea());
		
		mainContainer.getStyle().setMargin(2, 0, 2, 0);
		mainContainer.getStyle().setPadding(5, 0, 3, 4);
		hashTable.put(new String(entity.getStoreId()+ ""), mainContainer);
		return mainContainer;
	}
	
	private Container getBrandNameAndLocation() {
		Container innerContainer = getInnerContainer(new BoxLayout(BoxLayout.Y_AXIS));
		innerContainer.addComponent(getHeadingTextArea(entity.getStoreName().toLowerCase()));
		innerContainer.addComponent(getTypeAndDistance());
		innerContainer.getStyle().setMargin(0, 0, 5, 0);
		innerContainer.getStyle().setPadding(0, 0, 0, 0);
		return innerContainer;
	}
	
	private Label getBrandImage() {
		 Label lblimgBrand = new Label();
		  lblimgBrand.getStyle().setMargin(0, 0, 0, 0);
		  Image imgBrand = null;
		  try {
		   imgBrand = ImageHelper.getImageFromWeb(entity.getBrandPictureUrl());
		  } catch (Exception e) {
		  }
		  if (imgBrand == null) {
		   imgBrand = ImageHelper.getImageFromResources(ImageConstants.DEFAULT_BRAND_IMAGE);
		  }
		  Image imgBrandScaled = imgBrand.scaledSmallerRatio(38, 38);
		  lblimgBrand.setIcon(imgBrandScaled);
		return lblimgBrand;
	}
	
	private Button getDealImageButton() {
		CustomImageButton dealsButton = new CustomImageButton(ImageConstants.BUTTON_DEALS, "");
		dealsButton.setPreferredH(43);
		dealsButton.setPreferredW(45);
		dealsButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				OlaroundMidlet.getInstance().showDealsDetailForm(null, false, entity.getStoreId(), 
						entity.getBrandBackGroundPictureUrl(), DealsListingForm.class.getName());
				
			}
		});
		return dealsButton;
	}
	
	private Label getTypeAndDistance() {
		String text = "";
		Label label = new Label();
	
		String categoryName = entity.getStoreCategoriesName();
		if(categoryName != null) {
			text += categoryName.toLowerCase() + " ";
		}
		double distance = entity.getDistance();
		
		label.setText(text  + Double.toString(distance).substring(0, 4) + "km" );
		label.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		label.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		return label;
	}
	
	private Label getHeadingTextArea(String text) {
		if (text.length() > 25) {
			text = text.substring(0, 25);
			text = text.concat("...");
		}
		Label label = new Label(text);
		label.getStyle().setBgTransparency(0);
		label.getStyle().setFgColor(ApplicationConstants.COLOR_BLACK);
		label.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
		label.setPreferredH(16);
		return label;
	}

	private Container getContainer(Layout layout) {
		Container container = new Container();
		container.setLayout(layout);
		return container;
	}
	
	private Container getInnerContainer(Layout layout) {
		Container container = getContainer(layout);
		container.getStyle().setMargin(0, 0, 0, 0); 
		container.getStyle().setPadding(0, 0, 0, 0);
		container.getStyle().setBgTransparency(0);
		return container;
	}
	
	private CustomTextArea getDealsDetailArea() {
		CustomTextArea detailArea = new CustomTextArea(entity.getPromotions().getPromotionsTitle(), false);
		detailArea.setCustomFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		detailArea.setCustomMargin(2, 0, 7, 2);
		detailArea.setCustomTextColor(ApplicationConstants.COLOR_FONT_LIGHT_GREY);
		return detailArea;
	}

	private Image getListBg(){
		Image img = ImageHelper.getImageFromResources(ImageConstants.INFO_CONTAINER_LARGE);
		return img;
	}

	public void dealloc() {
		if (hashTable != null) {
			hashTable.clear();
			hashTable = null;
		}
		entity = null;
	}

	public Component getListFocusComponent(List arg0) {
		return null;
	}

}
