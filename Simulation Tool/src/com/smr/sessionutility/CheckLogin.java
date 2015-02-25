package com.smr.sessionutility;
import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CheckLogin extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		/*
		 * if an authenticated user is logged, redirect the request in a current page,
		 * otherwise, propose a login web page
		 */
		
		String email, password;
		
		email = req.getParameter("email");
		password = req.getParameter("password");
		
		if(checkCredential(email, password)){
			 resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
			 resp.setHeader("Location", "/pages/index.jsp");
			 
			 CustomSession cSession = new CustomSession();
			 cSession.OpenSession(email, req);
		        
		} else {
			 resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
			 resp.setHeader("Location", "/pages/login.html?errorCode=1"); 
		}
		
	}
	
	
	private boolean checkCredential(String email, String password){
		/*
		 * is possible to implement different kind of check!
		 * in this case, we use a basic check...
		 */
		boolean result = (email.equalsIgnoreCase("test@test.test")&&password.equals("1234"))?true:false;
		
		return result;
	}
}
