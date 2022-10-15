package com.example.demo.usecase.actor.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.demo.controller.common.psersonal_change.PersonalDataChangeBean;
import com.example.demo.controller.login.LoginBean;
import com.example.demo.controller.manager_menu.book_request_confirm.BookRequestConfirmBean;
import com.example.demo.controller.manager_menu.book_request_confirm.detail.BookRequestConfirmDetailBean;
import com.example.demo.controller.manager_menu.order_product_confirm.OrderedProductConfirmBean;
import com.example.demo.controller.manager_menu.order_product_confirm.OrderedProductConfirmBean.SearchOrderBookCompleted;
import com.example.demo.controller.manager_menu.order_product_confirm.detail.OrderedProductConfirmDetailBean;
import com.example.demo.controller.manager_menu.rental_book_confirm.RentalBookConfirmBean;
import com.example.demo.controller.manager_menu.rental_book_confirm.detail.RentalBookConfirmDetailBean;
import com.example.demo.controller.personInformationRegist.PersonInformationRegistBean;
import com.example.demo.data.dto.リクエスト書籍Dto;
import com.example.demo.data.dto.予約者Dto;
import com.example.demo.data.dto.管理者Dto;
import com.example.demo.data.dto.管理者から利用者へのメッセージDto;
import com.example.demo.data.service.リクエスト書籍DtoService;
import com.example.demo.data.service.予約者DtoService;
import com.example.demo.data.service.管理者DtoService;
import com.example.demo.data.service.管理者から利用者へのメッセージDtoService;
import com.example.demo.domain.orderedproduct.注文書籍Aggregate;
import com.example.demo.domain.rental.貸出状況Aggregate;
import com.example.demo.domain.rental.貸出状況ValueObject;

/**図書館管理者の利用場面**/
public interface 管理者ユースケース {

	/**管理者DBへ個人情報を登録**/
	public default PersonInformationRegistBean 管理者を登録する(PersonInformationRegistBean bean, 管理者DtoService service) {
		管理者Dto saveDto = new 管理者Dto();
		//乱数で登録番号を生成
		saveDto.set管理者番号(new Random().nextLong(100000));
		saveDto.set名前(bean.getName());
		saveDto.setふりがな(bean.getFurigana());
		saveDto.set住所(bean.getAddress());
		saveDto.set郵便番号(bean.getPostNo());
		saveDto.setEMAIL(bean.geteMail());
		saveDto.setパスワード(bean.getPassword());
		bean.setSearchManagerResult(service.save(saveDto));
		return bean;
	}

	/**管理者DBから個人情報を取得**/
	public default LoginBean 管理者の個人情報を取得(LoginBean bean, 管理者DtoService service) {
		String errMessage = "入力したカード番号、パスワード、メールアドレスと一致する情報が存在しません。";
		管理者Dto searchDto = new 管理者Dto();
		searchDto.set管理者番号(Long.parseLong(bean.getCardId()));
		searchDto.setEMAIL(bean.geteMail());
		searchDto.setパスワード(bean.getPassword());
		bean.setSearchManagerResult(service.findBy管理者番号_EMAIL_パスワード(searchDto));
		//データが存在しない場合、エラーがメッセージを返却
		if (bean.getSearchManagerResult() == null) {
			bean.getUsecaseErrMessageList().add(errMessage);
		}
		return bean;
	}

	/**管理者DBから個人情報を取得**/
	public default PersonalDataChangeBean 管理者の個人情報を取得(PersonalDataChangeBean bean, 管理者DtoService service) {
		//DBから取得した情報をBeanへ設定
		管理者Dto result = service.findOneBy管理者番号(bean.getCardId()).orElse(null);
		bean.setName(result.get名前());
		bean.setFurigana(result.getふりがな());
		bean.setAddress(result.get住所());
		bean.setPostNo(result.get郵便番号());
		bean.seteMail(result.getEMAIL());
		bean.setPassword(result.getパスワード());
		return bean;
	}

