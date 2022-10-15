package com.example.demo.controller.manager_menu.rental_book_confirm;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.controller.common.AbstractBean;
import com.example.demo.data.dto.予約者Dto;
import com.example.demo.data.dto.貸出状況Dto;

public class RentalBookConfirmBean extends AbstractBean {
	/**利用者名**/
	@Size(max = 100, message = "利用者名は100文字以内で入力をお願いします。")
	private String userName;
	/**返却予定日From**/
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String rentalBookReturnDateFrom;
	/**返却予定日to**/
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String rentalBookReturnDateTo;

	/**書籍検索結果**/
	private List<SearchResult> searchResultList = new ArrayList<SearchResult>();

	public List<SearchResult> getSearchResultList() {
		return searchResultList;
	}

	public void factorySearchResultList(予約者Dto user, List<貸出状況Dto> bookRentalList, Long 返却冊数) {
		SearchResult searchResult = new SearchResult();
		searchResult.setUserId(user.get予約者ID());
		searchResult.setUserName(user.get名前());
		searchResult.setCardNo(user.get貸出券番号().toString());
		searchResult.setReturnBookCount(返却冊数.toString());
		searchResult.setMail(user.getEMAIL());
		Long rentalBookCount = bookRentalList.stream().count();
		searchResult.setRentalBookCount(rentalBookCount.toString());
		for (貸出状況Dto tmp : bookRentalList) {
			searchResult.getRentalBookList().add(tmp);
		}
		searchResultList.add(searchResult);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRentalBookReturnDateFrom() {
		return rentalBookReturnDateFrom;
	}

	public void setRentalBookReturnDateFrom(String rentalBookReturnDateFrom) {
		this.rentalBookReturnDateFrom = rentalBookReturnDateFrom;
	}

	public String getRentalBookReturnDateTo() {
		return rentalBookReturnDateTo;
	}

	public void setRentalBookReturnDateTo(String rentalBookReturnDateTo) {
		this.rentalBookReturnDateTo = rentalBookReturnDateTo;
	}

	public void setSearchResultList(List<SearchResult> searchResultList) {
		this.searchResultList = searchResultList;
	}

	public class SearchResult {
		private Long userId;
		private String userName;
		private String cardNo;
		private String mail;
		private List<貸出状況Dto> rentalBookList = new ArrayList<貸出状況Dto>();
		private String returnBookCount;
		private String rentalBookCount;

		public String getRentalBookCount() {
			return rentalBookCount;
		}

		public void setRentalBookCount(String rentalBookCount) {
			this.rentalBookCount = rentalBookCount;
		}

		public String getReturnBookCount() {
			return returnBookCount;
		}

		public void setReturnBookCount(String returnBookCount) {
			this.returnBookCount = returnBookCount;
		}

		public String getDetailLink() {
			return detailLink;
		}

		public void setDetailLink(String detailLink) {
			this.detailLink = detailLink;
		}

		private String detailLink;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public List<貸出状況Dto> getRentalBookList() {
			return rentalBookList;
		}

		public void setRentalBookList(List<貸出状況Dto> rentalBookList) {
			this.rentalBookList = rentalBookList;
		}

		/**返却予定日を取得**/
		public String getBookReturnDate() {
			return this.rentalBookList.get(0).get返却予定日().toString();
		}

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}

	}

}
