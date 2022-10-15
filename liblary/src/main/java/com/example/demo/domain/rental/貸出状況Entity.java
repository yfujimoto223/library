package com.example.demo.domain.rental;

import com.example.demo.data.dto.予約者Dto;
import com.example.demo.data.dto.書籍Dto;

class 貸出状況Entity {
	private String 予約者名;
	private String 書籍名;

	public 貸出状況Entity(予約者Dto 予約者, 書籍Dto 書籍) {
		this.予約者名 = 予約者.get名前();
		this.書籍名 = 書籍.get書名();
	}

	public String get予約者名() {
		return 予約者名;
	}

	public String get書籍名() {
		return 書籍名;
	}

}
