package com.smr.logger;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.log.*;
import com.smr.sessionutility.CustomSession;

import java.io.PrintWriter;
import java.util.Calendar;

// Get request logs along with their app log lines and display them 5 at
// a time, using a Next link to cycle through to the next 5.

@SuppressWarnings("serial")
public class getLogInformation extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		CustomSession cS = new CustomSession();
		if(!cS.IsOpen(req)) resp.sendRedirect("/pages/index.jsp");
		
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();

		// This retrieves the offset from the Next link upon user click.
		String offset = req.getParameter("offset");

		// We want the App logs for each request log
		LogQuery query = LogQuery.Builder.withDefaults();
		query.includeAppLogs(true);

		// Set the offset value retrieved from the Next link click.
		if (offset != null) {
			query.offset(offset);
		}

		// This gets filled from the last request log in the iteration
		int i = 0;

		/*
    Format:

    1 Column: Identifier
    2 Column: IP
    3 Column: Method
    4 Column: Resource
    5 Column: Date
    6 Column: Agent
    7 Column: Latency

		 */
		writer.println("{");
		writer.println("\"data\": [");


		// Display a few properties of each request log.
		for (RequestLogs record : LogServiceFactory.getLogService().fetch(query)) {
			// writer.println("<br />REQUEST LOG <br />");
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(record.getStartTimeUsec() / 1000);

			if(i==0){       
				writer.println("[");
				writer.println("\""+i+"\",");
				writer.println("\""+record.getIp()+"\",");
				writer.println("\""+record.getMethod()+"\",");
				writer.println("\""+record.getResource()+"\",");
				writer.println("\""+String.format("%s",cal.getTime().toString())+"\",");
				writer.println("\""+record.getUserAgent()+"\",");
				writer.println("\""+record.getLatencyUsec()+"\"");
				writer.println("]");
			} else {
				writer.println(",[");
				writer.println("\""+i+"\",");
				writer.println("\""+record.getIp()+"\",");
				writer.println("\""+record.getMethod()+"\",");
				writer.println("\""+record.getResource()+"\",");
				writer.println("\""+String.format("%s",cal.getTime().toString())+"\",");
				writer.println("\""+record.getUserAgent()+"\",");
				writer.println("\""+record.getLatencyUsec()+"\"");
				writer.println("]");
			}

			i++;
//			if(i==1000){
//				break;
//			}

		} // for each record

		writer.println("]"); 
		writer.println("}");  
	}  // end doGet
} //end class




