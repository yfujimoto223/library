package com.example.demo.domain.orderedproduct;

import java.util.List;

import com.example.demo.data.dto.注文Dto;
import com.example.demo.data.dto.注文明細Dto;
import com.example.demo.data.service.書籍DtoService;
import com.example.demo.data.service.注文DtoService;
import com.example.demo.data.service.注文明細DtoService;

class 注文書籍Specification {
	/**発注依頼された商品が既に納品済みか確認**/
	boolean 発注依頼された商品が既に納品済みか確認(Long 注文Id, 注文DtoService 注文DtoService, 注文明細DtoService 注文明細DtoService,
			書籍DtoService 書籍DtoService)
			throws Exception {
		注文Dto 注文 = 注文DtoService.findOne(注文Id).orElse(null);
		if (注文 == null) {
			return false;
		}
		List<注文明細Dto> 注文明細リスト = 注文明細DtoService.findAll().stream().filter(a -> a.get注文ID().equals(注文.get注文ID()))
				.toList();
		if (注文明細リスト.size() == 0) {
			return false;
		}

		//注文商品が既に納品済みが確認
		int 納品カウント = 0;
		for (注文明細Dto 注文明細 : 注文明細リスト) {
			if (書籍DtoService.findOneTitleName(注文明細.get書名()).stream().count() > 0) {
				納品カウント++;
			}
		}
		//既に納品済みの
		if (納品カウント == 注文明細リスト.size()) {
			return true;
		}

		return false;
	}
}
