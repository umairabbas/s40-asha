package com.tenpearls.olaround.entities;

public class Brand {
	private int id;
	private String name;
	private String brandSlug;
	private String pictureURL;
	private String backgroundPictureURL;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBrandSlug() {
		return brandSlug;
	}
	
	public void setBrandSlug(String brandSlug) {
		this.brandSlug = brandSlug;
	}
	
	public String getPictureURL() {
		return pictureURL;
	}
	
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	
	public String getBackgroundPictureURL() {
		return backgroundPictureURL;
	}
	
	public void setBackgroundPictureURL(String backgroundPictureURL) {
		this.backgroundPictureURL = backgroundPictureURL;
	}
	
}
