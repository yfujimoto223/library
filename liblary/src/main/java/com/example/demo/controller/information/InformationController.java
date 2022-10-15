package com.example.demo.controller.information;

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
import com.example.demo.data.service.出版社DtoService;
import com.example.demo.data.service.書籍DtoService;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.書籍ユースケース;

@Controller
@RequestMapping("/information")
public class InformationController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/information.html";

	@Autowired
	HttpSession session;
	@Autowired
	出版社DtoService 出版社service;
	@Autowired
	書籍DtoService 書籍Service;

	@ModelAttribute
	InformationBean setUpForm() {
		return new InformationBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start(InformationBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		書籍ユースケース actor = new DataRegistrantActor();
		bean = actor.お知らせ画面に表示するデータを取得(bean, 出版社service, 書籍Service);
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
