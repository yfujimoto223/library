package com.example.demo.controller.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

/**基底Beanクラス**/
public abstract class AbstractBean {
	/**カード番号**/
	Long cardNo;
	/**ログインカテゴリを格納する**/
	private int loginCategory;

	/**入力値チェックで発生するエラーメッセージを格納する**/
	private List<String> validationErrorList = new ArrayList<String>();
	/**ユースケースで発生するメッセージを格納する**/
	private List<String> usecaseErrMessageList = new ArrayList<String>();
	/**処理に成功したメッセージを格納する**/
	private List<String> successMessageList = new ArrayList<String>();

	public int getLoginCategory() {
		return loginCategory;
	}

	public void setLoginCategory(int loginCategory) {
		this.loginCategory = loginCategory;
	}

	public Long getCardNo() {
		return cardNo;
	}

	public void setCardNo(Long cardNo) {
		this.cardNo = cardNo;
	}

	public List<String> getUsecaseErrMessageList() {
		return usecaseErrMessageList;
	}

	public void setUsecaseErrMessageList(List<String> usecaseErrMessageList) {
		this.usecaseErrMessageList = usecaseErrMessageList;
	}

	public List<String> getSuccessMessageList() {
		return successMessageList;
	}

	public void setSuccessMessageList(List<String> successMessageList) {
		this.successMessageList = successMessageList;
	}

	public List<String> getValidationErrorList() {
		return validationErrorList;
	}

	public void setValidationErrorList(BindingResult result) {
		List<String> errorList = new ArrayList<String>();
		//入力値チェック
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
		}
		this.validationErrorList = errorList;
	}

	/**html画面へ表示するメッセージを設定する**/
	public ModelAndView setHtmlMessageArea(ModelAndView modelAndView) {
		//入力値チェックのエラーメッセージを設定する
		if (this.validationErrorList.size() > 0) {
			modelAndView.addObject("validationError", this.validationErrorList);
		}
		//ユースケースのエラーメッセージを設定する
		if (this.usecaseErrMessageList.size() > 0) {
			modelAndView.addObject("validationError", this.usecaseErrMessageList);
		}
		//処理成功のメッセージを設定する
		if (this.successMessageList.size() > 0) {
			modelAndView.addObject("successMessageList", this.successMessageList);
		}
		return modelAndView;
	}

}
