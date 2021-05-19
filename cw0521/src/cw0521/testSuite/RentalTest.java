package cw0521.testSuite; 

import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

import java.util.*;

import cw0521.common.FormatData;
import cw0521.module.*;

@RunWith(Parameterized.class)
public class RentalTest {
	// Specified at checkout
	private String sToolCode; 
	private Integer iRentalDays; 
	private String sCheckOut; 
	private Integer iDiscountPercent; 
	
	// Calculated values
	private Boolean bValidRentalDays; 
	private boolean bValidDiscount; 
	private Double dDiscount; 
	private String sDueDate;
	private Tool currentTool; 
	private Price currentPrice;
	private String sToolType; 
	private String sToolBrand; 
	private Double dDailyCharge; 
	private Integer iChargeDays; 
	private Double dPreDiscountCharge; 
	private Double dDiscountAmount; 
	private Double dFinalCharge; 
	
	// Expected calculated values
	private Boolean bExpectedValidRentalDays; 
	private Boolean bExpectedValidRentalDisc; 
	private String sExpectedToolType; 
	private String sExpectedToolBrand; 
	private String sExpectedDueDate; 
	private Double dExpectedDailyRentalCharge; 
	private Integer iExpectedChargeDays; 
	private Double dExpectedConvDisc; 
	private Double dExpectedPreDiscCharge; 
	private Double dExpectedDiscountAmount; 
	private Double dExpectedFinalChange; 
		
	private static Rental rental;
	
	// Constructor for this class. Set all of the variables for the rental - including the expected values for testing.
	public RentalTest(String sCode, int iRentalDays, int iDiscount, String sCheckOut, boolean bValidRentalDays, boolean bValidRentalDisc, 
			String sExpectedToolType, String sExpectedToolBrand, String sExpectedDueDate, double dExpectedDailyRentalCharge, int iExpectedChargeDays, 
			double dExpectedConvDisc, double dExpectedPreDiscCharge, double dExpectedDiscountAmount, double dExpectedFinalChange){
		super(); 
	   	this.sToolCode = sCode;
	    this.iRentalDays = iRentalDays;
		this.sCheckOut = sCheckOut; 
		this.iDiscountPercent = iDiscount;
		
		this.bExpectedValidRentalDays = bValidRentalDays; 
		this.bExpectedValidRentalDisc = bValidRentalDisc; 
		this.sExpectedToolType = sExpectedToolType; 
		this.sExpectedToolBrand = sExpectedToolBrand; 
		this.sExpectedDueDate = sExpectedDueDate; 
		this.dExpectedDailyRentalCharge = dExpectedDailyRentalCharge; 
		this.iExpectedChargeDays = iExpectedChargeDays; 
		this.dExpectedConvDisc = dExpectedConvDisc; 
		this.dExpectedPreDiscCharge = dExpectedPreDiscCharge; 
		this.dExpectedDiscountAmount = dExpectedDiscountAmount; 
		this.dExpectedFinalChange = dExpectedFinalChange; 
	}
		
	@Before
	public void init() {
		// Create a rental instance
		rental = new Rental(); 
			
		// Get the current tool data for this rental
	 	currentTool = Tool.findTool(sToolCode);
	 	
	 	// Get the current price data for this rental
		currentPrice = Price.findPrice(currentTool.getType());
	}
		
