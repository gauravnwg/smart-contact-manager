package com.scm.helper;



import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
       
//	public static void removeMessage() {
//		try {
//			System.out.println("Removing message from session...");
//			 HttpSession session = ((ServletRequestAttributes)RequestContextHolder
//					 .getRequestAttributes()).getRequest().getSession();
//			 session.removeAttribute("message");
//			
//		} catch (Exception e) {
//	      	System.out.println("Error removing message from session" + e);
//		}
//		
//	}
	// second way to remove message
	private final HttpSession session;

   
    public SessionHelper(HttpSession session) {
        this.session = session;
    }

    public void removeMessage() {
        try {
            System.out.println("Removing message from session...");
            session.removeAttribute("message");
        } catch (Exception e) {
            System.out.println("Error removing message from session: " + e.getMessage());
        }
    }
}
	

