package com.example.demo.controller.privacy_policy;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.aop.SessionCheckInterface;
import com.example.demo.controller.common.AbstractController;

@Controller
@RequestMapping("/privacy_policy")
public class PrivacyPolicyController extends AbstractController implements SessionCheckInterface {

	@ModelAttribute
	PrivacyPolicyBean setUpForm() {
		return new PrivacyPolicyBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView init(PrivacyPolicyBean bean, BindingResult result, ModelAndView modelAndView) {
		modelAndView.setViewName("/privacy_policy.html");
		return modelAndView;

	}

}
