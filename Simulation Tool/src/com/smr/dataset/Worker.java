package com.smr.dataset;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.smr.sessionutility.CustomSession;

@SuppressWarnings("serial")
public class Worker extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomSession cS = new CustomSession();
		if(!cS.IsOpen(request)) response.sendRedirect("/pages/index.jsp");
		
    	String key = request.getParameter("key");
    	String user = request.getParameter("user");
    	long simulationProg = Long.valueOf(request.getParameter("simulationCount")).longValue();
	
        // Do something with key.
        Integer[] listNumber = new Integer[1000];
        
        for(int i=0;i<1000;i++){
        	
        	Random rand = new Random();
        	listNumber[i] = rand.nextInt(51);
        	
        }
        
		 DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		 
		 Filter filterSimulationID = new FilterPredicate("simulationCount",FilterOperator.EQUAL,	simulationProg);
		 Filter filterCurrentUser = new FilterPredicate("key",FilterOperator.EQUAL,	key);

		//Use CompositeFilter to combine multiple filters
		Filter heightRangeFilter =  CompositeFilterOperator.and(filterSimulationID, filterCurrentUser);	    
		 
    	Query q = new Query("EntitySimulation");
		q.setFilter(heightRangeFilter);
		
		PreparedQuery pqSimCount = ds.prepare(q);

		boolean canBeStored = true;
		
		try{
			for (Entity result : pqSimCount.asIterable(FetchOptions.Builder.withLimit(1))) {
				canBeStored = false;
				break;
			}
		}catch(Exception e){
			canBeStored = true;
		}
        
		if(canBeStored){
			
	        Entity entityCustom = new Entity("EntitySimulation");
	        entityCustom.setProperty("key", key);
	        entityCustom.setProperty("value", new ArrayList<Integer>(Arrays.asList(listNumber)));
	        entityCustom.setProperty("user", user);
	        entityCustom.setProperty("simulationCount", simulationProg);
	        
	        Date d = new Date();
	        entityCustom.setProperty("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(d));
	        
	        ds.put(entityCustom);
        }
    }
}

