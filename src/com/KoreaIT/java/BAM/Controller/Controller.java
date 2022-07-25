package com.KoreaIT.java.BAM.Controller;

import com.KoreaIT.java.BAM.dto.Member;

public abstract class Controller {

	public abstract void doAction(String cmd, String actionMethodName);
		
	public abstract void makeTestData();
	
	public static Member loginedMember;
	
	public static boolean isLogined() {
		return loginedMember != null; 
			
	}
	
}