	@Parameterized.Parameters
	public static Collection<Object> input() {
		// All the test cases and their expected values
		Object[] oTestCase1 = new Object[] {"JAKR", 5, 101, "9/3/15", true, false, "", "", "00/00/00", 0.0, 0, 0.0, 0.0, 0.0, 0.0}; 
		Object[] oTestCase2 = new Object[] {"LADW", 3, 10, "7/2/20", true, true, "Ladder", "Werner", "07/05/20", 1.99, 1, 0.1, 1.99, 0.20, 1.79};
		Object[] oTestCase3 = new Object[] {"CHNS", 5, 25, "7/2/15", true, true, "Chainsaw", "Stihl", "07/07/15", 1.49, 4, 0.25, 5.96, 1.49, 4.47};
		Object[] oTestCase4 = new Object[] {"JAKD", 6, 0, "9/3/15", true, true, "Jackhammer", "DeWalt", "09/09/15", 2.99, 3, 0.0, 8.97, 0.0, 8.97};
		Object[] oTestCase5 = new Object[] {"JAKR", 9, 0, "7/2/15", true, true, "Jackhammer", "Ridgid", "07/11/15", 2.99, 5, 0.0, 14.95, 0.0, 14.95}; 
		Object[] oTestCase6 = new Object[] {"JAKR", 4, 50, "7/2/20", true, true, "Jackhammer", "Ridgid", "07/06/20", 2.99, 1, 0.5, 2.99, 1.50, 1.49};
		
		Object[] oAllTestCases = new Object[] {oTestCase1, oTestCase2, oTestCase3, oTestCase4, oTestCase5, oTestCase6}; 
 	
		return Arrays.asList(oAllTestCases); 
	}
	
	
	@Test
	public void testRental() {
		// Indicator that we are starting a new rental
		System.out.println("Starting new rental agreement..."); 
		
		// Test if the rental days provided is valid (must be greater than  or equal to 1)
		bValidRentalDays = rental.validateDays(iRentalDays); 
		assertEquals("Failed to validate rental days", bExpectedValidRentalDays, bValidRentalDays);
		
		// Test if the discount provided is valid (must be between 0-100)
		bValidDiscount = rental.validateDiscount(iDiscountPercent);
		assertEquals("Failed to validate discount", bExpectedValidRentalDisc, bValidDiscount);		 
		
		// If we didn't pass validation based on the rental data, then no need to continue testing
		if(bValidRentalDays && bValidDiscount) {
			// DISCOUNT - Covert the discount from the whole number given in the rental info. Then test if discount was converted correctly. 
			dDiscount = rental.convertDiscount(iDiscountPercent);
	        assertEquals("Failed converting disount", dExpectedConvDisc, dDiscount); 
	        
	        // DUE DATE - Calculate the due date based on the rental days provided in the rental info. Then test if due date is correct.  
	        sDueDate = rental.calcDueDate(iRentalDays, sCheckOut); 
	        assertEquals("Failed calculating due date", sExpectedDueDate, sDueDate); 
	        
	        // TOOL TYPE - Get the tool type from the current tool. Then test if tool type is correct.
	        sToolType = currentTool.getType(); 
			assertEquals("Failed getting tool type", sExpectedToolType, sToolType);
			
			// TOOL BRAND - Get the tool brand from the current tool instance being referenced. Then test if tool brand is correct.		
			sToolBrand = currentTool.getBrand(); 
			assertEquals("Incorrect too brand", sExpectedToolBrand, sToolBrand);
			
			// DAILY RENTAL CHARGE - Get the daily rental charge from the current price instance being referenced. Then test if price is correct.	
			dDailyCharge = currentPrice.getDaily(); 
			assertEquals("Incorrect daily price", dExpectedDailyRentalCharge, dDailyCharge);
			
			// CHARGE DAYS - Calculate the charge days for the rental period. Then test if charge days is correct.
			iChargeDays = currentPrice.calculateChargeDays(sCheckOut, iRentalDays); 
			assertEquals("Failed calulcating charge days", iExpectedChargeDays, iChargeDays);
			
			// PRE-DISCOUNT CHARGE - Calculate the pre-discount charge. Then test if the total is correct.
			dPreDiscountCharge = FormatData.roundPrice(currentPrice.calculatePreDiscTotal(iChargeDays)); 
			assertEquals("Failed calculating pre-discount total", dExpectedPreDiscCharge, dPreDiscountCharge);
			
			// DISCOUNT AMOUNT - Calculate the discount amount using the pre-discount charge and the discount provided in the rental info. Then test if the total is correct.
			dDiscountAmount = FormatData.roundPrice(rental.calcDiscTotal(dDiscount,dPreDiscountCharge)); 
			assertEquals("Failed calculating discount total", dExpectedDiscountAmount, dDiscountAmount);
			
			// FINAL CHARGE - Calculate the final charge. Then test if the total is correct.
			dFinalCharge = FormatData.roundPrice(rental.calcFinalCharge(dPreDiscountCharge,dDiscountAmount)); 
			assertEquals("Failed calcuating total charge", dExpectedFinalChange, dFinalCharge);
		} 
	}
	
	@After
	public void printRentalAgreement() {
		// If the rental info provided was not valid, do not print the rental agreement, otherwise print the rental agreement data.
		if(bValidRentalDays && bValidDiscount) {
			System.out.println("***** RENTAL AGREEMENT *****"); // Added just to have an easy indicator in the console of a new rental agreement. 
			System.out.println("Tool code: " + sToolCode);
			System.out.println("Tool type: " + sToolType);
			System.out.println("Tool brand: " + sToolBrand);
			System.out.println("Rental days: " + iRentalDays);
			System.out.println("Check out date: " + sCheckOut);
			System.out.println("Due date: " + sDueDate);
			System.out.println("Daily rental charge: $" + FormatData.formatDecimal(dDailyCharge));
			System.out.println("Charge days: " + iChargeDays);
			System.out.println("Pre-discount charge: $" + FormatData.formatDecimal(dPreDiscountCharge));
			System.out.println("Discount percent: " + FormatData.formatPercent(dDiscount));
			System.out.println("Discount amount: $" + FormatData.formatDecimal(dDiscountAmount));
			System.out.println("Final charge: $" + FormatData.formatDecimal(dFinalCharge));
			System.out.print("\n\n\n"); // Added just for easier readability in the console. 
		}
	}
}