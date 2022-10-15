package com.example.demo.controller.logout;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.common.AbstractController;
import com.example.demo.controller.login.LoginBean;

@Controller
@RequestMapping("/logout")
public class LogoutController extends AbstractController {
	@Autowired
	HttpSession session;

	@ModelAttribute
	LogoutBean setUpForm() {
		return new LogoutBean();
	}

	/**ログアウトクリック **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView logout(@Validated LogoutBean logoutBean, BindingResult result, ModelAndView modelAndView) {
		//セッション破棄
		this.deleteSession(session);
		//ログイン画面へ遷移
		modelAndView.addObject("loginBean", new LoginBean());
		modelAndView.setViewName("/login.html");
		return modelAndView;
	}
}
