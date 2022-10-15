package com.example.demo.controller.manager_menu.book_request_confirm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.controller.common.AbstractBean;
import com.example.demo.data.dto.リクエスト書籍Dto;

public class BookRequestConfirmBean extends AbstractBean {
	/**リクエスト日From**/
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String requestDateFrom;
	/**クエスト日to**/
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String requestDateTo;

	/**書籍検索結果**/
	private List<SearchResult> searchResultList = new ArrayList<SearchResult>();
	/**カート追加リスト**/
	private List<SearchResult> addCartList = new ArrayList<SearchResult>();
	
	//返却チェックボックス
	private List<String> checks = new ArrayList<>();

	public List<String> getChecks() {
		return checks;
	}

	public void setChecks(List<String> checks) {
		this.checks = checks;
	}

	public String getRequestDateFrom() {
		return requestDateFrom;
	}

	public void setRequestDateFrom(String requestDateFrom) {
		this.requestDateFrom = requestDateFrom;
	}

	public String getRequestDateTo() {
		return requestDateTo;
	}

	public void setRequestDateTo(String requestDateTo) {
		this.requestDateTo = requestDateTo;
	}

	public void factorySearchResultList(List<リクエスト書籍Dto> リクエスト書籍DtoList) {
		for (リクエスト書籍Dto tmp : リクエスト書籍DtoList) {
			SearchResult searchResult = new SearchResult();
			searchResult.setRequestId(tmp.getリクエストID());
			searchResult.setRequestUserId(tmp.get予約者ID());
			searchResult.setIsbn(tmp.getISBN());
			searchResult.setTitleName(tmp.get書名());
			searchResult.setAuthor(tmp.get著者());
			searchResult.setPrice(tmp.get価格());
			searchResult.setPublisherName(tmp.get出版社名());
			searchResult.setPublisherDate(tmp.get出版年().toString());
			searchResult.setRequestDate(tmp.getリクエスト日().toString());
			searchResultList.add(searchResult);
		}

	}
	public void factoryAddCartList(List<リクエスト書籍Dto> リクエスト書籍DtoList) {
		for (リクエスト書籍Dto tmp : リクエスト書籍DtoList) {
			SearchResult searchResult = new SearchResult();
			searchResult.setRequestId(tmp.getリクエストID());
			searchResult.setRequestUserId(tmp.get予約者ID());
			searchResult.setIsbn(tmp.getISBN());
			searchResult.setTitleName(tmp.get書名());
			searchResult.setAuthor(tmp.get著者());
			searchResult.setPrice(tmp.get価格());
			searchResult.setPublisherName(tmp.get出版社名());
			searchResult.setPublisherDate(tmp.get出版年().toString());
			searchResult.setRequestDate(tmp.getリクエスト日().toString());
			addCartList.add(searchResult);
		}

	}

	public class SearchResult {
		private Long requestId;
		private Long requestUserId;
		private Long isbn;
		private String titleName;
		private String author;
		private Long price;
		private String publisherName;
		private String publisherDate;
		private String requestDate;

		public Long getRequestId() {
			return requestId;
		}

		public void setRequestId(Long requestId) {
			this.requestId = requestId;
		}

		public Long getRequestUserId() {
			return requestUserId;
		}

		public void setRequestUserId(Long requestUserId) {
			this.requestUserId = requestUserId;
		}

		public Long getIsbn() {
			return isbn;
		}

		public void setIsbn(Long isbn) {
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

		public Long getPrice() {
			return price;
		}

		public void setPrice(Long price) {
			this.price = price;
		}

		public String getPublisherName() {
			return publisherName;
		}

		public void setPublisherName(String publisherName) {
			this.publisherName = publisherName;
		}

		public String getPublisherDate() {
			return publisherDate;
		}

		public void setPublisherDate(String publisherDate) {
			this.publisherDate = publisherDate;
		}

		public String getRequestDate() {
			return requestDate;
		}

		public void setRequestDate(String requestDate) {
			this.requestDate = requestDate;
		}
	}

	public List<SearchResult> getSearchResultList() {
		return searchResultList;
	}

	public void setSearchResultList(List<SearchResult> searchResultList) {
		this.searchResultList = searchResultList;
	}

	public List<SearchResult> getAddCartList() {
		return addCartList;
	}

	public void setAddCartList(List<SearchResult> addCartList) {
		this.addCartList = addCartList;
	}

}
