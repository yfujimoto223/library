package com.example.demo.controller.user_menu.message;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.controller.common.AbstractBean;
import com.example.demo.data.dto.予約者Dto;
import com.example.demo.data.dto.管理者Dto;
import com.example.demo.data.dto.管理者から利用者へのメッセージDto;

public class MessageBean extends AbstractBean {

	/**選択チェックボックス**/
	private List<String> Checks = new ArrayList<>();

	public List<String> getChecks() {
		return Checks;
	}

	public void setChecks(List<String> checks) {
		Checks = checks;
	}

	/**検索結果**/
	private List<SearchResult> searchResultList = new ArrayList<SearchResult>();

	public void factorySearchResultList(List<管理者から利用者へのメッセージDto> メッセージリスト, List<管理者Dto> 管理者リスト, 予約者Dto 予約者) {
		for (管理者から利用者へのメッセージDto tmp : メッセージリスト) {
			管理者Dto 管理者 = 管理者リスト.stream().filter(a -> a.get管理者ID().equals(tmp.get管理者ID())).findFirst().orElse(null);
			SearchResult searchResult = new SearchResult();
			searchResult.setMessageId(tmp.getメッセージID().toString());
			searchResult.setManagerName(管理者.get名前());
			searchResult.setMessage(tmp.get内容());
			searchResult.setSendDate(tmp.get登録日時().toString());
			this.searchResultList.add(searchResult);
		}

	}

	public class SearchResult {

		private String messageId;
		private String managerName;
		private String message;
		private String sendDate;

		public String getMessageId() {
			return messageId;
		}

		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}

		public String getManagerName() {
			return managerName;
		}

		public void setManagerName(String managerName) {
			this.managerName = managerName;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getSendDate() {
			return sendDate;
		}

		public void setSendDate(String sendDate) {
			this.sendDate = sendDate;
		}

	}

	public List<SearchResult> getSearchResultList() {
		return searchResultList;
	}

	public void setSearchResultList(List<SearchResult> searchResultList) {
		this.searchResultList = searchResultList;
	}

}
