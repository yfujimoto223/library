package com.example.demo.controller.common.psersonal_change;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.common.AbstractController;
import com.example.demo.controller.common.CommontBean;
import com.example.demo.data.service.予約者DtoService;
import com.example.demo.data.service.管理者DtoService;
import com.example.demo.data.service.販売者DtoService;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.予約者ユースケース;
import com.example.demo.usecase.actor.data.管理者ユースケース;
import com.example.demo.usecase.actor.data.販売者ユースケース;

@Controller
@RequestMapping("/personal_data_change")
public class PersonalDataChangeController extends AbstractController {
	private static final String SuccessMessage = "登録された個人情報の変更が完了しました。";
	@Autowired
	予約者DtoService 予約者DtoService;
	@Autowired
	管理者DtoService 管理者DtoService;
	@Autowired
	販売者DtoService 販売者DtoService;
	@Autowired
	HttpSession session;

	@ModelAttribute
	PersonalDataChangeBean setUpForm() {
		return new PersonalDataChangeBean();
	}

	/**初期処理**/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start(PersonalDataChangeBean bean, BindingResult result, ModelAndView modelAndView) {
		//セッションから情報を取得
		bean.setCardId(this.getSession(session).getCardId());
		bean.setLoginCategory(this.getSession(session).getLoginCategory());

		//ログインカテゴリが利用者の場合
		if (CommontBean.LogincategoryEnum.利用者.id == bean.getLoginCategory()) {
			予約者ユースケース actor = new DataRegistrantActor();
			bean = actor.予約者の個人情報を取得(bean, 予約者DtoService);
			//ユースケースでエラーが発生していないかチェック
			if (bean.getUsecaseErrMessageList().size() > 0) {
				modelAndView = bean.setHtmlMessageArea(modelAndView);
				modelAndView = this.setMovePageConf(modelAndView);
				return modelAndView;
			}
		}
		//ログインカテゴリが管理者の場合
		else if (CommontBean.LogincategoryEnum.管理者.id == bean.getLoginCategory()) {
			管理者ユースケース actor = new DataRegistrantActor();
			bean = actor.管理者の個人情報を取得(bean, 管理者DtoService);
			//ユースケースでエラーが発生していないかチェック
			if (bean.getUsecaseErrMessageList().size() > 0) {
				modelAndView = bean.setHtmlMessageArea(modelAndView);
				modelAndView = this.setMovePageConf(modelAndView);
				return modelAndView;
			}
		}
		//ログインカテゴリが販売者の場合
		else if (CommontBean.LogincategoryEnum.販売者.id == bean.getLoginCategory()) {
			販売者ユースケース actor = new DataRegistrantActor();
			bean = actor.販売者の個人情報を取得(bean, 販売者DtoService);
			//ユースケースでエラーが発生していないかチェック
			if (bean.getUsecaseErrMessageList().size() > 0) {
				modelAndView = bean.setHtmlMessageArea(modelAndView);
				modelAndView = this.setMovePageConf(modelAndView);
				return modelAndView;
			}
		}
		return modelAndView;

	}

	/**個人情報変更処理  **/
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView request(@Validated PersonalDataChangeBean bean,
			BindingResult result, ModelAndView modelAndView) {
		//セッションから情報を取得
		bean.setCardId(this.getSession(session).getCardId());
		bean.setLoginCategory(this.getSession(session).getLoginCategory());
		//入力値チェック
		bean.setValidationErrorList(result);
		if (bean.getValidationErrorList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = this.setMovePageConf(modelAndView);
			return modelAndView;
		}
		//個人情報を変更
		//ログインカテゴリが利用者の場合
		if (CommontBean.LogincategoryEnum.利用者.id == bean.getLoginCategory()) {
			予約者ユースケース actor = new DataRegistrantActor();
			bean = actor.予約者の個人情報を変更(bean, 予約者DtoService);
			//ユースケースでエラーが発生していないかチェック
			if (bean.getUsecaseErrMessageList().size() > 0) {
				modelAndView = bean.setHtmlMessageArea(modelAndView);
				modelAndView = this.setMovePageConf(modelAndView);
				return modelAndView;
			}
		}
		//ログインカテゴリが管理者の場合
		else if (CommontBean.LogincategoryEnum.管理者.id == bean.getLoginCategory()) {
			管理者ユースケース actor = new DataRegistrantActor();
			bean = actor.管理者の個人情報を変更(bean, 管理者DtoService);
			//ユースケースでエラーが発生していないかチェック
			if (bean.getUsecaseErrMessageList().size() > 0) {
				modelAndView = bean.setHtmlMessageArea(modelAndView);
				modelAndView = this.setMovePageConf(modelAndView);
				return modelAndView;
			}
		}
		//ログインカテゴリが販売者の場合
		else if (CommontBean.LogincategoryEnum.販売者.id == bean.getLoginCategory()) {
			販売者ユースケース actor = new DataRegistrantActor();
			bean = actor.販売者の個人情報を変更(bean, 販売者DtoService);
			//ユースケースでエラーが発生していないかチェック
			if (bean.getUsecaseErrMessageList().size() > 0) {
				modelAndView = bean.setHtmlMessageArea(modelAndView);
				modelAndView = this.setMovePageConf(modelAndView);
				return modelAndView;
			}
		}
		//処理が全て正常に完了した場合
		bean.getSuccessMessageList().add(SuccessMessage);
		modelAndView = bean.setHtmlMessageArea(modelAndView);
		modelAndView = this.setMovePageConf(modelAndView);
		return modelAndView;
	}

	/**遷移する画面を設定 **/
	private ModelAndView setMovePageConf(ModelAndView modelAndView) {
		modelAndView.setViewName("/personal_data_change.html");
		return modelAndView;
	}

}
