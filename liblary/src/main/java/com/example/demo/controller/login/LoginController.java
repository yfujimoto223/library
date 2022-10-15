package com.example.demo.controller.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.common.AbstractController;
import com.example.demo.controller.common.CommontBean;
import com.example.demo.controller.menu.MenuBean;
import com.example.demo.controller.personInformationRegist.PersonInformationRegistBean;
import com.example.demo.data.service.予約者DtoService;
import com.example.demo.data.service.管理者DtoService;
import com.example.demo.data.service.販売者DtoService;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.予約者ユースケース;
import com.example.demo.usecase.actor.data.管理者ユースケース;
import com.example.demo.usecase.actor.data.販売者ユースケース;

@Controller
@RequestMapping("/login")
public class LoginController extends AbstractController {
	private static final String Move_URL = "/menu.html";
	@Autowired
	予約者DtoService 予約者DtoService;
	@Autowired
	管理者DtoService 管理者DtoService;
	@Autowired
	販売者DtoService 販売者DtoService;
	@Autowired
	HttpSession session;

	@ModelAttribute
	LoginBean setUpForm() {
		return new LoginBean();
	}

	@GetMapping("/")
	public String login() {
		return "login";
	}

	/**ログインボタンクリック **/
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView login(@Validated LoginBean bean, BindingResult result, ModelAndView modelAndView) {

		//入力値チェック
		bean.setValidationErrorList(result);
		if (bean.getValidationErrorList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			return modelAndView;
		}

		//選択者ラジオボタンが利用者を選択した場合
		if (CommontBean.LogincategoryEnum.利用者.id == Integer.parseInt(bean.getSelectLoginCategory())) {
			予約者ユースケース actor = new DataRegistrantActor();
			bean = actor.予約者の個人情報を取得(bean, 予約者DtoService);
			//ユースケースでエラーが発生していないかチェック
			if (bean.getUsecaseErrMessageList().size() > 0) {
				modelAndView = bean.setHtmlMessageArea(modelAndView);
				return modelAndView;
			}
			this.setUserLoginInfo(bean);
		}
		//選択者ラジオボタンが管理者を選択した場合
		else if (CommontBean.LogincategoryEnum.管理者.id == Integer.parseInt(bean.getSelectLoginCategory())) {
			管理者ユースケース actor = new DataRegistrantActor();
			bean = actor.管理者の個人情報を取得(bean, 管理者DtoService);
			//ユースケースでエラーが発生していないかチェック
			if (bean.getUsecaseErrMessageList().size() > 0) {
				modelAndView = bean.setHtmlMessageArea(modelAndView);
				return modelAndView;
			}
			this.setManagerLoginInfo(bean);
		}
		//選択者ラジオボタンが販売者を選択した場合
		else if (CommontBean.LogincategoryEnum.販売者.id == Integer.parseInt(bean.getSelectLoginCategory())) {
			販売者ユースケース actor = new DataRegistrantActor();
			bean = actor.販売者の個人情報を取得(bean, 販売者DtoService);
			//ユースケースでエラーが発生していないかチェック
			if (bean.getUsecaseErrMessageList().size() > 0) {
				modelAndView = bean.setHtmlMessageArea(modelAndView);
				return modelAndView;
			}
			this.setSalesUserLoginInfo(bean);
		}
		MenuBean menuBean=new MenuBean();
		menuBean.setLoginCategory(super.getSession(session).getLoginCategory());
		modelAndView.addObject("menuBean", menuBean);
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;
	}

	/**新規登録ボタンクリック **/
	@RequestMapping(value = "/move/", method = RequestMethod.POST)
	public ModelAndView personInformationRegistered(ModelAndView modelAndView) {
		modelAndView.addObject("personInformationRegistBean", new PersonInformationRegistBean());
		modelAndView.setViewName("/personInformationRegist.html");
		return modelAndView;
	}

	/**利用者ログイン情報をセッションに設定 **/
	private void setUserLoginInfo(LoginBean loginBean) {
		CommontBean commontBean = new CommontBean();
		commontBean.setCardId(loginBean.getSearcUserResult().get貸出券番号());
		commontBean.setUserName(loginBean.getSearcUserResult().get名前());
		commontBean.setUserLoginCategory();
		commontBean.setToken(true);
		this.setSession(session, commontBean);
	}

	/**管理者ログイン情報をセッションに設定 **/
	private void setManagerLoginInfo(LoginBean loginBean) {
		CommontBean commontBean = new CommontBean();
		commontBean.setCardId(loginBean.getSearchManagerResult().get管理者番号());
		commontBean.setUserName(loginBean.getSearchManagerResult().get名前());
		commontBean.setManagerLoginCategory();
		commontBean.setToken(true);
		this.setSession(session, commontBean);
	}

	/**販売者ログイン情報をセッションに設定 **/
	private void setSalesUserLoginInfo(LoginBean loginBean) {
		CommontBean commontBean = new CommontBean();
		commontBean.setCardId(loginBean.getSearchSalesUserrResult().get販売者番号());
		commontBean.setUserName(loginBean.getSearchSalesUserrResult().get名前());
		commontBean.setSalesUserLoginCategory();
		commontBean.setToken(true);
		this.setSession(session, commontBean);
	}
}
