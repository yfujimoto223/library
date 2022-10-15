package com.example.demo.controller.manager_menu.rental_book_confirm.detail;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.example.demo.controller.common.AbstractBean;
import com.example.demo.data.dto.出版社Dto;
import com.example.demo.data.dto.書籍Dto;
import com.example.demo.data.dto.貸出状況Dto;
import com.example.demo.domain.rental.貸出状況ValueObject;

public class RentalBookConfirmDetailBean extends AbstractBean {
	/**ユーザーID**/
	private Long userId;
	/**利用者名**/
	private String userName;
	/**利用者カード番号**/
	private String userCardNo;
	/**メルアド**/
	private String mail;
	/**利用者へ連絡するメッセージの内容**/
	@Size(max = 200, message = "メッセージの内容は200文字以内でお願いします。")
	private String contactMessage;
	/**書籍検索結果**/
	private List<SearchResult> searchResultList = new ArrayList<SearchResult>();

	public void factorySearchResultList(貸出状況ValueObject 貸出状況ValueObject) {
		for (貸出状況Dto 貸出状況Result : 貸出状況ValueObject.レンタルしている本の貸出情報を取得()) {
			書籍Dto 書籍情報 = 貸出状況ValueObject.レンタルしている本の書籍情報を取得(貸出状況Result.get書籍ID());
			出版社Dto 出版社情報 = 貸出状況ValueObject.レンタルしている本の出版社情報を取得(書籍情報.get出版社ID());
			SearchResult searchResult = new SearchResult();
			searchResult.setBookId(貸出状況Result.get書籍ID());
			searchResult.setTitleName(書籍情報.get書名());
			searchResult.setAuthor(書籍情報.get著者());
			searchResult.setPublisherId(出版社情報.get出版社ID());
			searchResult.setPublisherName(出版社情報.get出版社名());
			searchResult.setRentalDate(貸出状況Result.get申込日().toString());
			searchResult.setBookReturnDate(貸出状況Result.get返却予定日().toString());
			searchResult.setRentalBookStatus(貸出状況ValueObject.レンタルしている本の貸出状況ステータスメッセージを取得(貸出状況Result.get書籍ID()));
			searchResultList.add(searchResult);
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCardNo() {
		return userCardNo;
	}

	public void setUserCardNo(String userCardNo) {
		this.userCardNo = userCardNo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getContactMessage() {
		return contactMessage;
	}

	public void setContactMessage(String contactMessage) {
		this.contactMessage = contactMessage;
	}

	public void setSearchResultList(List<SearchResult> searchResultList) {
		this.searchResultList = searchResultList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<SearchResult> getSearchResultList() {
		return searchResultList;
	}

	public class SearchResult {

		/**書籍ID**/
		private Long bookId;
		/**書籍タイトル**/
		private String titleName;
		/**著者**/
		private String author;
		/**出版社ID**/
		private Long publisherId;
		/**出版名**/
		private String publisherName;
		/**申込日**/
		private String rentalDate;
		/**返却予定日**/
		private String bookReturnDate;
		/**貸出本ステータス**/
		private String rentalBookStatus;

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

		public Long getPublisherId() {
			return publisherId;
		}

		public void setPublisherId(Long publisherId) {
			this.publisherId = publisherId;
		}

		public String getPublisherName() {
			return publisherName;
		}

		public void setPublisherName(String publisherName) {
			this.publisherName = publisherName;
		}

		public String getRentalDate() {
			return rentalDate;
		}

		public void setRentalDate(String rentalDate) {
			this.rentalDate = rentalDate;
		}

		public String getBookReturnDate() {
			return bookReturnDate;
		}

		public void setBookReturnDate(String bookReturnDate) {
			this.bookReturnDate = bookReturnDate;
		}

		public String getRentalBookStatus() {
			return rentalBookStatus;
		}

		public void setRentalBookStatus(String rentalBookStatus) {
			this.rentalBookStatus = rentalBookStatus;
		}

	}

}