	/**本の貸出状況を取得**/
	public default RentalBookConfirmBean 本の貸出状況を取得(RentalBookConfirmBean bean, 貸出状況Aggregate aggregate,
			予約者DtoService 予約者DtoService) {
		List<予約者Dto> userList = 予約者DtoService.findAll();
		//利用者名でフィルタリング
		if (!bean.getUserName().isEmpty()) {
			userList = userList.stream().filter(a -> a.get名前().contains(bean.getUserName()))
					.toList();
		}
		for (予約者Dto tmp : userList) {
			貸出状況ValueObject 貸出状況Object = aggregate.予約者がレンタルしている本一覧を取得(tmp.get貸出券番号());
			//レンタルしている本が存在する場合
			if (貸出状況Object != null && 貸出状況Object.レンタルしている本の書籍情報を取得().size() > 0) {
				//返却日From～返却日toでフィルタリング
				if (!bean.getRentalBookReturnDateFrom().isEmpty() && !bean.getRentalBookReturnDateTo().isEmpty()) {
					if (!bean.getRentalBookReturnDateFrom().isEmpty() && !bean.getRentalBookReturnDateTo().isEmpty()) {
						if (this.日付変換(bean.getRentalBookReturnDateFrom())
								.before(貸出状況Object.レンタルしている本の貸出情報を取得().get(0).get返却予定日())
								&& this.日付変換(bean.getRentalBookReturnDateTo())
										.after(貸出状況Object.レンタルしている本の貸出情報を取得().get(0).get返却予定日())) {
							bean.factorySearchResultList(tmp, 貸出状況Object.レンタルしている本の貸出情報を取得(), 貸出状況Object.get返却冊数());

						}
					}
				} else if (!bean.getRentalBookReturnDateFrom().isEmpty()) {
					if (this.日付変換(bean.getRentalBookReturnDateFrom())
							.before(貸出状況Object.レンタルしている本の貸出情報を取得().get(0).get返却予定日())) {
						bean.factorySearchResultList(tmp, 貸出状況Object.レンタルしている本の貸出情報を取得(), 貸出状況Object.get返却冊数());

					}
				} else if (!bean.getRentalBookReturnDateTo().isEmpty()) {
					if (this.日付変換(bean.getRentalBookReturnDateTo())
							.after(貸出状況Object.レンタルしている本の貸出情報を取得().get(0).get返却予定日()))
						bean.factorySearchResultList(tmp, 貸出状況Object.レンタルしている本の貸出情報を取得(), 貸出状況Object.get返却冊数());

				} else {
					bean.factorySearchResultList(tmp, 貸出状況Object.レンタルしている本の貸出情報を取得(), 貸出状況Object.get返却冊数());
				}
			}
		}

		return bean;
	}

	/**本の貸出状況詳細を取得**/
	public default RentalBookConfirmDetailBean 本の貸出状況詳細を取得(RentalBookConfirmDetailBean bean,
			予約者DtoService 予約者DtoServicce, 貸出状況Aggregate aggregate) {
		予約者Dto user = 予約者DtoServicce.findOne(bean.getUserId()).orElse(null);
		//ユーザーが存在しない場合、エラーとして処理を終了
		if (user == null) {
			bean.getUsecaseErrMessageList().add("ユーザーが存在しません。");
			return bean;
		}
		貸出状況ValueObject 貸出状況Object = aggregate.予約者がレンタルしている本一覧を取得(user.get貸出券番号());
		//レンタルしている本が存在しない場合、処理を終了
		if (貸出状況Object == null) {
			bean.getUsecaseErrMessageList().add("レンタルしている本が存在しません。");
			return bean;
		}
		bean.factorySearchResultList(貸出状況Object);
		bean.setUserName(user.get名前());
		bean.setUserCardNo(user.get貸出券番号().toString());
		bean.setMail(user.getEMAIL());

		return bean;
	}

