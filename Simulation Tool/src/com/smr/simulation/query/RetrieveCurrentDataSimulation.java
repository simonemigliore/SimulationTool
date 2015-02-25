package com.smr.simulation.query;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
import com.google.appengine.api.datastore.Query.SortDirection;
import com.smr.sessionutility.CustomSession;


@SuppressWarnings("serial")
public class RetrieveCurrentDataSimulation extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CustomSession cS = new CustomSession();
		if(!cS.IsOpen(request)) response.sendRedirect("/pages/index.jsp");
		
		String cUser = cS.getCurrentUser(request);
		long currentSimulationCount=0;
		boolean completed = false;

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		//retrive startpoint...
		Query qSimulationCount = new Query("EntitySimulation");
		qSimulationCount.setFilter(new Query.FilterPredicate("user",Query.FilterOperator.EQUAL,cUser));
		qSimulationCount.addSort("simulationCount", SortDirection.DESCENDING);
		
		PreparedQuery pqSimCount = ds.prepare(qSimulationCount);

		for (Entity result : pqSimCount.asIterable(FetchOptions.Builder.withLimit(1))) {
			currentSimulationCount = ((long) result.getProperty("simulationCount")>currentSimulationCount)?(long) result.getProperty("simulationCount"):currentSimulationCount;
		}
		
		//check if have been stored 100 series with this currentSimulationCount

		//retrive startpoint...
		Query qCheckSeries = new Query("EntitySimulation");
		qCheckSeries.setFilter(new Query.FilterPredicate("user",Query.FilterOperator.EQUAL,cUser));
		qCheckSeries.setFilter(new Query.FilterPredicate("simulationCount",Query.FilterOperator.EQUAL,currentSimulationCount));
				
		qCheckSeries.addSort("simulationCount");
		PreparedQuery pqCheck = ds.prepare(qCheckSeries);

		int countCurrentSimEntity = 0;
		for (@SuppressWarnings("unused") Entity result : pqCheck.asIterable(FetchOptions.Builder.withLimit(100))) {
			countCurrentSimEntity++;    	
		}

		completed = (countCurrentSimEntity==100)?true:false;

		if(completed){
			PreparedQuery pq = pqCheck;

			int[] elementi = new int[52];
			int numeriDifferenti = 0;
			Date timestampMIN, timestampMAX, tempTimestamp;
			timestampMAX = new Date();
			timestampMIN = new Date();

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);
			try {
				timestampMIN = format.parse("2020-12-31 00:00:00.000");
				timestampMAX = format.parse("1980-12-31 00:00:00.000");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			//reset array
			for(int i = 0;i<52; i++){
				elementi[i]=0;
			}


			//for each series
			for (Entity result : pq.asIterable()) {
				String abc = ""+ result.getProperty("value");
				abc = abc.replaceAll("\\[", "");
				abc = abc.replaceAll("\\]", "");

				String[] newABC =  abc.split(",");

				for (String currentValue : newABC) {
					elementi[Integer.parseInt(currentValue.trim())]++;
				}

				try {
					tempTimestamp = format.parse((String) result.getProperty("timestamp"));

					timestampMAX = (tempTimestamp.after(timestampMAX))?tempTimestamp:timestampMAX;
					timestampMIN = (tempTimestamp.before(timestampMIN))?tempTimestamp:timestampMIN;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				// ...
			}

			int numeroApparsoPiuVolte = 1;

			for(int i=0;i<52;i++){
				//quanti numeri differenti surante la simulazione
				if(elementi[i]>0) numeriDifferenti++; 

				//il numero comparso più volte
				numeroApparsoPiuVolte = (elementi[numeroApparsoPiuVolte]>elementi[i])?numeroApparsoPiuVolte:i;
			}
			
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			String graph = "[";
			for(int i = 0; i<51;i++){
				graph += (i<50)?(df.format((double)elementi[i]/1000))+"-":(df.format((double)elementi[i]/1000))+"";
			}
			graph +="]";
			
			graph = graph.replace(",", ".");
			graph = graph.replace("-", ",");
			
			
			
			final PrintWriter out = response.getWriter(); 
			//out.print("<div id=\"differentNum\">Sono stati generati <strong>"+numeriDifferenti+"</strong> numeri differenti.</div>");
			//out.print("["+percentageStringList+"]");
			//out.print("<div id=\"mostApp\">Il numero maggiormente estratto: <strong>"+numeroApparsoPiuVolte+"</strong></div>");
			//out.print("<div id=\"elapseSimul\">Tempo di esecuzione della simulazione: <strong>"+(timestampMAX.getTime() - timestampMIN.getTime())+"</strong> millisecondi.</div>");
			out.print("{\"graph\" : "+graph+", \"differentNum\" : \"Sono stati generati <strong>"+numeriDifferenti+"</strong> numeri differenti.\", \"gauge\" : ["+numeroApparsoPiuVolte+"], \"elapseSimul\" : \""+(timestampMAX.getTime() - timestampMIN.getTime())+" millisecondi.\"}");
		}
		else{
			final PrintWriter out = response.getWriter(); 
			out.print("{\"graph\" : [], \"differentNum\" : \"Simulazione in corso...\", \"gauge\" : [], \"elapseSimul\" : \"Simulazione in corso...\"}");
		}
	}
}