package com.tenpearls.olaround.helpers;

import java.io.IOException;

import com.sun.lwuit.Image;

public class ImageHelper {

	public static final String PATH_PREFIX = "/images/";

	public static String getCompleteImagePath(String imageName) {
		String path = PATH_PREFIX + imageName;
		return path;
	}

	public static Image getImageFromResources(String imageName) {

		String path = ImageHelper.getCompleteImagePath(imageName);

		Image image = null;
		try {
			image = Image.createImage(path);
			
		} catch (IOException e) {
			e.printStackTrace();
			image = null;
		}
		return image;
	}

	public static javax.microedition.lcdui.Image getLcduiImageFromResources(
			String imageName) {
		String path = ImageHelper.getCompleteImagePath(imageName);
		javax.microedition.lcdui.Image image = null;
		try {
			 image = (javax.microedition.lcdui.Image) Image.createImage(path).getImage();
		} catch (IOException e) {			
			e.printStackTrace();
			image = null;
		}
		
		return image;
	}
	
	
	public synchronized  static Image getImageFromWeb(String url) {
		url = Utils.urlEncode(url);
		Image image = null;
		System.out.println(url);
	    image = WebServiceHelper.fetchImage(url);
		
		return image;
	}
	
}
