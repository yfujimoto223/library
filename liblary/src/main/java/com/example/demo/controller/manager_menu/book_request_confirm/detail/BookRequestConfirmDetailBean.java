package com.example.demo.controller.manager_menu.book_request_confirm.detail;

import java.util.List;

import com.example.demo.controller.common.AbstractBean;
import com.example.demo.controller.common.CommontBean;
import com.example.demo.controller.common.CommontBean.Cart;

public class BookRequestConfirmDetailBean extends AbstractBean {
	/**キャッシュ情報を画面に表示**/
	private CommontBean commontBean;

	public CommontBean getCommontBean() {
		return commontBean;
	}

	public void setCommontBean(CommontBean commontBean) {
		this.commontBean = commontBean;
	}

	public List<Cart> getReuestOrederList() {
		return this.commontBean.getReuestOrederList();
	}

}
