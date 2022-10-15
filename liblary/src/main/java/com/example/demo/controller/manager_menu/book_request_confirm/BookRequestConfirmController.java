package com.example.demo.controller.manager_menu.book_request_confirm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.example.demo.controller.common.CommontBean;
import com.example.demo.controller.manager_menu.book_request_confirm.BookRequestConfirmBean.SearchResult;
import com.example.demo.controller.manager_menu.book_request_confirm.detail.BookRequestConfirmDetailBean;
import com.example.demo.data.service.リクエスト書籍DtoService;
import com.example.demo.domain.orderedproduct.注文書籍Aggregate;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.管理者ユースケース;

@Controller
@RequestMapping("/manager_menu/book_request_confirm")
public class BookRequestConfirmController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/manager_menu/book_request_confirm.html";
	private static final String Move_DetailURL = "/manager_menu/book_request_confirm_detail.html";
	private static final String SuccessMessage = "注文する書籍をカートに入れました。";

	@Autowired
	HttpSession session;
	@Autowired
	リクエスト書籍DtoService リクエスト書籍DtoService;
	@Autowired
	注文書籍Aggregate aggregate;

	@ModelAttribute
	BookRequestConfirmBean setUpForm() {
		return new BookRequestConfirmBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start(BookRequestConfirmBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;

	}

	/**検索処理
	 * @throws Exception **/
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView search(@Validated BookRequestConfirmBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		//入力値チェック
		bean.setValidationErrorList(result);
		if (!this.日付チェック(bean)) {
			bean.getValidationErrorList().add("日付範囲を正しく入力してください。");
		}
		if (bean.getValidationErrorList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			//リクエストされた書籍情報を取得
			bean = this.getRequestBooks(bean);
			return modelAndView;
		}
		//リクエストされた書籍情報を取得
		bean = this.getRequestBooks(bean);
		//ユースケースでエラーが発生していないかチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;
	}

	/**カートキャッシュ情報に注文する書籍を追加
	 * @throws Exception **/
	@RequestMapping(value = "/add_cart", method = RequestMethod.POST)
	public ModelAndView addCart(BookRequestConfirmBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		//入力値チェック
		if (bean.getChecks().size() == 0) {
			bean.getValidationErrorList().add("チェックをつけてボタンをクリックしてください。");
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			//リクエストされた書籍情報を取得
			bean = this.getRequestBooks(bean);
			return modelAndView;
		}
		//チェックされたリクエスト書籍をカートに登録
		管理者ユースケース actor = new DataRegistrantActor();
		bean = actor.チェックされたリクエスト書籍の情報を取得(bean, リクエスト書籍DtoService);
		//ユースケースでエラーが発生していないかチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			//リクエストされた書籍情報を取得
			bean = this.getRequestBooks(bean);
			return modelAndView;

		}
		//カートにリクエスト書籍を追加
		this.addCard(bean);
		//リクエストされた書籍情報を取得
		bean = this.getRequestBooks(bean);
		bean.getSuccessMessageList().add(SuccessMessage);
		modelAndView = bean.setHtmlMessageArea(modelAndView);
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;
	}

	/**リクエスト書籍注文明細画面へ移動
	 * @throws Exception **/
	@RequestMapping(value = "/move", method = RequestMethod.POST)
	public ModelAndView move(BookRequestConfirmBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		//入力値チェック
		if (super.getSession(session).getReuestOrederList().size() == 0) {
			bean.getValidationErrorList().add("カートに商品を入れてボタンをクリックしてください。");
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			//リクエストされた書籍情報を取得
			bean = this.getRequestBooks(bean);
			return modelAndView;
		}
		//リクエスト書籍注文明細画面へ遷移
		BookRequestConfirmDetailBean bookRequestConfirmDetailBean = new BookRequestConfirmDetailBean();
		bookRequestConfirmDetailBean.setCommontBean(super.getSession(session));
		modelAndView.addObject("bookRequestConfirmDetailBean", bookRequestConfirmDetailBean);
		modelAndView = super.setMovePageConf(modelAndView, Move_DetailURL);
		return modelAndView;
	}

	private boolean 日付チェック(BookRequestConfirmBean bean) {
		if (!bean.getRequestDateFrom().isEmpty() && !bean.getRequestDateTo().isEmpty())
			if (this.日付変換(bean.getRequestDateFrom()).after(this.日付変換(bean.getRequestDateTo()))) {
				return false;
			}
		return true;
	}

	private Date 日付変換(String coveret) {
		return java.sql.Date.valueOf(coveret);
	}

	/**リクエスト書籍のデータ一覧を取得**/
	private BookRequestConfirmBean getRequestBooks(BookRequestConfirmBean bean) {
		//リクエストされた書籍を取得
		管理者ユースケース actor = new DataRegistrantActor();
		bean = actor.リクエストされた書籍を取得(bean, aggregate);
		//セッション情報からカートを取得
		CommontBean commonBean = super.getSession(session);
		//カートに含まれる商品は検索結果から除外する
		List<SearchResult> cashDeleteList=new ArrayList<SearchResult>();
		for(SearchResult tmp:bean.getSearchResultList()) {
			if(commonBean.getReuestOrederList().stream().filter(a->a.getIsbn().equals(tmp.getIsbn())).count()==0) {
				cashDeleteList.add(tmp);
			}
		}
		bean.getSearchResultList().clear();
		bean.getSearchResultList().addAll(cashDeleteList);
		return bean;
	}

	/**カートにリクエスト書籍を追加**/
	private BookRequestConfirmBean addCard(BookRequestConfirmBean bean) {
		//セッション情報を取得
		CommontBean commonBean = super.getSession(session);
		commonBean.setReuestOrederList(bean);
		return bean;
	}
}
