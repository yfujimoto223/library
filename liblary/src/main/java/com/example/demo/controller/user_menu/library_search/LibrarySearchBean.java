package com.example.demo.controller.user_menu.library_search;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.controller.common.AbstractBean;
import com.example.demo.data.dto.書籍Dto;

public class LibrarySearchBean extends AbstractBean {
	/**書名**/
	@Size(max = 100, message = "書名は100文字以内で入力をお願いします。")
	private String titleName;
	/**著者**/
	@Size(max = 100, message = "著者は100文字以内で入力をお願いします。")
	private String author;
	/**出版日from**/
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String publicationDateFrom;
	/**出版日to**/
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String publicationDateTo;
	/**出版社プルダウンList**/
	private Map<Long, String> publisherList = new LinkedHashMap<Long, String>();

	/**書籍検索結果**/
	private List<SearchResult> searchResultList = new ArrayList<SearchResult>();

	/**選択出版社**/
	private String selectPublisher;

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

	public String getPublicationDateFrom() {
		return publicationDateFrom;
	}

	public void setPublicationDateFrom(String publicationDateFrom) {
		this.publicationDateFrom = publicationDateFrom;
	}

	public String getPublicationDateTo() {
		return publicationDateTo;
	}

	public void setPublicationDateTo(String publicationDateTo) {
		this.publicationDateTo = publicationDateTo;
	}

	public List<SearchResult> getSearchResultList() {
		return searchResultList;
	}

	public String getSelectPublisher() {
		return selectPublisher;
	}

	public void setSelectPublisher(String selectPublisher) {
		this.selectPublisher = selectPublisher;
	}

	public void factorySearchResultList(List<書籍Dto> args) {
		for (書籍Dto tmp : args) {
			SearchResult add = new SearchResult();
			add.setBookId(tmp.get書籍ID());
			add.setTitleName(tmp.get書名());
			add.setAuthor(tmp.get著者());
			add.setPrice(tmp.get価格());
			add.setPublisherId(tmp.get出版社ID());
			add.setPublisherDate(tmp.get出版年());
			add.setPublisherName(this.getPublisherList().get(tmp.get出版社ID()));
			this.searchResultList.add(add);
		}
	}

	public void createSearchResultListpublisherName() {

		for (SearchResult tmp : this.searchResultList) {
			tmp.setPublisherName(this.publisherList.get(tmp.getPublisherId()));
		}

	}

	public Map<Long, String> getPublisherList() {
		return publisherList;
	}

	public void setPublisherList(Map<Long, String> publisherList) {
		this.publisherList = publisherList;
	}

	public class SearchResult {

		private Long bookId;
		private String titleName;
		private String author;
		private String furigana;
		private Long price;
		private Long publisherId;
		private Date publisherDate;
		private String publisherName;

		public String getDetailLink() {
			return detailLink;
		}

		public void setDetailLink(String detailLink) {
			this.detailLink = detailLink;
		}

		private String detailLink;

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

		public String getFurigana() {
			return furigana;
		}

		public void setFurigana(String furigana) {
			this.furigana = furigana;
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

	}

}
