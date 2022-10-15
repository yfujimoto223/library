package com.example.demo.usecase.actor.data;

import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;

import com.example.demo.controller.user_menu.book_request.BookRequestBean;
import com.example.demo.data.dto.リクエスト書籍Dto;
import com.example.demo.data.dto.予約者Dto;
import com.example.demo.data.service.リクエスト書籍DtoService;
import com.example.demo.data.service.予約者DtoService;

/**リクエスト書籍に関する利用場面**/
public interface リクエスト書籍ユースケース {

	/**外部Apiから取得した書籍データをリクエストDBへ登録**/
	public default BookRequestBean 本をリクエスト(BookRequestBean bean, リクエスト書籍DtoService service,
			予約者DtoService 予約者DtoService) {
		String errMeaage = "入力したしたISBNコードは既にリクエスト済みです。";
		try {
			予約者Dto 予約者 = 予約者DtoService.findOneBy貸出券番号(bean.getCardId()).orElse(null);

			リクエスト書籍Dto registDto = new リクエスト書籍Dto();

			registDto.set予約者ID(予約者.get予約者ID());
			registDto.setISBN(Long.parseLong(bean.getIsbn()));
			registDto.set書名(bean.getTitleName());
			registDto.set著者(bean.getAuthor());
			registDto.set価格(Long.parseLong(bean.getPrice()));
			registDto.set出版社名(bean.getPublisherName());
			registDto.set出版年(java.sql.Date.valueOf(bean.getPublisherDate()));
			Date nowDate = new Date();
			registDto.setリクエスト日(new java.sql.Date(nowDate.getTime()));
			service.save(registDto);
		} catch (DataIntegrityViolationException e) {
			bean.getUsecaseErrMessageList().add(errMeaage);
		}
		return bean;
	}

}
