package cw0521.module;
import java.text.*;
import java.util.*;

import cw0521.common.FormatData;

public class Rental{ 	
  	// Constructor
	public Rental() {
		
	}
		
	// Calculate the final charge
	public double calcFinalCharge(double dPreDiscTotal, double dDiscAmount) {
		return dPreDiscTotal-dDiscAmount; 
	}
	
	// Calculate the total discount
	public double calcDiscTotal(double dDiscount, double dCharge) {
		return dDiscount*dCharge; 
	}
	
	// Calculate the due date based on the start date and rental days specified at checkout
	public String calcDueDate(int iRentalDays, String sCheckOut){  
		try {
			Calendar cal = Calendar.getInstance(); 
			Date dCheckOut;
			dCheckOut = FormatData.dateFormatter.parse(sCheckOut);
			cal.setTime(dCheckOut);
			cal.add(Calendar.DAY_OF_YEAR, iRentalDays); 
			
			return FormatData.dateFormatter.format(cal.getTime()); 
		} catch (ParseException e) {
			System.out.println("Failed to parse check out date."); 
			e.printStackTrace();
			return ""; 
		} 
	}

	// Convert the discount from the whole number provided at checkout to a double for calculations
	public double  convertDiscount(int iDiscount) {
		return (double) iDiscount/ (double) 100;
	}
	
	// Validate that rental days specified at checkout is greater than or equal to 1
	public boolean validateDays(int iRentalDays) {
  		boolean bValid = true; 
  		
  		if(iRentalDays <= 1) {
  			bValid = false; 
  			System.out.println("Invalid rental day count. Must be greater than or equal to 1.\n\n");
  		}
  		
  		return bValid; 
  	}
  	
	// Validate that rental discount specified at checkout is between 0-100
 	public boolean validateDiscount(int iDiscount) {
  		boolean bValid = true; 
  		
  		if((iDiscount<0) | (iDiscount>100)) {
  			bValid = false; 
  			System.out.println("Invalid discount percent. Must be between 0-100.\n\n"); 
  		}

  		return bValid; 
  	}
}
