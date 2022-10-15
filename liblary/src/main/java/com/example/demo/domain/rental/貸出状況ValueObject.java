package com.example.demo.domain.rental;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.data.dto.予約者Dto;
import com.example.demo.data.dto.出版社Dto;
import com.example.demo.data.dto.書籍Dto;
import com.example.demo.data.dto.貸出状況Dto;

public class 貸出状況ValueObject {
	private 予約者Dto 予約者;
	private List<貸出本一覧> 貸出本構造体 = new ArrayList<貸出本一覧>();
	private Long 貸出冊数;
	private Long 返却冊数;

	貸出状況ValueObject(予約者Dto 予約者, List<書籍Dto> 書籍DtoList, List<貸出状況Dto> 貸出状況DtoList, List<出版社Dto> 出版社DtoList) {
		this.予約者 = 予約者;
		this.貸出冊数 = 書籍DtoList.stream().count();
		貸出状況Specification spec = new 貸出状況Specification();
		this.返却冊数 = spec.返却冊数を取得(貸出状況DtoList);
		for (書籍Dto 書籍 : 書籍DtoList) {
			貸出状況Dto 貸出状況 = 貸出状況DtoList.stream().filter(a -> a.get書籍ID().equals(書籍.get書籍ID())).findFirst().orElse(null);
			出版社Dto 出版社 = 出版社DtoList.stream().filter(a -> a.get出版社ID().equals(書籍.get出版社ID())).findFirst().orElse(null);
			貸出本構造体.add(new 貸出本一覧(書籍, 貸出状況, 出版社));
		}
	}

	public Long get貸出冊数() {
		return 貸出冊数;
	}

	public Long get返却冊数() {
		return 返却冊数;
	}

	public 予約者Dto get予約者() {
		return 予約者;
	}

	public List<書籍Dto> レンタルしている本の書籍情報を取得() {
		List<書籍Dto> result = new ArrayList<書籍Dto>();
		for (貸出本一覧 tmp : this.貸出本構造体) {
			result.add(tmp.get書籍Dtoオブジェクト());
		}
		return result;
	}

	public 書籍Dto レンタルしている本の書籍情報を取得(Long 書籍ID) {
		書籍Dto result = new 書籍Dto();
		for (貸出本一覧 tmp : this.貸出本構造体) {
			if (tmp.get書籍Dtoオブジェクト().get書籍ID().equals(書籍ID))
				return tmp.get書籍Dtoオブジェクト();
		}
		return result;
	}

	public List<出版社Dto> レンタルしている本の出版社情報を取得() {
		List<出版社Dto> result = new ArrayList<出版社Dto>();
		for (貸出本一覧 tmp : this.貸出本構造体) {
			result.add(tmp.get出版社Dtoオブジェクト());
		}
		return result;
	}

	public 出版社Dto レンタルしている本の出版社情報を取得(Long 書籍ID) {
		出版社Dto result = new 出版社Dto();
		for (貸出本一覧 tmp : this.貸出本構造体) {
			if (tmp.get書籍Dtoオブジェクト().get書籍ID().equals(書籍ID))
				return tmp.出版社Dtoオブジェクト;
		}
		return result;
	}

	public List<貸出状況Dto> レンタルしている本の貸出情報を取得() {
		List<貸出状況Dto> result = new ArrayList<貸出状況Dto>();
		for (貸出本一覧 tmp : this.貸出本構造体) {
			result.add(tmp.get貸出状況Dtoオブジェクト());
		}
		return result;
	}

	public String レンタルしている本の貸出状況ステータスメッセージを取得(Long 書籍ID) {
		for (貸出本一覧 tmp : this.貸出本構造体) {
			if (tmp.get書籍Dtoオブジェクト().get書籍ID().equals(書籍ID))
				return tmp.get貸出状況ステータスメッセージ();
		}
		return "";
	}

	public 貸出状況Dto レンタルしている本の貸出情報を取得(Long 予約ID) {
		貸出状況Dto result = new 貸出状況Dto();
		for (貸出本一覧 tmp : this.貸出本構造体) {
			if (tmp.get貸出状況Dtoオブジェクト().get予約ID().equals(予約ID)) {
				return tmp.get貸出状況Dtoオブジェクト();
			}
		}
		return result;
	}

	private class 貸出本一覧 {
		private 書籍Dto 書籍Dtoオブジェクト;
		private 貸出状況Dto 貸出状況Dtoオブジェクト;
		private 出版社Dto 出版社Dtoオブジェクト;

		private 貸出本一覧(書籍Dto 書籍Dtoオブジェクト, 貸出状況Dto 貸出状況Dtoオブジェクト, 出版社Dto 出版社Dtoオブジェクト) {
			this.書籍Dtoオブジェクト = 書籍Dtoオブジェクト;
			this.貸出状況Dtoオブジェクト = 貸出状況Dtoオブジェクト;
			this.出版社Dtoオブジェクト = 出版社Dtoオブジェクト;
		}

		private 書籍Dto get書籍Dtoオブジェクト() {
			return 書籍Dtoオブジェクト;
		}

		private 貸出状況Dto get貸出状況Dtoオブジェクト() {
			return 貸出状況Dtoオブジェクト;
		}

		private 出版社Dto get出版社Dtoオブジェクト() {
			return 出版社Dtoオブジェクト;
		}

		private String get貸出状況ステータスメッセージ() {
			貸出状況Specification spec = new 貸出状況Specification();
			return spec.貸出状況ステータスメッセージを取得(this.貸出状況Dtoオブジェクト);
		}

	}
}
