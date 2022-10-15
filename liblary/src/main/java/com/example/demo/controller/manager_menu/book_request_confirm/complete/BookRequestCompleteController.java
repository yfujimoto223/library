package com.example.demo.controller.manager_menu.book_request_confirm.complete;

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
@RequestMapping("/manager_menu/book_request/complete")
public class BookRequestCompleteController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/manager_menu/book_request_complete.html";
	@Autowired
	HttpSession session;

	@ModelAttribute
	BookRequestCompleteBean setUpForm() {
		return new BookRequestCompleteBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView init(BookRequestCompleteBean bean, BindingResult result, ModelAndView modelAndView) {
		bean.setLoginCategory(super.getSession(session).getLoginCategory());
		modelAndView.setViewName(Move_URL);
		return modelAndView;

	}

}
