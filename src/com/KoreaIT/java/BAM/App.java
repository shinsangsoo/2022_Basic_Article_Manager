package com.KoreaIT.java.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.BAM.dto.Article;
import com.KoreaIT.java.BAM.dto.Member;
import com.KoreaIT.java.BAM.utill.Util;

public class App {
	
	private List<Article> articles;
	private List<Member> members;

	public App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void run() {
		System.out.println("==프로그램 시작==");

		makeTestData();

		Scanner sc = new Scanner(System.in);


		while (true) {

			System.out.printf("명령어 ) ");
			String cmd = sc.nextLine().trim();
			if (cmd.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (cmd.equals("exit")) {
				break;
			}

			if (cmd.equals("article write")) {
				int id = articles.size() + 1;
				String regDate = Util.getNowDateStr();
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, title, body);
				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다\n", id);

			}
			if (cmd.equals("member join")) {
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
		
			
			
				
			else if (cmd.startsWith("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시물이 없습니다");
					continue;
				}
				
				String serchKeyword = cmd.substring("article List".length()).trim();
				
				System.out.printf("검색어 : %s\n", serchKeyword);
				List<Article> forPrintArticles = articles;
				
				if (serchKeyword.length() > 0) {
					forPrintArticles = new ArrayList<>();
					
					for(Article article : articles) {
						if(article.title.contains(serchKeyword)) {
							forPrintArticles.add(article);
						}
					}
					
				
					
					if (forPrintArticles.size() == 0) {
						System.out.println("해당 검색어를 포함하는 게시물을 찾을 수 없습니다");
						continue;
					}
				}
				
				
											
				System.out.printf("번호     |   제목     |   	  %9s        |   조회\n", "날짜");
				for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
					Article article = forPrintArticles.get(i);

					System.out.printf("%7d | %6s   | %5s   | %5d\n", article.id, article.title, article.regDate,
							article.hit);
				}
				

			} else if (cmd.startsWith("article detail ")) {

				String[] cmdBits = cmd.split(" ");

				int id = Integer.parseInt(cmdBits[2]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 없습니다\n", id);
					continue;
				}

				foundArticle.increaseHit();

				System.out.printf("번호 : %d\n", foundArticle.id);
				System.out.printf("날짜 : %s\n", foundArticle.regDate);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.body);
				System.out.printf("조회 : %d\n", foundArticle.hit);

			} else if (cmd.startsWith("article modify ")) {

				String[] cmdBits = cmd.split(" ");

				int id = Integer.parseInt(cmdBits[2]);

				Article foundArticle = getArticleById(id);


				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 없습니다\n", id);
					continue;
				}
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				foundArticle.title = title;
				foundArticle.body = body;

				System.out.printf("%d번 게시물을 수정했습니다\n", id);

			} else if (cmd.startsWith("article delete ")) {

				String[] cmdBits = cmd.split(" ");

				int id = Integer.parseInt(cmdBits[2]);

				int foundIndex = getArticleIndexById(id);

				if (foundIndex == -1) {
					System.out.printf("%d번 게시물은 없습니다\n", id);
					continue;
				}

				articles.remove(foundIndex);
				System.out.printf("%d번 게시물을 삭제했습니다\n", id);

			} else {
				System.out.println("존재하지 않는 명령어입니다");
			}
		}

		System.out.println("==프로그램 끝==");
		sc.close();
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

	private int getArticleIndexById(int id) {
		int i = 0;
		for (Article article : articles) {
			
			if (article.id == id) {
				return i;
				
			}
			i++;
		}

		return -1;
	}

	private Article getArticleById(int id) {
//		====v3====		
		int index = getArticleIndexById(id);
		
		if (index != -1) {
			return articles.get(index);
			}
//		====v1====		
//		for (int i = 0; i < articles.size(); i++) {
//			Article article = articles.get(i);
//
//			if (article.id == id) {
//				return article;
//				
//			}
//		}
//		====v2====		
//		for (Article article : articles) {
//			
//			if (article.id == id) {
//				return article;
//				
//			}
//		}
//		
		return null;
	}

	private void makeTestData() {
		System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");

		articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 33));
	}
	 
		
	}
	


