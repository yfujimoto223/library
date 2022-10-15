package com.example.demo.controller.user_menu.confirm;

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
import com.example.demo.domain.rental.貸出状況Aggregate;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.貸出ユースケース;

@Controller
@RequestMapping("/user_menu/user_confirm")
public class UserConfirmController extends AbstractController implements SessionCheckInterface {
	private static final String SuccessMessage = "本の返却が完了しました。";
	@Autowired
	貸出状況Aggregate 貸出状況AggregateService;
	@Autowired
	HttpSession session;

	@ModelAttribute
	UserConfirmBean setUpForm() {
		return new UserConfirmBean();
	}

	/**初期処理**/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start(UserConfirmBean bean, BindingResult result, ModelAndView modelAndView) {
		//レンタルしている書籍情報を取得
		bean = this.getRentalBookInfo(bean);
		modelAndView.setViewName("/user_menu/user_confirm.html");
		return modelAndView;

	}

	/**予約処理  **/
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView request(UserConfirmBean bean,
			BindingResult result, ModelAndView modelAndView) throws Exception {
		//チェックボックスにチェックがついているか確認
		if (bean.getChecks().size() == 0) {
			//レンタルしている書籍情報を取得
			bean = this.getRentalBookInfo(bean);
			bean.getSuccessMessageList().add("チェックを付けてからボタンをクリックしてください。");
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView.setViewName("/user_menu/user_confirm.html");
			return modelAndView;
		}
		bean.setCardId(this.getSession(session).getCardId());
		貸出ユースケース actor = new DataRegistrantActor();
		bean = actor.本を返却(bean, 貸出状況AggregateService);
		//ユースケースのエラー件数をチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView.setViewName("/user_menu/user_confirm.html");
			return modelAndView;
		}
		//メッセージを設定
		bean.getSuccessMessageList().add(SuccessMessage);
		modelAndView = bean.setHtmlMessageArea(modelAndView);
		//レンタルしている書籍情報を取得
		bean = this.getRentalBookInfo(bean);
		modelAndView.setViewName("/user_menu/user_confirm.html");
		return modelAndView;
	}

	/**レンタルしている書籍情報を取得  **/
	public UserConfirmBean getRentalBookInfo(UserConfirmBean bean) {
		//ユーザーID取得
		bean.setCardId(this.getSession(session).getCardId());
		貸出ユースケース actor = new DataRegistrantActor();
		return bean = actor.本の貸出状況を取得(bean, 貸出状況AggregateService);
	}
}
