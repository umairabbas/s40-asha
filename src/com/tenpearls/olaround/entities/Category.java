package com.tenpearls.olaround.entities;

public class Category {
	
	private int id;
	private String name;
	private int pictureId;
	private String pictureURL;
	
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
	
	public int getPictureId() {
		return pictureId;
	}
	
	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}
	
	public String getPictureURL() {
		return pictureURL;
	}
	
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

}
