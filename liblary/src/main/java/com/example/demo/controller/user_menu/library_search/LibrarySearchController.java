package com.example.demo.controller.user_menu.library_search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.aop.SessionCheckInterface;
import com.example.demo.controller.common.AbstractController;
import com.example.demo.controller.user_menu.library_search.LibrarySearchBean.SearchResult;
import com.example.demo.data.service.出版社DtoService;
import com.example.demo.data.service.書籍DtoService;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.書籍ユースケース;

@Controller
@RequestMapping("/user_menu/library_search")
public class LibrarySearchController extends AbstractController implements SessionCheckInterface {
	@Autowired
	HttpSession session;
	@Autowired
	出版社DtoService 出版社DtoService;
	@Autowired
	書籍DtoService 書籍DtoService;

	@ModelAttribute
	LibrarySearchBean setUpForm() {
		return new LibrarySearchBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start(LibrarySearchBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		書籍ユースケース actor = new DataRegistrantActor();
		bean = actor.全出版社のプルダウンデータを取得(bean, 出版社DtoService);
		modelAndView.setViewName("/user_menu/library_search.html");
		return modelAndView;

	}

	/**検索処理
	 * @throws Exception **/
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView search(LibrarySearchBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		List<String> errorList = new ArrayList<String>();
		//入力値チェック
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
		}
		errorList = this.日付チェック(bean, errorList);
		if (errorList.size() > 0) {
			modelAndView.addObject("validationError", errorList);
			return modelAndView;
		}

		書籍ユースケース 書籍アクター = new DataRegistrantActor();
		bean = 書籍アクター.全出版社のプルダウンデータを取得(bean, 出版社DtoService);
		bean = 書籍アクター.書籍のデータを書名_著書_出版社_出版日で取得(bean, 書籍DtoService);
		//詳細画面へのリンクを生成
		bean = this.createDetailUrl(bean);
		modelAndView.setViewName("/user_menu/library_search.html");
		return modelAndView;
	}

	private List<String> 日付チェック(LibrarySearchBean bean, List<String> errList) {
		if (!bean.getPublicationDateFrom().isEmpty() && !bean.getPublicationDateTo().isEmpty())
			if (this.日付変換(bean.getPublicationDateFrom()).after(this.日付変換(bean.getPublicationDateTo()))) {
				errList.add("日付範囲を正しく入力してください。");
			}
		return errList;
	}

	private Date 日付変換(String coveret) {
		return java.sql.Date.valueOf(coveret);
	}

	/**詳細画面へのリンク生成 **/
	private LibrarySearchBean createDetailUrl(LibrarySearchBean bean) {
		String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString() + "/detail/";
		for (SearchResult tmp : bean.getSearchResultList()) {
			tmp.setDetailLink(uri + tmp.getBookId());
		}
		return bean;
	}

}
