package com.smr.sessionutility;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")

		public class CheckGoogleLogin extends HttpServlet {
		    @Override
		    public void doGet(HttpServletRequest req, HttpServletResponse resp)
		     throws IOException {
		    	
		        UserService userService = UserServiceFactory.getUserService();

		        resp.setContentType("text/html");
		        if (req.getUserPrincipal() != null) {

					 resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
					 resp.setHeader("Location", "/pages/index.jsp"); 
					 
					 CustomSession cSession = new CustomSession();
					 cSession.OpenSession(userService.getCurrentUser().getEmail(), req);
		        } else {
					 resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
					 resp.setHeader("Location", userService.createLoginURL("/CheckGoogleLogin")); 
		        }
		    }
}