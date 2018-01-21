package com.tenpearls.olaround.ui.listcellrenderers;

import java.util.Hashtable;

import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.Layout;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.entities.Category;
import com.tenpearls.olaround.entities.VenueEntity;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.ui.forms.base.IMemoryManager;

public class VenueListCellRenderer implements ListCellRenderer, IMemoryManager {

	Hashtable hashTable = new Hashtable();
	Label focusLabel = null;
	VenueEntity entity;
	
	public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
		this.entity = (VenueEntity) value;

		if (hashTable.containsKey(new String(entity.getId() + "" ))) {
			return (Component) hashTable.get(new String(entity.getId() + ""));
		}
		Container mainContainer = getContainer(new BoxLayout(BoxLayout.X_AXIS));
		mainContainer.setCellRenderer(true);
		mainContainer.setPreferredW(240);
		mainContainer.setPreferredH(55);
		mainContainer.getStyle().setBgImage(getListBg());
		mainContainer.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
		mainContainer.getStyle().setBgTransparency(100);
		
		mainContainer.addComponent(getImage());
		
		Container innerContainer = getInnerContainer(new BorderLayout());
		innerContainer.addComponent(BorderLayout.NORTH,  getHeadingTextArea(entity.getName().toLowerCase()));
		innerContainer.addComponent(BorderLayout.SOUTH, getTypeAndDistance());
		innerContainer.getStyle().setMargin(0, 0, 0, 0);
		innerContainer.getStyle().setPadding(0, 0, 0, 0);
		mainContainer.addComponent(innerContainer);
		innerContainer = null;
		
		mainContainer.getStyle().setMargin(0, 0, 0, 0);
		mainContainer.getStyle().setPadding(5, 7, 2, 0);

		hashTable.put(new String(entity.getId()+ ""), mainContainer);
		return mainContainer;
	}
	
	private Container getImage() {
		Container leftContainer = getInnerContainer(new BoxLayout(BoxLayout.X_AXIS));
			Label label = new Label();
			Image imgBrandScaled = null;
			Image image = null;
			try {
				
				//image = ImageHelper.getImageFromWeb(entity.getBrand().getPictureURL());
				if(image == null) {
					System.out.println("Image is not created");
					image =  ImageHelper.getImageFromResources(ImageConstants.BRAND);
				} 
				imgBrandScaled = image.scaledSmallerRatio(36, 36);
				label.setIcon(imgBrandScaled);
				leftContainer.getStyle().setMargin(0, 0, 0, 5);
				leftContainer.getStyle().setPadding(0, 0, 0, 0);
			leftContainer.addComponent(label);
			label = null;
			image = null;
			return leftContainer;
			
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return leftContainer;
	}
	
	private Container getTypeAndDistance() {
		Container container = new Container(new BoxLayout(BoxLayout.X_AXIS));
		if(entity.getCategories() != null) {
			container.addComponent(getCategoryName());
		}
		container.addComponent(getDistance());
		return container;
	}
	
	private Label getCategoryName() {
		Label cateogryLabel = new Label();
		Category category = (Category) entity.getCategories().firstElement();
		if(category != null) {
			String categoryName = category.getName().toLowerCase();
				if(entity.getDistance() != null) {
					categoryName += " - ";
				}
			cateogryLabel.setText(categoryName);
		}
		cateogryLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		cateogryLabel.getStyle().setFgColor(0x000000);
		cateogryLabel.getStyle().setMargin(0, 2, 0, 0);
		cateogryLabel.getStyle().setPadding(0, 0, 0, 0);
		
		return cateogryLabel;
	}
	
	private Label getDistance() {
		Label distanceLabel = new Label();
		distanceLabel.setText(entity.getDistance().substring(0, 4) + "km");
		distanceLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		distanceLabel.getStyle().setFgColor(0x000000);
		distanceLabel.getStyle().setMargin(0, 2, 0, 0);
		distanceLabel.getStyle().setPadding(0, 0, 0, 0);
		return distanceLabel;
	}
	
	public Component getListFocusComponent(List list) {
		if (focusLabel == null) {
			focusLabel = new Label();
			focusLabel.getStyle().setBgTransparency(45);
			focusLabel.getStyle().setMargin(0, 0, 0, 0);
			focusLabel.getStyle().setPadding(0, 0, 0, 0);
		}
		return focusLabel;
	}
	
	private Label getHeadingTextArea(String text) {
		if (text.length() > 25) {
			text = text.substring(0, 25);
			text = text.concat("...");
		}
		Label label = new Label(text);
		label.getStyle().setBgTransparency(0);
		label.getStyle().setFgColor(0x000000);
		label.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
		label.getStyle().setPadding(0, 4, 0, 0);
		label.getStyle().setMargin(3, 0, 0, 0);
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

	private Image getListBg(){
		Image img = ImageHelper.getImageFromResources(ImageConstants.BG_LIST);
		return img;
	}

	public void dealloc() {
		if (hashTable != null) {
			hashTable.clear();
			hashTable = null;
		}
		focusLabel = null;
		entity = null;
	}
}
