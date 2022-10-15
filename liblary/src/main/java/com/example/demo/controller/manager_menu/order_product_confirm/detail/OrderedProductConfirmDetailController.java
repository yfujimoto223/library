package com.example.demo.controller.manager_menu.order_product_confirm.detail;

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
import com.example.demo.aop.SessionCheckInterface;
import com.example.demo.controller.common.AbstractController;
import com.example.demo.data.service.管理者DtoService;
import com.example.demo.domain.orderedproduct.注文書籍Aggregate;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.管理者ユースケース;

@Controller
@RequestMapping("/manager_menu/order_product_confirm/detail")
public class OrderedProductConfirmDetailController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/manager_menu/order_product_confirm_detail.html";

	@Autowired
	HttpSession session;
	@Autowired
	注文書籍Aggregate aggregate;
	@Autowired
	管理者DtoService 管理者DtoService;

	@ModelAttribute
	OrderedProductConfirmDetailBean setUpForm() {
		return new OrderedProductConfirmDetailBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	public ModelAndView start(@PathVariable(name = "orderId", required = true) String orderId,
			OrderedProductConfirmDetailBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		//キャッシュから管理番号を取得
		bean.setCardNo(this.getSession(session).getCardId());
		//納品された書籍リストの注文番号を取得
		bean.setOrderId(Long.parseLong(orderId));
		管理者ユースケース actor = new DataRegistrantActor();
		bean = actor.注文商品の納品状況を確認する(bean, aggregate, 管理者DtoService);
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
