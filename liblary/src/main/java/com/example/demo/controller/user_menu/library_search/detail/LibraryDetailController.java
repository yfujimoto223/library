package com.example.demo.controller.user_menu.library_search.detail;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.aop.SessionCheckInterface;
import com.example.demo.controller.common.AbstractController;
import com.example.demo.data.service.出版社DtoService;
import com.example.demo.data.service.書籍DtoService;
import com.example.demo.domain.rental.貸出状況Aggregate;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.書籍ユースケース;
import com.example.demo.usecase.actor.data.貸出ユースケース;

@Controller
@RequestMapping("/user_menu/library_search/detail")
public class LibraryDetailController extends AbstractController implements SessionCheckInterface {
	private static final String SuccessMessage = "本の貸出が完了しました。";
	@Autowired
	出版社DtoService 出版社DtoService;
	@Autowired
	書籍DtoService 書籍DtoService;
	@Autowired
	貸出状況Aggregate 貸出状況AggregateService;
	@Autowired
	HttpSession session;

	@ModelAttribute
	LibraryDetailBean setUpForm() {
		return new LibraryDetailBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
	public ModelAndView start(@PathVariable(name = "bookId", required = true) String bookId, LibraryDetailBean bean,
			BindingResult result, ModelAndView modelAndView) {
		bean.setBookId(Long.parseLong(bookId));
		書籍ユースケース actor = new DataRegistrantActor();
		bean = actor.書籍詳細情報のデータを取得(bean, 出版社DtoService, 書籍DtoService);
		modelAndView.setViewName("user_menu/detail.html");
		return modelAndView;
	}

	/**予約処理
	 * @throws Exception **/
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView request(LibraryDetailBean bean,
			BindingResult result, ModelAndView modelAndView) throws Exception {
		//カードIDを取得
		bean.setCardId(this.getSession(session).getCardId());
		//画面に書籍情報を表示する為呼び出し
		書籍ユースケース bookUsecase = new DataRegistrantActor();
		bean = bookUsecase.書籍詳細情報のデータを取得(bean, 出版社DtoService, 書籍DtoService);
		貸出ユースケース actor = new DataRegistrantActor();
		bean = actor.本を貸出(bean, 貸出状況AggregateService);
		//ユースケースのエラー件数をチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView.setViewName("user_menu/detail.html");
			return modelAndView;
		}
		bean.getSuccessMessageList().add(SuccessMessage);
		modelAndView = bean.setHtmlMessageArea(modelAndView);
		modelAndView.setViewName("user_menu/detail.html");
		return modelAndView;
	}
}