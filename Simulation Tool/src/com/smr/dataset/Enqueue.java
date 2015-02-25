
package com.smr.dataset;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.smr.sessionutility.CustomSession;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.*;

@SuppressWarnings("serial")
public class Enqueue extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		CustomSession cS = new CustomSession();
		if (cS.IsOpen(request)){

			Date startSimulation = new Date();
			
			//deleteOldIstances(request,startSimulation.getTime());

			// Add the task to the default queue.
			Queue queue = QueueFactory.getDefaultQueue();

			for(int i=0;i<100;i++){
				queue.add(withUrl("/worker").param("key", i+"").param("user", cS.getCurrentUser(request)).param("simulationCount", ""+startSimulation.getTime()));
			}
			response.sendRedirect("/pages/simulationlunched.html");
		} else {
			response.sendRedirect("/pages/login.html");
		}
		
		
	}

	private void deleteOldIstances(HttpServletRequest request,long startSimulation) {

		CustomSession cS = new CustomSession();
		String currentUser = cS.getCurrentUser(request);

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("EntitySimulation");
		q.setFilter(new Query.FilterPredicate("user",Query.FilterOperator.EQUAL,currentUser));
		q.setFilter(new Query.FilterPredicate("simulationCount",Query.FilterOperator.LESS_THAN,startSimulation));

		PreparedQuery pq = ds.prepare(q);

		for (Entity result : pq.asIterable()) {     		

			ds.delete(result.getKey());
			// ...
		}

	}
	
}