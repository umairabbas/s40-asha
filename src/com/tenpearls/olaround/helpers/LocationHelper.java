package com.tenpearls.olaround.helpers;

import javax.microedition.location.Coordinates;
import javax.microedition.location.Location;
import javax.microedition.location.LocationProvider;

import com.nokia.mid.location.LocationUtil;

public class LocationHelper {
	public static com.tenpearls.olaround.entities.Location currentlocation ;
	
	public static void getLocation() {
		currentlocation  = new com.tenpearls.olaround.entities.Location();
		try {
		   
		    int[] methods = {(Location.MTA_ASSISTED | Location.MTE_CELLID
		            | Location.MTE_SHORTRANGE | Location.MTY_NETWORKBASED)};
		    
		  LocationProvider lp = LocationUtil.getLocationProvider(methods, null);
		    
		    
		    // get the location, one minute timeout
		    Location l = lp.getLocation(60);
	
		    Coordinates c = l.getQualifiedCoordinates();
		    
		    if (c != null) {
		       // use coordinate information
		    	Double lattitude = new Double(c.getLatitude());
		    	Double longitude = new Double(c.getLongitude());
		    	
		     
		    	System.out.println("lattitude: " + lattitude + "\nlongitude: " + longitude);

		    	currentlocation.setLattitude(lattitude.toString());
		    	currentlocation.setLongitude(longitude.toString());
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}

	public static com.tenpearls.olaround.entities.Location getCurrentlocation() {
		return currentlocation;
	}

	public static void setCurrentlocation(
			com.tenpearls.olaround.entities.Location currentlocation) {
		LocationHelper.currentlocation = currentlocation;
	}
}
