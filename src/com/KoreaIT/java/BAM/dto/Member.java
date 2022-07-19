package com.KoreaIT.java.BAM.dto;

public class Member {
	public int id;
	public String regDate;
	public String title;
	public String body;
	public int hit;

	public Member(int id, String regDate, String title, String body) {
		this(id, regDate, title, body, 0);
	}

	public Member(int id, String regDate, String title, String body, int hit) {
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
		this.hit = hit;
	}

	public void increaseHit() {
		hit++;
	}
}