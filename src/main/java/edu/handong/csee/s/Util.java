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
		String pattern = "#,###.########";
		
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(Double.parseDouble(raw));
		
	}

	@SuppressWarnings("deprecation")
	public static void ignore(ArrayList<String> buff) {
		
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
		else if(op.equals("*")) {
			result = n1.multiply(n2).doubleValue();
			buff.set(2, String.valueOf(result));
			buff.remove(1);
			buff.remove(0);
		}
		else if(op.equals("/")) {
			result = n1.divide(n2, 8, BigDecimal.ROUND_HALF_UP).doubleValue();
			buff.set(2, String.valueOf(result));
			buff.remove(1);
			buff.remove(0);
		}
	}
	
	public static void keep(ArrayList<String> buff) {
		String op1 = buff.get(1);
		String op2 = buff.get(3);
		int count = 2 ;
		
		if(op1.equals("*") || op1.equals("/") )
			ignore(buff);
		else if(op2.equals("+")||op2.equals("-")||op2.equals("="))
			ignore(buff);
		else if(op2.equals("*") || op2.equals("/")){
			if(buff.size()>4) {
				ArrayList<String> subBuff = new ArrayList<String>( buff.subList(2, buff.size()));
				keep(subBuff);
				for(String element : subBuff) {
					buff.set(count, element);
					count++;
			}
			buff.remove(4);
			buff.remove(3);
			System.out.println(buff);
			keep(buff);

			System.out.println(buff);
			}
		}
	}
}
