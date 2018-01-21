package com.tenpearls.olaround.ui.forms.base;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;

public interface IClickListener {
	
	public void onButtonClick(Button btn);
	
	public void onCommandClick(Command cmd);
}
