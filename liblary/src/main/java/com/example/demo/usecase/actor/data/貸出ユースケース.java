package com.example.demo.usecase.actor.data;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.controller.user_menu.confirm.UserConfirmBean;
import com.example.demo.controller.user_menu.library_search.detail.LibraryDetailBean;
import com.example.demo.domain.rental.貸出状況Aggregate;
import com.example.demo.domain.rental.貸出状況ValueObject;

/**図書館貸出時の利用場面**/
public interface 貸出ユースケース {

	/**利用者へ本を貸出**/
	public default LibraryDetailBean 本を貸出(LibraryDetailBean bean, 貸出状況Aggregate aggregate) {
		try {
			aggregate.本を貸出(bean.getCardId(), bean.getBookId());
		} catch (Exception e) {
			bean.getUsecaseErrMessageList().add(e.getMessage());
		}
		return bean;
	}

	/**本の貸出状況を取得**/
	public default UserConfirmBean 本の貸出状況を取得(UserConfirmBean bean, 貸出状況Aggregate aggregate) {
		貸出状況ValueObject result = aggregate.予約者がレンタルしている本一覧を取得(bean.getCardId());
		bean.factorySearchResult(result.レンタルしている本の書籍情報を取得(), result.レンタルしている本の貸出情報を取得(), result.レンタルしている本の出版社情報を取得());
		return bean;
	}

	/**本を返却する
	 * @throws Exception **/
	public default UserConfirmBean 本を返却(UserConfirmBean bean, 貸出状況Aggregate aggregate) throws Exception {
		//選択したチェックボックスの値をLong型に変換
		List<Long> checks = bean.getChecks().stream().map(a -> Long.parseLong(a)).collect(Collectors.toList());
		aggregate.本を返却(checks, bean.getCardId());
		return bean;
	}

}
