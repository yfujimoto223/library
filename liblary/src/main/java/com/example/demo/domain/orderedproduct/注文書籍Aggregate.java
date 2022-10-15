package com.example.demo.domain.orderedproduct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data.dto.リクエスト書籍Dto;
import com.example.demo.data.dto.出版社Dto;
import com.example.demo.data.dto.書籍Dto;
import com.example.demo.data.dto.注文Dto;
import com.example.demo.data.dto.注文明細Dto;
import com.example.demo.data.dto.管理者Dto;
import com.example.demo.data.service.リクエスト書籍DtoService;
import com.example.demo.data.service.出版社DtoService;
import com.example.demo.data.service.書籍DtoService;
import com.example.demo.data.service.注文DtoService;
import com.example.demo.data.service.注文明細DtoService;
import com.example.demo.data.service.管理者DtoService;

/**貸出状況Aggregate**/
@Service
public class 注文書籍Aggregate {
	@Autowired
	注文DtoService 注文DtoService;
	@Autowired
	注文明細DtoService 注文明細DtoService;
	@Autowired
	管理者DtoService 管理者DtoService;
	@Autowired
	リクエスト書籍DtoService リクエスト書籍DtoService;
	@Autowired
	書籍DtoService 書籍DtoService;
	@Autowired
	出版社DtoService 出版社DtoService;

	private enum リクエスト書籍注文エラーメッセージ {
		リクエストされた商品データが存在しない("リクエストされた商品データが存在しません。"), 同一ISBN("同様の書籍が既にリクエストされています。"), 管理者不在("商品を注文をした管理者のデータが存在しません。");

		public String message; // フィールドの定義

		private リクエスト書籍注文エラーメッセージ(String message) { // コンストラクタの定義
			this.message = message;
		}
	}

	private enum 発注商品エラーメッセージ {
		発注商品が存在しない("発注商品が存在しません。"), 同一ISBN("同様の書籍が既に発注されています。"), 注文者不在("注文者が存在しません。");

		public String message; // フィールドの定義

		private 発注商品エラーメッセージ(String message) { // コンストラクタの定義
			this.message = message;
		}
	}

	private enum 納品商品エラーメッセージ {
		注文した商品が存在しない("管理者が注文した商品データが存在しません");

		public String message; // フィールドの定義

		private 納品商品エラーメッセージ(String message) { // コンストラクタの定義
			this.message = message;
		}
	}

	/**リクエストされた書籍を注文
	 * @throws Exception **/
	public void リクエストされた書籍を注文(List<Long> requestIdList, Long managerId) throws Exception {
		List<リクエスト書籍Dto> requestBooks = new ArrayList<リクエスト書籍Dto>();
		//リクエストIDでリクエスト書籍情報を取得
		for (Long requesId : requestIdList) {
			リクエスト書籍Dto tmp = リクエスト書籍DtoService.findOne(requesId).orElse(null);
			if (tmp == null) {
				throw new Exception(リクエスト書籍注文エラーメッセージ.リクエストされた商品データが存在しない.message);
			}
			requestBooks.add(tmp);
		}
		//注文商品情報をDBに登録
		try {
			注文Dto 注文 = new 注文Dto();
			注文.set管理者ID(managerId);
			Date nowDate = new Date();
			注文.set注文日(new java.sql.Date(nowDate.getTime()));
			注文.set注文金額(requestBooks.stream().mapToLong(a -> a.get価格()).sum());
			注文 = 注文DtoService.save(注文);
			for (リクエスト書籍Dto tmp : requestBooks) {
				注文明細Dto 明細 = new 注文明細Dto();
				明細.set注文ID(注文.get注文ID());
				明細.setISBN(tmp.getISBN());
				明細.set書名(tmp.get書名());
				明細.set著者(tmp.get著者());
				明細.set価格(tmp.get価格());
				明細.set出版社名(tmp.get出版社名());
				明細.set出版年(tmp.get出版年());
				注文明細DtoService.save(明細);
			}
		} catch (Exception e) {
			throw new Exception(リクエスト書籍注文エラーメッセージ.同一ISBN.message);
		}
	}

