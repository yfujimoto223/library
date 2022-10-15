package com.example.demo.controller.manager_menu.order_product_confirm;

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
import com.example.demo.aop.SessionCheckInterface;
import com.example.demo.controller.common.AbstractController;
import com.example.demo.controller.manager_menu.order_product_confirm.OrderedProductConfirmBean.SearchOrderBookCompleted;
import com.example.demo.data.service.管理者DtoService;
import com.example.demo.domain.orderedproduct.注文書籍Aggregate;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.管理者ユースケース;

@Controller
@RequestMapping("/manager_menu/order_product_confirm")
public class OrderedProductConfirmController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/manager_menu/order_product_confirm.html";

	@Autowired
	HttpSession session;
	@Autowired
	注文書籍Aggregate aggregate;
	@Autowired
	管理者DtoService 管理者DtoService;

	@ModelAttribute
	OrderedProductConfirmBean setUpForm() {
		return new OrderedProductConfirmBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start(OrderedProductConfirmBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;

	}

	/**検索処理
	 * @throws Exception **/
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView search(@Validated OrderedProductConfirmBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		//セッション情報から管理番号を取得
		bean.setCardNo(this.getSession(session).getCardId());
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
		bean = this.getOrderBookCompleteList(bean);
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

	private boolean 日付チェック(OrderedProductConfirmBean bean) {
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
	private OrderedProductConfirmBean createDetailUrl(OrderedProductConfirmBean bean) {
		String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString() + "/detail/";
		for (SearchOrderBookCompleted tmp : bean.getSearchResultList()) {
			tmp.setDetailLink(uri + tmp.getOrderId());
		}
		return bean;
	}

	/**注文書籍の納品情報を取得 **/
	private OrderedProductConfirmBean getOrderBookCompleteList(OrderedProductConfirmBean bean) {
		//注文された書籍を取得
		管理者ユースケース actor = new DataRegistrantActor();
		bean = actor.注文商品の納品状況を確認する(bean, aggregate, 管理者DtoService);
		return bean;
	}
}
