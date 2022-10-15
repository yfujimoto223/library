package com.example.demo.controller.common;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractController {
	private static final String sessionSetKey = "commontBean";

	/**入場許可トークンが存在するかチェックし、存在しない場合はエラー画面へ遷移する例外をスロー
	 * @return 
	 * @throws Exception **/
	public void tokenCheck(HttpSession session) throws Exception {
		CommontBean bean = (CommontBean) session.getAttribute(sessionSetKey);
		if (bean.isToken()) {
			throw new Exception();
		}
	}

	/**セッション情報を取得する
	 * @return 
	 * @throws Exception **/
	public CommontBean getSession(HttpSession session) {
		return (CommontBean) session.getAttribute(sessionSetKey);
	}

	/**セッション情報を設定する**/
	public void setSession(HttpSession session, CommontBean bean) {
		session.setAttribute(sessionSetKey, bean);
	}

	/**セッション情報を破棄する **/
	public void deleteSession(HttpSession session) {
		session.invalidate();
	}

	/**遷移する画面を設定 **/
	protected ModelAndView setMovePageConf(ModelAndView modelAndView,String moveUrl) {
		modelAndView.setViewName(moveUrl);
		return modelAndView;
	}
}
