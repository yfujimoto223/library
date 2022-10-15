package com.example.demo.domain.orderedproduct;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.data.dto.注文Dto;
import com.example.demo.data.dto.注文明細Dto;
import com.example.demo.data.dto.管理者Dto;

/**貸出状況Aggregate**/
@Service
public class 注文書籍ValueObject {
	private List<注文一覧> 注文構造体 = new ArrayList<注文一覧>();
	private boolean 納品済みフラグ;

	public boolean is納品済みフラグ() {
		return 納品済みフラグ;
	}

	public void set納品済みフラグ(boolean 納品済みフラグ) {
		this.納品済みフラグ = 納品済みフラグ;
	}

	public void 注文書籍を追加(注文Dto 注文, List<注文明細Dto> 注文明細, 管理者Dto 注文者) {
		this.注文構造体.add(new 注文一覧(注文, 注文明細, 注文者));
	}

	public List<注文Dto> 注文情報を取得する() {
		return this.注文構造体.stream().map(a -> a.注文オブジェクト).collect(Collectors.toList());
	}

	public 注文Dto 注文情報を取得する(Long 注文id) {
		return this.注文構造体.stream().filter(a -> a.get注文オブジェクト().get注文ID().equals(注文id)).findFirst()
				.orElse(null).get注文オブジェクト();
	}

	public List<注文明細Dto> 注文明細情報を取得する(Long 注文id) {
		return this.注文構造体.stream().filter(a -> a.get注文オブジェクト().get注文ID().equals(注文id)).findFirst()
				.orElse(null).get注文オブジェクトリスト();
	}

	public 管理者Dto 注文者情報を取得する(Long 管理者id) {
		return this.注文構造体.stream().filter(a -> a.get注文者オブジェクト().get管理者ID().equals(管理者id)).findFirst()
				.orElse(null).get注文者オブジェクト();
	}

	private class 注文一覧 {
		private 注文Dto 注文オブジェクト;
		private List<注文明細Dto> 注文オブジェクトリスト;
		private 管理者Dto 注文者オブジェクト;

		private 注文一覧(注文Dto 注文, List<注文明細Dto> 注文明細, 管理者Dto 注文者) {
			this.注文オブジェクト = 注文;
			this.注文オブジェクトリスト = 注文明細;
			this.注文者オブジェクト = 注文者;
		}

		private 注文Dto get注文オブジェクト() {
			return 注文オブジェクト;
		}

		private List<注文明細Dto> get注文オブジェクトリスト() {
			return 注文オブジェクトリスト;
		}

		private 管理者Dto get注文者オブジェクト() {
			return 注文者オブジェクト;
		}

	}
}
