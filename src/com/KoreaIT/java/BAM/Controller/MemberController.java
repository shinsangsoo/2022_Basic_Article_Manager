package com.KoreaIT.java.BAM.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.BAM.dto.Member;
import com.KoreaIT.java.BAM.utill.Util;

public class MemberController extends Controller {
	private Scanner sc;
	private List<Member> members;
	private String cmd;
	private String actionMethodName;	
	public MemberController(Scanner sc){
		this.sc = sc;	
		members = new ArrayList<>();
	}
	
	public void doAction(String cmd, String actionMethodName) {
		this.cmd = cmd;
		this.actionMethodName = actionMethodName;
		
		switch(actionMethodName) {
		case "join" :
			dojoin();
			break;				
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		case "profile":
			showProfile();
			break;
		default:
			System.out.println("존재하지 않는 명령어입니다");
			
			
		}
	}
	
	private void showProfile() {
		if(loginedMember == null) {
			System.out.println("로그아웃 상태입니다");
			return;
		}else {
			System.out.printf("로그인 아이디 : %s\n", loginedMember.loginId);
			System.out.printf("이름 : %s\n", loginedMember.name);
		}
		
	}

	private void dojoin() {
		
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
		
		System.out.printf("%s 회원님 환영합니다\n",name);
		
	}
	private void doLogin() {
		if(isLogined()) {
			System.out.println("이미 로그인 상태입니다.");
			return;
		}
		System.out.printf("로그인 ID : ");
		String loginId = sc.nextLine();
		System.out.printf("비밀번호 : ");
		String loginPw = sc.nextLine();
		
		Member member = getMemberByLoginId(loginId);
		
		if(member == null) {
			System.out.println("해당 회원은 존재하지 않습니다");
			return;
		}
		if(member.loginPw.equals(loginPw) == false) {
			System.out.println("비밀번호를 확인해주세요");
			return;
		}
		
		loginedMember = member;
		System.out.printf("로그인 성공!, %s님 환영합니다.\n", loginedMember.name);
			
		
	}
	private void doLogout() {
		if(isLogined() == false) {
			System.out.println("로그인 상태가 아닙니다");
			return;
		}
		
		loginedMember = null;
		System.out.println("로그아웃 되었습니다");
	}
	
	
	

	private Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return null;
		}

		return members.get(index);
		
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
	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");

		members.add(new Member(1, Util.getNowDateStr(), "test1", "test1", "홍길동"));
		members.add(new Member(2, Util.getNowDateStr(), "test2", "test2", "김철수"));
		members.add(new Member(3, Util.getNowDateStr(), "test3", "test3", "박영수"));
		}
	
	
		
	}


