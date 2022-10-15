package com.example.demo.usecase.actor.data;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.demo.controller.common.psersonal_change.PersonalDataChangeBean;
import com.example.demo.controller.login.LoginBean;
import com.example.demo.controller.personInformationRegist.PersonInformationRegistBean;
import com.example.demo.controller.sales_menu.OrderedProductBean;
import com.example.demo.controller.sales_menu.OrderedProductBean.SearchResult;
import com.example.demo.controller.sales_menu.detail.OrderedProductDetailBean;
import com.example.demo.data.dto.販売者Dto;
import com.example.demo.data.service.販売者DtoService;
import com.example.demo.domain.orderedproduct.注文書籍Aggregate;

/**販売者の利用場面**/
public interface 販売者ユースケース {
	/**販売者DBへ個人情報を登録**/
	public default PersonInformationRegistBean 販売者を登録する(PersonInformationRegistBean bean, 販売者DtoService service) {
		販売者Dto saveDto = new 販売者Dto();
		//乱数で登録番号を生成
		saveDto.set販売者番号(new Random().nextLong(100000));
		saveDto.set名前(bean.getName());
		saveDto.setふりがな(bean.getFurigana());
		saveDto.set住所(bean.getAddress());
		saveDto.set郵便番号(bean.getPostNo());
		saveDto.setEMAIL(bean.geteMail());
		saveDto.setパスワード(bean.getPassword());
		bean.setSearchSalesUserrResult(service.save(saveDto));
		return bean;
	}

	/**販売者DBから個人情報を取得**/
	public default LoginBean 販売者の個人情報を取得(LoginBean bean, 販売者DtoService service) {
		String errMessage = "入力したカード番号、パスワード、メールアドレスと一致する情報が存在しません。";
		販売者Dto searchDto = new 販売者Dto();
		searchDto.set販売者番号(Long.parseLong(bean.getCardId()));
		searchDto.setEMAIL(bean.geteMail());
		searchDto.setパスワード(bean.getPassword());
		bean.setSearchSalesUserrResult(service.findBy販売者番号_EMAIL_パスワード(searchDto));
		//データが存在しない場合、エラーがメッセージを返却
		if (bean.getSearchSalesUserrResult() == null) {
			bean.getUsecaseErrMessageList().add(errMessage);
		}
		return bean;
	}

	/**販売者DBから個人情報を取得**/
	public default PersonalDataChangeBean 販売者の個人情報を取得(PersonalDataChangeBean bean, 販売者DtoService service) {
		//DBから取得した情報をBeanへ設定
		販売者Dto result = service.findOneBy販売者番号(bean.getCardId()).orElse(null);
		bean.setName(result.get名前());
		bean.setFurigana(result.getふりがな());
		bean.setAddress(result.get住所());
		bean.setPostNo(result.get郵便番号());
		bean.seteMail(result.getEMAIL());
		bean.setPassword(result.getパスワード());
		return bean;
	}

	/**管理者から発注された商品情報をs取得**/
	public default OrderedProductBean 注文商品情報を取得する(OrderedProductBean bean, 注文書籍Aggregate aggregate) {
		try {
			bean.factorySearchResultList(aggregate.発注商品を取得する());
			List<SearchResult> searchResult = bean.getSearchResultList();
			List<SearchResult> tmpResult = bean.getSearchResultList();
			//注文商品を注文者名名_注文日From～Toで絞り込み
			if (!bean.getSelectOrderName().isEmpty()) {
				tmpResult = searchResult.stream().filter(a -> a.getManagerName().contains(bean.getSelectOrderName()))
						.toList();
				bean.getSearchResultList().clear();
				bean.getSearchResultList().addAll(tmpResult);
			}
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

	/**管理者から発注された商品情報を登録**/
	public default OrderedProductDetailBean 注文商品情報を取得する(OrderedProductDetailBean bean, 注文書籍Aggregate aggregate) {
		try {
			bean.factorySearchResultList(aggregate.発注商品を取得する(bean.getOrderId()));
		} catch (Exception e) {
			bean.getUsecaseErrMessageList().add(e.getMessage());
		}
		return bean;
	}

	/**管理者から発注された商品情報を登録**/
	public default OrderedProductBean 注文された商品を納品する(OrderedProductBean bean, 注文書籍Aggregate aggregate) {
		List<Long> convertOrderIdList = bean.getChecks().stream().map(a -> Long.parseLong(a))
				.collect(Collectors.toList());
		try {
			aggregate.注文された商品を納品する(convertOrderIdList);
		} catch (Exception e) {
			bean.getUsecaseErrMessageList().add(e.getMessage());
		}
		return bean;
	}

	/**販売者DBの個人情報を変更**/
	public default PersonalDataChangeBean 販売者の個人情報を変更(PersonalDataChangeBean bean, 販売者DtoService service) {
		//Bean取得した情報をDBへ登録
		販売者Dto regist = service.findOneBy販売者番号(bean.getCardId()).orElse(null);
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