	/**リクエストされた書籍情報を取得
	 * @throws Exception **/
	public List<リクエスト書籍Dto> リクエストされた書籍情報を取得() {
		List<リクエスト書籍Dto> requestBooks = new ArrayList<リクエスト書籍Dto>();
		for (リクエスト書籍Dto tmp : リクエスト書籍DtoService.findAll()) {
			//既に注文されていないリクエスト書籍を取得
			注文明細Dto result = 注文明細DtoService.findOneByIsbn(tmp.getISBN()).orElse(null);
			if (result == null) {
				requestBooks.add(tmp);
			}
		}
		return requestBooks;
	}

	/**注文された商品を納品する
	 * @throws Exception **/
	public void 注文された商品を納品する(List<Long> orderIdList) throws Exception {
		for (Long orderId : orderIdList) {
			注文Dto 注文 = 注文DtoService.findOne(orderId).orElse(null);
			if (注文 == null) {
				throw new Exception(発注商品エラーメッセージ.発注商品が存在しない.message);
			}
			List<注文明細Dto> 注文明細 = 注文明細DtoService.findAll().stream().filter(a -> a.get注文ID().equals(注文.get注文ID()))
					.toList();
			if (注文明細.size() == 0) {
				throw new Exception(発注商品エラーメッセージ.発注商品が存在しない.message);
			}
			for (注文明細Dto tmp : 注文明細) {
				//注文商品の出版社名が「出版社」DBに存在しない場合、新規で追加
				boolean 出版社不在フラグ = false;
				出版社Dto registDto = null;
				if (出版社DtoService.findAll().stream().filter(a -> a.get出版社名().equals(tmp.get出版社名())).count() == 0) {
					registDto = new 出版社Dto();
					registDto.set出版社名(tmp.get出版社名());
					registDto = 出版社DtoService.save(registDto);
					出版社不在フラグ = true;
				}

				書籍Dto regist = new 書籍Dto();
				regist.set書名(tmp.get書名());
				regist.set著者(tmp.get著者());
				regist.set価格(tmp.get価格());
				regist.set価格(tmp.get価格());
				if (出版社不在フラグ == true) {
					regist.set出版社ID(registDto.get出版社ID());
				}
				regist.set出版年(tmp.get出版年());
				regist.set登録日時(new java.sql.Date(new Date().getTime()));
				書籍DtoService.save(regist);
			}
		}
	}

	/**発注商品を取得する
	 * @throws Exception **/
	public 注文書籍ValueObject 発注商品を取得する() throws Exception {
		注文書籍ValueObject result = new 注文書籍ValueObject();

		List<注文Dto> 注文 = 注文DtoService.findAll();
		if (注文.size() == 0) {
			throw new Exception(発注商品エラーメッセージ.発注商品が存在しない.message);
		}
		for (注文Dto tmp : 注文) {
			List<注文明細Dto> 注文明細 = 注文明細DtoService.findAll().stream().filter(a -> a.get注文ID().equals(tmp.get注文ID()))
					.toList();
			if (注文明細.size() == 0) {
				throw new Exception(発注商品エラーメッセージ.発注商品が存在しない.message);
			}
			管理者Dto 管理者 = 管理者DtoService.findOne(tmp.get管理者ID()).orElse(null);

			if (管理者 == null) {
				throw new Exception(発注商品エラーメッセージ.注文者不在.message);
			}
			注文書籍Specification check = new 注文書籍Specification();
			if (!check.発注依頼された商品が既に納品済みか確認(tmp.get注文ID(), 注文DtoService, 注文明細DtoService, 書籍DtoService)) {
				result.注文書籍を追加(tmp, 注文明細, 管理者);
			}
		}
		return result;
	}

