package com.google.training.helloworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Results page code
public class CompanyInfo {
    public String message = " ";
    public String title = " ";
    public String total = " ";
    public Integer proCount = 0;
    public Integer adminCount = 0;
    public Integer leadCount = 0;
    public Integer operativesCount = 0;
    public Integer techCount = 0;
    public Integer craftCount = 0;
    public Integer salesCount = 0;
    public Integer serviceCount = 0;
    public Integer execCount = 0;
    public Integer laborCount = 0;
    public ArrayList<String> aList;
    HashMap<String, Integer> chartData = new HashMap<>();  
    
    public CompanyInfo (HashMap<String, List<String>> map, String titleVar) {
	   
	   HashMap<String, Integer> chartData = new HashMap<>();
	   ArrayList<Integer> countList = new ArrayList<Integer>();
    
        for( List<String> value : map.values()) { 	
        	//change to ints
        	int one = Integer.parseInt(value.get(0));
        int two = Integer.parseInt(value.get(1));
        //add them together
        int three = one + two;
        countList.add(three);
        }
        
        //make new dataset
        int index = 0;
        for (String key : map.keySet()) {
        	if(key == "n/a" || key == "Previous_totals") {
        		index++;
        	} else {
        		chartData.put(key, countList.get(index));
        	}
        	index++;
        }
        
    
        //set vars in .js file
        
        //var - graph counts
        	this.salesCount = chartData.get("Sales workers");
        	
        	this.laborCount = chartData.get("laborers and helpers");
  
        	this.proCount = chartData.get("Professionals");
        	
        	this.execCount = chartData.get("Executive/Senior Manager");
        	
        	this.adminCount = chartData.get("Administrative support");
        	
        	this.techCount = chartData.get("Technicians");
        	
        	this.operativesCount = chartData.get("operatives");

        	this.craftCount = chartData.get("Craft workers");
        	
        	this.serviceCount = chartData.get("Service workers");

        	this.leadCount = chartData.get("Lead/Manager");
     
        this.title = titleVar;
        
       //var - raw stats
        String prettyChartData = chartData.toString();
        prettyChartData = prettyChartData.replaceAll(",", "         ");
        this.message = prettyChartData;
    }
}