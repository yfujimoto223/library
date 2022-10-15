package com.example.demo.controller.user_menu.library_search.detail;

import java.util.Date;

import com.example.demo.controller.common.AbstractBean;

public class LibraryDetailBean extends AbstractBean {
	/**カードID**/
	private Long cardId;

	/**書籍ID**/
	private Long bookId;
	/**書籍タイトル**/
	private String titleName;
	/**著者**/
	private String author;
	/**値段**/
	private Long price;
	/**出版社ID**/
	private Long publisherId;
	/**出版日**/
	private Date publisherDate;
	/**出版社**/
	private String publisherName;

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public Date getPublisherDate() {
		return publisherDate;
	}

	public void setPublisherDate(Date publisherDate) {
		this.publisherDate = publisherDate;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

}
