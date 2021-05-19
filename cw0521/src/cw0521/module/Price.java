package cw0521.module;

import java.util.*;
import java.math.*;
import java.text.ParseException;

import cw0521.common.FormatData;
import cw0521.testSuite.TestMain; 

public class Price{
	private String sType;
	private double dDailyPrice;
	private boolean bWeekday;
	private boolean bWeekend;
	private boolean bHoliday;
  
	// Constructor
	public Price(String sType, double dDaily, boolean bWeekday, boolean bWeekend, boolean bHoliday){
		this.sType = sType;
		this.dDailyPrice = dDaily;
		this.bWeekday = bWeekday;
		this.bWeekend = bWeekend;
		this.bHoliday = bHoliday;
	}
	
	// Get the daily price of an instance
	public double getDaily() {
		return dDailyPrice; 
	}
	
	// Get the type of an instance
	public String getType() {
		return sType; 
	}
	
	// Calculate the chargeable days based on the checkout date and rental days provided at check out
	public int calculateChargeDays(String sCheckOut, int iDuration) {
		// Charge days - Count of chargeable days, from day after checkout through and including due date, 
		// excluding “no charge” days as specified by the tool type.
		int iChargeDays = 0; 
		
		try {
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(FormatData.dateFormatter.parse(sCheckOut));
			
			boolean bIsWeekend; 
			boolean bIsHoliday; 
			
			// Go through each rental day and determine if it's chargeable
			for(int i = 0; i<iDuration; i++){
				// Add a day to evaluate the next day
				cal.add(Calendar.DAY_OF_YEAR, 1);  
				
				// Assume this isn't a weekend or holiday until proven otherwise
				bIsWeekend = false;
				bIsHoliday = false;
				
				// Determine if day is a weekend or weekday
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
					bIsWeekend = true; 
				} 
				
				// HANDLE THE HOLIDAY OF 4TH OF JULY AND OBSERVED DAYS
				if (cal.get(Calendar.MONTH) == Calendar.JULY && (cal.get(Calendar.DAY_OF_MONTH) == 3 | cal.get(Calendar.DAY_OF_MONTH) == 4 | cal.get(Calendar.DAY_OF_MONTH) == 5)){
					// July 4th is on a Saturday so observe on Friday
					if(cal.get(Calendar.DAY_OF_MONTH) == 3 && cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
						bIsHoliday = true; 
					}
					
					// July 4th is on a Sunday so observe on Monday
					if(cal.get(Calendar.DAY_OF_MONTH) == 5 && cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
						bIsHoliday = true; 
					}
					
					// It's the 4th, no matter what day it falls on it's a holiday
					if(cal.get(Calendar.DAY_OF_MONTH) == 4) {
						bIsHoliday = true;
					}
				}				
				
				// HANDLE THE HOLIDAY OF LABOR DAY
				if (cal.get(Calendar.MONTH) == Calendar.SEPTEMBER && cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1 && cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
					bIsHoliday = true; 
				}
				
				// Determine if the date is chargeable based on the data provided for the current price instance
				if((bHoliday && bIsHoliday) | ((!bIsHoliday) && ((bWeekday && !bIsWeekend) | (bWeekend && bIsWeekend)))){
					iChargeDays++; 
				} 
			}
		} catch (ParseException e) {
			System.out.println("Failed to parse check out date."); 
			e.printStackTrace();
		}
		
		return iChargeDays; 
	}	
	
	// Find the instance of the price data based on the unique tool type
	public static Price findPrice(String sToolType) {
		List<Price> lPrices = TestMain.getPriceList(); 
		
  		for(int i=0; i < lPrices.size(); i++) {
  			Price curPrice = lPrices.get(i); 
  			
  			if(curPrice.getType() == sToolType) {
  				return curPrice; 
  			} 
  		}
  	
  		return null; 
 	}
	
	// Calculate the pre-discount total
	public double calculatePreDiscTotal(int iDays) {
		return FormatData.roundPrice(iDays*dDailyPrice);
	}
	
	
	
}
