package com.tenpearls.olaround.ui.listcellrenderers;

import java.util.Hashtable;

import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.Layout;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.entities.MenuEntity.MenuItem;
import com.tenpearls.olaround.helpers.Utils;
import com.tenpearls.olaround.ui.forms.base.IMemoryManager;

public class MenuListCellRenderer  implements ListCellRenderer, IMemoryManager {

	Hashtable hashTable = new Hashtable();
	Label focusLabel = null;
	MenuItem entity;
	
	public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
		this.entity = (MenuItem) value;

		if (hashTable.containsKey(new String(entity.getItemName()))) {
			return (Component) hashTable.get(new String(entity.getItemName()));
		}
		
		Container mainContainer = getContainer(new BoxLayout(BoxLayout.X_AXIS));
		mainContainer.setCellRenderer(true);
		mainContainer.setPreferredW(240);
		mainContainer.setPreferredH(30);
		
		mainContainer.getStyle().setBgTransparency(0);
		mainContainer.getStyle().setBgColor(ApplicationConstants.COLOR_WHITE);
		mainContainer.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
		mainContainer.getStyle().setBgTransparency(255);
		mainContainer.getStyle().setBorder(Border.createInsetBorder(1));
		
		Container innerContainer = getInnerContainer(new BoxLayout(BoxLayout.X_AXIS));
		innerContainer.addComponent(getHeadingArea(entity.getItemName()));
		innerContainer.addComponent(getTextArea("PKR", 0, 5, 20));
		
		String price = Integer.toString(((int)entity.getPrice()));
		if(entity.getPrice() == 0) {
			price = "n/a";
		}
		
		innerContainer.addComponent(getTextArea(price, 0, 0, 30));
		innerContainer.getStyle().setMargin(0, 0, 0, 0);
		innerContainer.getStyle().setPadding(0, 0, 10, 0);
		mainContainer.addComponent(innerContainer);
		innerContainer = null;
		
		mainContainer.getStyle().setMargin(0, 0, 5, 0);
		mainContainer.getStyle().setPadding(0, 0, 0, 0);

		//hashTable.put(new String(entity.getId()+ ""), mainContainer);
		hashTable.put(entity.getItemName(), mainContainer);
		return mainContainer;
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
	
	private Label getHeadingArea(String text) {
		if (text.length() > 22) {
			text = text.substring(0, 22);
			text = text.concat("...");
		}
		Label label = new Label(text);
		label.getStyle().setFgColor(ApplicationConstants.COLOR_FONT_LIGHT_GREY);
		label.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		label.getStyle().setPadding(0, 0, 0, 0);
		label.getStyle().setMargin(0, 0, 0, 0);
		label.setPreferredH(16);
		label.setPreferredW(150);
		return label;
	}
	
	private Label getTextArea(String text, int leftMargin, int rightMargin, int width) {
		if (text.length() > 23) {
			text = text.substring(0, 23);
			text = text.concat("...");
		}
		Label label = new Label(text);
		label.getStyle().setFgColor(ApplicationConstants.COLOR_FONT_LIGHT_GREY);
		Font font = Utils.getFontFromResources(ApplicationConstants.FONT_EXTRA_SMALL);
		label.getStyle().setFont(font);
		label.getStyle().setPadding(0, 0, 0, 0);
		label.getStyle().setMargin(0, 0, leftMargin, rightMargin);
		label.getStyle().setAlignment(Component.RIGHT);
		label.setPreferredH(16);
		label.setPreferredW(width);
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

	public void dealloc() {
		if (hashTable != null) {
			hashTable.clear();
			hashTable = null;
		}
		focusLabel = null;
		entity = null;
	}

}
