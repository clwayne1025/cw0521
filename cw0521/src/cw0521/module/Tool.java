package cw0521.module;

import java.util.*;

import cw0521.testSuite.TestMain;

public class Tool{

	private String sType;
	private String sBrand;
	private String sCode;

	// Constructor
	public Tool(String sType, String sBrand, String sCode){
		this.sType = sType;
		this.sBrand = sBrand;
		this.sCode = sCode;
	}
	
	// Get the tool type of an instance
	public String getType() {
		return sType; 
	}
	
	// Get the brand of an instance
	public String getBrand() {
		return sBrand; 
	}
	
	// Get the code of an instance
	public String getCode() {
		return sCode; 
	}
	
	// Find the instance of the tool data based on the unique tool code
	public static Tool findTool(String sToolCode) { 
		List<Tool> lTools = TestMain.getToolList(); 
		
  		for(int i=0; i < lTools.size(); i++) {
  			Tool curTool = lTools.get(i); 
  			
  			if(curTool.getCode() == sToolCode) {
  				return curTool; 
  			} 
  		}
  	
  		return null; 
  	}
}
