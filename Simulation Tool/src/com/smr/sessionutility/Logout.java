package com.smr.sessionutility;
import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class Logout extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
			 CustomSession cSession = new CustomSession();
			 cSession.CloseSession(req);;
		        
			 resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
			 resp.setHeader("Location", "/pages/login.html"); 
			 
		    UserService userService = UserServiceFactory.getUserService();

	        resp.setContentType("text/html");
	        if (req.getUserPrincipal() != null) {

				 resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
				 resp.setHeader("Location", userService.createLogoutURL("/pages/login.html")); 
	        } 
	}
		
}
	
