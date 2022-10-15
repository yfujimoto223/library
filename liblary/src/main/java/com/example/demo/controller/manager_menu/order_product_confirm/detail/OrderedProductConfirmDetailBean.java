package com.example.demo.controller.manager_menu.order_product_confirm.detail;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.controller.common.AbstractBean;
import com.example.demo.data.dto.注文Dto;
import com.example.demo.data.dto.注文明細Dto;
import com.example.demo.domain.orderedproduct.注文書籍ValueObject;

public class OrderedProductConfirmDetailBean extends AbstractBean {
	/**書籍検索結果**/
	private List<SearchResult> searchResultList = new ArrayList<SearchResult>();
	private Long orderId;

	public void factorySearchResultList(注文書籍ValueObject valueObject) {
		for (注文Dto 注文 : valueObject.注文情報を取得する()) {
			for (注文明細Dto 注文明細 : valueObject.注文明細情報を取得する(注文.get注文ID())) {
				SearchResult searchResult = new SearchResult();
				searchResult.setIsbn(注文明細.getISBN().toString());
				searchResult.setTitleName(注文明細.get書名());
				searchResult.setAuthor(注文明細.get著者());
				searchResult.setPrice(注文明細.get価格());
				searchResult.setPublisherName(注文明細.get出版社名());
				searchResult.setPublisherDate(注文明細.get出版年().toString());

				searchResultList.add(searchResult);
			}
		}
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public class SearchResult {
		private String isbn;
		private String titleName;
		private String author;
		private Long price;
		private String publisherName;
		private String publisherDate;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		private String orderId;

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

	}

	public List<SearchResult> getSearchResultList() {
		return searchResultList;
	}

	public void setSearchResultList(List<SearchResult> searchResultList) {
		this.searchResultList = searchResultList;
	}

}
