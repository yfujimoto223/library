package com.example.demo.controller.manager_menu.rental_book_confirm;

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
import com.example.demo.controller.common.AbstractController;
import com.example.demo.controller.manager_menu.rental_book_confirm.RentalBookConfirmBean.SearchResult;
import com.example.demo.data.service.予約者DtoService;
import com.example.demo.domain.rental.貸出状況Aggregate;
import com.example.demo.usecase.actor.data.DataRegistrantActor;
import com.example.demo.usecase.actor.data.管理者ユースケース;

@Controller
@RequestMapping("/manager_menu/rental_book_confirm")
public class RentalBookConfirmController extends AbstractController implements SessionCheckInterface {
	private static final String Move_URL = "/manager_menu/rental_book_confirm.html";

	@Autowired
	HttpSession session;
	@Autowired
	予約者DtoService 予約者DtoService;
	@Autowired
	貸出状況Aggregate aggregate;

	@ModelAttribute
	RentalBookConfirmBean setUpForm() {
		return new RentalBookConfirmBean();
	}

	/**初期処理
	 * @throws Exception **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start(RentalBookConfirmBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
		modelAndView = super.setMovePageConf(modelAndView, Move_URL);
		return modelAndView;

	}

	/**検索処理
	 * @throws Exception **/
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView search(@Validated RentalBookConfirmBean bean, BindingResult result, ModelAndView modelAndView)
			throws Exception {
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
		//貸出状況一覧取得
		管理者ユースケース actor = new DataRegistrantActor();
		bean = actor.本の貸出状況を取得(bean, aggregate, 予約者DtoService);
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

	private boolean 日付チェック(RentalBookConfirmBean bean) {
		if (!bean.getRentalBookReturnDateFrom().isEmpty() && !bean.getRentalBookReturnDateTo().isEmpty())
			if (this.日付変換(bean.getRentalBookReturnDateFrom()).after(this.日付変換(bean.getRentalBookReturnDateTo()))) {
				return false;
			}
		return true;
	}

	private Date 日付変換(String coveret) {
		return java.sql.Date.valueOf(coveret);
	}

	/**詳細画面へのリンク生成 **/
	private RentalBookConfirmBean createDetailUrl(RentalBookConfirmBean bean) {
		String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString() + "/detail/";
		for (SearchResult tmp : bean.getSearchResultList()) {
			tmp.setDetailLink(uri + tmp.getUserId());
		}
		return bean;
	}

}
