package com.KoreaIT.java.BAM.Controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.BAM.dto.Article;
import com.KoreaIT.java.BAM.dto.Member;
import com.KoreaIT.java.BAM.utill.Util;

public class MemberController extends Controller {
	private Scanner sc;
	private List<Member> members;
	
	public MemberController(Scanner sc, List<Member> members) {
		this.sc = sc;
		this.members = members;
	}
	
	public void doAction(String cmd) {
		
	}

	public void dojoin() {
		
		int id = members.size() +1;
		String regDate = Util.getNowDateStr();
		String logId = null;
		
		while(true) {
			
			System.out.printf("로그인 ID : ");
			logId = sc.nextLine();
			
			
		
			if(isJoinableLoginId(logId) == false) {
				System.out.printf("%s은(는) 이미 사용 중인 아이디입니다\n",logId);
				continue;
			}
			break;
		}
		
		String logPass = null;
		String logPassOk = null;
		
		while(true) {
			
		
		System.out.printf("비밀번호 : ");
		logPass = sc.nextLine();
		System.out.printf("비밀번호 확인 : ");
		logPassOk = sc.nextLine();
		
			if(logPass.equals(logPassOk) == false) {
				System.out.println("비밀번호를 다시 입력해주세요");
				continue;
			}
			break;
		}
		System.out.printf("이름 : ");
		String name = sc.nextLine();
		
		Member member = new Member(id, regDate, logId, logPass, name);
		members.add(member);
		
		System.out.printf("%d번 회원님 환영합니다\n",id);
		
	}
	
	private boolean isJoinableLoginId(String logId) {
		 int index = getMemberIndexByLoginId(logId);
		 
		 if(index == -1) {
			 return true;
		 }
		 
		return false;
	}

	private int getMemberIndexByLoginId(String logId) {
		int i = 0;
		for(Member member : members) {
			if(member.loginId.equals(logId)) {
				return i;
			}
			i++;
		}
		
		return -1;
	}
	
	
		
	}


