package com.example.demo.controller.manager_menu.rental_book_confirm.detail;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.aop.SessionCheckInterface;
import com.example.demo.controller.common.AbstractController;
import com.example.demo.data.service.予約者DtoService;
import com.example.demo.data.service.管理者DtoService;
import com.example.demo.data.service.管理者から利用者へのメッセージDtoService;
import com.example.demo.domain.rental.貸出状況Aggregate;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.管理者ユースケース;

@Controller
@RequestMapping("/manager_menu/rental_book_confirm/detail")
public class RentalBookConfirmDetailController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/manager_menu/rental_book_confirm_detail.html";
	private static final String SuccessMessage = "利用者宛てのメッセージを書き込みました。";

	@Autowired
	HttpSession session;
	@Autowired
	貸出状況Aggregate aggregate;
	@Autowired
	予約者DtoService 予約者DtoServicce;
	@Autowired
	管理者から利用者へのメッセージDtoService 管理者から利用者へのメッセージDtoService;
	@Autowired
	管理者DtoService 管理者DtoService;

	@ModelAttribute
	RentalBookConfirmDetailBean setUpForm() {
		return new RentalBookConfirmDetailBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ModelAndView start(@PathVariable(name = "userId", required = true) String userId,
			RentalBookConfirmDetailBean bean,
			BindingResult result, ModelAndView modelAndView) {
		//ログインユーザーの識別番号を取得
		bean.setCardNo(this.getSession(session).getCardId());
		//ユーザーIDを取得
		bean.setUserId(Long.parseLong(userId));
		管理者ユースケース actor = new DataRegistrantActor();
		bean = actor.本の貸出状況詳細を取得(bean, 予約者DtoServicce, aggregate);
		modelAndView = bean.setHtmlMessageArea(modelAndView);
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;
	}

	/**メッセージ送信処理
	 * @throws Exception **/
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView search(@Validated RentalBookConfirmDetailBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		//ログインユーザーの識別番号を取得
		bean.setCardNo(this.getSession(session).getCardId());
		//入力値チェック
		bean.setValidationErrorList(result);
		if (bean.getValidationErrorList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}
		//貸出状況一覧取得
		管理者ユースケース actor = new DataRegistrantActor();
		bean = actor.管理者から利用者宛にメッセージを送信(bean, 予約者DtoServicce, 管理者から利用者へのメッセージDtoService, 管理者DtoService);
		//ユースケースでエラーが発生していないかチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}
		//本の貸出状況詳細を取得
		bean = actor.本の貸出状況詳細を取得(bean, 予約者DtoServicce, aggregate);
		//ユースケースでエラーが発生していないかチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}

		bean.getSuccessMessageList().add(SuccessMessage);
		modelAndView = bean.setHtmlMessageArea(modelAndView);
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;
	}

}
