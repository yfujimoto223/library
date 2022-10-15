package com.example.demo.usecase.actor.data;

import java.util.List;
import java.util.Random;

import com.example.demo.controller.common.psersonal_change.PersonalDataChangeBean;
import com.example.demo.controller.login.LoginBean;
import com.example.demo.controller.personInformationRegist.PersonInformationRegistBean;
import com.example.demo.controller.user_menu.message.MessageBean;
import com.example.demo.data.dto.予約者Dto;
import com.example.demo.data.dto.管理者Dto;
import com.example.demo.data.dto.管理者から利用者へのメッセージDto;
import com.example.demo.data.service.予約者DtoService;
import com.example.demo.data.service.管理者DtoService;
import com.example.demo.data.service.管理者から利用者へのメッセージDtoService;

/**図書館予約者の利用場面**/
public interface 予約者ユースケース {

	/**予約者DBへ個人情報を登録**/
	public default PersonInformationRegistBean 予約者を登録する(PersonInformationRegistBean bean, 予約者DtoService service) {
		予約者Dto saveDto = new 予約者Dto();
		//乱数で登録番号を生成
		saveDto.set貸出券番号(new Random().nextLong(100000));
		saveDto.set名前(bean.getName());
		saveDto.setふりがな(bean.getFurigana());
		saveDto.set住所(bean.getAddress());
		saveDto.set郵便番号(bean.getPostNo());
		saveDto.setEMAIL(bean.geteMail());
		saveDto.setパスワード(bean.getPassword());
		bean.setSearcUserResult(service.save(saveDto));
		return bean;
	}

	/**予約者DBから個人情報を取得**/
	public default LoginBean 予約者の個人情報を取得(LoginBean bean, 予約者DtoService service) {
		String errMessage = "入力したカード番号、パスワード、メールアドレスと一致する情報が存在しません。";
		予約者Dto searchDto = new 予約者Dto();
		searchDto.set貸出券番号(Long.parseLong(bean.getCardId()));
		searchDto.setEMAIL(bean.geteMail());
		searchDto.setパスワード(bean.getPassword());
		bean.setSearcUserResult(service.findBy貸出券番号_EMAIL_パスワード(searchDto));
		//データが存在しない場合、エラーがメッセージを返却
		if (bean.getSearcUserResult() == null) {
			bean.getUsecaseErrMessageList().add(errMessage);
		}
		return bean;
	}

	/**予約者DBから個人情報を取得**/
	public default PersonalDataChangeBean 予約者の個人情報を取得(PersonalDataChangeBean bean, 予約者DtoService service) {
		//DBから取得した情報をBeanへ設定
		予約者Dto result = service.findOneBy貸出券番号(bean.getCardId()).orElse(null);
		bean.setName(result.get名前());
		bean.setFurigana(result.getふりがな());
		bean.setAddress(result.get住所());
		bean.setPostNo(result.get郵便番号());
		bean.seteMail(result.getEMAIL());
		bean.setPassword(result.getパスワード());
		return bean;
	}

	/**予約者DBの個人情報を変更**/
	public default PersonalDataChangeBean 予約者の個人情報を変更(PersonalDataChangeBean bean, 予約者DtoService service) {
		//Bean取得した情報をDBへ登録
		予約者Dto regist = service.findOneBy貸出券番号(bean.getCardId()).orElse(null);
		regist.set名前(bean.getName());
		regist.setふりがな(bean.getFurigana());
		regist.set住所(bean.getAddress());
		regist.set郵便番号(bean.getPostNo());
		regist.setEMAIL(bean.geteMail());
		regist.setパスワード(bean.getPassword());
		service.update(regist);
		return bean;
	}

	/**管理者からのメッセージを取得**/
	public default MessageBean 管理者からのメッセージを取得(MessageBean bean, 予約者DtoService 予約者Service,
			管理者DtoService 管理者DtoService, 管理者から利用者へのメッセージDtoService 管理者から利用者へのメッセージDtoService) {
		予約者Dto 予約者 = 予約者Service.findOneBy貸出券番号(bean.getCardNo()).orElse(null);
		if (予約者 == null) {
			return bean;
		}
		List<管理者Dto> 管理者リスト = 管理者DtoService.findAll();
		if (管理者リスト.size() == 0) {
			return bean;
		}
		List<管理者から利用者へのメッセージDto> メッセージリスト = 管理者から利用者へのメッセージDtoService.findBy予約者Id(予約者.get予約者ID());
		if (メッセージリスト.size() == 0) {
			return bean;
		}
		bean.factorySearchResultList(メッセージリスト, 管理者リスト, 予約者);
		return bean;
	}

	/**管理者からのメッセージを削除**/
	public default MessageBean 管理者からのメッセージを削除(MessageBean bean, 管理者から利用者へのメッセージDtoService 管理者から利用者へのメッセージDtoService) {
		for (String messageId : bean.getChecks()) {
			管理者から利用者へのメッセージDto regist = 管理者から利用者へのメッセージDtoService.findOne(Long.parseLong(messageId)).orElse(null);
			管理者から利用者へのメッセージDtoService.delete(regist);
		}
		return bean;
	}

}