	/**管理者から利用者宛にメッセージを送信**/
	public default RentalBookConfirmDetailBean 管理者から利用者宛にメッセージを送信(RentalBookConfirmDetailBean bean,
			予約者DtoService 予約者DtoServicce, 管理者から利用者へのメッセージDtoService 管理者から利用者へのメッセージDtoService,
			管理者DtoService 管理者DtoService) {
		予約者Dto user = 予約者DtoServicce.findOne(bean.getUserId()).orElse(null);
		//ユーザーが存在しない場合、エラーとして処理を終了
		if (user == null) {
			bean.getUsecaseErrMessageList().add("ユーザーが存在しません。");
			return bean;
		}
		管理者Dto manager = 管理者DtoService.findOneBy管理者番号(bean.getCardNo()).orElse(null);
		//管理者が存在しない場合、処理を終了
		if (manager == null) {
			bean.getUsecaseErrMessageList().add("管理者が存在しません。");
			return bean;
		}

		//登録情報を設定
		管理者から利用者へのメッセージDto registDto = new 管理者から利用者へのメッセージDto();
		registDto.set管理者ID(manager.get管理者ID());
		registDto.set予約者ID(user.get予約者ID());
		registDto.set内容(bean.getContactMessage());
		registDto.set登録日時(new java.sql.Date(new Date().getTime()));

		管理者から利用者へのメッセージDtoService.save(registDto);

		return bean;
	}

	/**リクエストされた本を取得**/
	public default BookRequestConfirmBean リクエストされた書籍を取得(BookRequestConfirmBean bean, 注文書籍Aggregate aggregate) {
		//リクエストされた書籍情報を取得
		List<リクエスト書籍Dto> searchResult = new ArrayList<リクエスト書籍Dto>();
		searchResult = aggregate.リクエストされた書籍情報を取得();
		//リクエスト日From～リクエスト日Toで検索
		if (!bean.getRequestDateFrom().isEmpty() && !bean.getRequestDateTo().isEmpty()) {
			searchResult = searchResult.stream()
					.filter(a -> this.日付変換(bean.getRequestDateFrom()).before(a.get出版年())
							&& this.日付変換(bean.getRequestDateTo()).after(a.get出版年()))
					.toList();
		} else if (!bean.getRequestDateFrom().isEmpty()) {
			searchResult = searchResult.stream()
					.filter(a -> this.日付変換(bean.getRequestDateFrom()).before(a.get出版年())).toList();
		} else if (!bean.getRequestDateTo().isEmpty()) {
			searchResult = searchResult.stream()
					.filter(a -> this.日付変換(bean.getRequestDateTo()).after(a.get出版年())).toList();

		}
		bean.factorySearchResultList(searchResult);
		return bean;
	}

	/**チェックされたリクエスト書籍の情報を取得**/
	public default BookRequestConfirmBean チェックされたリクエスト書籍の情報を取得(BookRequestConfirmBean bean, リクエスト書籍DtoService service) {
		//カートに追加する商品情報を取得
		List<リクエスト書籍Dto> cartList = new ArrayList<リクエスト書籍Dto>();
		//チェックされたリクエスト書籍でフィルタリング
		for (String requestId : bean.getChecks()) {
			リクエスト書籍Dto result = service.findOne(Long.parseLong(requestId)).orElse(null);
			if (result != null) {
				cartList.add(result);
			}
		}
		bean.factoryAddCartList(cartList);
		return bean;
	}

	/**リクエスト書籍を購入する**/
	public default BookRequestConfirmDetailBean リクエスト書籍を注文(BookRequestConfirmDetailBean bean,
			注文書籍Aggregate aggregate, 管理者DtoService 管理者DtoService) {
		管理者Dto manager = 管理者DtoService.findOneBy管理者番号(bean.getCardNo()).orElse(null);
		if (manager == null) {
			bean.getUsecaseErrMessageList().add("管理者が不在です。");
			return bean;
		}
		List<Long> requestIdList = bean.getCommontBean().getReuestOrederList().stream().map(a -> a.getRequestId())
				.collect(Collectors.toList());
		try {
			aggregate.リクエストされた書籍を注文(requestIdList, manager.get管理者ID());
		} catch (Exception e) {
			bean.getUsecaseErrMessageList().add(e.getMessage());
		}
		return bean;
	}

