package com.example.demo.controller.user_menu.message;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.aop.SessionCheckInterface;
import com.example.demo.controller.common.AbstractController;
import com.example.demo.data.service.予約者DtoService;
import com.example.demo.data.service.管理者DtoService;
import com.example.demo.data.service.管理者から利用者へのメッセージDtoService;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.予約者ユースケース;

@Controller
@RequestMapping("/user_menu/message")
public class MessageController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/user_menu/message.html";
	private static final String SuccessMessage = "メッセージの削除が完了しました。";

	@Autowired
	HttpSession session;
	@Autowired
	予約者DtoService 予約者Service;
	@Autowired
	管理者DtoService 管理者DtoService;
	@Autowired
	管理者から利用者へのメッセージDtoService 管理者から利用者へのメッセージDtoService;

	@ModelAttribute
	MessageBean setUpForm() {
		return new MessageBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start(MessageBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		//キャッシュから管理番号を取得
		bean.setCardNo(this.getSession(session).getCardId());
		//メッセージを取得
		bean = this.getMessageList(bean);
		//ユースケースでエラーが発生していないかチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}

		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;
	}

	/**メッセージ削除
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView delete(MessageBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		//キャッシュから管理番号を取得
		bean.setCardNo(this.getSession(session).getCardId());
		//入力値チェック
		if (bean.getChecks().size() == 0) {
			//メッセージを取得
			bean = this.getMessageList(bean);
			//エラーメッセージの設定
			bean.getValidationErrorList().add("チェックをつけてボタンをクリックしてください。");
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}
		予約者ユースケース actor = new DataRegistrantActor();
		bean = actor.管理者からのメッセージを削除(bean, 管理者から利用者へのメッセージDtoService);
		//ユースケースでエラーが発生していないかチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}
		//メッセージ一覧のデータを取得
		bean = this.getMessageList(bean);
		//ユースケースでエラーが発生していないかチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}
		//成功メッセージを設定
		bean.getSuccessMessageList().add(SuccessMessage);
		modelAndView = bean.setHtmlMessageArea(modelAndView);
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);

		return modelAndView;
	}

	private MessageBean getMessageList(MessageBean bean) {
		予約者ユースケース actor = new DataRegistrantActor();
		bean = actor.管理者からのメッセージを取得(bean, 予約者Service, 管理者DtoService, 管理者から利用者へのメッセージDtoService);
		//メッセージ一覧のデータを取得
		return bean;
	}

}
