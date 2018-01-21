package com.tenpearls.olaround.helpers;

import javax.microedition.lcdui.Display;



public class MapCanvas extends com.nokia.maps.map.MapCanvas
{


	public MapCanvas(Display display)
	{
		super(display);
	}

	public void keyPressed(int keycode, int gameAction)
	{
		super.keyPressed(getGameAction(keycode));
	}

	public void keyReleased(int keycode, int gameAction)
	{
		super.keyPressed(getGameAction(keycode));
	}

	public void keyRepeated(int keycode, int gameAction)
	{
		super.keyPressed(getGameAction(keycode));
	}
	
	public void pointerPressed(int x, int y)
	{
		super.pointerPressed(x, y);
	}
	
	public void pointerReleased(int x, int y)
	{
		super.pointerReleased(x, y);
	}
	
	public void pointerDragged(int x, int y)
	{
		super.pointerDragged(x, y);
	}

	public void onMapContentComplete()
	{
		super.onMapContentUpdated();
	}

	public void onMapUpdateError(String err, Throwable t, boolean critical)
	{
	}

}
