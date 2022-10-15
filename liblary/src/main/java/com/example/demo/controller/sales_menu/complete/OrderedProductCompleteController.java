package com.example.demo.controller.sales_menu.complete;

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

@Controller
@RequestMapping("/sales_user_menu/order_product/complete")
public class OrderedProductCompleteController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/sales_user_menu/order_product_complete.html";
	@Autowired
	HttpSession session;

	@ModelAttribute
	OrderedProductCompleteBean setUpForm() {
		return new OrderedProductCompleteBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView init(OrderedProductCompleteBean bean, BindingResult result, ModelAndView modelAndView) {
		bean.setLoginCategory(super.getSession(session).getLoginCategory());
		modelAndView.setViewName(Move_URL);
		return modelAndView;

	}

}