	/**発注商品を取得する
	 * @throws Exception **/
	public 注文書籍ValueObject 発注商品を取得する(Long 注文Id) throws Exception {
		注文書籍ValueObject result = new 注文書籍ValueObject();

		注文Dto 注文 = 注文DtoService.findOne(注文Id).orElse(null);
		if (注文 == null) {
			throw new Exception(発注商品エラーメッセージ.発注商品が存在しない.message);
		}
		List<注文明細Dto> 注文明細 = 注文明細DtoService.findAll().stream().filter(a -> a.get注文ID().equals(注文.get注文ID()))
				.toList();
		if (注文明細.size() == 0) {
			throw new Exception(発注商品エラーメッセージ.発注商品が存在しない.message);
		}
		管理者Dto 管理者 = 管理者DtoService.findOne(注文.get管理者ID()).orElse(null);

		if (管理者 == null) {
			throw new Exception(発注商品エラーメッセージ.注文者不在.message);
		}
		result.注文書籍を追加(注文, 注文明細, 管理者);

		return result;
	}

	/**管理者が注文した商品の納品状況を取得する
	 * @throws Exception **/
	public 注文書籍ValueObject 管理者が注文した商品の納品情報を取得する(Long 管理者Id) throws Exception {
		注文書籍ValueObject result = new 注文書籍ValueObject();

		List<注文Dto> 注文 = 注文DtoService.findOneBy管理者Id(管理者Id);
		if (注文.size() == 0) {
			throw new Exception(納品商品エラーメッセージ.注文した商品が存在しない.message);
		}
		for (注文Dto tmp : 注文) {
			List<注文明細Dto> 注文明細 = 注文明細DtoService.findAll().stream().filter(a -> a.get注文ID().equals(tmp.get注文ID()))
					.toList();
			if (注文明細.size() == 0) {
				throw new Exception(納品商品エラーメッセージ.注文した商品が存在しない.message);
			}
			管理者Dto 管理者 = 管理者DtoService.findOne(tmp.get管理者ID()).orElse(null);

			if (管理者 == null) {
				throw new Exception(発注商品エラーメッセージ.注文者不在.message);
			}
			注文書籍Specification check = new 注文書籍Specification();
			boolean 納品済みフラグ = false;
			if (check.発注依頼された商品が既に納品済みか確認(tmp.get注文ID(), 注文DtoService, 注文明細DtoService, 書籍DtoService)) {
				納品済みフラグ = true;
			}
			result.注文書籍を追加(tmp, 注文明細, 管理者);
			result.set納品済みフラグ(納品済みフラグ);

		}
		return result;
	}

	/**管理者が注文した商品の納品状況を取得する
	 * @throws Exception **/
	public 注文書籍ValueObject 管理者が注文した商品の納品情報を取得する(Long 管理者Id, Long 注文Id) throws Exception {
		注文書籍ValueObject result = new 注文書籍ValueObject();
		List<注文Dto> 注文 = 注文DtoService.findOneBy管理者Id(管理者Id).stream().filter(a -> a.get注文ID().equals(注文Id)).toList();
		if (注文.size() == 0) {
			throw new Exception(納品商品エラーメッセージ.注文した商品が存在しない.message);
		}

		for (注文Dto tmp : 注文) {
			List<注文明細Dto> 注文明細 = 注文明細DtoService.findAll().stream().filter(a -> a.get注文ID().equals(tmp.get注文ID()))
					.toList();
			if (注文明細.size() == 0) {
				throw new Exception(納品商品エラーメッセージ.注文した商品が存在しない.message);
			}
			管理者Dto 管理者 = 管理者DtoService.findOne(tmp.get管理者ID()).orElse(null);

			if (管理者 == null) {
				throw new Exception(発注商品エラーメッセージ.注文者不在.message);
			}
			注文書籍Specification check = new 注文書籍Specification();
			boolean 納品済みフラグ = false;
			if (check.発注依頼された商品が既に納品済みか確認(tmp.get注文ID(), 注文DtoService, 注文明細DtoService, 書籍DtoService)) {
				納品済みフラグ = true;
			}
			result.注文書籍を追加(tmp, 注文明細, 管理者);
			result.set納品済みフラグ(納品済みフラグ);

		}
		return result;
	}

}
