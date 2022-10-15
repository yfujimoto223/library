package com.example.demo.controller.menu;

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
@RequestMapping("/menu")
public class MenuController extends AbstractController implements SessionCheckInterface {
	@Autowired
	HttpSession session;

	@ModelAttribute
	MenuBean setUpForm() {
		return new MenuBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView init(MenuBean bean, BindingResult result, ModelAndView modelAndView) {
		bean.setLoginCategory(super.getSession(session).getLoginCategory());
		modelAndView.setViewName("/menu.html");
		return modelAndView;

	}

}
