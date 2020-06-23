package edu.handong.csee.s;

import java.text.DecimalFormat;

public class Util {

	
	public static String convertOutputNumber(String display, String numb){
		
		if(display.equals("0"))
			return numb; 
		else
			return display+numb;
	}
	
	public static String convertFormat(String raw) {
		String pattern = "#,###.#############";
		
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(Double.parseDouble(raw));
		
	}
}
