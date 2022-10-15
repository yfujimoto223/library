package com.example.demo.usecase.actor.data;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.demo.controller.information.InformationBean;
import com.example.demo.controller.user_menu.library_search.LibrarySearchBean;
import com.example.demo.controller.user_menu.library_search.detail.LibraryDetailBean;
import com.example.demo.data.dto.出版社Dto;
import com.example.demo.data.dto.書籍Dto;
import com.example.demo.data.service.出版社DtoService;
import com.example.demo.data.service.書籍DtoService;

/**書籍に関する利用場面**/
public interface 書籍ユースケース {

	/**書籍のデータを取得**/
	public default LibrarySearchBean 書籍のデータを書名_著書_出版社_出版日で取得(LibrarySearchBean bean, 書籍DtoService service) {
		//検索結果を初期化
		bean.getSearchResultList().clear();
		//書籍DBから全データ取得
		List<書籍Dto> searchResult = service.findAll();
		//書籍を書名_著書_出版社_出版日で絞り込み
		if (!bean.getTitleName().isEmpty()) {
			searchResult = searchResult.stream().filter(a -> a.get書名().contains(bean.getTitleName())).toList();
		}
		if (!bean.getAuthor().isEmpty()) {
			searchResult = searchResult.stream().filter(a -> a.get著者().contains(bean.getAuthor())).toList();
		}
		if (!bean.getSelectPublisher().isEmpty()) {
			searchResult = searchResult.stream()
					.filter(a -> a.get出版社ID().equals(Long.parseLong(bean.getSelectPublisher()))).toList();
		}
		if (!bean.getPublicationDateFrom().isEmpty() && !bean.getPublicationDateTo().isEmpty()) {
			searchResult = searchResult.stream()
					.filter(a -> this.日付変換(bean.getPublicationDateFrom()).before(a.get出版年())
							&& this.日付変換(bean.getPublicationDateTo()).after(a.get出版年()))
					.toList();
		} else if (!bean.getPublicationDateFrom().isEmpty()) {
			searchResult = searchResult.stream()
					.filter(a -> this.日付変換(bean.getPublicationDateFrom()).before(a.get出版年())).toList();
		} else if (!bean.getPublicationDateTo().isEmpty()) {
			searchResult = searchResult.stream()
					.filter(a -> this.日付変換(bean.getPublicationDateTo()).after(a.get出版年())).toList();

		}
		//検索結果が存在しない場合、処理を終了
		if (searchResult.size() == 0) {
			return bean;
		}
		//検索結果をBeanに格納
		bean.factorySearchResultList(searchResult);
		return bean;
	}

	/**全出版社のデータを取得**/
	public default LibrarySearchBean 全出版社のプルダウンデータを取得(LibrarySearchBean bean, 出版社DtoService service) {
		bean.getPublisherList().clear();
		for (出版社Dto tmp : service.findAll()) {
			bean.getPublisherList().put(tmp.get出版社ID(), tmp.get出版社名());
		}
		return bean;
	}

	/**書籍詳細画面に表示するデータを取得**/
	public default LibraryDetailBean 書籍詳細情報のデータを取得(LibraryDetailBean bean, 出版社DtoService 出版社service,
			書籍DtoService 書籍Service) {
		書籍Dto searchObject = new 書籍Dto();
		searchObject.set書籍ID(bean.getBookId());
		書籍Dto result = 書籍Service.findOne(searchObject).orElse(null);
		出版社Dto searchObject2 = new 出版社Dto();
		searchObject2.set出版社ID(result.get出版社ID());
		出版社Dto result2 = 出版社service.findOne(searchObject2).orElse(null);
		bean.setTitleName(result.get書名());
		bean.setAuthor(result.get著者());
		bean.setPrice(result.get価格());
		bean.setPublisherId(result.get出版社ID());
		bean.setPublisherDate(result.get出版年());
		bean.setPublisherName(result2.get出版社名());
		return bean;
	}

	/**お知らせ画面に表示するデータを取得**/
	public default InformationBean お知らせ画面に表示するデータを取得(InformationBean bean, 出版社DtoService 出版社service,
			書籍DtoService 書籍Service) {
		//一週間以内のデータを取得
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.add(Calendar.DATE, -7);
		Date from = fromCalendar.getTime();
		Date to = new Date();
		List<書籍Dto> result = 書籍Service.findAll().stream().filter(a -> from.before(a.get登録日時()) && to.after(a.get登録日時()))
				.toList();
		for (書籍Dto tmp : result) {
			出版社Dto 出版社 = 出版社service.findOne(tmp.get出版社ID()).orElse(null);
			bean.addSearchResultList(tmp, 出版社);
		}
		return bean;
	}

	private Date 日付変換(String coveret) {
		return java.sql.Date.valueOf(coveret);
	}
}
