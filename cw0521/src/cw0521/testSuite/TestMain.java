package cw0521.testSuite;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import cw0521.module.*;

public class TestMain{
	public static List<Tool> lTools = new ArrayList<>();  
	public static List<Price> lPrices = new ArrayList<>();; 
	
	 public static void main(String[] args) {
		 // Create the list of existing tools
		lTools.add(new Tool("Ladder", "Werner", "LADW"));
		lTools.add(new Tool("Chainsaw", "Stihl", "CHNS"));
		lTools.add(new Tool("Jackhammer", "Ridgid", "JAKR"));
		lTools.add(new Tool("Jackhammer", "DeWalt", "JAKD"));

		// Create the list of existing prices
		lPrices.add(new Price("Ladder", 1.99, true, true, false));
		lPrices.add(new Price("Chainsaw", 1.49, true, false, true));
		lPrices.add(new Price("Jackhammer", 2.99, true, false, false));
		 
		//Result result = JUnitCore.runClasses(); 
		Result result = JUnitCore.runClasses(RentalTest.class); 
		
		// If there were any failures, print them out
		if(result.getFailures().size() > 0) {
			for(Failure failure : result.getFailures()) {
				System.out.println(failure.toString()); 
			}
		}
	}
	 
	 // Access the list of Tools 
	public static List<Tool> getToolList() {
		return lTools; 
	}

	// Access the list of Tools 
	public static List<Price> getPriceList() {
		return lPrices; 
	}
 }