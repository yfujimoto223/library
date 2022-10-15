package com.example.demo.controller.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.controller.common.AbstractBean;
import com.example.demo.data.dto.予約者Dto;
import com.example.demo.data.dto.管理者Dto;
import com.example.demo.data.dto.販売者Dto;

public class LoginBean extends AbstractBean {
	/**カード番号 **/
	@NotEmpty(message = "カード番号を入力してください")
	@Pattern(regexp = "^[0-9]+$", message = "カード番号は数字のみ入力してください。")
	private String cardId;
	/**eMail **/
	@NotEmpty(message = "メールアドレスを入力してください")
	@Email
	private String eMail;
	/**パスワード **/
	@NotEmpty(message = "パスワードを入力してください")
	@Size(min = 8, max = 16, message = "パスワードは8桁～16桁でお願いします。")
	private String password;
	/**ログインカテゴリ **/
	@NotEmpty(message = "ログインカテゴリを選択してください")
	private String selectLoginCategory;
	/**検索結果DTO(予約者) **/
	private 予約者Dto searcUserResult;
	/**検索結果DTO(管理者) **/
	private 管理者Dto searchManagerResult;
	/**検索結果DTO(販売者) **/
	private 販売者Dto searchSalesUserrResult;

	public 販売者Dto getSearchSalesUserrResult() {
		return searchSalesUserrResult;
	}

	public void setSearchSalesUserrResult(販売者Dto searchSalesUserrResult) {
		this.searchSalesUserrResult = searchSalesUserrResult;
	}

	public 管理者Dto getSearchManagerResult() {
		return searchManagerResult;
	}

	public void setSearchManagerResult(管理者Dto searchManagerResult) {
		this.searchManagerResult = searchManagerResult;
	}

	public 予約者Dto getSearcUserResult() {
		return searcUserResult;
	}

	public void setSearcUserResult(予約者Dto searcUserResult) {
		this.searcUserResult = searcUserResult;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCardId() {
		return cardId;
	}

	public String getSelectLoginCategory() {
		return selectLoginCategory;
	}

	public void setSelectLoginCategory(String selectLoginCategory) {
		this.selectLoginCategory = selectLoginCategory;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

}
