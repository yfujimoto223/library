package com.example.demo.domain.rental;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.demo.data.dto.貸出状況Dto;

class 貸出状況Specification {
	private static final int レンタル期間 = 7;
	private static final int 貸出可能冊数 = 5;

	private enum 貸出状況ステータス {
		貸出中(0L, "貸出中です。"), 返却済み(1L, "返却済みです。");

		// フィールドの定義
		public Long id;
		public String statusMessage;

		private 貸出状況ステータス(Long id, String statusMessage) { // コンストラクタの定義
			this.id = id;
			this.statusMessage = statusMessage;

		}
	}

	boolean 貸出されているかチェック(List<貸出状況Dto> 貸出状況DtoList) {
		if (貸出状況DtoList.stream().filter(a -> a.get貸出ステータス().equals(貸出状況ステータス.貸出中.id)).count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	boolean 貸出可能冊数を超えていないかかチェック(List<貸出状況Dto> 貸出状況DtoList) {
		if (貸出状況DtoList.size() == 0) {
			return true;
		} else if (貸出状況DtoList.stream().filter(a -> a.get貸出ステータス().equals(貸出状況ステータス.貸出中.id)).count() < 貸出可能冊数) {
			return true;
		} else {
			return false;
		}
	}

	boolean 全て返却済みになっているかチェック(List<貸出状況Dto> 貸出状況DtoList) {
		if (貸出状況DtoList.size() == 0) {
			return true;
		} else if (貸出状況DtoList.stream().filter(a -> a.get貸出ステータス().equals(貸出状況ステータス.貸出中.id)).count() == 0) {
			return true;
		} else {
			return false;
		}
	}

	Long 返却冊数を取得(List<貸出状況Dto> 貸出状況DtoList) {
		return 貸出状況DtoList.stream().filter(a -> a.get貸出ステータス().equals(貸出状況ステータス.返却済み.id)).count();
	}

	貸出状況Dto 貸出中ステータスを設定(貸出状況Dto dto) {
		dto.set貸出ステータス(貸出状況ステータス.貸出中.id);
		return dto;
	}

	貸出状況Dto 返却ステータスを設定(貸出状況Dto dto) {
		dto.set貸出ステータス(貸出状況ステータス.返却済み.id);
		return dto;
	}

	貸出状況Dto 申込日と返却予定日を設定(貸出状況Dto dto) {
		Calendar cdr = Calendar.getInstance();
		Date nowDate = new Date();
		dto.set申込日(new java.sql.Date(nowDate.getTime()));
		cdr.add(Calendar.DATE, レンタル期間);
		Date returnDate = cdr.getTime();
		dto.set返却予定日(new java.sql.Date(returnDate.getTime()));
		return dto;
	}

	String 貸出状況ステータスメッセージを取得(貸出状況Dto dto) {
		if (貸出状況ステータス.貸出中.id.equals(dto.get貸出ステータス())) {
			return 貸出状況ステータス.貸出中.statusMessage;
		} else if (貸出状況ステータス.返却済み.id.equals(dto.get貸出ステータス())) {
			return 貸出状況ステータス.返却済み.statusMessage;
		}
		return "";
	}
}
