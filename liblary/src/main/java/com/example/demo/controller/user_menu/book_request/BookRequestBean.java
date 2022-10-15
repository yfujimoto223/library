package com.example.demo.controller.user_menu.book_request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.example.demo.controller.common.AbstractBean;

public class BookRequestBean extends AbstractBean {
	/**カードID**/
	private Long cardId;
	/**ISBN **/	
	@NotEmpty(message = "ISBNを入力してください")
	@Pattern(regexp = "^[0-9]+$", message = "ISBNは数字のみ入力してください。")	
	private String isbn;
	/**書名**/
	@NotEmpty(message = "書名を入力してください")
	private String titleName;
	/**著者**/
	@NotEmpty(message = "著者を入力してください")
	private String author;
	/**値段**/
	@NotEmpty(message = "値段を入力してください")	
	private String price;
	/**出版日**/
	@NotEmpty(message = "出版日を入力してください")		
	private String publisherDate;

	/**出版社**/
	private String publisherName;

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public String getPublisherDate() {
		return publisherDate;
	}

	public void setPublisherDate(String publisherDate) {
		this.publisherDate = publisherDate;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

}
