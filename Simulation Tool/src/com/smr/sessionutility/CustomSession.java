package com.smr.sessionutility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CustomSession{
	
	public CustomSession(){
		
	}
	
	protected void OpenSession(String user, HttpServletRequest req){
		
		String currentUser;
		
		//check if someone is already connected
		HttpSession session = req.getSession();	     
		currentUser = (String) session.getAttribute("user");
		
		if(currentUser!=null){			//someone is connected
			//.... 
			//can choose how to manage this case
			//for the sake of simplicity, I will modify "user" attribute value
			
			session.setAttribute("user", user);
			
			
		} else {						//nobody is connected
			
			session.setAttribute("user", user);
			
		}
		
	}

	protected void CloseSession(HttpServletRequest req){
		HttpSession session = req.getSession();	     
		session.setAttribute("user",null);
	}
	
	public boolean IsOpen(HttpServletRequest req){
		
		boolean result;
		
		HttpSession session = req.getSession();	     
		result = ((String) session.getAttribute("user")!=null)?true:false;
			
		return result;	
	}
	
	public String getCurrentUser(HttpServletRequest req){
		HttpSession session = req.getSession();	
		return ((String) session.getAttribute("user")!=null)?(String) session.getAttribute("user"):"";
	}
	
	
}