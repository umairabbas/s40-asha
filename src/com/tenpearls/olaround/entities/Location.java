package com.tenpearls.olaround.entities;

public class Location {
	
	private String lattitude;
	private String longitude;
	
	public Location() {
	
	}
	
	public Location(String lattitude, String longitude) {
		this.lattitude = lattitude;
		this.longitude = longitude;
	}
	
	public String getLattitude() {
		return lattitude;
	}
	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
}
