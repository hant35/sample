package com.panpages.bow.service.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptUtils {
	
	public static boolean generateBCryptPassword(String origPassword, int loop) {
	    if (origPassword == null || origPassword.trim().equals("")) {
	    	return false;
	    }
		
		loop = (loop == -1) ? 10 : loop;
	    int i = 0;
		while (i < loop) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(origPassword);
	 
			System.out.println(hashedPassword);
			i++;
		}
		
		return true;
	  }
	
	
}
