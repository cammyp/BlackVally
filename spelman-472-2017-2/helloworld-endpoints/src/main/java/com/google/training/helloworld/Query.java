// Imports the Google Cloud client library
package com.google.training.helloworld;

import java.util.UUID;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryResponse;
import com.google.cloud.bigquery.QueryResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class Query {
	
	private final static Logger log =  Logger.getLogger(Query.class.getName());
	private static HashMap<String, String> companyTable = new HashMap<>();
	
	public static CompanyInfo lookUp(String companyVar, String raceVar) {
		
		companyTable.put("HPE", "vallydata.vallydata_hh");
		companyTable.put("Lyft", "vallydata.vallydata_ll");
		companyTable.put("Uber", "vallydata.vallydata_uv");
		companyTable.put("View", "vallydata.vallydata_uv");
		companyTable.put("eBay", "vallydata.vallydata_ef");
		companyTable.put("Adobe", "vallydata.vallydata_aa");
		companyTable.put("Apple", "vallydata.vallydata_ac");
		companyTable.put("Cisco", "vallydata.vallydata_ac");
		companyTable.put("Intel", "vallydata.vallydata_ii");
		companyTable.put("Airbnb", "vallydata.vallydata_aa");
		companyTable.put("Google", "vallydata.vallydata_g2");
		companyTable.put("Intuit", "vallydata.vallydata_ii");
		companyTable.put("Nvidia", "vallydata.vallydata_mn");
		companyTable.put("Square", "vallydata.vallydata_st");
		companyTable.put("23andMe", "vallydata.vallydata_g2");
		companyTable.put("HP Inc.", "vallydata.vallydata_hh");
		companyTable.put("Twitter", "vallydata.vallydata_st");
		companyTable.put("Facebook", "vallydata.vallydata_ef");
		companyTable.put("LinkedIn", "vallydata.vallydata_ll");
		companyTable.put("Pinterest", "vallydata.vallydata_ps");
		companyTable.put("MobileIron", "vallydata.vallydata_mn");
		companyTable.put("Salesforce", "vallydata.vallydata_ps");
		
		HashMap<String, List<String>> map = new HashMap<>();
	
		BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
	    QueryJobConfiguration queryConfig =
	        QueryJobConfiguration.newBuilder(
	          
	        		"SELECT job,count FROM " + companyTable.get(companyVar) + " WHERE company = \"" + companyVar + "\" AND race =  \"" + raceVar + "\";")
	            // Use standard SQL syntax for queries.
	            // See: https://cloud.google.com/bigquery/sql-reference/
	            .setUseLegacySql(false)
	            .build();
	    
	    // Create a job ID so that we can safely retry.
	    JobId jobId = JobId.of(UUID.randomUUID().toString());
	    Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

	    // Wait for the query to complete.
	    try {
			queryJob = queryJob.waitFor();
		} catch (InterruptedException e) {
			return new CompanyInfo(map, companyVar);
		}
	    
	    // Check for errors
	    if (queryJob == null) {
	      throw new RuntimeException("Job no longer exists");
	    } else if (queryJob.getStatus().getError() != null) {
	      // You can also look at queryJob.getStatus().getExecutionErrors() for all errors, not just the latest one.
	      throw new RuntimeException(queryJob.getStatus().getError().toString());
	    }
   
	    // Get the results.
	    QueryResponse response = bigquery.getQueryResults(jobId);
	    QueryResult result = response.getResult();
	    
	    if (result == null) { log.warning("no results"); }
	    
	      // Print rows from query
	      for (List<FieldValue> row : result.iterateAll()) {
	        String job = row.get(0).getStringValue();
	        String count = row.get(1).getStringValue();
	        
	        if (map.containsKey(job)) {
                map.get(job).add(count);
            } else {
                map.put(job, new ArrayList<String>(Arrays.asList(count)));
            }
	       
	      }
	 
	    return new CompanyInfo(map, companyVar);
	    	    
	  }
		
	}
