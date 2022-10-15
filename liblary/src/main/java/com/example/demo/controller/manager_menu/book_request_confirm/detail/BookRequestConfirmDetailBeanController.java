package com.example.demo.controller.manager_menu.book_request_confirm.detail;

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
import com.example.demo.controller.manager_menu.book_request_confirm.complete.BookRequestCompleteBean;
import com.example.demo.data.service.管理者DtoService;
import com.example.demo.domain.orderedproduct.注文書籍Aggregate;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.管理者ユースケース;

@Controller
@RequestMapping("/manager_menu/book_request_confirm/detail")
public class BookRequestConfirmDetailBeanController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/manager_menu/book_request_confirm_detail.html";
	private static final String Order_Success_URL = "/manager_menu/book_request_complete.html";

	@Autowired
	HttpSession session;
	@Autowired
	注文書籍Aggregate リクエスト書籍Aggregate;
	@Autowired
	管理者DtoService 管理者DtoService;

	@ModelAttribute
	BookRequestConfirmDetailBean setUpForm() {
		return new BookRequestConfirmDetailBean();
	}

	/**初期処理 キャッシュからカートを取得する
	 * @throws Exception **/
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView start(BookRequestConfirmDetailBean bean,
			BindingResult result, ModelAndView modelAndView) {
		bean.setCommontBean(super.getSession(session));
		modelAndView.setViewName(Move_URL);
		return modelAndView;
	}

	/**リクエストした書籍を注文
	 * @throws Exception **/
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView orderRequestBook(BookRequestConfirmDetailBean bean,
			BindingResult result, ModelAndView modelAndView) throws Exception {
		//キャッシュからリクエスト書籍を取得
		bean.setCommontBean(super.getSession(session));
		bean.setCardNo(super.getSession(session).getCardId());
		//画面に書籍情報を表示する為呼び出し
		管理者ユースケース actor = new DataRegistrantActor();
		bean = actor.リクエスト書籍を注文(bean, リクエスト書籍Aggregate, 管理者DtoService);
		//ユースケースのエラー件数をチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView.setViewName("user_menu/detail.html");
			return modelAndView;
		}
		//カートのキャッシュから注文情報を削除
		super.getSession(session).getReuestOrederList().clear();

		//注文完了ページへ遷移
		modelAndView.addObject("bookRequestCompleteBean", new BookRequestCompleteBean());
		modelAndView = super.setMovePageConf(modelAndView, Order_Success_URL);
		return modelAndView;
	}
}