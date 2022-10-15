package com.example.demo.controller.user_menu.confirm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.controller.common.AbstractBean;
import com.example.demo.data.dto.出版社Dto;
import com.example.demo.data.dto.書籍Dto;
import com.example.demo.data.dto.貸出状況Dto;

public class UserConfirmBean extends AbstractBean {
	/**カードID**/
	private Long cardId;

	/**書籍検索結果**/
	private List<SearchResult> searchResultList = new ArrayList<SearchResult>();

	//返却チェックボックス
	private List<String> checks = new ArrayList<>();
	
	public List<String> getChecks() {
		return checks;
	}

	public void setChecks(List<String> checks) {
		this.checks = checks;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public List<SearchResult> getSearchResultList() {
		return searchResultList;
	}

	public void setSearchResultList(List<SearchResult> searchResultList) {
		this.searchResultList = searchResultList;
	}

	public void factorySearchResult(List<書籍Dto> 書籍DtoList, List<貸出状況Dto> 貸出状況DtoList, List<出版社Dto> 出版社DtoList) {
		for (書籍Dto 書籍 : 書籍DtoList) {
			貸出状況Dto 貸出状況 = 貸出状況DtoList.stream().filter(a -> a.get書籍ID().equals(書籍.get書籍ID())).findFirst()
					.orElse(null);
			出版社Dto 出版社 = 出版社DtoList.stream().filter(a -> a.get出版社ID().equals(書籍.get出版社ID())).findFirst()
					.orElse(null);
			SearchResult searchResult = new SearchResult();
			searchResult.setRentalId(貸出状況.get予約ID());
			searchResult.setBookId(貸出状況.get書籍ID());
			searchResult.setTitleName(書籍.get書名());
			searchResult.setAuthor(書籍.get著者());
			searchResult.setPublisherId(書籍.get出版社ID());
			searchResult.setPublisherName(出版社.get出版社名());
			searchResult.setRentalDate(貸出状況.get申込日());
			searchResult.setReturnDate(貸出状況.get返却予定日());
			searchResult.setRentalStatus(貸出状況.get貸出ステータス());
			this.searchResultList.add(searchResult);
		}
	}

	public class SearchResult {
		private Long rentalId;
		private Long bookId;
		private String titleName;
		private String author;
		private Long publisherId;
		private String publisherName;
		private Date rentalDate;
		private Date returnDate;
		private Long rentalStatus;

		public Long getRentalId() {
			return rentalId;
		}

		public void setRentalId(Long rentalId) {
			this.rentalId = rentalId;
		}

		public Date getRentalDate() {
			return rentalDate;
		}

		public void setRentalDate(Date rentalDate) {
			this.rentalDate = rentalDate;
		}

		public Date getReturnDate() {
			return returnDate;
		}

		public void setReturnDate(Date returnDate) {
			this.returnDate = returnDate;
		}

		public Long getRentalStatus() {
			return rentalStatus;
		}

		public void setRentalStatus(Long rentalStatus) {
			this.rentalStatus = rentalStatus;
		}

		public String getPublisherName() {
			return publisherName;
		}

		public void setPublisherName(String publisherName) {
			this.publisherName = publisherName;
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

		public Long getPublisherId() {
			return publisherId;
		}

		public void setPublisherId(Long publisherId) {
			this.publisherId = publisherId;
		}

	}

}
