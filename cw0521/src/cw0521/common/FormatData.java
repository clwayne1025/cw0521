package cw0521.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
public class FormatData{

	// Create one instance of the formatters so there is no risk of inconsistency throughout program. I 
	// only use these in one of the classes but from experience of larger programs this has proven to 
	// be a better practice.
	public static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy"); 
  	public static NumberFormat percentFormatter = NumberFormat.getPercentInstance();
  	private static DecimalFormat decimalFormatter = new DecimalFormat("0.00");

  	// Get the date formatter
  	public static SimpleDateFormat getDateFormatter() {
   		return dateFormatter; 
   	}
   	
  	// Format a decimal to 2 decimal points
   	public static String formatDecimal(double dFormat) {
   		return decimalFormatter.format(dFormat); 
   	}
   	
   	// Format the percent
   	public static String formatPercent(double dFormat) {
   		return percentFormatter.format(dFormat).toString();
   	}
   	
   	// Round data to 2 decimal points
   	public static double roundPrice(double dPrice) {
		BigDecimal bd = BigDecimal.valueOf(dPrice);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}