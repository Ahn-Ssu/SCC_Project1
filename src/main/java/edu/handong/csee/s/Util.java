package edu.handong.csee.s;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

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

	public static void igonore(ArrayList<String> buff) {
		
		String op = buff.get(1);
		double result = 0;
		BigDecimal n1 = BigDecimal.valueOf(Double.parseDouble(buff.get(0)));
		BigDecimal n2 = BigDecimal.valueOf(Double.parseDouble(buff.get(2)));
		
		
		
		if(op.equals("+") ) {
			 
			result = n1.add(n2).doubleValue();
			buff.set(2, String.valueOf(result));
			buff.remove(1);
			buff.remove(0);
		}
		else if(op.equals("-")) {
			result = n1.subtract(n2).doubleValue();
			buff.set(2, String.valueOf(result));
			buff.remove(1);
			buff.remove(0);
		}
		else if(op.equals("X")) {
			result = n1.multiply(n2).doubleValue();
			buff.set(2, String.valueOf(result));
			buff.remove(1);
			buff.remove(0);
		}
		else if(op.equals("/")) {
			result = n1.divide(n2).doubleValue();
			buff.set(2, String.valueOf(result));
			buff.remove(1);
			buff.remove(0);
		}
	}
}
