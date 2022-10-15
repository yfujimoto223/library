package com.example.demo.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.controller.common.CommontBean;

@Component
@Aspect
public class SessionCheckAop {
	@Autowired
	HttpSession session;

	/**セッションにログイン情報が含まれているかチェックし、セッションエラーが発生した場合、SessionCheckException例外をスロー
	 * @throws SessionCheckException **/
	@Before("execution(* *..*.*Controller.*(..))")
	public void sessionCheck(JoinPoint joinPoint) throws SessionCheckException {
		//セッションにログイン情報が含まれているかチェック
		CommontBean bean = (CommontBean) session.getAttribute("commontBean");
		Object checkClass = joinPoint.getTarget();
		if (checkClass instanceof SessionCheckInterface) {
			if (bean == null) {
				throw new SessionCheckException();
			}

		}

	}

}
