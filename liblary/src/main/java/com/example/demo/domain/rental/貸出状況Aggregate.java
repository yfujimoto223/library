package com.example.demo.domain.rental;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data.dto.予約者Dto;
import com.example.demo.data.dto.出版社Dto;
import com.example.demo.data.dto.書籍Dto;
import com.example.demo.data.dto.貸出状況Dto;
import com.example.demo.data.service.予約者DtoService;
import com.example.demo.data.service.出版社DtoService;
import com.example.demo.data.service.書籍DtoService;
import com.example.demo.data.service.貸出状況DtoService;

/**貸出状況Aggregate**/
@Service
public class 貸出状況Aggregate {
	@Autowired
	予約者DtoService 予約者DtoService;
	@Autowired
	出版社DtoService 出版社DtoService;
	@Autowired
	書籍DtoService 書籍DtoService;
	@Autowired
	貸出状況DtoService 貸出状況DtoService;

	private enum エラーメッセージ {
		貸出失敗("本が既に貸出されています。"), 貸出冊数リミット("これ以上は本を貸出できません。");

		public String message; // フィールドの定義

		private エラーメッセージ(String message) { // コンストラクタの定義
			this.message = message;
		}
	}

	/**本を貸し出す
	 * @throws Exception **/
	public void 本を貸出(Long 貸出券番号, Long 書籍Id) throws Exception {
		予約者Dto 予約者 = 予約者DtoService.findOneBy貸出券番号(貸出券番号).orElse(null);
		貸出状況Specification check = new 貸出状況Specification();
		if (!check.貸出可能冊数を超えていないかかチェック(貸出状況DtoService.findBy予約者Id(予約者.get予約者ID()))) {
			throw new Exception(エラーメッセージ.貸出冊数リミット.message);
		}
		if (!check.貸出されているかチェック(貸出状況DtoService.findBy書籍Id(書籍Id))) {
			貸出状況Dto registDto = new 貸出状況Dto();
			registDto.set予約者ID(予約者.get予約者ID());
			registDto.set書籍ID(書籍Id);
			registDto = check.申込日と返却予定日を設定(registDto);
			registDto = check.貸出中ステータスを設定(registDto);
			貸出状況DtoService.save(registDto);
		} else {
			throw new Exception(エラーメッセージ.貸出失敗.message);
		}
	}

	/**本を返却
	 * @throws Exception **/
	public void 本を返却(List<Long> 予約IdList, Long 貸出券番号) throws Exception {
		予約者Dto 予約者 = 予約者DtoService.findOneBy貸出券番号(貸出券番号).orElse(null);
		for (Long 予約ID : 予約IdList) {
			貸出状況Dto tmp = 貸出状況DtoService.findOne(予約ID).orElse(null);
			貸出状況Specification statusChange = new 貸出状況Specification();
			statusChange.返却ステータスを設定(tmp);
			貸出状況DtoService.update(tmp);
		}
		貸出状況Specification check = new 貸出状況Specification();
		//全ての本が返却済みの場合、貸出状況DBから予約者IDに関するデータを削除する。
		if (check.全て返却済みになっているかチェック(貸出状況DtoService.findBy予約者Id(予約者.get予約者ID()))) {
			;
			for (貸出状況Dto tmp : 貸出状況DtoService.findBy予約者Id(予約者.get予約者ID())) {
				貸出状況DtoService.delete(tmp);
			}
		}

	}

	/**予約者へ貸し出している本を検索**/
	public 貸出状況ValueObject 予約者がレンタルしている本一覧を取得(Long 貸出券番号) {
		貸出状況ValueObject result = null;
		//予約者の情報を取得
		予約者Dto user = 予約者DtoService.findOneBy貸出券番号(貸出券番号).orElse(null);
		if (user == null) {
			return result;
		}

		//レンタル本一覧を取得
		List<貸出状況Dto> rental = 貸出状況DtoService.findBy予約者Id(user.get予約者ID());
		if (rental == null) {
			return result;
		}

		//書籍に関する情報を取得
		List<書籍Dto> bookList = new ArrayList<書籍Dto>();
		for (貸出状況Dto tmp : rental) {
			書籍Dto book = 書籍DtoService.findOne(tmp.get書籍ID()).orElse(null);
			if (book != null) {
				bookList.add(book);
			}
		}

		//出版社に関する情報を取得
		List<出版社Dto> publisherList = new ArrayList<出版社Dto>();
		for (書籍Dto tmp : bookList) {
			出版社Dto publisher = 出版社DtoService.findOne(tmp.get出版社ID()).orElse(null);
			if (publisher != null) {
				publisherList.add(publisher);
			}
		}
		result = new 貸出状況ValueObject(user, bookList, rental, publisherList);
		return result;
	}
}
