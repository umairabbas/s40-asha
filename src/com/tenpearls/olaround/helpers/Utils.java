package com.tenpearls.olaround.helpers;

import com.sun.lwuit.Font;
import com.sun.lwuit.util.Resources;


public class Utils {
	
	public static String urlEncode(String sUrl) {  
        StringBuffer urlOK = new StringBuffer();  
        for(int i=0; i<sUrl.length(); i++) {  
            char ch=sUrl.charAt(i);  
            switch(ch) {  
                case '<': urlOK.append("%3C"); break;  
                case '>': urlOK.append("%3E"); break;                
                case ' ': urlOK.append("%20"); break;
                default: urlOK.append(ch); break;  
            }   
        }  
        return urlOK.toString();  
	 } 
	 
	public static Font getFontFromResources(String fontName) {
       Font font = null;
       try {          
    	  Resources res = com.sun.lwuit.util.Resources.open("/font.res");
           font = res.getFont(fontName);	           
        }
       catch(Throwable t) {
           System.out.println(t.toString());
       }
       return font;
	} 
	
	public static boolean toBoolean(String s) {
	    return ((s != null) && s.equalsIgnoreCase("true"));
	}
}
