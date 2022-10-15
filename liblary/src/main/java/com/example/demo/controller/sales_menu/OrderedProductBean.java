package com.example.demo.controller.sales_menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.controller.common.AbstractBean;
import com.example.demo.data.dto.注文Dto;
import com.example.demo.data.dto.管理者Dto;
import com.example.demo.domain.orderedproduct.注文書籍ValueObject;

public class OrderedProductBean extends AbstractBean {
	/**注文者**/
	private String selectOrderName;

	/**注文日From**/
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String orderDateFrom;
	/**注文日to**/
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String orderDateTo;
	/**書籍検索結果**/
	private List<SearchResult> searchResultList = new ArrayList<SearchResult>();

	/**発注チェックボックス**/
	private List<String> checks = new ArrayList<>();

	public String getOrderDateFrom() {
		return orderDateFrom;
	}

	public void setOrderDateFrom(String orderDateFrom) {
		this.orderDateFrom = orderDateFrom;
	}

	public String getOrderDateTo() {
		return orderDateTo;
	}

	public String getSelectOrderName() {
		return selectOrderName;
	}

	public void setSelectOrderName(String selectOrderName) {
		this.selectOrderName = selectOrderName;
	}

	public void setOrderDateTo(String orderDateTo) {
		this.orderDateTo = orderDateTo;
	}

	public List<String> getChecks() {
		return checks;
	}

	public void setChecks(List<String> checks) {
		this.checks = checks;
	}

	public void factorySearchResultList(注文書籍ValueObject valueObject) {
		for (注文Dto 注文 : valueObject.注文情報を取得する()) {
			管理者Dto 注文者 = valueObject.注文者情報を取得する(注文.get管理者ID());
			SearchResult searchResult = new SearchResult();
			searchResult.setOrderId(注文.get注文ID().toString());
			searchResult.setManagerId(注文.get管理者ID().toString());
			searchResult.setManagerName(注文者.get名前());
			searchResult.setOrderDate(注文.get注文日().toString());
			searchResult.setOrderAmount(注文.get注文金額().toString());
			searchResultList.add(searchResult);

		}
	}

	public class SearchResult {
		private String managerId;
		private String managerName;
		private String orderDate;
		private String orderAmount;
		private String detailLink;

		public String getDetailLink() {
			return detailLink;
		}

		public void setDetailLink(String detailLink) {
			this.detailLink = detailLink;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		private String orderId;

		public String getManagerId() {
			return managerId;
		}

		public void setManagerId(String managerId) {
			this.managerId = managerId;
		}

		public String getManagerName() {
			return managerName;
		}

		public void setManagerName(String managerName) {
			this.managerName = managerName;
		}

		public String getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(String orderDate) {
			this.orderDate = orderDate;
		}

		public String getOrderAmount() {
			return orderAmount;
		}

		public void setOrderAmount(String orderAmount) {
			this.orderAmount = orderAmount;
		}

	}

	public List<SearchResult> getSearchResultList() {
		return searchResultList;
	}

	public void setSearchResultList(List<SearchResult> searchResultList) {
		this.searchResultList = searchResultList;
	}

}
