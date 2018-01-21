package com.tenpearls.olaround.helpers;

import java.util.Vector;

public class StringUtils {

	public static boolean isContains(String str, String strToCheck) {
		return (str.indexOf(strToCheck) > 0) ;
	}
	
	public static String[] split(String original, String separator) {
		    Vector nodes = new Vector();
		    // Parse nodes into vector
		    int index = original.indexOf(separator);
		    while(index >= 0) {
		        nodes.addElement( original.substring(0, index) );
		        original = original.substring(index+separator.length());
		        index = original.indexOf(separator);
		    }
		    // Get the last node
		    nodes.addElement( original );

		     // Create split string array
		    String[] result = new String[ nodes.size() ];
		    if( nodes.size() > 0 ) {
		        for(int loop = 0; loop < nodes.size(); loop++)
		        {
		            result[loop] = (String)nodes.elementAt(loop);
		        }

		    }
		   return result;
	}
}
