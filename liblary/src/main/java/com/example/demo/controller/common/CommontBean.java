package com.example.demo.controller.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.controller.manager_menu.book_request_confirm.BookRequestConfirmBean;
import com.example.demo.controller.manager_menu.book_request_confirm.BookRequestConfirmBean.SearchResult;

public class CommontBean implements Serializable {
	private static final long serialVersionUID = 808198990406616280L;

	public enum LogincategoryEnum {
		利用者(0), 管理者(1), 販売者(2);

		public int id; // フィールドの定義

		private LogincategoryEnum(int id) { // コンストラクタの定義
			this.id = id;
		}
	}

	/**図書館利用者名**/
	private String userName;
	/**カードID**/
	private Long cardId;
	/**リクエスト注文商品一覧**/
	private List<Cart> reuestOrederList = new ArrayList<Cart>();
	/**ログイン画面 or 新規登録画面から経由した際に発行されるトークン**/
	private boolean token;
	/**ログインカテゴリ **/
	private int loginCategory;

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public boolean isToken() {
		return token;
	}

	public void setToken(boolean token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Cart> getReuestOrederList() {
		return reuestOrederList;
	}

	public void setReuestOrederList(BookRequestConfirmBean bean) {
		List<Cart> cartList = new ArrayList<Cart>();
		for (SearchResult tmp : bean.getAddCartList()) {
			//同一のISBNコードを持つリクエスト書籍はカートに入れない
			if (this.getReuestOrederList().stream().filter(a -> a.getIsbn().equals(tmp.getIsbn())).count() > 0) {
				continue;
			}
			Cart cart = new Cart();
			cart.setRequestId(tmp.getRequestId());
			cart.setRequestUserId(tmp.getRequestId());
			cart.setIsbn(tmp.getIsbn());
			cart.setTitleName(tmp.getTitleName());
			cart.setAuthor(tmp.getAuthor());
			cart.setPrice(tmp.getPrice());
			cart.setPublisherName(tmp.getPublisherName());
			cart.setPublisherDate(tmp.getPublisherDate());
			cart.setRequestDate(tmp.getRequestDate());
			cartList.add(cart);
		}
		this.reuestOrederList = cartList;
	}

	public int getLoginCategory() {
		return loginCategory;
	}

	public void setManagerLoginCategory() {
		this.loginCategory = LogincategoryEnum.管理者.id;
	}

	public void setUserLoginCategory() {
		this.loginCategory = LogincategoryEnum.利用者.id;
	}

	public void setSalesUserLoginCategory() {
		this.loginCategory = LogincategoryEnum.販売者.id;
	}

	public class Cart {
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

}
