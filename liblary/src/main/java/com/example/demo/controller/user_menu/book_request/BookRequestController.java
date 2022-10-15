package com.example.demo.controller.user_menu.book_request;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.aop.SessionCheckInterface;
import com.example.demo.controller.common.AbstractController;
import com.example.demo.data.service.リクエスト書籍DtoService;
import com.example.demo.data.service.予約者DtoService;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.リクエスト書籍ユースケース;

@Controller
@RequestMapping("/user_menu/book_request")
public class BookRequestController extends AbstractController implements SessionCheckInterface {
	private static final String SuccessMessage = "本のリクエストが完了しました。";
	private static final String Move_URL = "/user_menu/book_request.html";
	@Autowired
	リクエスト書籍DtoService リクエスト書籍DtoeService;
	@Autowired
	予約者DtoService 予約者DtoService;
	@Autowired
	HttpSession session;

	@ModelAttribute
	BookRequestBean setUpForm() {
		return new BookRequestBean();
	}

	/**初期処理**/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start(BookRequestBean bean, BindingResult result, ModelAndView modelAndView) {
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;

	}

	/**本をリクエスト  **/
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView requestBook(@Validated BookRequestBean bean,
			BindingResult result, ModelAndView modelAndView) {
		//キャッシュからカード番号を取得
		bean.setCardId(this.getSession(session).getCardId());
		//入力値チェック
		bean.setValidationErrorList(result);
		if (bean.getValidationErrorList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}		
		//本をリクエスト
		リクエスト書籍ユースケース actor = new DataRegistrantActor();
		bean = actor.本をリクエスト(bean, リクエスト書籍DtoeService, 予約者DtoService);
		//ユースケースでエラーが発生していないかチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			return modelAndView;
		}
		//処理が全て正常に完了した場合
		bean.getSuccessMessageList().add(SuccessMessage);
		modelAndView = bean.setHtmlMessageArea(modelAndView);
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;
	}

}