	/**注文商品の納品状況を確認する**/
	public default OrderedProductConfirmBean 注文商品の納品状況を確認する(OrderedProductConfirmBean bean,
			注文書籍Aggregate aggregate, 管理者DtoService 管理者DtoService) {
		管理者Dto manager = 管理者DtoService.findOneBy管理者番号(bean.getCardNo()).orElse(null);
		if (manager == null) {
			bean.getUsecaseErrMessageList().add("管理者が不在です。");
			return bean;
		}
		try {
			bean.factorySearchResultList(aggregate.管理者が注文した商品の納品情報を取得する(manager.get管理者ID()));
			List<SearchOrderBookCompleted> searchResult = bean.getSearchResultList();
			List<SearchOrderBookCompleted> tmpResult = bean.getSearchResultList();
			if (!bean.getOrderDateFrom().isEmpty() && !bean.getOrderDateTo().isEmpty()) {
				tmpResult = searchResult.stream()
						.filter(a -> this.日付変換(bean.getOrderDateFrom()).before(this.日付変換(a.getOrderDate()))
								&& this.日付変換(bean.getOrderDateTo()).after(this.日付変換(a.getOrderDate())))
						.toList();
				bean.getSearchResultList().clear();
				bean.getSearchResultList().addAll(tmpResult);
			} else if (!bean.getOrderDateFrom().isEmpty()) {
				tmpResult = searchResult.stream()
						.filter(a -> this.日付変換(bean.getOrderDateFrom()).before(this.日付変換(a.getOrderDate()))).toList();
				bean.getSearchResultList().clear();
				bean.getSearchResultList().addAll(tmpResult);
			} else if (!bean.getOrderDateTo().isEmpty()) {
				tmpResult = searchResult.stream()
						.filter(a -> this.日付変換(bean.getOrderDateTo()).after(this.日付変換(a.getOrderDate()))).toList();
				bean.getSearchResultList().clear();
				bean.getSearchResultList().addAll(tmpResult);
			}
		} catch (Exception e) {
			bean.getUsecaseErrMessageList().add(e.getMessage());
		}
		return bean;
	}

	/**注文商品の納品状況を確認する**/
	public default OrderedProductConfirmDetailBean 注文商品の納品状況を確認する(OrderedProductConfirmDetailBean bean,
			注文書籍Aggregate aggregate, 管理者DtoService 管理者DtoService) {
		管理者Dto manager = 管理者DtoService.findOneBy管理者番号(bean.getCardNo()).orElse(null);
		if (manager == null) {
			bean.getUsecaseErrMessageList().add("管理者が不在です。");
			return bean;
		}
		try {
			bean.factorySearchResultList(aggregate.管理者が注文した商品の納品情報を取得する(manager.get管理者ID(), bean.getOrderId()));
		} catch (Exception e) {
			bean.getUsecaseErrMessageList().add(e.getMessage());
		}
		return bean;
	}

	/**管理者DBの個人情報を変更**/
	public default PersonalDataChangeBean 管理者の個人情報を変更(PersonalDataChangeBean bean, 管理者DtoService service) {
		//Bean取得した情報をDBへ登録
		管理者Dto regist = service.findOneBy管理者番号(bean.getCardId()).orElse(null);
		regist.set名前(bean.getName());
		regist.setふりがな(bean.getFurigana());
		regist.set住所(bean.getAddress());
		regist.set郵便番号(bean.getPostNo());
		regist.setEMAIL(bean.geteMail());
		regist.setパスワード(bean.getPassword());
		service.update(regist);
		return bean;
	}

	private Date 日付変換(String coveret) {
		return java.sql.Date.valueOf(coveret);
	}
}
