package com.example.demo.aop;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class SessionChekExceptionController {
	//中略(全文は下記参考)
	/**
	 * SessionCheckException発生時に強制的にセッションチェックエラーページへ飛ばす
	 */
	@ExceptionHandler(SessionCheckException.class)
	public String sessionCheckExceptionHandler(SessionCheckException e, Model model) {
		return "session_check_error";
	}
}
