package com.example.demo.controller.common.psersonal_change;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.controller.common.AbstractBean;

public class PersonalDataChangeBean extends AbstractBean {
	/**カードID**/
	private Long cardId;
	/**名前 **/
	@NotEmpty(message = "名前を入力してください")
	private String name;
	/**ふりがな **/
	@NotEmpty(message = "ふりがなを入力してください")
	private String furigana;
	/**住所 **/
	@NotEmpty(message = "住所を入力してください")
	private String address;
	/**郵便番号 **/
	@NotEmpty(message = "郵便番号を入力してください")
	@Size(min = 8, max = 8, message = "郵便番号は8桁でお願いします。")
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号は数字3桁-数字4桁で入力してください。")
	private String postNo;
	/**パスワード **/
	@NotEmpty(message = "パスワードを入力してください")
	@Size(min = 8, max = 16, message = "パスワードは8桁～16桁でお願いします。")
	private String password;
	/**E-Mail **/
	@NotEmpty(message = "メールアドレスを入力してください")
	@Email
	private String eMail;
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFurigana() {
		return furigana;
	}
	public void setFurigana(String furigana) {
		this.furigana = furigana;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostNo() {
		return postNo;
	}
	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
}
