package com.tenpearls.olaround.helpers;

import java.util.Hashtable;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;

import com.nokia.maps.common.ApplicationContext;
import com.nokia.maps.common.GeoCoordinate;
import com.nokia.maps.map.MapComponent;
import com.nokia.maps.map.MapDisplay;
import com.nokia.maps.map.MapFactory;
import com.nokia.maps.map.MapListener;
import com.nokia.maps.map.MapMarker;
import com.nokia.maps.map.MapStandardMarker;
import com.nokia.maps.map.Point;
import com.sun.lwuit.Component;
import com.sun.lwuit.Graphics;
import com.tenpearls.olaround.constants.ApplicationConstants;

public class Map extends Component implements MapListener {

	private static MapCanvas mapCanvas;
	private static MapDisplay mapDisplay;
	private static MapFactory factory;
	javax.microedition.lcdui.Image helperImg;
	javax.microedition.lcdui.Graphics helperImgGraphics;
	private int markerCount;
	private Hashtable markerDataTable;

	public Map(Display display, String appID, String token, double longitude,
			double latitude) {
		ApplicationContext.getInstance().setAppID(appID);
		ApplicationContext.getInstance().setToken(token);
		mapCanvas = new MapCanvas(display);
		factory = mapCanvas.getMapFactory();
		mapDisplay = mapCanvas.getMapDisplay();
		mapDisplay.setMapListener(this);
		mapDisplay.setCenter(new GeoCoordinate(latitude, longitude, 0));
		mapDisplay.setZoomLevel(ApplicationConstants.MAP_ZOOM_LEVEL, 0, 0);
		setFocusable(true);
		markerCount = 0;
		markerDataTable = new Hashtable();
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (helperImg == null) {
			int width = this.getWidth();
			helperImg = javax.microedition.lcdui.Image.createImage(width, ApplicationConstants.MAP_HEIGHT);
			helperImgGraphics = helperImg.getGraphics();
		}
		mapDisplay.paint(helperImgGraphics);
		MapComponent[] comps = mapDisplay.getAllMapComponents();
		for (int i = 0; i < comps.length; i++) {
			comps[i].paint(helperImgGraphics);
		}
		com.sun.lwuit.Image img = com.sun.lwuit.Image.createImage(helperImg);
		//com.sun.lwuit.Image imgScaled = img.scaledSmallerRatio(getWidth(),
		//		ApplicationConstants.MAP_HEIGHT);
		//g.drawImage(imgScaled, 0, 0);
		g.drawImage(img, 0, 0);
	}

	public void onMapContentUpdated() {
		repaint();
	}

	public void onMapUpdateError(String err, Throwable t, boolean a) {
	}

	public void onMapContentComplete() {
		repaint();
	}

	public MapDisplay getMapDisplay() {
		return mapDisplay;
	}

	public void addMarker(GeoCoordinate coord, Image img, Object data) {
		if (img != null) {
			MapMarker marker = factory.createMapMarker(coord, img);
			marker.setAnchor(new Point(-img.getWidth() / 2,
					-img.getHeight() / 2));
			mapDisplay.addMapObject(marker);
			markerDataTable.put(marker, data);
		} else {
			MapStandardMarker marker = factory.createStandardMarker(coord);
			mapDisplay.addMapObject(marker);
			markerDataTable.put(marker, data);
		}
		if (++markerCount == 1)
			mapDisplay.setCenter(coord);
	}

	public void clearMap() {
		mapDisplay.removeAllMapObjects();
		markerDataTable.clear();
		markerCount = 0;
	}

	public void pointerPressed(int x, int y) {
		if (y < ApplicationConstants.MAP_HEIGHT) {
			mapCanvas.pointerPressed(x - getAbsoluteX(), y - getAbsoluteY());
		}
	}

	public void pointerDragged(int x, int y) {
		if (y < ApplicationConstants.MAP_HEIGHT) {
			mapCanvas.pointerDragged(x - getAbsoluteX(), y - getAbsoluteY());
		}
	}

	public void pointerReleased(int x, int y) {
		if (y < ApplicationConstants.MAP_HEIGHT) {
			mapCanvas.pointerReleased(x - getAbsoluteX(), y - getAbsoluteY());
		}
	}

	public void keyPressed(int keyCode) {
		mapCanvas.keyPressed(keyCode, 0);
	}

	public void keyReleased(int keyCode) {
		mapCanvas.keyReleased(keyCode, 0);
	}

	public void keyRepeated(int keyCode) {
		keyPressed(keyCode);
		keyReleased(keyCode);
	}
}
