package com.example.demo.controller.sales_menu.detail;

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
import com.example.demo.domain.orderedproduct.注文書籍Aggregate;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.販売者ユースケース;

@Controller
@RequestMapping("/sales_menu/order_product/detail")
public class OrderedProductDetailController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/sales_user_menu/order_product_detail.html";

	@Autowired
	HttpSession session;
	@Autowired
	注文書籍Aggregate aggregate;

	@ModelAttribute
	OrderedProductDetailBean setUpForm() {
		return new OrderedProductDetailBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	public ModelAndView start(@PathVariable(name = "orderId", required = true) String orderId,
			OrderedProductDetailBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		//リクエストされた書籍を取得
		bean.setOrderId(Long.parseLong(orderId));
		販売者ユースケース actor = new DataRegistrantActor();
		bean = actor.注文商品情報を取得する(bean, aggregate);
		//ユースケースでエラーが発生していないかチェック
		if (bean.getUsecaseErrMessageList().size() > 0) {
			modelAndView = bean.setHtmlMessageArea(modelAndView);
			modelAndView = super.setMovePageConf(modelAndView, Move_URL);
			return modelAndView;
		}

		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;
	}

}
