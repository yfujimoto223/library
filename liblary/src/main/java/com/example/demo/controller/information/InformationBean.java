package com.example.demo.controller.information;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.controller.common.AbstractBean;
import com.example.demo.data.dto.出版社Dto;
import com.example.demo.data.dto.書籍Dto;

public class InformationBean extends AbstractBean {
	/**書籍検索結果**/
	private List<SearchResult> searchResultList = new ArrayList<SearchResult>();
	private Long orderId;

	public void addSearchResultList(書籍Dto 書籍, 出版社Dto 出版) {
		SearchResult searchResult = new SearchResult();
		searchResult.setTitleName(書籍.get書名());
		searchResult.setAuthor(書籍.get著者());
		searchResult.setPrice(書籍.get価格());
		searchResult.setPublisherId(出版.get出版社ID());
		searchResult.setPublisherDate(書籍.get出版年());
		searchResult.setPublisherName(出版.get出版社名());
		searchResult.setInputDate(書籍.get登録日時().toString());

		searchResultList.add(searchResult);

	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public class SearchResult {

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
		/**入荷日**/
		private String inputDate;

		public String getInputDate() {
			return inputDate;
		}

		public void setInputDate(String inputDate) {
			this.inputDate = inputDate;
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

	public List<SearchResult> getSearchResultList() {
		return searchResultList;
	}

	public void setSearchResultList(List<SearchResult> searchResultList) {
		this.searchResultList = searchResultList;
	}

}
