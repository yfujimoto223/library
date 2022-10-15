package com.example.demo.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data.dto.貸出状況Dto;
import com.example.demo.data.repository.貸出状況Repository;

@Service
@Transactional(rollbackFor = Exception.class)
public class 貸出状況DtoService {
	@Autowired
	private 貸出状況Repository 貸出状況Repository;

	/** 予約IDを検索キーとして貸出状況DBを検索**/
	public Optional<貸出状況Dto> findOne(Long 予約ID) {
		return 貸出状況Repository.findById(予約ID);
	}

	/**予約者IDを検索キーとして貸出状況DBを検索**/
	public List<貸出状況Dto> findBy予約者Id(Long 予約者Id) {
		return 貸出状況Repository.findAll().stream().filter(a -> a.get予約者ID().equals(予約者Id)).toList();
	}

	/**書籍IDを検索キーとして貸出状況DBを検索**/
	public List<貸出状況Dto> findBy書籍Id(Long 書籍Id) {
		return 貸出状況Repository.findAll().stream().filter(a -> a.get書籍ID().equals(書籍Id)).toList();

	}

	/**全データを抽出**/
	public List<貸出状況Dto> findAll() {
		return 貸出状況Repository.findAll();

	}

	/**DBへ保存**/
	public 貸出状況Dto save(貸出状況Dto 貸出状況Dto) {
		return 貸出状況Repository.save(貸出状況Dto);
	}

	/**DBを更新**/
	public 貸出状況Dto update(貸出状況Dto 貸出状況Dto) {
		return 貸出状況Repository.save(貸出状況Dto);
	}

	/**DBを削除**/
	public void delete(貸出状況Dto 貸出状況Dto) {
		貸出状況Repository.delete(貸出状況Dto);
	}

}
