package com.example.demo.controller.sales_menu;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.aop.SessionCheckInterface;
import com.example.demo.controller.common.AbstractController;
import com.example.demo.controller.sales_menu.OrderedProductBean.SearchResult;
import com.example.demo.controller.sales_menu.complete.OrderedProductCompleteBean;
import com.example.demo.domain.orderedproduct.注文書籍Aggregate;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.販売者ユースケース;

@Controller
@RequestMapping("/sales_menu/order_product")
public class OrderedProductController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/sales_user_menu/order_product.html";
	private static final String Move_Succes_URL = "/sales_user_menu/order_product_complete.html";

	@Autowired
	HttpSession session;
	@Autowired
	注文書籍Aggregate aggregate;

	@ModelAttribute
	OrderedProductBean setUpForm() {
		return new OrderedProductBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start(OrderedProductBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;

	}

	/**検索処理
	 * @throws Exception **/
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView search(@Validated OrderedProductBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		//入力値チェック
		bean.setValidationErrorList(result);
		if (!this.日付チェック(bean)) {
			bean.getValidationErrorList().add("日付範囲を正しく入力してください。");
		}
		if (bean.getValidationErrorList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}
		//注文した書籍情報の取得
		this.getOrderBoolList(bean);
		//ユースケースでエラーが発生していないかチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}
		//詳細画面へのリンクを生成
		bean = this.createDetailUrl(bean);
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;
	}

	/**注文された書籍を納品処理
	 * @throws Exception **/
	@RequestMapping(value = "/delivery_book", method = RequestMethod.POST)
	public ModelAndView deliveryBook(OrderedProductBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		//入力値チェック
		if (bean.getChecks().size() == 0) {
			//注文した書籍情報の取得
			this.getOrderBoolList(bean);
			//エラーメッセージの設定
			bean.getValidationErrorList().add("チェックをつけてボタンをクリックしてください。");
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}
		//チェックされた注文書籍を納品
		販売者ユースケース actor = new DataRegistrantActor();
		bean = actor.注文された商品を納品する(bean, aggregate);
		//ユースケースでエラーが発生していないかチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;

		}
		OrderedProductCompleteBean orderedProductCompleteBean = new OrderedProductCompleteBean();
		modelAndView.addObject("orderedProductCompleteBean", orderedProductCompleteBean);
		modelAndView = super.setMovePageConf(modelAndView, Move_Succes_URL);
		return modelAndView;
	}

	private boolean 日付チェック(OrderedProductBean bean) {
		if (!bean.getOrderDateFrom().isEmpty() && !bean.getOrderDateTo().isEmpty())
			if (this.日付変換(bean.getOrderDateFrom()).after(this.日付変換(bean.getOrderDateTo()))) {
				return false;
			}
		return true;
	}

	private Date 日付変換(String coveret) {
		return java.sql.Date.valueOf(coveret);
	}

	/**詳細画面へのリンク生成 **/
	private OrderedProductBean createDetailUrl(OrderedProductBean bean) {
		String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString() + "/detail/";
		for (SearchResult tmp : bean.getSearchResultList()) {
			tmp.setDetailLink(uri + tmp.getOrderId());
		}
		return bean;
	}

	/**注文書籍情報を取得 **/
	private OrderedProductBean getOrderBoolList(OrderedProductBean bean) {
		//注文された書籍を取得
		販売者ユースケース actor = new DataRegistrantActor();
		bean = actor.注文商品情報を取得する(bean, aggregate);
		return bean;
	}
}
